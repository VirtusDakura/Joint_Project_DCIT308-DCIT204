package org.ug.dsa;
import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching;
import org.ug.dsa.algorithms.optimization.DynamicProgrammingBatching.BatchingResult;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.ServiceRequest;

// import org.ug.dsa.algorithms.optimization.package.BatchingResult


/**
 * Main application entry point for Ghana Smart Food and Parcel Delivery System.
 * This class provides a console menu so examiners can run demonstrations
 * without editing source code.
 *
 * Assigned to: Virtus Dakura (22052950)
 *
 * The console menu should allow the examiner to:
 *   1. Load CSV data into the database
 *   2. Display loaded locations, roads, requests, and resources
 *   3. Run any sorting algorithm and see results
 *   4. Run any search algorithm and see results
 *   5. Run Dijkstra shortest path between two locations
 *   6. Run BFS/DFS from a starting location
 *   7. Run greedy and DP batching
 *   8. Run performance benchmarks
 *   9. Exit
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println(" Ghana Smart Food & Parcel Delivery System (DCIT 204/308) ");
        System.out.println("==========================================================");
        System.out.println();
        System.out.println("System scaffold loaded. Awaiting module implementations...");
        System.out.println();
        System.out.println("TODO: Implement the interactive console menu here.");
        System.out.println("      See TASK_DISTRIBUTION.md for full requirements.");


        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        ServiceRequest[] demoRequests = new ServiceRequest[] {
            new ServiceRequest("ORD-001", "L002", "L007", "Hot Meal (Jollof)", 5, now, now.plusMinutes(30), "NEW"),
            new ServiceRequest("ORD-002", "L006", "L008", "Gobe Special", 4, now, now.plusMinutes(30), "NEW"),
            new ServiceRequest("ORD-003", "L004", "L009", "Express Burger Meal", 3, now, now.plusMinutes(40), "NEW"),
            new ServiceRequest("ORD-004", "L001", "L009", "Fried Rice Family Pack", 4, now, now.plusMinutes(45), "NEW"),
            new ServiceRequest("ORD-005", "L003", "L010", "Waakye Party Pack", 5, now, now.plusMinutes(50), "NEW"),
        };

        Resource rider = new Resource("RIDER-103", "Bicycle (Yaw)", "L011", 3, "AVAILABLE");

        System.out.println("Index-number derived demo parameter (digit sum of " + DynamicProgrammingBatching.OWNER_INDEX_NUMBER + "): " + DynamicProgrammingBatching.INDEX_DIGIT_SUM);
        System.out.println("Batching " + demoRequests.length + " requests onto " + rider.resourceType()
                + " (capacity = " + rider.capacity() + ")");
        System.out.println();

        DynamicProgrammingBatching batcher = new DynamicProgrammingBatching();
        BatchingResult result = batcher.solve(demoRequests, rider);

        System.out.println(DynamicProgrammingBatching.renderTable(result));
        System.out.println("Total urgency value achieved: " + result.totalValue);
        System.out.println("Capacity used: " + result.totalWeightUsed + " / " + result.capacity);
        System.out.println("Selected orders:");
        for (int i = 0; i < result.selectedCount; i++) {
            ServiceRequest r = result.selectedRequests[i];
            System.out.println("  - " + r.requestId() + " (" + r.category() + ", urgency=" + r.urgency() + ")");
        }
    
    }
}
