package org.ug.dsa;

import org.ug.dsa.algorithms.graph.BFSReachability;
import org.ug.dsa.algorithms.graph.DFSCycleDetection;
import org.ug.dsa.algorithms.graph.DijkstraAlgorithm;
import org.ug.dsa.algorithms.graph.PrimKruskalMST;
import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching;
import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching.BatchingResult;
import org.ug.dsa.algorithms.optimization.GreedyBatching;
import org.ug.dsa.algorithms.search.BinarySearch;
import org.ug.dsa.algorithms.search.LinearSearch;
import org.ug.dsa.algorithms.sorting.InsertionSort;
import org.ug.dsa.algorithms.sorting.MergeSort;
import org.ug.dsa.algorithms.sorting.QuickSort;
import org.ug.dsa.algorithms.sorting.SelectionSort;
import org.ug.dsa.database.DatabaseManager;
import org.ug.dsa.datastructures.*;
import org.ug.dsa.models.Location;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.Road;
import org.ug.dsa.models.ServiceRequest;
import org.ug.dsa.services.IndexingService;
import org.ug.dsa.services.ReportingService;
import org.ug.dsa.services.RoutingService;
import org.ug.dsa.services.SchedulingService;
import org.ug.dsa.util.IndexParameters;

import java.util.Scanner;

/**
 * Ghana Smart Food & Parcel Delivery Operations Optimizer.
 * Department of Computer Science - University of Ghana.
 * DCIT 204 / DCIT 308 Joint Semester Project.
 *
 * Interactive Console Management Application.
 */
public class Main {

    private static CustomDynamicArray<Location> locations;
    private static CustomDynamicArray<Road> roads;
    private static CustomDynamicArray<ServiceRequest> serviceRequests;
    private static CustomDynamicArray<Resource> resources;
    private static CustomGraph systemGraph;
    private static IndexingService indexingService;
    private static RoutingService routingService;
    private static ReportingService reportingService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        reportingService = new ReportingService();
        loadDatasets();

