package org.ug.dsa.algorithms.sorting;

import org.ug.dsa.models.ServiceRequest;

/**
 * Implements Insertion Sort for food-delivery orders.
 *
 * Orders are sorted according to ServiceRequest.compareTo():
 * 1. Higher urgency comes first.
 * 2. If urgency is equal, the earlier deadline comes first.
 *
 * Time complexity:
 * Best case: O(n)
 * Average case: O(n^2)
 * Worst case: O(n^2)
 *
 * Space complexity: O(1)
 */
public final class InsertionSort {

    private InsertionSort() {
        // Prevent creation of objects from this utility class.
    }

    /**
     * Sorts the orders array in place.
     *
     * @param orders food-delivery orders to sort
     * @throws IllegalArgumentException if the array or an element is null
     */
    public static void sort(ServiceRequest[] orders) {
        validate(orders);

        for (int i = 1; i < orders.length; i++) {
            ServiceRequest key = orders[i];
            int j = i - 1;

            /*
             * Shift orders that should appear after the key
             * one position to the right.
             */
            while (j >= 0 && orders[j].compareTo(key) > 0) {
                orders[j + 1] = orders[j];
                j--;
            }

            orders[j + 1] = key;
        }
    }

    private static void validate(ServiceRequest[] orders) {
        if (orders == null) {
            throw new IllegalArgumentException(
                "Orders array must not be null."
            );
        }

        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                throw new IllegalArgumentException(
                    "Order at index " + i + " must not be null."
                );
            }
        }
    }
}