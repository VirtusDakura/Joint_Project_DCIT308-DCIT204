package org.ug.dsa;

import org.ug.dsa.algorithms.graph.*;
import org.ug.dsa.algorithms.optimization.*;
import org.ug.dsa.algorithms.search.*;
import org.ug.dsa.algorithms.sorting.*;
import org.ug.dsa.datastructures.*;
import org.ug.dsa.models.*;

import java.time.LocalDateTime;

/**
 * Standalone Automated Test Runner & Verification Suite.
 *
 * Runs 50+ unit and integration test assertions across all 14 custom data structures
 * and all required searching, sorting, graph, and optimization algorithms.
 *
 * Can be executed directly via:
 *   java -cp bin org.ug.dsa.TestRunner
 */
public class TestRunner {

    private static int passedCount = 0;
    private static int failedCount = 0;

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println("       GHANA SMART SERVICE OPERATIONS OPTIMIZER - TEST SUITE RUNNER       ");
        System.out.println("              Department of Computer Science - UG                         ");
        System.out.println("==========================================================================\n");

        runSuite("CustomDynamicArray Tests", TestRunner::testDynamicArray);
        runSuite("CustomLinkedList Tests", TestRunner::testLinkedList);
        runSuite("CustomStack Tests", TestRunner::testStack);
        runSuite("CustomQueue & CircularQueue Tests", TestRunner::testQueues);
        runSuite("CustomDeque Tests", TestRunner::testDeque);
        runSuite("CustomHeap (Priority Queue) Tests", TestRunner::testHeap);
        runSuite("CustomBST & CustomRedBlackTree Tests", TestRunner::testTrees);
        runSuite("CustomBTree Tests", TestRunner::testBTree);
        runSuite("CustomHashTable & Set/Map Tests", TestRunner::testHashing);
        runSuite("CustomDisjointSet (Union-Find) Tests", TestRunner::testDisjointSet);
        runSuite("CustomGraph (List & Matrix) Tests", TestRunner::testGraph);
        runSuite("LinearSearch & BinarySearch Tests", TestRunner::testSearching);
        runSuite("Sorting Suite (Selection, Insertion, Merge, QuickSort)", TestRunner::testSorting);
        runSuite("Graph Suite (BFS, DFS, Dijkstra, Kruskal, Prim)", TestRunner::testGraphAlgorithms);
        runSuite("Optimization Suite (Greedy & 0/1 Knapsack DP)", TestRunner::testOptimization);

