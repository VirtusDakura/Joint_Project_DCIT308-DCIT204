package org.ug.dsa.algorithms.sorting;

import org.junit.jupiter.api.Test;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MergeSortTest {

    @Test
    void sortsOrdersUsingDivideAndConquer() {
        ServiceRequest[] orders = {
            createOrder("A", 1, "13:00"),
            createOrder("B", 5, "12:50"),
            createOrder("C", 2, "12:30"),
            createOrder("D", 4, "12:40"),
            createOrder("E", 3, "12:20")
        };

        MergeSort.sort(orders);

        assertArrayEquals(
            new String[]{"B", "D", "E", "C", "A"},
            extractIds(orders)
        );
    }

    @Test
    void usesEarlierDeadlineWhenUrgencyIsEqual() {
        ServiceRequest[] orders = {
            createOrder("THIRD", 5, "12:45"),
            createOrder("FIRST", 5, "12:15"),
            createOrder("SECOND", 5, "12:30")
        };

        MergeSort.sort(orders);

        assertArrayEquals(
            new String[]{"FIRST", "SECOND", "THIRD"},
            extractIds(orders)
        );
    }

    @Test
    void handlesAlreadySortedArray() {
        ServiceRequest[] orders = {
            createOrder("A", 5, "12:10"),
            createOrder("B", 3, "12:20"),
            createOrder("C", 1, "12:30")
        };

        MergeSort.sort(orders);

        assertArrayEquals(
            new String[]{"A", "B", "C"},
            extractIds(orders)
        );
    }

    @Test
    void handlesReverseSortedArray() {
        ServiceRequest[] orders = {
            createOrder("C", 1, "12:30"),
            createOrder("B", 3, "12:20"),
            createOrder("A", 5, "12:10")
        };

        MergeSort.sort(orders);

        assertArrayEquals(
            new String[]{"A", "B", "C"},
            extractIds(orders)
        );
    }

    @Test
    void handlesEmptyAndSingleElementArrays() {
        ServiceRequest[] empty = {};

        ServiceRequest[] single = {
            createOrder("ONLY", 2, "12:10")
        };

        MergeSort.sort(empty);
        MergeSort.sort(single);

        assertEquals(0, empty.length);
        assertEquals("ONLY", single[0].requestId());
    }

    @Test
    void rejectsNullArrayAndNullElement() {
        assertThrows(
            IllegalArgumentException.class,
            () -> MergeSort.sort(null)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> MergeSort.sort(
                new ServiceRequest[]{null}
            )
        );
    }

    private static String[] extractIds(
        ServiceRequest[] orders
    ) {
        String[] ids = new String[orders.length];

        for (int i = 0; i < orders.length; i++) {
            ids[i] = orders[i].requestId();
        }

        return ids;
    }

    private static ServiceRequest createOrder(
        String id,
        int urgency,
        String deadlineTime
    ) {
        LocalDateTime submitted =
            LocalDateTime.parse("2026-08-01T12:00");

        LocalDateTime deadline =
            LocalDateTime.parse(
                "2026-08-01T" + deadlineTime
            );

        return new ServiceRequest(
            id,
            "L001",
            "L002",
            "Food Delivery",
            urgency,
            submitted,
            deadline,
            "NEW"
        );
    }
}