package org.ug.dsa.algorithms.optimization;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DynamicProgrammingBatching (0/1 Knapsack) Unit Tests")
class DynamicProgrammingBatchingTest {

    @Test
    @DisplayName("0/1 Knapsack DP selects optimal subset under capacity constraint")
    void testOptimalBatching() {
        LocalDateTime now = LocalDateTime.now();

        // 3 requests with custom weights and urgency values
        ServiceRequest req1 = new ServiceRequest("R1", "LocA", "LocB", "Party Food", 5, now, now.plusHours(1), "NEW"); // wt 2, val 5
        ServiceRequest req2 = new ServiceRequest("R2", "LocA", "LocB", "Standard", 4, now, now.plusHours(1), "NEW");   // wt 1, val 4
        ServiceRequest req3 = new ServiceRequest("R3", "LocA", "LocB", "Standard", 3, now, now.plusHours(1), "NEW");   // wt 1, val 3

        ServiceRequest[] orders = {req1, req2, req3};
        Resource rider = new Resource("RID-1", "Motorbike", "LocA", 2, "AVAILABLE"); // capacity 2

        DynamicProgrammingBatching dp = new DynamicProgrammingBatching();
        DynamicProgrammingBatching.BatchingResult result = dp.solve(orders, rider);

        // Capacity = 2.
        // Choices: {R1} (wt 2, val 5) vs {R2, R3} (wt 1+1=2, val 4+3=7).
        // Optimal is {R2, R3} with totalValue = 7.
        assertEquals(7, result.totalValue);
        assertEquals(2, result.totalWeightUsed);
        assertEquals(2, result.selectedCount);
        assertNotNull(result.dpTable);
        assertNotNull(DynamicProgrammingBatching.renderTable(result));
    }

    @Test
    @DisplayName("Empty orders array returns zero value")
    void testEmptyOrders() {
        DynamicProgrammingBatching dp = new DynamicProgrammingBatching();
        ServiceRequest[] empty = new ServiceRequest[0];
        Resource rider = new Resource("RID-1", "Bicycle", "LocA", 5, "AVAILABLE");

        DynamicProgrammingBatching.BatchingResult result = dp.solve(empty, rider);
        assertEquals(0, result.totalValue);
        assertEquals(0, result.selectedCount);
    }
}
