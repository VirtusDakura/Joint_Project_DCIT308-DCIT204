package org.ug.dsa.algorithms.optimization;

import org.ug.dsa.algorithms.sorting.MergeSort;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomList;
import org.ug.dsa.datastructures.CustomMap;
import org.ug.dsa.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Greedy Order Batching and Resource Engine.
 *
 * Groups pending orders by destination, assigns the first available rider
 * with sufficient capacity, and estimates route travel times using
 * Ghana-local road conditions.
 *
 * All internal logic uses custom data structures only (CustomDynamicArray,
 * CustomMap, MergeSort) — no java.util collections.
 */
public class GreedyBatching {

    /**
     * A single batch: one destination, one rider, N orders.
     */
    public record BatchAssignment(
            String destinationId,
            Resource assignedResource,
            CustomDynamicArray<ServiceRequest> batchOrders,
            double estimatedTravelTimeMin,
            boolean hasDeadlineBreach) {
    }

    public static void main(String[] args) {
        CustomDynamicArray<Location> locations = loadLocations("data/locations.csv");
        CustomDynamicArray<Resource> resources = loadResources("data/resources.csv");
        CustomDynamicArray<Road> roads = loadRoads("data/roads.csv");
        CustomDynamicArray<ServiceRequest> requests = loadRequests("data/service_requests.csv");

        System.out.println("   GREEDY ORDER BATCHING & DISPATCH EVALUATION SYSTEM    ");

        CustomDynamicArray<BatchAssignment> assignments = runGreedyBatching(requests, resources, roads, locations);
        printBatchSummary(assignments);
    }

    /**
     * Core greedy algorithm:
     *   1. Filter pending requests (status == NEW).
     *   2. Sort by urgency descending (using custom MergeSort).
     *   3. Group sorted requests by destination (using CustomMap).
     *   4. For each destination group, greedily assign the first available
     *      rider whose capacity accommodates the batch size.
     */
    public static CustomDynamicArray<BatchAssignment> runGreedyBatching(
            CustomDynamicArray<ServiceRequest> requests,
            CustomDynamicArray<Resource> resources,
            CustomDynamicArray<Road> roads,
            CustomDynamicArray<Location> locations) {

        // 1. Filter pending requests
        CustomDynamicArray<ServiceRequest> pendingRequests = new CustomDynamicArray<>();
        for (int i = 0; i < requests.size(); i++) {
            ServiceRequest req = requests.get(i);
            if ("NEW".equalsIgnoreCase(req.status())) {
                pendingRequests.add(req);
            }
        }

        // 2. Sort by urgency using custom MergeSort (no Collections.sort)
        ServiceRequest[] sortedArr = new ServiceRequest[pendingRequests.size()];
        for (int i = 0; i < pendingRequests.size(); i++) {
            sortedArr[i] = pendingRequests.get(i);
        }
        MergeSort.sort(sortedArr);

        // 3. Group by destination using CustomMap (no LinkedHashMap)
        CustomMap<String, CustomDynamicArray<ServiceRequest>> destinationGroups = new CustomMap<>();
        CustomDynamicArray<String> insertionOrder = new CustomDynamicArray<>();

        for (ServiceRequest req : sortedArr) {
            String dest = req.destinationLocationId();
            if (!destinationGroups.containsKey(dest)) {
                destinationGroups.put(dest, new CustomDynamicArray<>());
                insertionOrder.add(dest);
            }
            destinationGroups.get(dest).add(req);
        }

        // 4. Greedy resource assignment per destination batch
        CustomDynamicArray<BatchAssignment> resultAssignments = new CustomDynamicArray<>();
        CustomDynamicArray<Resource> availablePool = new CustomDynamicArray<>();
        for (int i = 0; i < resources.size(); i++) {
            availablePool.add(resources.get(i));
        }

        for (int d = 0; d < insertionOrder.size(); d++) {
            String destinationId = insertionOrder.get(d);
            CustomDynamicArray<ServiceRequest> batch = destinationGroups.get(destinationId);

            // Find first available rider with sufficient capacity
            Resource selectedResource = null;
            int selectedIdx = -1;
            for (int i = 0; i < availablePool.size(); i++) {
                Resource r = availablePool.get(i);
                if ("AVAILABLE".equalsIgnoreCase(r.availabilityStatus()) && r.capacity() >= batch.size()) {
                    selectedResource = r;
                    selectedIdx = i;
                    break;
                }
            }

            if (selectedResource != null) {
                availablePool.remove(selectedIdx);
                double totalEstimatedTime = calculateGreedyRouteTime(selectedResource, batch, roads, locations);
                boolean breach = checkDeadlineViolations(batch, totalEstimatedTime);
                resultAssignments.add(new BatchAssignment(destinationId, selectedResource, batch, totalEstimatedTime, breach));
            } else {
                resultAssignments.add(new BatchAssignment(destinationId, null, batch, 0.0, true));
            }
        }

        return resultAssignments;
    }

    private static double calculateGreedyRouteTime(
            Resource rider,
            CustomDynamicArray<ServiceRequest> batch,
            CustomDynamicArray<Road> roads,
            CustomDynamicArray<Location> locations) {
        String currentLoc = rider.homeLocationId();
        double totalTime = 0.0;

        // Route: Rider Base -> Pickup 1 -> Pickup 2 -> ... -> Final Destination
        for (int i = 0; i < batch.size(); i++) {
            ServiceRequest req = batch.get(i);
            totalTime += getTravelTime(currentLoc, req.sourceLocationId(), roads, locations);
            currentLoc = req.sourceLocationId();
        }

        if (!batch.isEmpty()) {
            totalTime += getTravelTime(currentLoc, batch.get(0).destinationLocationId(), roads, locations);
        }

        return totalTime;
    }

