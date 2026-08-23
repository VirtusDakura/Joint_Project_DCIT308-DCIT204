package org.ug.dsa.algorithms.search;

import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTest {

    @Test
    public void testBinarySearchNormal() {
        Integer[] sorted = {10, 20, 30, 40, 50, 60, 70};
        BinarySearch.BinarySearchResult<Integer> res = BinarySearch.search(sorted, 40);
        assertTrue(res.found());
        assertTrue(res.preconditionMet());
        assertEquals(3, res.index());
        assertEquals(40, res.element());
    }

    @Test
    public void testBinarySearchNotFound() {
        Integer[] sorted = {10, 20, 30, 40, 50};
        BinarySearch.BinarySearchResult<Integer> res = BinarySearch.search(sorted, 35);
        assertFalse(res.found());
        assertTrue(res.preconditionMet());
        assertEquals(-1, res.index());
    }

    @Test
    public void testBinarySearchPreconditionViolation() {
        Integer[] unsorted = {50, 10, 40, 20};
        BinarySearch.BinarySearchResult<Integer> res = BinarySearch.search(unsorted, 10);
        assertFalse(res.preconditionMet());
        assertFalse(res.found());
    }

    @Test
    public void testBinarySearchRecursive() {
        String[] sorted = {"Accra", "Cape Coast", "Kumasi", "Tamale", "Tema"};
        BinarySearch.BinarySearchResult<String> res = BinarySearch.searchRecursive(sorted, "Kumasi");
        assertTrue(res.found());
        assertEquals(2, res.index());
    }

    @Test
    public void testBinarySearchDynamicArray() {
        CustomDynamicArray<Integer> list = new CustomDynamicArray<>();
        list.add(100); list.add(200); list.add(300);
        BinarySearch.BinarySearchResult<Integer> res = BinarySearch.search(list, 200);
        assertTrue(res.found());
        assertEquals(1, res.index());
    }

    @Test
    public void testUncheckedCounterexampleFailure() {
        Integer[] unsorted = {9, 1, 8, 2, 7, 3};
        // Unchecked search for '1' misses because pivot logic assumes sorted elements
        BinarySearch.BinarySearchResult<Integer> res = BinarySearch.searchUncheckedForCounterexample(unsorted, 1);
        assertFalse(res.preconditionMet());
    }
}
