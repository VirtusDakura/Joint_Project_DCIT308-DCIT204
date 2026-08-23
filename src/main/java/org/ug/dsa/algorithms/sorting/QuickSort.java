package org.ug.dsa.algorithms.sorting;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.ServiceRequest;

/**
 * QuickSort Algorithm Implementation.
 *
 * Divide-and-conquer sorting algorithm that partitions an array around a chosen pivot element.
 *
 * Characteristics:
 *   - In-place: O(log n) call stack space.
 *   - Stability: NOT stable in standard partitioning.
 *   - Pivot Strategy: Median-of-three pivot selection to prevent worst-case O(n^2) on sorted inputs.
 *
 * Time Complexity:
 *   - Best Case: O(n log n) [Balanced partitioning]
 *   - Average Case: O(n log n)
 *   - Worst Case: O(n^2) [Highly skewed partitions]
 * Space Complexity: O(log n) call stack space.
 */
public final class QuickSort {

    private QuickSort() {
        // Prevent instantiation
    }

    /**
     * Metrics tracking record.
     */
    public record QuickSortMetrics(long comparisons, long swaps, int maxRecursionDepth, long timeNs) {
    }

    private static class MetricsHolder {
        long comparisons = 0;
        long swaps = 0;
        int maxDepth = 0;
    }

    /**
     * Sorts an array of ServiceRequest in place.
     */
    public static QuickSortMetrics sort(ServiceRequest[] orders) {
        validate(orders);
        if (orders.length <= 1) {
            return new QuickSortMetrics(0, 0, 0, 0);
        }

        MetricsHolder metrics = new MetricsHolder();
        long start = System.nanoTime();

        quickSort(orders, 0, orders.length - 1, 1, metrics);

        long elapsed = System.nanoTime() - start;
        return new QuickSortMetrics(metrics.comparisons, metrics.swaps, metrics.maxDepth, elapsed);
    }

    private static void quickSort(ServiceRequest[] orders, int low, int high, int depth, MetricsHolder metrics) {
        if (depth > metrics.maxDepth) {
            metrics.maxDepth = depth;
        }

        if (low < high) {
            int pivotIndex = partition(orders, low, high, metrics);
            quickSort(orders, low, pivotIndex - 1, depth + 1, metrics);
            quickSort(orders, pivotIndex + 1, high, depth + 1, metrics);
        }
    }

    private static int partition(ServiceRequest[] orders, int low, int high, MetricsHolder metrics) {
        // Median-of-three pivot selection: low, mid, high
        int mid = low + (high - low) / 2;
        medianOfThree(orders, low, mid, high, metrics);

        ServiceRequest pivot = orders[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (orders[j].compareTo(pivot) <= 0) {
                i++;
                swap(orders, i, j, metrics);
            }
        }

        swap(orders, i + 1, high, metrics);
        return i + 1;
    }

    private static void medianOfThree(ServiceRequest[] orders, int low, int mid, int high, MetricsHolder metrics) {
        metrics.comparisons++;
        if (orders[low].compareTo(orders[mid]) > 0) {
            swap(orders, low, mid, metrics);
        }
        metrics.comparisons++;
        if (orders[low].compareTo(orders[high]) > 0) {
            swap(orders, low, high, metrics);
        }
        metrics.comparisons++;
        if (orders[mid].compareTo(orders[high]) > 0) {
            swap(orders, mid, high, metrics);
        }
        // Place median at high position as pivot
        swap(orders, mid, high, metrics);
    }

    private static void swap(ServiceRequest[] orders, int i, int j, MetricsHolder metrics) {
        if (i != j) {
            ServiceRequest temp = orders[i];
            orders[i] = orders[j];
            orders[j] = temp;
            metrics.swaps++;
        }
    }

    /**
     * Generic QuickSort for any Comparable array.
     */
    public static <T extends Comparable<? super T>> QuickSortMetrics sort(T[] array) {
        if (array == null || array.length <= 1) {
            return new QuickSortMetrics(0, 0, 0, 0);
        }

        MetricsHolder metrics = new MetricsHolder();
        long start = System.nanoTime();

        quickSortGeneric(array, 0, array.length - 1, 1, metrics);

        long elapsed = System.nanoTime() - start;
        return new QuickSortMetrics(metrics.comparisons, metrics.swaps, metrics.maxDepth, elapsed);
    }

    private static <T extends Comparable<? super T>> void quickSortGeneric(T[] array, int low, int high, int depth, MetricsHolder metrics) {
        if (depth > metrics.maxDepth) {
            metrics.maxDepth = depth;
        }

        if (low < high) {
            int pivotIndex = partitionGeneric(array, low, high, metrics);
            quickSortGeneric(array, low, pivotIndex - 1, depth + 1, metrics);
            quickSortGeneric(array, pivotIndex + 1, high, depth + 1, metrics);
        }
    }

    private static <T extends Comparable<? super T>> int partitionGeneric(T[] array, int low, int high, MetricsHolder metrics) {
        int mid = low + (high - low) / 2;
        if (array[low].compareTo(array[mid]) > 0) swapGeneric(array, low, mid, metrics);
        if (array[low].compareTo(array[high]) > 0) swapGeneric(array, low, high, metrics);
        if (array[mid].compareTo(array[high]) > 0) swapGeneric(array, mid, high, metrics);
        swapGeneric(array, mid, high, metrics);

        T pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swapGeneric(array, i, j, metrics);
            }
        }

        swapGeneric(array, i + 1, high, metrics);
        return i + 1;
    }

    private static <T> void swapGeneric(T[] array, int i, int j, MetricsHolder metrics) {
        if (i != j) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            metrics.swaps++;
        }
    }

    /**
     * Sorts a CustomDynamicArray in place.
     */
    public static <T extends Comparable<? super T>> QuickSortMetrics sort(CustomDynamicArray<T> list) {
        if (list == null || list.size() <= 1) {
            return new QuickSortMetrics(0, 0, 0, 0);
        }

        T[] array = (T[]) new Comparable[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        QuickSortMetrics m = sort(array);

        for (int i = 0; i < list.size(); i++) {
            list.set(i, array[i]);
        }

        return m;
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
