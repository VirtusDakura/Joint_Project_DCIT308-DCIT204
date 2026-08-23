package org.ug.dsa.algorithms.search;

import org.ug.dsa.datastructures.CustomDynamicArray;

import java.util.Comparator;

/**
 * Binary Search Algorithm Implementation.
 * 
 * Divides sorted search space in half logarithmically.
 * Precondition: The input sequence MUST be sorted in ascending order.
 * 
 * Time Complexity:
 *   - Best Case: O(1) [Element at midpoint]
 *   - Average Case: O(log n)
 *   - Worst Case: O(log n)
 * Space Complexity:
 *   - Iterative: O(1) auxiliary space
 *   - Recursive: O(log n) call stack space
 * 
 * Strict Constraint: Uses custom data structures only.
 */
public class BinarySearch {

    /**
     * Result wrapper capturing index, comparisons, and precondition status.
     */
    public record BinarySearchResult<T>(
            int index,
            T element,
            int comparisonCount,
            boolean found,
            boolean preconditionMet) {
    }

    /**
     * Verifies if an array is sorted in ascending order according to natural ordering.
     */
    public static <T extends Comparable<? super T>> boolean isSorted(T[] array) {
        if (array == null || array.length <= 1) return true;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if a CustomDynamicArray is sorted in ascending order.
     */
    public static <T extends Comparable<? super T>> boolean isSorted(CustomDynamicArray<T> list) {
        if (list == null || list.size() <= 1) return true;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Iterative Binary Search with mandatory precondition verification.
     *
     * @param array  The sorted array to search
     * @param target The target value
     * @param <T>    The comparable element type
     * @return BinarySearchResult containing match index and metrics
     */
    public static <T extends Comparable<? super T>> BinarySearchResult<T> search(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return new BinarySearchResult<>(-1, null, 0, false, true);
        }

        if (!isSorted(array)) {
            // Precondition failed: Binary search cannot guarantee correctness on unsorted data
            return new BinarySearchResult<>(-1, null, 0, false, false);
        }

        int low = 0;
        int high = array.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int cmp = array[mid].compareTo(target);

            if (cmp == 0) {
                return new BinarySearchResult<>(mid, array[mid], comparisons, true, true);
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new BinarySearchResult<>(-1, null, comparisons, false, true);
    }

    /**
     * Iterative Binary Search on a CustomDynamicArray.
     */
    public static <T extends Comparable<? super T>> BinarySearchResult<T> search(CustomDynamicArray<T> list, T target) {
        if (list == null || list.isEmpty() || target == null) {
            return new BinarySearchResult<>(-1, null, 0, false, true);
        }

        if (!isSorted(list)) {
            return new BinarySearchResult<>(-1, null, 0, false, false);
        }

        int low = 0;
        int high = list.size() - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            T midVal = list.get(mid);
            int cmp = midVal.compareTo(target);

            if (cmp == 0) {
                return new BinarySearchResult<>(mid, midVal, comparisons, true, true);
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new BinarySearchResult<>(-1, null, comparisons, false, true);
    }

    /**
     * Recursive Binary Search implementation.
     */
    public static <T extends Comparable<? super T>> BinarySearchResult<T> searchRecursive(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return new BinarySearchResult<>(-1, null, 0, false, true);
        }
        if (!isSorted(array)) {
            return new BinarySearchResult<>(-1, null, 0, false, false);
        }
        int[] comparisons = new int[]{0};
        int idx = recursiveHelper(array, target, 0, array.length - 1, comparisons);
        return new BinarySearchResult<>(idx, idx != -1 ? array[idx] : null, comparisons[0], idx != -1, true);
    }

    private static <T extends Comparable<? super T>> int recursiveHelper(T[] array, T target, int low, int high, int[] comparisons) {
        if (low > high) return -1;
        comparisons[0]++;
        int mid = low + (high - low) / 2;
        int cmp = array[mid].compareTo(target);

        if (cmp == 0) return mid;
        if (cmp < 0) return recursiveHelper(array, target, mid + 1, high, comparisons);
        return recursiveHelper(array, target, low, mid - 1, comparisons);
    }

    /**
     * COUNTEREXAMPLE RUNNER:
     * Executes binary search blindly on an unsorted array WITHOUT verifying preconditions.
     * Demonstrates how binary search fails silently and misses existing elements.
     */
    public static <T extends Comparable<? super T>> BinarySearchResult<T> searchUncheckedForCounterexample(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return new BinarySearchResult<>(-1, null, 0, false, false);
        }

        int low = 0;
        int high = array.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int cmp = array[mid].compareTo(target);

            if (cmp == 0) {
                return new BinarySearchResult<>(mid, array[mid], comparisons, true, false);
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new BinarySearchResult<>(-1, null, comparisons, false, false);
    }
}
