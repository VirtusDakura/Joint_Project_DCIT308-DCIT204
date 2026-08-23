package org.ug.dsa.algorithms.sorting;

import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    public void testQuickSortIntegerArray() {
        Integer[] array = {10, 80, 30, 90, 40, 50, 70};
        QuickSort.QuickSortMetrics metrics = QuickSort.sort(array);
        assertArrayEquals(new Integer[]{10, 30, 40, 50, 70, 80, 90}, array);
        assertTrue(metrics.comparisons() > 0);
        assertTrue(metrics.maxRecursionDepth() > 0);
    }

    @Test
    public void testQuickSortCustomDynamicArray() {
        CustomDynamicArray<Integer> list = new CustomDynamicArray<>();
        list.add(99); list.add(12); list.add(45); list.add(1); list.add(67);
        QuickSort.sort(list);
        assertEquals(1, list.get(0));
        assertEquals(12, list.get(1));
        assertEquals(45, list.get(2));
        assertEquals(67, list.get(3));
        assertEquals(99, list.get(4));
    }

    @Test
    public void testQuickSortDuplicateElements() {
        Integer[] array = {5, 2, 8, 5, 1, 2, 8, 5};
        QuickSort.sort(array);
        assertArrayEquals(new Integer[]{1, 2, 2, 5, 5, 5, 8, 8}, array);
    }

    @Test
    public void testQuickSortServiceRequests() {
        LocalDateTime now = LocalDateTime.now();
        ServiceRequest req1 = new ServiceRequest("R1", "L1", "L2", "FOOD", 1, now, now.plusHours(1), "NEW");
        ServiceRequest req2 = new ServiceRequest("R2", "L3", "L4", "FOOD", 5, now, now.plusHours(1), "NEW");
        ServiceRequest req3 = new ServiceRequest("R3", "L5", "L6", "FOOD", 3, now, now.plusHours(1), "NEW");

        ServiceRequest[] orders = {req1, req2, req3};
        QuickSort.sort(orders);

        assertEquals("R2", orders[0].requestId());
        assertEquals("R3", orders[1].requestId());
        assertEquals("R1", orders[2].requestId());
    }

    @Test
    public void testQuickSortEmptyAndSingle() {
        Integer[] empty = new Integer[0];
        QuickSort.QuickSortMetrics m1 = QuickSort.sort(empty);
        assertEquals(0, m1.comparisons());

        Integer[] single = new Integer[]{100};
        QuickSort.QuickSortMetrics m2 = QuickSort.sort(single);
        assertEquals(0, m2.comparisons());
    }
}
