package org.ug.dsa.services;

import org.ug.dsa.algorithms.graph.BFSReachability;
import org.ug.dsa.algorithms.graph.DFSCycleDetection;
import org.ug.dsa.algorithms.graph.DijkstraAlgorithm;
import org.ug.dsa.algorithms.graph.PrimKruskalMST;
import org.ug.dsa.algorithms.search.BinarySearch;
import org.ug.dsa.algorithms.search.LinearSearch;
import org.ug.dsa.algorithms.sorting.InsertionSort;
import org.ug.dsa.algorithms.sorting.MergeSort;
import org.ug.dsa.algorithms.sorting.QuickSort;
import org.ug.dsa.algorithms.sorting.SelectionSort;
import org.ug.dsa.database.DatabaseManager;
import org.ug.dsa.datastructures.CustomBST;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomHashTable;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.datastructures.CustomRedBlackTree;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Handles recording algorithm runtime metrics, running benchmarks, and exporting data.
 */
public class ReportingService {

    public ReportingService() {
    }

    /**
     * Inserts an empirical algorithm run record into the database.
     */
    public boolean logAlgorithmRun(String algorithmName, int inputSize, long timeNs, long memoryKb) {
        String runId = "RUN-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 1000);
        String sql = "INSERT INTO algorithm_runs (runId, algorithmName, inputSize, timeNs, memoryKb, dateRun) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, runId);
            ps.setString(2, algorithmName);
            ps.setInt(3, inputSize);
            ps.setLong(4, timeNs);
            ps.setLong(5, memoryKb);
            ps.setString(6, LocalDateTime.now().toString());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Database might not be initialized yet; fallback silently
            return false;
        }
    }

    /**
     * Exports all algorithm benchmark runs from the database into a CSV file.
     */
    public boolean exportRunsToCSV(String outputPath) {
        String sql = "SELECT runId, algorithmName, inputSize, timeNs, memoryKb, dateRun FROM algorithm_runs ORDER BY algorithmName, inputSize";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {

            writer.println("runId,algorithmName,inputSize,timeNs,timeMs,memoryKb,dateRun");
            int count = 0;
            while (rs.next()) {
                long ns = rs.getLong("timeNs");
                double ms = ns / 1_000_000.0;
                writer.printf("%s,%s,%d,%d,%.4f,%d,%s%n",
                        rs.getString("runId"),
                        rs.getString("algorithmName"),
                        rs.getInt("inputSize"),
                        ns,
                        ms,
                        rs.getLong("memoryKb"),
                        rs.getString("dateRun"));
                count++;
            }
            System.out.printf("Successfully exported %d benchmark records to %s%n", count, outputPath);
            return true;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to export algorithm runs to CSV: " + e.getMessage());
            return false;
        }
    }

    /**
     * 1. Search Benchmark: Linear Search vs Binary Search.
     */
    public void runSearchBenchmark(int[] sizes) {
        System.out.println("\n=== EXPERIMENT 1: SEARCHING ALGORITHMS (Linear vs Binary Search) ===");
        System.out.printf("%-10s | %-15s | %-15s | %-15s%n", "Size (n)", "Linear Time (ns)", "Binary Time (ns)", "Speedup Factor");
        System.out.println("-".repeat(65));

        Random rng = new Random(42);

        for (int size : sizes) {
            Integer[] arr = new Integer[size];
            for (int i = 0; i < size; i++) {
                arr[i] = i * 2;
            }
            int target = arr[rng.nextInt(size)];

            // Linear Search timing (averaged over 5 runs)
            long linearTotal = 0;
            for (int r = 0; r < 5; r++) {
                long s = System.nanoTime();
                LinearSearch.search(arr, target);
                linearTotal += (System.nanoTime() - s);
            }
            long linearAvg = linearTotal / 5;

            // Binary Search timing (averaged over 5 runs)
            long binaryTotal = 0;
            for (int r = 0; r < 5; r++) {
                long s = System.nanoTime();
                BinarySearch.search(arr, target);
                binaryTotal += (System.nanoTime() - s);
            }
            long binaryAvg = binaryTotal / 5;

            double speedup = (binaryAvg > 0) ? (double) linearAvg / binaryAvg : 1.0;
            System.out.printf("%-10d | %-15d | %-15d | %-15.1fx%n", size, linearAvg, binaryAvg, speedup);

            logAlgorithmRun("LinearSearch", size, linearAvg, getEstimatedMemoryKb());
            logAlgorithmRun("BinarySearch", size, binaryAvg, getEstimatedMemoryKb());
        }
    }

    /**
     * 2. Sorting Benchmark: Selection vs Insertion vs Merge vs QuickSort.
     */
    public void runSortingBenchmark(int[] sizes) {
        System.out.println("\n=== EXPERIMENT 2: SORTING ALGORITHMS COMPARISON ===");
        System.out.printf("%-8s | %-12s | %-12s | %-12s | %-12s%n", "Size (n)", "Selection(ms)", "Insertion(ms)", "Merge(ms)", "Quick(ms)");
        System.out.println("-".repeat(70));

        Random rng = new Random(101);

        for (int size : sizes) {
            Integer[] base = new Integer[size];
            for (int i = 0; i < size; i++) {
                base[i] = rng.nextInt(100_000);
            }

            // Selection Sort (skip for large n > 10,000 to avoid hanging)
            double selMs = 0.0;
            if (size <= 10_000) {
                Integer[] arr1 = base.clone();
                long s = System.nanoTime();
                SelectionSort.sort(arr1);
                selMs = (System.nanoTime() - s) / 1_000_000.0;
                logAlgorithmRun("SelectionSort", size, (long) (selMs * 1_000_000), getEstimatedMemoryKb());
            }

            // Insertion Sort
            double insMs = 0.0;
            if (size <= 10_000) {
                Integer[] arr2 = base.clone();
                long s = System.nanoTime();
                // Insertion sort on Integer[]
                for (int i = 1; i < arr2.length; i++) {
                    Integer key = arr2[i];
                    int j = i - 1;
                    while (j >= 0 && arr2[j].compareTo(key) > 0) {
                        arr2[j + 1] = arr2[j];
                        j--;
                    }
                    arr2[j + 1] = key;
                }
                insMs = (System.nanoTime() - s) / 1_000_000.0;
                logAlgorithmRun("InsertionSort", size, (long) (insMs * 1_000_000), getEstimatedMemoryKb());
            }

            // Merge Sort
            Integer[] arr3 = base.clone();
            long s3 = System.nanoTime();
            MergeSortGeneric(arr3);
            double mergeMs = (System.nanoTime() - s3) / 1_000_000.0;
            logAlgorithmRun("MergeSort", size, (long) (mergeMs * 1_000_000), getEstimatedMemoryKb());

            // QuickSort
            Integer[] arr4 = base.clone();
            long s4 = System.nanoTime();
            QuickSort.sort(arr4);
            double quickMs = (System.nanoTime() - s4) / 1_000_000.0;
            logAlgorithmRun("QuickSort", size, (long) (quickMs * 1_000_000), getEstimatedMemoryKb());

            System.out.printf("%-8d | %-12.2f | %-12.2f | %-12.2f | %-12.2f%n", size, selMs, insMs, mergeMs, quickMs);
        }
    }

    /**
     * 3. Hash Table Benchmark: Load factor vs collisions.
     */
    public void runHashTableBenchmark() {
        System.out.println("\n=== EXPERIMENT 3: HASH TABLE LOAD FACTOR & COLLISION ANALYSIS ===");
        System.out.printf("%-10s | %-12s | %-12s | %-12s | %-12s%n", "Capacity", "Keys Stored", "Load Factor", "Collisions", "Put Time(ms)");
        System.out.println("-".repeat(70));

        int capacity = 1000;
        int[] keyCounts = {200, 500, 750, 1000, 1500, 2000};

        for (int count : keyCounts) {
            CustomHashTable<String, String> table = new CustomHashTable<>(capacity);
            long s = System.nanoTime();
            for (int i = 0; i < count; i++) {
                table.put("LOC_GH_" + i, "Location Data Payload " + i);
            }
            long elapsed = System.nanoTime() - s;
            double ms = elapsed / 1_000_000.0;

            System.out.printf("%-10d | %-12d | %-12.2f | %-12d | %-12.2f%n",
                    capacity, table.size(), table.loadFactor(), table.collisionCount(), ms);
            logAlgorithmRun("HashTablePut", count, elapsed, getEstimatedMemoryKb());
        }
    }

    /**
     * 4. BST vs Red-Black Tree Benchmark.
     */
    public void runTreeBenchmark() {
        System.out.println("\n=== EXPERIMENT 4: BST VS RED-BLACK BALANCED TREE COMPARISON ===");
        System.out.printf("%-12s | %-12s | %-15s | %-15s%n", "Input Type", "Key Count", "Plain BST Height", "RB-Tree Height");
        System.out.println("-".repeat(65));

        int[] counts = {15, 31, 63, 127, 255};
        for (int n : counts) {
            CustomBST<Integer, String> bst = new CustomBST<>();
            CustomRedBlackTree<Integer, String> rbTree = new CustomRedBlackTree<>();

            // Sequential insertion (Worst-case for BST)
            for (int i = 1; i <= n; i++) {
                bst.insert(i, "val-" + i);
                rbTree.insert(i, "val-" + i);
            }

            System.out.printf("%-12s | %-12d | %-15d | %-15d%n", "Sequential", n, bst.height(), rbTree.height());
            logAlgorithmRun("BSTSequential", n, bst.height(), getEstimatedMemoryKb());
            logAlgorithmRun("RBTreeSequential", n, rbTree.height(), getEstimatedMemoryKb());
        }
    }

    /**
     * 5. Priority Queue / Heap Benchmark.
     */
    public void runHeapBenchmark() {
        System.out.println("\n=== EXPERIMENT 5: HEAP / PRIORITY QUEUE DISPATCH ENGINE ===");
        System.out.printf("%-12s | %-15s | %-15s%n", "Request Count", "Insert Time (ms)", "ExtractMin Time (ms)");
        System.out.println("-".repeat(50));

        int[] sizes = {500, 1000, 5000, 10000};
        Random rng = new Random(77);

        for (int size : sizes) {
            CustomHeap<Integer> heap = new CustomHeap<>();

            long s1 = System.nanoTime();
            for (int i = 0; i < size; i++) {
                heap.insert(rng.nextInt(1000));
            }
            double insertMs = (System.nanoTime() - s1) / 1_000_000.0;

            long s2 = System.nanoTime();
            while (!heap.isEmpty()) {
                heap.extractMin();
            }
            double extractMs = (System.nanoTime() - s2) / 1_000_000.0;

            System.out.printf("%-12d | %-15.2f | %-15.2f%n", size, insertMs, extractMs);
            logAlgorithmRun("HeapInsert", size, (long) (insertMs * 1_000_000), getEstimatedMemoryKb());
            logAlgorithmRun("HeapExtract", size, (long) (extractMs * 1_000_000), getEstimatedMemoryKb());
        }
    }

    /**
     * 6. Graph Algorithms Benchmark.
     */
    public void runGraphBenchmark(CustomGraph graph) {
        System.out.println("\n=== EXPERIMENT 6: GRAPH ALGORITHMS (BFS, DFS, Dijkstra, Kruskal, Prim) ===");
        System.out.printf("%-15s | %-15s | %-15s%n", "Algorithm", "Vertices/Edges", "Runtime (ms)");
        System.out.println("-".repeat(50));

        if (graph == null || graph.getVertexCount() == 0) {
            System.out.println("Graph is empty. Load dataset first.");
            return;
        }

        String source = graph.getAllVertices().get(0);
        int v = graph.getVertexCount();
        int e = graph.getEdgeCount();
        String sizeStr = String.format("%dV / %dE", v, e);

        // BFS
        long sBfs = System.nanoTime();
        BFSReachability.explore(graph, source);
        double bfsMs = (System.nanoTime() - sBfs) / 1_000_000.0;
        System.out.printf("%-15s | %-15s | %-15.3f%n", "BFS", sizeStr, bfsMs);

        // DFS
        long sDfs = System.nanoTime();
        DFSCycleDetection.analyzeGraph(graph);
        double dfsMs = (System.nanoTime() - sDfs) / 1_000_000.0;
        System.out.printf("%-15s | %-15s | %-15.3f%n", "DFS", sizeStr, dfsMs);

        // Dijkstra
        long sDij = System.nanoTime();
        DijkstraAlgorithm.computeShortestPaths(graph, source);
        double dijMs = (System.nanoTime() - sDij) / 1_000_000.0;
        System.out.printf("%-15s | %-15s | %-15.3f%n", "Dijkstra", sizeStr, dijMs);

        // Kruskal
        long sKru = System.nanoTime();
        PrimKruskalMST.kruskalMST(graph);
        double kruMs = (System.nanoTime() - sKru) / 1_000_000.0;
        System.out.printf("%-15s | %-15s | %-15.3f%n", "Kruskal MST", sizeStr, kruMs);

        // Prim
        long sPri = System.nanoTime();
        PrimKruskalMST.primMST(graph, source);
        double priMs = (System.nanoTime() - sPri) / 1_000_000.0;
        System.out.printf("%-15s | %-15s | %-15.3f%n", "Prim MST", sizeStr, priMs);

        logAlgorithmRun("BFS", v, (long) (bfsMs * 1_000_000), getEstimatedMemoryKb());
        logAlgorithmRun("DFS", v, (long) (dfsMs * 1_000_000), getEstimatedMemoryKb());
        logAlgorithmRun("Dijkstra", v, (long) (dijMs * 1_000_000), getEstimatedMemoryKb());
        logAlgorithmRun("Kruskal", v, (long) (kruMs * 1_000_000), getEstimatedMemoryKb());
        logAlgorithmRun("Prim", v, (long) (priMs * 1_000_000), getEstimatedMemoryKb());
    }

    private static <T extends Comparable<? super T>> void MergeSortGeneric(T[] arr) {
        if (arr.length < 2) return;
        T[] temp = (T[]) new Comparable[arr.length];
        mergeSortRec(arr, temp, 0, arr.length - 1);
    }

    private static <T extends Comparable<? super T>> void mergeSortRec(T[] arr, T[] temp, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortRec(arr, temp, left, mid);
        mergeSortRec(arr, temp, mid + 1, right);
        mergeGeneric(arr, temp, left, mid, right);
    }

    private static <T extends Comparable<? super T>> void mergeGeneric(T[] arr, T[] temp, int left, int mid, int right) {
        for (int i = left; i <= right; i++) temp[i] = arr[i];
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        while (i <= mid) arr[k++] = temp[i++];
        while (j <= right) arr[k++] = temp[j++];
    }

    private long getEstimatedMemoryKb() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024;
    }
}
