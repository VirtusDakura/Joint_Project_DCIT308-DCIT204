package org.ug.dsa.algorithms.sorting;

import org.junit.jupiter.api.Test;
import org.ug.dsa.models.ServiceRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InsertionSortTest {

    @Test
    void sortsOrdersByHigherUrgencyFirst() {
        ServiceRequest[] orders = {
            createOrder("ORD-1", 2, "12:50"),
            createOrder("ORD-2", 5, "12:40"),
            createOrder("ORD-3", 3, "12:30")
        };

        InsertionSort.sort(orders);

        assertEquals("ORD-2", orders[0].requestId());
        assertEquals("ORD-3", orders[1].requestId());
        assertEquals("ORD-1", orders[2].requestId());
    }

    @Test
    void usesEarlierDeadlineWhenUrgencyIsEqual() {
        ServiceRequest[] orders = {
            createOrder("LATE", 4, "13:00"),
            createOrder("EARLY", 4, "12:20")
        };

        InsertionSort.sort(orders);

        assertEquals("EARLY", orders[0].requestId());
        assertEquals("LATE", orders[1].requestId());
    }

    @Test
    void handlesEmptyArray() {
        ServiceRequest[] orders = {};

        InsertionSort.sort(orders);

        assertEquals(0, orders.length);
    }

    @Test
    void handlesSingleElementArray() {
        ServiceRequest[] orders = {
            createOrder("ONLY", 5, "12:00")
        };

        InsertionSort.sort(orders);

        assertEquals("ONLY", orders[0].requestId());
    }

    @Test
    void rejectsNullArray() {
        assertThrows(
            IllegalArgumentException.class,
            () -> InsertionSort.sort(null)
        );
    }

    @Test
    void rejectsNullElement() {
        ServiceRequest[] orders = {
            createOrder("VALID", 3, "12:00"),
            null
        };

        assertThrows(
            IllegalArgumentException.class,
            () -> InsertionSort.sort(orders)
        );
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