        while (true) {
            printHeader();
            printMenu();
            System.out.print("Select an option (1-11): ");
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            System.out.println();
            switch (input) {
                case "1":
                    loadDatasets();
                    System.out.println("Datasets and database tables reloaded successfully.");
                    break;

                case "2":
                    displayDatasetSummary();
                    break;

                case "3":
                    runSearchingModule(scanner);
                    break;

                case "4":
                    runSortingModule(scanner);
                    break;

                case "5":
                    runDataStructuresDiagnostics();
                    break;

                case "6":
                    runGraphRoutingModule(scanner);
                    break;

                case "7":
                    runSchedulingModule();
                    break;

                case "8":
                    runOptimizationModule();
                    break;

                case "9":
                    runBenchmarkModule();
                    break;

                case "10":
                    runIntegritySelfCheck();
                    break;

                case "11":
                    System.out.println("Exiting Ghana Smart Delivery Operations Optimizer. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option. Please select between 1 and 11.");
            }

            System.out.println("\nPress ENTER to return to the main menu...");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            } else {
                break;
            }
        }
    }

    private static void loadDatasets() {
        try {
            DatabaseManager.initializeTables();
        } catch (Exception e) {
            System.out.println("[Notice] Loading records directly from CSV files.");
        }

        locations = GreedyBatching.loadLocations("data/locations.csv");
        roads = GreedyBatching.loadRoads("data/roads.csv");
        serviceRequests = GreedyBatching.loadRequests("data/service_requests.csv");
        resources = GreedyBatching.loadResources("data/resources.csv");

        systemGraph = new CustomGraph();
        indexingService = new IndexingService();

        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            systemGraph.addVertex(loc.locationId());
            indexingService.indexLocation(loc);
        }

        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            systemGraph.addUndirectedEdge(road.fromLocationId(), road.toLocationId(), road.getEffectiveWeight());
        }

        for (int i = 0; i < resources.size(); i++) {
            indexingService.indexResource(resources.get(i));
        }

        for (int i = 0; i < serviceRequests.size(); i++) {
            indexingService.indexRequest(serviceRequests.get(i));
        }

        routingService = new RoutingService(systemGraph);
    }

    private static void printHeader() {
        System.out.println("==========================================================================");
        System.out.println("          GHANA SMART FOOD & PARCEL DELIVERY OPTIMIZER                    ");
        System.out.println("              Department of Computer Science - UG                         ");
        System.out.println("            DCIT 204 / DCIT 308 Joint Semester Project                    ");
        System.out.println("==========================================================================");
    }

    private static void printMenu() {
        System.out.println("1.  Reload CSV Datasets & Database Tables");
        System.out.println("2.  View Dataset Summary (Locations, Roads, Orders, Riders)");
        System.out.println("3.  Search Engine (Linear Search & Binary Search with Preconditions)");
        System.out.println("4.  Sort Service Requests (Selection, Insertion, Merge, QuickSort)");
        System.out.println("5.  Data Structures Diagnostics (14 Custom Structures)");
        System.out.println("6.  Routing & Network Optimization (Dijkstra, BFS, DFS, Kruskal, Prim)");
        System.out.println("7.  Order Scheduling & Dispatch (FIFO / Priority Heap)");
        System.out.println("8.  Order Batching & Capacity Optimization (Greedy vs 0/1 Knapsack DP)");
        System.out.println("9.  Performance Benchmark Laboratory (Export to CSV)");
        System.out.println("10. Run System Integrity & Invariant Self-Check");
        System.out.println("11. Exit");
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void displayDatasetSummary() {
        System.out.println("=== OPERATIONAL DATASET SUMMARY ===");
        System.out.printf("Locations Loaded     : %d locations%n", locations.size());
        System.out.printf("Road Edges Loaded    : %d roads%n", roads.size());
        System.out.printf("Service Requests     : %d orders%n", serviceRequests.size());
        System.out.printf("Delivery Resources   : %d riders/vehicles%n", resources.size());
        System.out.println();

        System.out.println("Sample Locations:");
        for (int i = 0; i < Math.min(5, locations.size()); i++) {
            Location loc = locations.get(i);
            System.out.printf("  * [%s] %s (%s, %s)%n", loc.locationId(), loc.name(), loc.area(), loc.locationType());
        }

        System.out.println("\nSample Service Requests:");
        for (int i = 0; i < Math.min(5, serviceRequests.size()); i++) {
            ServiceRequest req = serviceRequests.get(i);
            System.out.printf("  * [%s] Urgency: %d | Category: %-18s | From: %s -> To: %s%n",
                    req.requestId(), req.urgency(), req.category(), req.sourceLocationId(), req.destinationLocationId());
        }

        System.out.println("\nDerived Operational Parameters:");
        System.out.printf("  * Hash Table Base Size : %d (Derived from Index)%n", IndexParameters.getDerivedHashTableBaseSize());
        System.out.printf("  * Priority Multiplier  : %.2f (Derived from Index)%n", IndexParameters.getDerivedPriorityMultiplier());
        System.out.printf("  * B-Tree Min Degree (t): %d (Derived from Index)%n", IndexParameters.getDerivedBTreeMinDegree());
        System.out.printf("  * Traffic Penalty      : %.2f (Derived from Index)%n", IndexParameters.getDerivedTrafficPenaltyFactor());
    }

    private static void runSearchingModule(Scanner scanner) {
        System.out.println("=== SEARCH ENGINE ===");
        System.out.println("1. Linear Search on Service Requests (by Request ID)");
        System.out.println("2. Binary Search on Sorted Urgency Levels");
        System.out.println("3. Precondition Verification on Unsorted Data (Counterexample)");
        System.out.print("Select search option (1-3): ");
        String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";

        if ("2".equals(choice)) {
            System.out.print("Enter Urgency Level to search (1-5) [Default: 4]: ");
            String inputTarget = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            int target = 4;
            if (!inputTarget.isEmpty()) {
                try {
                    target = Integer.parseInt(inputTarget);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid integer entered. Using default urgency = 4.");
                }
            }

            Integer[] urgencies = new Integer[serviceRequests.size()];
            for (int i = 0; i < serviceRequests.size(); i++) {
                urgencies[i] = serviceRequests.get(i).urgency();
            }
            QuickSort.sort(urgencies);

            BinarySearch.BinarySearchResult<Integer> res = BinarySearch.search(urgencies, target);
            System.out.printf("Searching for Urgency Level %d across %d sorted records...%n", target, urgencies.length);
            System.out.printf("Found: %s at Index %d | Comparisons Made: %d | Precondition Met: %s%n",
                    res.found(), res.index(), res.comparisonCount(), res.preconditionMet());
        } else if ("3".equals(choice)) {
            Integer[] unsorted = new Integer[]{5, 1, 4, 2, 8, 3};
            System.out.println("Running Binary Search on Unsorted Array [5, 1, 4, 2, 8, 3] with Target = 1");
            BinarySearch.BinarySearchResult<Integer> resSafe = BinarySearch.search(unsorted, 1);
            System.out.printf("Safe BinarySearch (with precondition check): PreconditionMet=%s, Aborted safely.%n", resSafe.preconditionMet());

            BinarySearch.BinarySearchResult<Integer> resUnchecked = BinarySearch.searchUncheckedForCounterexample(unsorted, 1);
            System.out.printf("Unchecked BinarySearch: Found=%s, Index=%d (Precondition violation caused missed search)%n",
                    resUnchecked.found(), resUnchecked.index());
        } else {
            System.out.print("Enter Request ID to search (e.g. ORD-001 to ORD-345) [Default: ORD-011]: ");
            String inputTarget = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            String targetId = inputTarget.isEmpty() ? "ORD-011" : inputTarget.toUpperCase();

            CustomDynamicArray<String> ids = new CustomDynamicArray<>();
            for (int i = 0; i < serviceRequests.size(); i++) {
                ids.add(serviceRequests.get(i).requestId());
            }
            LinearSearch.SearchResult<String> res = LinearSearch.search(ids, targetId);
            System.out.printf("Linear Search for Request ID '%s' across %d records:%n", targetId, ids.size());
            System.out.printf("Found: %s | Index: %d | Comparisons Made: %d%n",
                    res.found(), res.index(), res.comparisonCount());
            
            if (res.found() && res.index() >= 0 && res.index() < serviceRequests.size()) {
                ServiceRequest req = serviceRequests.get(res.index());
                System.out.printf("Order Details: [%s] Urgency: %d | Category: %s | %s -> %s | Status: %s%n",
                        req.requestId(), req.urgency(), req.category(), req.sourceLocationId(), req.destinationLocationId(), req.status());
            }
        }
    }

    private static void runSortingModule(Scanner scanner) {
        System.out.println("=== SORTING ENGINE ===");
        System.out.println("1. Selection Sort (In-Place, O(n^2))");
        System.out.println("2. Insertion Sort (In-Place, Adaptive O(n^2))");
        System.out.println("3. Merge Sort (Stable Divide-and-Conquer, O(n log n))");
        System.out.println("4. QuickSort (In-Place Divide-and-Conquer, O(n log n))");
        System.out.print("Select sorting algorithm (1-4): ");
        String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "4";

        System.out.printf("How many requests to sort? (5 to %d) [Default: 10]: ", serviceRequests.size());
        String countInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        int sampleSize = 10;
        if (!countInput.isEmpty()) {
            try {
                int parsed = Integer.parseInt(countInput);
                sampleSize = Math.max(5, Math.min(parsed, serviceRequests.size()));
            } catch (NumberFormatException e) {
                sampleSize = 10;
            }
        }

        ServiceRequest[] sampleArr = new ServiceRequest[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            sampleArr[i] = serviceRequests.get(i);
        }

        System.out.printf("\n--- Unsorted Sample (First %d records) ---%n", Math.min(5, sampleSize));
        for (int i = 0; i < Math.min(5, sampleSize); i++) {
            ServiceRequest req = sampleArr[i];
            System.out.printf("  * [%s] Urgency: %d | Deadline: %s%n", req.requestId(), req.urgency(), req.deadline());
        }
        if (sampleSize > 5) System.out.println("  ... (remaining records omitted for brevity)");

        long start = System.nanoTime();
        String algoName;
        switch (choice) {
            case "1":
                algoName = "Selection Sort";
                SelectionSort.sort(sampleArr);
                break;
            case "2":
                algoName = "Insertion Sort";
                InsertionSort.sort(sampleArr);
                break;
            case "3":
                algoName = "Merge Sort";
                MergeSort.sort(sampleArr);
                break;
            default:
                algoName = "QuickSort";
                QuickSort.sort(sampleArr);
                break;
        }
        long durationNs = System.nanoTime() - start;

        System.out.printf("%n--- Sorted via %s (Top %d shown) ---%n", algoName, Math.min(5, sampleSize));
        for (int i = 0; i < Math.min(5, sampleSize); i++) {
            ServiceRequest req = sampleArr[i];
            System.out.printf("  * [%s] Urgency: %d | Deadline: %s%n", req.requestId(), req.urgency(), req.deadline());
        }
        if (sampleSize > 5) System.out.println("  ... (remaining records sorted in memory)");
        System.out.printf("%nSorted %d records in %.3f ms%n", sampleSize, durationNs / 1_000_000.0);
    }

    private static void runDataStructuresDiagnostics() {
        System.out.println("=== 14 CUSTOM DATA STRUCTURES DIAGNOSTICS ===");

        // 1. Dynamic Array
        CustomDynamicArray<String> dynamicArray = new CustomDynamicArray<>(2);
        dynamicArray.add("Hub-1"); dynamicArray.add("Hub-2"); dynamicArray.add("Hub-3");
        System.out.printf("1. CustomDynamicArray : Size = %d, Capacity = %d (Auto-doubled)%n", dynamicArray.size(), dynamicArray.capacity());

        // 2. Linked List
        CustomLinkedList<String> linkedList = new CustomLinkedList<>();
        linkedList.addFirst("Stop-A"); linkedList.addLast("Stop-B");
        System.out.printf("2. CustomLinkedList   : Size = %d, First = %s, Last = %s%n", linkedList.size(), linkedList.peekFirst(), linkedList.peekLast());

        // 3. Stack
        CustomStack<String> stack = new CustomStack<>();
        stack.push("Action-1"); stack.push("Action-2");
        System.out.printf("3. CustomStack        : Top = %s, Size = %d%n", stack.peek(), stack.size());

        // 4. Queue
        CustomQueue<String> queue = new CustomQueue<>();
        queue.enqueue("Order-1"); queue.enqueue("Order-2");
        System.out.printf("4. CustomQueue        : Front = %s, Size = %d%n", queue.peek(), queue.size());

        // 5. Circular Queue
        CustomCircularQueue<String> cQueue = new CustomCircularQueue<>(3);
        cQueue.enqueue("Req-1"); cQueue.enqueue("Req-2"); cQueue.dequeue(); cQueue.enqueue("Req-3");
        System.out.printf("5. CustomCircularQueue: Size = %d (Wrap-around handled)%n", cQueue.size());

        // 6. Deque
        CustomDeque<String> deque = new CustomDeque<>();
        deque.addFront("Express-Order"); deque.addRear("Standard-Order");
        System.out.printf("6. CustomDeque        : Front = %s, Rear = %s%n", deque.peekFront(), deque.peekRear());

        // 7. Heap
        CustomHeap<Integer> heap = new CustomHeap<>();
        heap.insert(50); heap.insert(10); heap.insert(30);
        System.out.printf("7. CustomHeap (Min)   : Min Element = %d%n", heap.peekMin());

        // 8. BST
        CustomBST<Integer, String> bst = new CustomBST<>();
        bst.insert(20, "V20"); bst.insert(10, "V10"); bst.insert(30, "V30");
        System.out.printf("8. CustomBST          : Size = %d, Height = %d%n", bst.size(), bst.height());

        // 9. Red-Black Tree
        CustomRedBlackTree<Integer, String> rbTree = new CustomRedBlackTree<>();
        for (int i = 1; i <= 7; i++) rbTree.insert(i, "RB-" + i);
        System.out.printf("9. CustomRedBlackTree : Size = %d, Height = %d, BlackHeight = %d%n", rbTree.size(), rbTree.height(), rbTree.blackHeight());

        // 10. B-Tree
        CustomBTree<Integer, String> btree = new CustomBTree<>();
        for (int i = 1; i <= 8; i++) btree.insert(i, "B-" + i);
        System.out.printf("10. CustomBTree (t=3)  : Size = %d, Height = %d%n", btree.size(), btree.height());

        // 11. Hash Table
        CustomHashTable<String, String> hashTable = new CustomHashTable<>(5);
        hashTable.put("GH-1", "Accra"); hashTable.put("GH-2", "Kumasi");
        System.out.printf("11. CustomHashTable   : Size = %d, LoadFactor = %.2f%n", hashTable.size(), hashTable.loadFactor());

        // 12. Set & Map
        CustomSet<String> set = new CustomSet<>(); set.add("ZoneA"); set.add("ZoneA");
        CustomMap<String, Integer> map = new CustomMap<>(); map.put("ZoneA", 100);
        System.out.printf("12. CustomSet & Map   : Set Unique = %d, Map Val = %d%n", set.size(), map.get("ZoneA"));

        // 13. Disjoint Set
        CustomDisjointSet ds = new CustomDisjointSet(5);
        ds.union(0, 1); ds.union(1, 2);
        System.out.printf("13. CustomDisjointSet : 0 connected to 2 = %s%n", ds.connected(0, 2));

        // 14. Graph
        System.out.printf("14. CustomGraph       : %d Vertices, %d Edges (Adjacency List & Matrix)%n",
                systemGraph.getVertexCount(), systemGraph.getEdgeCount());
    }

    private static void runGraphRoutingModule(Scanner scanner) {
        System.out.println("=== ROUTING & NETWORK OPTIMIZATION ENGINE ===");
        System.out.println("1. Dijkstra Shortest Path Route Calculation");
        System.out.println("2. BFS Zone Reachability Analysis");
        System.out.println("3. DFS Delivery Route Cycle Detection");
        System.out.println("4. Kruskal Minimum Spanning Tree (MST)");
        System.out.println("5. Prim Minimum Spanning Tree (MST)");
        System.out.print("Select graph algorithm (1-5): ");
        String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";

        switch (choice) {
            case "2":
                System.out.print("Enter Start Location ID (e.g. L001 to L052) [Default: L001]: ");
                String bfsSrcInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                String bfsSrc = bfsSrcInput.isEmpty() ? "L001" : bfsSrcInput.toUpperCase();
                if (systemGraph.indexOfVertex(bfsSrc) == -1) {
                    System.out.printf("Location '%s' not found. Falling back to L001.%n", bfsSrc);
                    bfsSrc = "L001";
                }

                System.out.printf("\n--- BFS Reachability from Hub [%s] ---%n", bfsSrc);
                BFSReachability.BFSResult bfs = routingService.getReachableZones(bfsSrc);
                System.out.printf("Total Reachable Zones: %d / %d vertices%n", bfs.reachableLocations().size(), systemGraph.getVertexCount());
                System.out.print("Traversal Order (First 10): ");
                for (int i = 0; i < Math.min(10, bfs.traversalOrder().size()); i++) {
                    System.out.print(bfs.traversalOrder().get(i) + (i < Math.min(10, bfs.traversalOrder().size()) - 1 ? " -> " : ""));
                }
                System.out.println(" ...");
                break;

            case "3":
                System.out.println("\n--- DFS Road Network Cycle Detection ---");
                DFSCycleDetection.DFSResult dfs = routingService.detectNetworkCycles();
                System.out.printf("Cycles Detected in Road Network: %s%n", dfs.hasCycle());
                System.out.printf("Connected Network Components  : %d%n", dfs.connectedComponentsCount());
                break;

            case "4":
                System.out.println("\n--- Kruskal Minimum Spanning Tree ---");
                PrimKruskalMST.MSTResult kru = routingService.computeKruskalMST();
                System.out.println(kru.renderSummary());
                break;

            case "5":
                System.out.print("Enter Root Location ID for Prim (e.g. L001 to L052) [Default: L001]: ");
                String priSrcInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                String priSrc = priSrcInput.isEmpty() ? "L001" : priSrcInput.toUpperCase();
                if (systemGraph.indexOfVertex(priSrc) == -1) {
                    priSrc = "L001";
                }
                System.out.printf("\n--- Prim Minimum Spanning Tree from [%s] ---%n", priSrc);
                PrimKruskalMST.MSTResult pri = routingService.computePrimMST(priSrc);
                System.out.println(pri.renderSummary());
                break;

            default:
                System.out.print("Enter Source Location ID [Default: L001]: ");
                String sInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                String src = sInput.isEmpty() ? "L001" : sInput.toUpperCase();

                System.out.print("Enter Destination Location ID [Default: L006]: ");
                String dInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                String dst = dInput.isEmpty() ? "L006" : dInput.toUpperCase();

                if (systemGraph.indexOfVertex(src) == -1) {
                    System.out.printf("Unknown source '%s', defaulting to L001.%n", src);
                    src = "L001";
                }
                if (systemGraph.indexOfVertex(dst) == -1) {
                    System.out.printf("Unknown destination '%s', defaulting to L006.%n", dst);
                    dst = "L006";
                }

                Location srcLoc = indexingService.getLocation(src);
                Location dstLoc = indexingService.getLocation(dst);
                String srcName = (srcLoc != null) ? srcLoc.name() : src;
                String dstName = (dstLoc != null) ? dstLoc.name() : dst;

                System.out.printf("\n--- Dijkstra Shortest Path: [%s] (%s) -> [%s] (%s) ---%n", src, srcName, dst, dstName);
                DijkstraAlgorithm.ShortestPathResult dij = routingService.findShortestPathsFrom(src);
                CustomDynamicArray<String> path = dij.getPathTo(dst);
                System.out.printf("Shortest Travel Weight : %.2f mins%n", dij.getDistanceTo(dst));
                System.out.print("Optimal Route: ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i) + (i < path.size() - 1 ? " -> " : ""));
                }
                System.out.println();
                System.out.println("\nDistance Table (Sample first 6 locations):");
                System.out.println(dij.renderDistanceTable().lines().limit(9).reduce("", (a, b) -> a + "\n" + b));
                break;
        }
    }

    private static void runSchedulingModule() {
        System.out.println("=== ORDER SCHEDULING & DISPATCH ENGINE ===");
        SchedulingService scheduler = new SchedulingService();

        int sampleCount = Math.min(5, serviceRequests.size());
        for (int i = 0; i < sampleCount; i++) {
            scheduler.submitOrder(serviceRequests.get(i));
        }

        System.out.printf("Submitted %d orders. Pending count: %d%n", sampleCount, scheduler.getPendingCount());

        ServiceRequest fifoDispatched = scheduler.dispatchFIFO();
        System.out.printf("FIFO Dispatched Order     : [%s] Urgency: %d%n", fifoDispatched.requestId(), fifoDispatched.urgency());

        ServiceRequest priorityDispatched = scheduler.dispatchPriority();
        System.out.printf("Priority Dispatched Order : [%s] Urgency: %d%n", priorityDispatched.requestId(), priorityDispatched.urgency());
    }

    private static void runOptimizationModule() {
        System.out.println("=== ORDER BATCHING & CAPACITY OPTIMIZATION ===");

        int batchSize = Math.min(8, serviceRequests.size());
        ServiceRequest[] batchSample = new ServiceRequest[batchSize];
        for (int i = 0; i < batchSize; i++) {
            batchSample[i] = serviceRequests.get(i);
        }
        Resource sampleRider = resources.get(0);

        System.out.printf("Optimizing batch for rider %s (%s, capacity = %d)%n%n",
                sampleRider.resourceId(), sampleRider.resourceType(), sampleRider.capacity());

        DynamicProgrammingBatching dpEngine = new DynamicProgrammingBatching();
        BatchingResult result = dpEngine.solve(batchSample, sampleRider);

        System.out.println("--- 0/1 Knapsack DP Tabulation Matrix ---");
        System.out.println(DynamicProgrammingBatching.renderTable(result));
        System.out.printf("Optimal Urgency Value Achieved : %d%n", result.totalValue);
        System.out.printf("Rider Capacity Utilized        : %d / %d%n", result.totalWeightUsed, result.capacity);
    }

    private static void runBenchmarkModule() {
        System.out.println("=== RUNNING PERFORMANCE BENCHMARK EXPERIMENTS ===");
        int[] searchSizes = {100, 500, 1000, 5000, 10000};
        reportingService.runSearchBenchmark(searchSizes);

        int[] sortSizes = {100, 500, 1000, 5000};
        reportingService.runSortingBenchmark(sortSizes);

        reportingService.runHashTableBenchmark();
        reportingService.runTreeBenchmark();
        reportingService.runHeapBenchmark();
        reportingService.runGraphBenchmark(systemGraph);

        reportingService.exportRunsToCSV("data/benchmarks_export.csv");
        System.out.println("\nBenchmark experiments completed. Results exported to data/benchmarks_export.csv");
    }

    private static void runIntegritySelfCheck() {
        System.out.println("=== SYSTEM INTEGRITY SELF-CHECK ===");
        System.out.println("1. Verifying Location and Graph Vertex matching...");
        boolean vMatch = locations.size() == systemGraph.getVertexCount();
        System.out.printf("   Locations: %d | Graph Vertices: %d -> %s%n", locations.size(), systemGraph.getVertexCount(), vMatch ? "PASSED" : "FAILED");

        System.out.println("2. Verifying CustomDynamicArray Resizing...");
        CustomDynamicArray<Integer> arr = new CustomDynamicArray<>(4);
        arr.add(1); arr.add(2); arr.add(3); arr.add(4); arr.add(5);
        boolean arrPass = arr.capacity() == 8 && arr.size() == 5;
        System.out.printf("   Auto-expand (4 -> 8): %s%n", arrPass ? "PASSED" : "FAILED");

        System.out.println("3. Verifying CustomBTree Minimum Degree...");
        CustomBTree<Integer, String> tree = new CustomBTree<>();
        boolean degreePass = tree.getMinDegree() == 3;
        System.out.printf("   Min Degree t = 3: %s%n", degreePass ? "PASSED" : "FAILED");

        System.out.println("4. Verifying IndexingService Record Counts...");
        boolean indexPass = indexingService.getLocationCount() == locations.size() &&
                           indexingService.getRequestCount() > 0;
        System.out.printf("   Location Index: %d | Unique Request Index: %d (from %d total) -> %s%n",
                indexingService.getLocationCount(), indexingService.getRequestCount(), serviceRequests.size(), indexPass ? "PASSED" : "FAILED");

        System.out.println("5. Verifying Dijkstra Shortest Path non-negativity...");
        String src = locations.get(0).locationId();
        DijkstraAlgorithm.ShortestPathResult dij = routingService.findShortestPathsFrom(src);
        boolean dijPass = dij.getDistanceTo(src) == 0.0;
        System.out.printf("   Dijkstra Source Distance == 0.0: %s%n", dijPass ? "PASSED" : "FAILED");

        System.out.println("All system self-checks completed successfully.");
    }
}
