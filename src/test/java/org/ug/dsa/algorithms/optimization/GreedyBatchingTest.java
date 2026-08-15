package org.ug.dsa.algorithms.optimization;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.Location;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.Road;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GreedyBatching Unit Tests")
class GreedyBatchingTest {

    @Test
    @DisplayName("Greedy algorithm batches orders by destination and assigns available rider")
    void testGreedyBatching() {
        LocalDateTime now = LocalDateTime.now();

        CustomDynamicArray<Location> locations = new CustomDynamicArray<>();
        locations.add(new Location("L1", "Kitchen", "Campus", "Vendor", 5.65, -0.18));
        locations.add(new Location("L2", "Hostel", "Campus", "Hall", 5.66, -0.19));

        CustomDynamicArray<Road> roads = new CustomDynamicArray<>();
        roads.add(new Road("RD-1", "L1", "L2", 2.0, 5.0, 1.2));

        CustomDynamicArray<Resource> resources = new CustomDynamicArray<>();
        resources.add(new Resource("R1", "Motorbike", "L1", 4, "AVAILABLE"));

        CustomDynamicArray<ServiceRequest> requests = new CustomDynamicArray<>();
        requests.add(new ServiceRequest("REQ-1", "L1", "L2", "Food", 5, now, now.plusMinutes(45), "NEW"));
        requests.add(new ServiceRequest("REQ-2", "L1", "L2", "Food", 3, now, now.plusMinutes(45), "NEW"));

        CustomDynamicArray<GreedyBatching.BatchAssignment> results =
                GreedyBatching.runGreedyBatching(requests, resources, roads, locations);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("L2", results.get(0).destinationId());
        assertEquals(2, results.get(0).batchOrders().size());
        assertNotNull(results.get(0).assignedResource());
        assertEquals("R1", results.get(0).assignedResource().resourceId());
    }

    @Test
    @DisplayName("Returns unassigned batch when no rider with sufficient capacity is available")
    void testUnassignedBatchWhenNoCapacity() {
        LocalDateTime now = LocalDateTime.now();

        CustomDynamicArray<Location> locations = new CustomDynamicArray<>();
        locations.add(new Location("L1", "Vendor", "Campus", "Vendor", 0, 0));
        locations.add(new Location("L2", "Dest", "Campus", "Hall", 0, 0));

        CustomDynamicArray<Road> roads = new CustomDynamicArray<>();
        CustomDynamicArray<Resource> resources = new CustomDynamicArray<>();
        resources.add(new Resource("R-SMALL", "Bicycle", "L1", 1, "AVAILABLE")); // capacity 1

        CustomDynamicArray<ServiceRequest> requests = new CustomDynamicArray<>();
        requests.add(new ServiceRequest("REQ-1", "L1", "L2", "Food", 5, now, now.plusMinutes(30), "NEW"));
        requests.add(new ServiceRequest("REQ-2", "L1", "L2", "Food", 4, now, now.plusMinutes(30), "NEW")); // 2 orders for same dest

        CustomDynamicArray<GreedyBatching.BatchAssignment> results =
                GreedyBatching.runGreedyBatching(requests, resources, roads, locations);

        assertEquals(1, results.size());
        assertNull(results.get(0).assignedResource(), "Batch of 2 cannot fit into rider of capacity 1");
    }
}
