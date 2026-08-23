package org.ug.dsa.algorithms.sorting;

import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortTest {

    @Test
    public void testSelectionSortIntegerArray() {
        Integer[] array = {64, 25, 12, 22, 11};
        SelectionSort.SortMetrics metrics = SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{11, 12, 22, 25, 64}, array);
        assertTrue(metrics.comparisons() > 0);
        assertTrue(metrics.swaps() > 0);
    }

    @Test
    public void testSelectionSortCustomDynamicArray() {
        CustomDynamicArray<Integer> list = new CustomDynamicArray<>();
        list.add(5); list.add(1); list.add(4); list.add(2); list.add(8);
        SelectionSort.sort(list);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(4, list.get(2));
        assertEquals(5, list.get(3));
        assertEquals(8, list.get(4));
    }

    @Test
    public void testSelectionSortServiceRequests() {
        LocalDateTime now = LocalDateTime.now();
        ServiceRequest req1 = new ServiceRequest("REQ-1", "LOC-A", "LOC-B", "FOOD", 2, now, now.plusHours(2), "NEW");
        ServiceRequest req2 = new ServiceRequest("REQ-2", "LOC-C", "LOC-D", "FOOD", 5, now, now.plusHours(1), "NEW");
        ServiceRequest req3 = new ServiceRequest("REQ-3", "LOC-E", "LOC-F", "FOOD", 4, now, now.plusHours(3), "NEW");

        ServiceRequest[] orders = {req1, req2, req3};
        SelectionSort.sort(orders);

        // Natural ordering: highest urgency first (5 -> 4 -> 2)
        assertEquals("REQ-2", orders[0].requestId());
        assertEquals("REQ-3", orders[1].requestId());
        assertEquals("REQ-1", orders[2].requestId());
    }

    @Test
    public void testSelectionSortAlreadySortedAndEmpty() {
        Integer[] empty = new Integer[0];
        SelectionSort.SortMetrics m1 = SelectionSort.sort(empty);
        assertEquals(0, m1.comparisons());

        Integer[] single = new Integer[]{42};
        SelectionSort.SortMetrics m2 = SelectionSort.sort(single);
        assertEquals(0, m2.comparisons());
    }
}
