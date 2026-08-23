package org.ug.dsa.algorithms.search;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomList;

import java.util.function.Predicate;

/**
 * Linear Search Algorithm Implementation.
 * 
 * Sequentially inspects elements until a match is found or the end of the collection is reached.
 * Time Complexity:
 *   - Best Case: O(1) [Element at first position]
 *   - Average Case: O(n)
 *   - Worst Case: O(n) [Element at last position or absent]
 * Space Complexity: O(1) auxiliary space.
 * 
 * Strict Constraint: Uses custom data structures only.
 */
public class LinearSearch {

    /**
     * Result wrapper that captures index and primitive comparison count.
     */
    public record SearchResult<T>(int index, T element, int comparisonCount, boolean found) {
    }

    /**
     * Searches for a target value in a standard array.
     *
     * @param array  The input array to search
     * @param target The target value to find
     * @param <T>    The generic element type
     * @return SearchResult containing match index and comparison count
     */
    public static <T> SearchResult<T> search(T[] array, T target) {
        if (array == null || array.length == 0) {
            return new SearchResult<>(-1, null, 0, false);
        }

        int comparisons = 0;
        for (int i = 0; i < array.length; i++) {
            comparisons++;
            if (target == null) {
                if (array[i] == null) {
                    return new SearchResult<>(i, array[i], comparisons, true);
                }
            } else if (target.equals(array[i])) {
                return new SearchResult<>(i, array[i], comparisons, true);
            }
        }

        return new SearchResult<>(-1, null, comparisons, false);
    }

    /**
     * Searches for a target value in a CustomDynamicArray.
     *
     * @param list   The CustomDynamicArray to search
     * @param target The target value to find
     * @param <T>    The generic element type
     * @return SearchResult containing match index and comparison count
     */
    public static <T> SearchResult<T> search(CustomDynamicArray<T> list, T target) {
        if (list == null || list.isEmpty()) {
            return new SearchResult<>(-1, null, 0, false);
        }

        int comparisons = 0;
        for (int i = 0; i < list.size(); i++) {
            comparisons++;
            T current = list.get(i);
            if (target == null) {
                if (current == null) {
                    return new SearchResult<>(i, current, comparisons, true);
                }
            } else if (target.equals(current)) {
                return new SearchResult<>(i, current, comparisons, true);
            }
        }

        return new SearchResult<>(-1, null, comparisons, false);
    }

    /**
     * Searches for the first element matching a predicate in a CustomDynamicArray.
     *
     * @param list      The CustomDynamicArray to search
     * @param predicate The matching condition
     * @param <T>       The generic element type
     * @return SearchResult containing match index and comparison count
     */
    public static <T> SearchResult<T> findFirst(CustomDynamicArray<T> list, Predicate<T> predicate) {
        if (list == null || list.isEmpty() || predicate == null) {
            return new SearchResult<>(-1, null, 0, false);
        }

        int comparisons = 0;
        for (int i = 0; i < list.size(); i++) {
            comparisons++;
            T current = list.get(i);
            if (predicate.test(current)) {
                return new SearchResult<>(i, current, comparisons, true);
            }
        }

        return new SearchResult<>(-1, null, comparisons, false);
    }

    /**
     * Finds all indices matching a target in a CustomDynamicArray.
     *
     * @param list   The CustomDynamicArray to search
     * @param target The target value to find
     * @param <T>    The generic element type
     * @return CustomDynamicArray of matching integer indices
     */
    public static <T> CustomDynamicArray<Integer> findAll(CustomDynamicArray<T> list, T target) {
        CustomDynamicArray<Integer> indices = new CustomDynamicArray<>();
        if (list == null || list.isEmpty()) {
            return indices;
        }

        for (int i = 0; i < list.size(); i++) {
            T current = list.get(i);
            if (target == null) {
                if (current == null) indices.add(i);
            } else if (target.equals(current)) {
                indices.add(i);
            }
        }

        return indices;
    }
}
