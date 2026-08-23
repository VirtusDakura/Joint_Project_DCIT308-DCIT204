package org.ug.dsa.algorithms.search;

import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;

import static org.junit.jupiter.api.Assertions.*;

public class LinearSearchTest {

    @Test
    public void testSearchArrayNormal() {
        String[] data = {"Legon", "Osu", "Tema", "Madina"};
        LinearSearch.SearchResult<String> res = LinearSearch.search(data, "Tema");
        assertTrue(res.found());
        assertEquals(2, res.index());
        assertEquals("Tema", res.element());
        assertEquals(3, res.comparisonCount());
    }

    @Test
    public void testSearchArrayNotFound() {
        String[] data = {"Legon", "Osu", "Tema"};
        LinearSearch.SearchResult<String> res = LinearSearch.search(data, "Kumasi");
        assertFalse(res.found());
        assertEquals(-1, res.index());
        assertEquals(3, res.comparisonCount());
    }

    @Test
    public void testSearchCustomDynamicArray() {
        CustomDynamicArray<Integer> list = new CustomDynamicArray<>();
        list.add(10); list.add(20); list.add(30);
        LinearSearch.SearchResult<Integer> res = LinearSearch.search(list, 20);
        assertTrue(res.found());
        assertEquals(1, res.index());
    }

    @Test
    public void testSearchEmptyAndNull() {
        String[] empty = new String[0];
        assertFalse(LinearSearch.search(empty, "Any").found());
        assertFalse(LinearSearch.search((String[]) null, "Any").found());
    }

    @Test
    public void testFindAllOccurrences() {
        CustomDynamicArray<String> list = new CustomDynamicArray<>();
        list.add("Accra"); list.add("Kumasi"); list.add("Accra");
        CustomDynamicArray<Integer> indices = LinearSearch.findAll(list, "Accra");
        assertEquals(2, indices.size());
        assertEquals(0, indices.get(0));
        assertEquals(2, indices.get(1));
    }
}