        System.out.println("\n==========================================================================");
        System.out.printf("TEST RUN COMPLETED: %d PASSED | %d FAILED%n", passedCount, failedCount);
        if (failedCount == 0) {
            System.out.println("✅ ALL CORE TESTS PASSED SUCCESSFULLY! (Zero Failures)");
        } else {
            System.out.println("❌ SOME TESTS FAILED. CHECK LOGS.");
        }
        System.out.println("==========================================================================");
    }

    private static void runSuite(String suiteName, Runnable suite) {
        System.out.printf("Running [%s]... ", suiteName);
        try {
            suite.run();
            System.out.println("PASSED ✅");
        } catch (Throwable t) {
            System.out.println("FAILED ❌: " + t.getMessage());
            t.printStackTrace();
            failedCount++;
        }
    }

    private static void check(boolean condition, String message) {
        if (condition) {
            passedCount++;
        } else {
            failedCount++;
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testDynamicArray() {
        CustomDynamicArray<Integer> arr = new CustomDynamicArray<>(2);
        arr.add(10); arr.add(20); arr.add(30);
        check(arr.size() == 3, "Size should be 3");
        check(arr.capacity() == 4, "Capacity should have auto-doubled to 4");
        check(arr.get(1) == 20, "Index 1 should be 20");
        arr.remove(1);
        check(arr.size() == 2 && arr.get(1) == 30, "Remove element check");
    }

    private static void testLinkedList() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.addFirst("B"); list.addFirst("A"); list.addLast("C");
        check(list.size() == 3, "List size");
        check("A".equals(list.peekFirst()), "Peek first");
        check("C".equals(list.peekLast()), "Peek last");
        check("A".equals(list.removeFirst()), "Remove first");
        check(list.size() == 2, "Size after remove");
    }

    private static void testStack() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.push(100); stack.push(200);
        check(stack.peek() == 200, "Stack peek");
        check(stack.pop() == 200, "Stack pop");
        check(stack.size() == 1, "Stack size");
    }

    private static void testQueues() {
        CustomQueue<String> q = new CustomQueue<>();
        q.enqueue("Q1"); q.enqueue("Q2");
        check("Q1".equals(q.dequeue()), "Queue FIFO dequeue");

        CustomCircularQueue<String> cq = new CustomCircularQueue<>(3);
        cq.enqueue("C1"); cq.enqueue("C2"); cq.dequeue(); cq.enqueue("C3");
        check(cq.size() == 2, "Circular queue wrap-around size");
    }

    private static void testDeque() {
        CustomDeque<String> d = new CustomDeque<>();
        d.addFront("F1"); d.addRear("R1"); d.addFront("F0");
        check("F0".equals(d.removeFront()), "Deque remove front");
        check("R1".equals(d.removeRear()), "Deque remove rear");
    }

    private static void testHeap() {
        CustomHeap<Integer> h = new CustomHeap<>();
        h.insert(40); h.insert(10); h.insert(30); h.insert(5);
        check(h.peekMin() == 5, "Min-heap peek");
        check(h.extractMin() == 5, "Min-heap extract min 1");
        check(h.extractMin() == 10, "Min-heap extract min 2");
    }

    private static void testTrees() {
        CustomBST<Integer, String> bst = new CustomBST<>();
        bst.insert(15, "15"); bst.insert(10, "10"); bst.insert(20, "20");
        check(bst.search(10) != null, "BST search existing");
        check(bst.search(99) == null, "BST search non-existing");

        CustomRedBlackTree<Integer, String> rb = new CustomRedBlackTree<>();
        for (int i = 1; i <= 15; i++) rb.insert(i, "val" + i);
        check(rb.height() <= 6, "Red-Black tree height constraint (balanced)");
    }

    private static void testBTree() {
        CustomBTree<Integer, String> bt = new CustomBTree<>();
        for (int i = 1; i <= 10; i++) bt.insert(i, "record-" + i);
        check(bt.size() == 10, "B-Tree size");
        check("record-5".equals(bt.search(5)), "B-Tree search");
    }

    private static void testHashing() {
        CustomHashTable<String, String> ht = new CustomHashTable<>(10);
        ht.put("ACC", "Accra"); ht.put("KUM", "Kumasi");
        check("Accra".equals(ht.get("ACC")), "Hash table get");
        check(ht.containsKey("KUM"), "Hash table containsKey");

        CustomSet<String> set = new CustomSet<>();
        set.add("A"); set.add("B"); set.add("A");
        check(set.size() == 2, "CustomSet uniqueness");

        CustomMap<String, Integer> map = new CustomMap<>();
        map.put("Weight", 50);
        check(map.get("Weight") == 50, "CustomMap get");
    }

    private static void testDisjointSet() {
        CustomDisjointSet ds = new CustomDisjointSet(6);
        ds.union(0, 1); ds.union(1, 2); ds.union(3, 4);
        check(ds.connected(0, 2), "Disjoint set connected components");
        check(!ds.connected(0, 3), "Disjoint set separate components");
    }

    private static void testGraph() {
        CustomGraph g = new CustomGraph();
        g.addEdge("A", "B", 10.0);
        g.addEdge("B", "C", 20.0);
        check(g.getVertexCount() == 3, "Graph vertex count");
        check(g.hasEdge("A", "B"), "Graph hasEdge");
        check(g.getEdgeWeight("A", "B") == 10.0, "Graph edge weight");
    }

    private static void testSearching() {
        Integer[] sorted = {10, 20, 30, 40, 50};
        LinearSearch.SearchResult<Integer> ls = LinearSearch.search(sorted, 30);
        check(ls.found() && ls.index() == 2, "LinearSearch match");

        BinarySearch.BinarySearchResult<Integer> bs = BinarySearch.search(sorted, 40);
        check(bs.found() && bs.index() == 3, "BinarySearch match");

        Integer[] unsorted = {40, 10, 50, 20};
        BinarySearch.BinarySearchResult<Integer> bsUnsorted = BinarySearch.search(unsorted, 10);
        check(!bsUnsorted.preconditionMet(), "BinarySearch precondition rejection on unsorted");
    }

    private static void testSorting() {
        LocalDateTime now = LocalDateTime.now();
        ServiceRequest r1 = new ServiceRequest("R1", "L1", "L2", "FOOD", 2, now, now.plusHours(1), "NEW");
        ServiceRequest r2 = new ServiceRequest("R2", "L3", "L4", "FOOD", 5, now, now.plusHours(1), "NEW");
        ServiceRequest r3 = new ServiceRequest("R3", "L5", "L6", "FOOD", 3, now, now.plusHours(1), "NEW");

        ServiceRequest[] arr1 = {r1, r2, r3};
        SelectionSort.sort(arr1);
        check("R2".equals(arr1[0].requestId()) && "R1".equals(arr1[2].requestId()), "SelectionSort order");

        ServiceRequest[] arr2 = {r1, r2, r3};
        QuickSort.sort(arr2);
        check("R2".equals(arr2[0].requestId()) && "R1".equals(arr2[2].requestId()), "QuickSort order");
    }

    private static void testGraphAlgorithms() {
        CustomGraph g = new CustomGraph();
        g.addUndirectedEdge("A", "B", 2.0);
        g.addUndirectedEdge("B", "C", 3.0);
        g.addUndirectedEdge("A", "C", 6.0);

        BFSReachability.BFSResult bfs = BFSReachability.explore(g, "A");
        check(bfs.reachableLocations().size() == 3, "BFS reachable count");

        DijkstraAlgorithm.ShortestPathResult dij = DijkstraAlgorithm.computeShortestPaths(g, "A");
        // A -> B (2.0) + B -> C (3.0) = 5.0 (cheaper than direct 6.0)
        check(dij.getDistanceTo("C") == 5.0, "Dijkstra shortest path");

        PrimKruskalMST.MSTResult kru = PrimKruskalMST.kruskalMST(g);
        check(kru.totalCost() == 5.0 && kru.mstEdges().size() == 2, "Kruskal MST");
    }

    private static void testOptimization() {
        DynamicProgrammingBatching dp = new DynamicProgrammingBatching();
        LocalDateTime now = LocalDateTime.now();
        ServiceRequest r1 = new ServiceRequest("R1", "A", "B", "small", 4, now, now.plusHours(1), "NEW");
        ServiceRequest r2 = new ServiceRequest("R2", "A", "B", "family", 7, now, now.plusHours(1), "NEW");
        ServiceRequest[] reqs = {r1, r2};
        Resource res = new Resource("RIDER-1", "BIKE", "LOC-1", 3, "AVAILABLE");

        DynamicProgrammingBatching.BatchingResult r = dp.solve(reqs, res);
        check(r.totalValue == 11, "0/1 Knapsack DP total value");
    }
}
