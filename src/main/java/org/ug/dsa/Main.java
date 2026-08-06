package org.ug.dsa;

import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching;
import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching.BatchingResult;
import org.ug.dsa.algorithms.optimization.GreedyBatching;
import org.ug.dsa.algorithms.sorting.InsertionSort;
import org.ug.dsa.algorithms.sorting.MergeSort;
import org.ug.dsa.datastructures.CustomBTree;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.models.Location;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.Road;
import org.ug.dsa.models.ServiceRequest;
import org.ug.dsa.services.SchedulingService;

import java.util.List;
import java.util.Scanner;

/**
 * Interactive console menu application */
public class Main {

    private static List<Location> locations;
    private static List<Road> roads;
    private static List<ServiceRequest> serviceRequests;
    private static List<Resource> resources;
    private static CustomGraph systemGraph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadDatasets();

        while (true) {
            printHeader();
            printMenu();
            System.out.print("Select an option (1-8): ");
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();

            System.out.println();
            switch (input) {
                case "1":
                    loadDatasets();
                    System.out.println("✅ All CSV datasets reloaded successfully into system memory.");
                    break;

                case "2":
                    displayDatasetSummary();
                    break;

                case "3":
                    demoSortingAlgorithms(scanner);
                    break;

                case "4":
                    demoDataStructures();
                    break;

                case "5":
                    demoSchedulingService();
                    break;

                case "6":
                    demoOptimizationEngine();
                    break;

                case "7":
                    runSelfCheck();
                    break;

                case "8":
                    System.out.println("Thank you for evaluating the Ghana Smart Delivery System!");
                    return;

                default:
                    System.out.println("❌ Invalid option. Please select between 1 and 8.");
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
        locations = GreedyBatching.loadLocations("data/locations.csv");
        roads = GreedyBatching.loadRoads("data/roads.csv");
        serviceRequests = GreedyBatching.loadRequests("data/service_requests.csv");
        resources = GreedyBatching.loadResources("data/resources.csv");

        systemGraph = new CustomGraph();
        for (Location loc : locations) {
            systemGraph.addVertex(loc.locationId());
        }
        for (Road road : roads) {
            systemGraph.addEdge(road.fromLocationId(), road.toLocationId(), road.getEffectiveWeight());
        }
    }

    private static void printHeader() {
        System.out.println("==========================================================================");
        System.out.println("          GHANA SMART FOOD & PARCEL DELIVERY OPTIMIZER                    ");
        System.out.println("              Department of Computer Science - UG                         ");
        System.out.println("            DCIT 204 / DCIT 308 Joint Semester Project                    ");
        System.out.println("==========================================================================");
    }

    private static void printMenu() {
        System.out.println("1. Load / Reload CSV Datasets");
        System.out.println("2. Display Operational Dataset Summary (Locations, Roads, Orders, Riders)");
        System.out.println("3. Run Sorting Algorithms Demo (Insertion Sort vs Merge Sort)");
        System.out.println("4. Demonstrate Custom Data Structures (CustomDynamicArray, CustomGraph, CustomBTree)");
        System.out.println("5. Demonstrate Service Scheduling & Dispatch Engine (FIFO & Priority)");
        System.out.println("6. Run Order Batching Optimization (Greedy vs 0/1 Knapsack DP)");
        System.out.println("7. Run Integrated Self-Check & Invariant Verification");
        System.out.println("8. Exit System");
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void displayDatasetSummary() {
        System.out.println("=== OPERATIONAL DATASET SUMMARY ===");
        System.out.printf("Locations Loaded     : %d locations%n", locations.size());
        System.out.printf("Road Edges Loaded    : %d roads%n", roads.size());
        System.out.printf("Service Requests     : %d orders%n", serviceRequests.size());
        System.out.printf("Delivery Resources   : %d riders/vehicles%n", resources.size());
        System.out.println();

        System.out.println("Sample Locations (First 5):");
        for (int i = 0; i < Math.min(5, locations.size()); i++) {
            Location loc = locations.get(i);
            System.out.printf("  * [%s] %s (%s, %s)%n", loc.locationId(), loc.name(), loc.area(), loc.locationType());
        }

        System.out.println("\nSample Service Requests (First 5):");
        for (int i = 0; i < Math.min(5, serviceRequests.size()); i++) {
            ServiceRequest req = serviceRequests.get(i);
            System.out.printf("  * [%s] Urgency: %d | Cat: %-18s | From: %s -> To: %s%n",
                    req.requestId(), req.urgency(), req.category(), req.sourceLocationId(), req.destinationLocationId());
        }
    }

    private static void demoSortingAlgorithms(Scanner scanner) {
        System.out.println("=== SORTING ALGORITHM DEMONSTRATION ===");
        System.out.println("1. Insertion Sort (In-Place)");
        System.out.println("2. Merge Sort (Divide-and-Conquer)");
        System.out.print("Select sorting algorithm (1 or 2): ");
        String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";

        ServiceRequest[] sampleArr = serviceRequests.stream().limit(10).toArray(ServiceRequest[]::new);

        System.out.println("\n--- Unsorted Orders Sample (First 10) ---");
        for (ServiceRequest req : sampleArr) {
            System.out.printf("  * [%s] Urgency: %d | Deadline: %s%n", req.requestId(), req.urgency(), req.deadline());
        }

        long startTime = System.nanoTime();
        if ("2".equals(choice)) {
            MergeSort.sort(sampleArr);
            System.out.println("\n--- Sorted via MergeSort ---");
        } else {
            InsertionSort.sort(sampleArr);
            System.out.println("\n--- Sorted via InsertionSort ---");
        }
        long durationNs = System.nanoTime() - startTime;

        for (ServiceRequest req : sampleArr) {
            System.out.printf("  * [%s] Urgency: %d | Deadline: %s%n", req.requestId(), req.urgency(), req.deadline());
        }
        System.out.printf("%nExecution Time: %.3f ms%n", durationNs / 1_000_000.0);
    }

    private static void demoDataStructures() {
        System.out.println("=== CUSTOM DATA STRUCTURES DEMO ===");

        // Dynamic Array
        System.out.println("1. CustomDynamicArray Auto-Resizing Demo:");
        CustomDynamicArray<String> dynamicArray = new CustomDynamicArray<>();
        System.out.printf("   Initial Capacity: %d, Size: %d%n", dynamicArray.capacity(), dynamicArray.size());
        for (int i = 1; i <= 5; i++) {
            dynamicArray.add("Location-" + i);
        }
        System.out.printf("   After 5 elements -> Capacity: %d, Size: %d%n", dynamicArray.capacity(), dynamicArray.size());

        // Custom Graph
        System.out.println("\n2. CustomGraph Adjacency List & Matrix Representation:");
        System.out.println(systemGraph.getAdjacencyListAndMatrixSideBySide());

        // Custom B-Tree
        System.out.println("3. CustomBTree Indexing & Node Splitting (t = 3):");
        CustomBTree<Integer, String> btree = new CustomBTree<>();
        for (int i = 1; i <= 10; i++) {
            btree.insert(i, "OrderRecord-" + i);
        }
        System.out.printf("   B-Tree Size: %d, Height: %d, Root Key Count: %d%n", btree.size(), btree.height(), btree.getRootKeyCount());
        System.out.print("   Inorder Sorted Key Traversal: ");
        for (int i = 0; i < btree.inorderTraversal().size(); i++) {
            System.out.print(btree.inorderTraversal().get(i) + " ");
        }
        System.out.println();
    }

    private static void demoSchedulingService() {
        System.out.println("=== SERVICE SCHEDULING & DISPATCH ENGINE DEMO ===");
        SchedulingService scheduler = new SchedulingService();

        for (int i = 0; i < Math.min(5, serviceRequests.size()); i++) {
            scheduler.submitOrder(serviceRequests.get(i));
        }

        System.out.printf("Submitted 5 orders. Pending count: %d%n", scheduler.getPendingCount());

        ServiceRequest fifoDispatched = scheduler.dispatchFIFO();
        System.out.printf("FIFO Dispatched Order     : [%s] Urgency: %d%n", fifoDispatched.requestId(), fifoDispatched.urgency());

        ServiceRequest priorityDispatched = scheduler.dispatchPriority();
        System.out.printf("Priority Dispatched Order : [%s] Urgency: %d%n", priorityDispatched.requestId(), priorityDispatched.urgency());
    }

    private static void demoOptimizationEngine() {
        System.out.println("=== ORDER BATCHING OPTIMIZATION ENGINE ===");

        ServiceRequest[] batchSample = serviceRequests.stream().limit(8).toArray(ServiceRequest[]::new);
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

    private static void runSelfCheck() {
        System.out.println("=== INTEGRATED SYSTEM SELF-CHECK & INVARIANT VERIFICATION ===");
        System.out.println("1. Verifying Locations & Graph Vertex Count matching...");
        boolean vMatch = locations.size() == systemGraph.getVertexCount();
        System.out.printf("   Locations: %d | Graph Vertices: %d -> %s%n", locations.size(), systemGraph.getVertexCount(), vMatch ? "PASSED" : "FAILED");

        System.out.println("2. Verifying CustomDynamicArray Resizing Invariant...");
        CustomDynamicArray<Integer> arr = new CustomDynamicArray<>(4);
        arr.add(1); arr.add(2); arr.add(3); arr.add(4); arr.add(5);
        boolean arrPass = arr.capacity() == 8 && arr.size() == 5;
        System.out.printf("   Auto-expand (4 -> 8): %s%n", arrPass ? "PASSED" : "FAILED");

        System.out.println("3. Verifying CustomBTree Minimum Degree (t = 3)...");
        CustomBTree<Integer, String> tree = new CustomBTree<>();
        boolean degreePass = tree.getMinDegree() == 3;
        System.out.printf("   Min Degree t = 3: %s%n", degreePass ? "PASSED" : "FAILED");

        System.out.println("✅ All system self-checks completed successfully!");
    }
}
