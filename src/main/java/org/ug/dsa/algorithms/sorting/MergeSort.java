package org.ug.dsa.algorithms.sorting;

import org.ug.dsa.models.ServiceRequest;

/**
 * Implements stable Merge Sort using divide-and-conquer.
 *
 * Orders are sorted according to ServiceRequest.compareTo():
 * 1. Higher urgency comes first.
 * 2. If urgency is equal, the earlier deadline comes first.
 *
 * Time complexity: O(n log n)
 * Space complexity: O(n)
 */
public final class MergeSort {

    private MergeSort() {
        // Prevent creation of objects from this utility class.
    }

    /**
     * Sorts the supplied array in place using Merge Sort.
     *
     * @param orders food-delivery orders to sort
     * @throws IllegalArgumentException if the array or an element is null
     */
    public static void sort(ServiceRequest[] orders) {
        validate(orders);

        if (orders.length < 2) {
            return;
        }

        ServiceRequest[] temporary =
            new ServiceRequest[orders.length];

        sort(
            orders,
            temporary,
            0,
            orders.length - 1
        );
    }

    /**
     * Recursively divides the array into smaller sections.
     */
    private static void sort(
        ServiceRequest[] orders,
        ServiceRequest[] temporary,
        int left,
        int right
    ) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        sort(orders, temporary, left, middle);
        sort(orders, temporary, middle + 1, right);

        merge(
            orders,
            temporary,
            left,
            middle,
            right
        );
    }

    /**
     * Merges two sorted sections:
     *
     * Left section:  left to middle
     * Right section: middle + 1 to right
     */
    private static void merge(
        ServiceRequest[] orders,
        ServiceRequest[] temporary,
        int left,
        int middle,
        int right
    ) {
        /*
         * Copy the current section into the temporary array.
         */
        for (int i = left; i <= right; i++) {
            temporary[i] = orders[i];
        }

        int leftIndex = left;
        int rightIndex = middle + 1;
        int writeIndex = left;

        /*
         * Compare the first remaining element in each half.
         */
        while (
            leftIndex <= middle &&
            rightIndex <= right
        ) {
            if (
                temporary[leftIndex]
                    .compareTo(temporary[rightIndex]) <= 0
            ) {
                orders[writeIndex] =
                    temporary[leftIndex];

                leftIndex++;
            } else {
                orders[writeIndex] =
                    temporary[rightIndex];

                rightIndex++;
            }

            writeIndex++;
        }

        /*
         * Copy any remaining elements from the left half.
         */
        while (leftIndex <= middle) {
            orders[writeIndex] =
                temporary[leftIndex];

            leftIndex++;
            writeIndex++;
        }

        /*
         * Copy any remaining elements from the right half.
         */
        while (rightIndex <= right) {
            orders[writeIndex] =
                temporary[rightIndex];

            rightIndex++;
            writeIndex++;
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