    private static double getTravelTime(String fromId, String toId,
            CustomDynamicArray<Road> roads, CustomDynamicArray<Location> locations) {
        if (fromId.equals(toId))
            return 0.0;

        // Check explicit road edge first
        for (int i = 0; i < roads.size(); i++) {
            Road r = roads.get(i);
            if (r.fromLocationId().equals(fromId) && r.toLocationId().equals(toId)) {
                return r.travelTimeMin() * r.conditionWeight(); // Factoring in road conditions/traffic
            }
        }

        // Fallback: Realistic urban travel speed (~20 km/h average in Ghanaian city traffic)
        Location locA = findLocation(fromId, locations);
        Location locB = findLocation(toId, locations);
        if (locA != null && locB != null) {
            double dx = locA.xCoord() - locB.xCoord();
            double dy = locA.yCoord() - locB.yCoord();
            double distanceKm = Math.sqrt(dx * dx + dy * dy) * 111.0;
            return (distanceKm / 20.0) * 60.0; // Adjusted for urban road travel
        }

        return 15.0;
    }

    private static boolean checkDeadlineViolations(CustomDynamicArray<ServiceRequest> batch, double totalTravelTimeMin) {
        if (batch.isEmpty())
            return false;

        // Find the earliest submission time in the batch
        LocalDateTime earliestSubmission = batch.get(0).timeSubmitted();
        for (int i = 1; i < batch.size(); i++) {
            if (batch.get(i).timeSubmitted().isBefore(earliestSubmission)) {
                earliestSubmission = batch.get(i).timeSubmitted();
            }
        }

        // Compute route completion timestamp
        LocalDateTime estimatedArrival = earliestSubmission.plusMinutes((long) Math.ceil(totalTravelTimeMin));

        for (int i = 0; i < batch.size(); i++) {
            if (estimatedArrival.isAfter(batch.get(i).deadline())) {
                return true; // Flags breach if destination arrival exceeds order deadline
            }
        }
        return false;
    }

    private static Location findLocation(String locationId, CustomDynamicArray<Location> locations) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).locationId().equals(locationId))
                return locations.get(i);
        }
        return null;
    }

    private static void printBatchSummary(CustomDynamicArray<BatchAssignment> assignments) {
        for (int a = 0; a < assignments.size(); a++) {
            BatchAssignment assignment = assignments.get(a);

            System.out.printf("Batch Destination : %s%n", assignment.destinationId());
            System.out.printf("Batch Size        : %d order(s)%n", assignment.batchOrders().size());

            if (assignment.assignedResource() != null) {
                Resource r = assignment.assignedResource();
                System.out.printf("Assigned Rider    : %s (%s | Capacity: %d | Base: %s)%n",
                        r.resourceId(), r.resourceType(), r.capacity(), r.homeLocationId());
                System.out.printf("Est. Travel Time  : %.2f mins%n", assignment.estimatedTravelTimeMin());
                System.out.printf("Status / Breach   : %s%n",
                        assignment.hasDeadlineBreach() ? "CRITICAL (Deadline Violated)" : "ON TIME");
            } else {
                System.out.println("Assigned Rider    : UNASSIGNED (Insufficient Capacity / No Rider)");
            }

            System.out.println("Orders Included:");
            CustomDynamicArray<ServiceRequest> orders = assignment.batchOrders();
            for (int i = 0; i < orders.size(); i++) {
                ServiceRequest req = orders.get(i);
                System.out.printf("  * [%s] Category: %-20s | Pickup: %s | Urgency: %d | Deadline: %s%n",
                        req.requestId(), req.category(), req.sourceLocationId(), req.urgency(), req.deadline());
            }
        }
    }

    // ────────────────────── CSV Data Loaders ──────────────────────
    // These use java.io (BufferedReader/FileReader) which is explicitly
    // allowed by the project brief for file reading utilities.

    public static CustomDynamicArray<Location> loadLocations(String filepath) {
        CustomDynamicArray<Location> list = new CustomDynamicArray<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");
                if (t.length >= 6) {
                    list.add(new Location(t[0].trim(), t[1].trim(), t[2].trim(), t[3].trim(),
                            Double.parseDouble(t[4].trim()), Double.parseDouble(t[5].trim())));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading locations CSV: " + e.getMessage());
        }
        return list;
    }

    public static CustomDynamicArray<Resource> loadResources(String filepath) {
        CustomDynamicArray<Resource> list = new CustomDynamicArray<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");
                if (t.length >= 5) {
                    list.add(new Resource(t[0].trim(), t[1].trim(), t[2].trim(),
                            Integer.parseInt(t[3].trim()), t[4].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading resources CSV: " + e.getMessage());
        }
        return list;
    }

    public static CustomDynamicArray<Road> loadRoads(String filepath) {
        CustomDynamicArray<Road> list = new CustomDynamicArray<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");
                if (t.length >= 6) {
                    list.add(new Road(t[0].trim(), t[1].trim(), t[2].trim(),
                            Double.parseDouble(t[3].trim()), Double.parseDouble(t[4].trim()),
                            Double.parseDouble(t[5].trim())));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading roads CSV: " + e.getMessage());
        }
        return list;
    }

    public static CustomDynamicArray<ServiceRequest> loadRequests(String filepath) {
        CustomDynamicArray<ServiceRequest> list = new CustomDynamicArray<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");
                if (t.length >= 8) {
                    list.add(new ServiceRequest(t[0].trim(), t[1].trim(), t[2].trim(), t[3].trim(),
                            Integer.parseInt(t[4].trim()),
                            LocalDateTime.parse(t[5].trim(), formatter),
                            LocalDateTime.parse(t[6].trim(), formatter),
                            t[7].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading service requests CSV: " + e.getMessage());
        }
        return list;
    }
}