package org.ug.dsa.algorithms.sorting;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.ServiceRequest;

/**
 * Selection Sort Algorithm Implementation.
 *
 * Repeatedly finds the minimum/target element from the unsorted segment
 * and places it at the beginning.
 *
 * Characteristics:
 *   - In-place: O(1) auxiliary space.
 *   - Stability: NOT stable in its default form due to long-distance swaps.
 *   - Comparisons: Always O(n^2) regardless of initial order: n(n-1)/2.
 *   - Swaps: O(n) swaps (at most n-1 swaps).
 *
 * Time Complexity:
 *   - Best Case: O(n^2)
 *   - Average Case: O(n^2)
 *   - Worst Case: O(n^2)
 * Space Complexity: O(1) auxiliary space.
 */
public final class SelectionSort {

    private SelectionSort() {
        // Prevent instantiation
    }

    /**
     * Metrics tracking record for empirical analysis.
     */
    public record SortMetrics(long comparisons, long swaps, long timeNs) {
    }

    /**
     * Sorts an array of ServiceRequest in place.
     * Higher urgency first, earlier deadline second (per ServiceRequest.compareTo).
     */
    public static SortMetrics sort(ServiceRequest[] orders) {
        validate(orders);
        long start = System.nanoTime();
        long comparisons = 0;
        long swaps = 0;

        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (orders[j].compareTo(orders[minIdx]) < 0) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                ServiceRequest temp = orders[i];
                orders[i] = orders[minIdx];
                orders[minIdx] = temp;
                swaps++;
            }
        }

        long elapsed = System.nanoTime() - start;
        return new SortMetrics(comparisons, swaps, elapsed);
    }

    /**
     * Generic selection sort for any Comparable array.
     */
    public static <T extends Comparable<? super T>> SortMetrics sort(T[] array) {
        if (array == null || array.length <= 1) {
            return new SortMetrics(0, 0, 0);
        }
        long start = System.nanoTime();
        long comparisons = 0;
        long swaps = 0;

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j].compareTo(array[minIdx]) < 0) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                T temp = array[i];
                array[i] = array[minIdx];
                array[minIdx] = temp;
                swaps++;
            }
        }

        long elapsed = System.nanoTime() - start;
        return new SortMetrics(comparisons, swaps, elapsed);
    }

    /**
     * Sorts a CustomDynamicArray in place.
     */
    public static <T extends Comparable<? super T>> SortMetrics sort(CustomDynamicArray<T> list) {
        if (list == null || list.size() <= 1) {
            return new SortMetrics(0, 0, 0);
        }
        long start = System.nanoTime();
        long comparisons = 0;
        long swaps = 0;

        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (list.get(j).compareTo(list.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                T temp = list.get(i);
                list.set(i, list.get(minIdx));
                list.set(minIdx, temp);
                swaps++;
            }
        }

        long elapsed = System.nanoTime() - start;
        return new SortMetrics(comparisons, swaps, elapsed);
    }

    private static void validate(ServiceRequest[] orders) {
        if (orders == null) {
            throw new IllegalArgumentException("Orders array must not be null.");
        }
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                throw new IllegalArgumentException("Order at index " + i + " must not be null.");
            }
        }
    }
}
