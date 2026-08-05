package org.ug.dsa.algorithms.optimization;

import org.ug.dsa.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Greedy Order Batching and Resource Engine.
 */
public class GreedyBatching {

    public record BatchAssignment(
            String destinationId,
            Resource assignedResource,
            List<ServiceRequest> batchOrders,
            double estimatedTravelTimeMin,
            boolean hasDeadlineBreach) {
    }

    public static void main(String[] args) {
        List<Location> locations = loadLocations("data/locations.csv");
        List<Resource> resources = loadResources("data/resources.csv");
        List<Road> roads = loadRoads("data/roads.csv");
        List<ServiceRequest> requests = loadRequests("data/service_requests.csv");

        System.out.println("   GREEDY ORDER BATCHING & DISPATCH EVALUATION SYSTEM    ");

        List<BatchAssignment> assignments = runGreedyBatching(requests, resources, roads, locations);
        printBatchSummary(assignments);
    }

    public static List<BatchAssignment> runGreedyBatching(
            List<ServiceRequest> requests,
            List<Resource> resources,
            List<Road> roads,
            List<Location> locations) {
        List<ServiceRequest> pendingRequests = new ArrayList<>();
        for (ServiceRequest req : requests) {
            if ("NEW".equalsIgnoreCase(req.status())) {
                pendingRequests.add(req);
            }
        }
        Collections.sort(pendingRequests);

        Map<String, List<ServiceRequest>> destinationGroups = new LinkedHashMap<>();
        for (ServiceRequest req : pendingRequests) {
            destinationGroups
                    .computeIfAbsent(req.destinationLocationId(), k -> new ArrayList<>())
                    .add(req);
        }

        List<BatchAssignment> resultAssignments = new ArrayList<>();
        List<Resource> availablePool = new ArrayList<>(resources);

        for (Map.Entry<String, List<ServiceRequest>> entry : destinationGroups.entrySet()) {
            String destinationId = entry.getKey();
            List<ServiceRequest> batch = entry.getValue();

            Resource selectedResource = null;
            for (Resource r : availablePool) {
                if ("AVAILABLE".equalsIgnoreCase(r.availabilityStatus()) && r.capacity() >= batch.size()) {
                    selectedResource = r;
                    break;
                }
            }

            if (selectedResource != null) {
                availablePool.remove(selectedResource);
                double totalEstimatedTime = calculateGreedyRouteTime(selectedResource, batch, roads, locations);
                boolean breach = checkDeadlineViolations(batch, totalEstimatedTime);
                resultAssignments
                        .add(new BatchAssignment(destinationId, selectedResource, batch, totalEstimatedTime, breach));
            } else {
                resultAssignments.add(new BatchAssignment(destinationId, null, batch, 0.0, true));
            }
        }

        return resultAssignments;
    }

    private static double calculateGreedyRouteTime(
            Resource rider,
            List<ServiceRequest> batch,
            List<Road> roads,
            List<Location> locations) {
        String currentLoc = rider.homeLocationId();
        double totalTime = 0.0;

        // Route: Rider Base -> Pickup 1 -> Pickup 2 -> ... -> Final Destination
        for (ServiceRequest req : batch) {
            totalTime += getTravelTime(currentLoc, req.sourceLocationId(), roads, locations);
            currentLoc = req.sourceLocationId();
        }

        if (!batch.isEmpty()) {
            totalTime += getTravelTime(currentLoc, batch.get(0).destinationLocationId(), roads, locations);
        }

        return totalTime;
    }

    private static double getTravelTime(String fromId, String toId, List<Road> roads, List<Location> locations) {
        if (fromId.equals(toId))
            return 0.0;

        // Check explicit road edge first
        for (Road r : roads) {
            if (r.fromLocationId().equals(fromId) && r.toLocationId().equals(toId)) {
                return r.travelTimeMin() * r.conditionWeight(); // Factoring in road conditions/traffic
            }
        }

        // Fallback: Realistic urban travel speed (~20 km/h average in Ghanaian city
        // traffic)
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

    private static boolean checkDeadlineViolations(List<ServiceRequest> batch, double totalTravelTimeMin) {
        if (batch.isEmpty())
            return false;

        // Find the earliest submission time in the batch
        LocalDateTime earliestSubmission = batch.get(0).timeSubmitted();
        for (ServiceRequest req : batch) {
            if (req.timeSubmitted().isBefore(earliestSubmission)) {
                earliestSubmission = req.timeSubmitted();
            }
        }

        // Complete route completion timestamp
        LocalDateTime estimatedArrival = earliestSubmission.plusMinutes((long) Math.ceil(totalTravelTimeMin));

        for (ServiceRequest req : batch) {
            if (estimatedArrival.isAfter(req.deadline())) {
                return true; // Flags breach if destination arrival exceeds order deadline
            }
        }
        return false;
    }

    private static Location findLocation(String locationId, List<Location> locations) {
        for (Location loc : locations) {
            if (loc.locationId().equals(locationId))
                return loc;
        }
        return null;
    }

    private static void printBatchSummary(List<BatchAssignment> assignments) {
        for (BatchAssignment assignment : assignments) {

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
            for (ServiceRequest req : assignment.batchOrders()) {
                System.out.printf("  * [%s] Category: %-20s | Pickup: %s | Urgency: %d | Deadline: %s%n",
                        req.requestId(), req.category(), req.sourceLocationId(), req.urgency(), req.deadline());
            }
        }

    }

    public static List<Location> loadLocations(String filepath) {
        List<Location> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine();
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

    public static List<Resource> loadResources(String filepath) {
        List<Resource> list = new ArrayList<>();
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

    public static List<Road> loadRoads(String filepath) {
        List<Road> list = new ArrayList<>();
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

    public static List<ServiceRequest> loadRequests(String filepath) {
        List<ServiceRequest> list = new ArrayList<>();
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