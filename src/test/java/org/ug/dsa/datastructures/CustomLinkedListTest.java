package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomLinkedList Unit Tests")
class CustomLinkedListTest {

    private CustomLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
    }

    @Test
    @DisplayName("Empty list invariants")
    void testEmptyList() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertNull(list.peekFirst());
        assertNull(list.peekLast());
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
        assertThrows(NoSuchElementException.class, () -> list.removeLast());
    }

    @Test
    @DisplayName("addFirst and addLast operations")
    void testAddFirstAndLast() {
        list.addFirst("B");
        list.addFirst("A");
        list.addLast("C");

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        assertEquals("A", list.peekFirst());
        assertEquals("C", list.peekLast());
    }

    @Test
    @DisplayName("insertAfter operation")
    void testInsertAfter() {
        list.addLast("A");
        list.addLast("C");

        assertTrue(list.insertAfter("A", "B"));
        assertEquals(3, list.size());
        assertEquals("B", list.get(1));

        assertTrue(list.insertAfter("C", "D"));
        assertEquals("D", list.peekLast());

        assertFalse(list.insertAfter("NON_EXISTENT", "X"));
    }

    @Test
    @DisplayName("removeFirst, removeLast, and remove by index")
    void testRemovals() {
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");

        assertEquals("A", list.removeFirst());
        assertEquals("D", list.removeLast());
        assertEquals(2, list.size());

        assertEquals("C", list.remove(1));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));

        assertEquals("B", list.removeFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("removeElement by value")
    void testRemoveElement() {
        list.addLast("Alpha");
        list.addLast("Beta");
        list.addLast("Gamma");

        assertTrue(list.removeElement("Beta"));
        assertFalse(list.contains("Beta"));
        assertEquals(2, list.size());

        assertTrue(list.removeElement("Alpha"));
        assertEquals("Gamma", list.peekFirst());

        assertFalse(list.removeElement("Delta"));
    }

    @Test
    @DisplayName("set and contains methods")
    void testSetAndContains() {
        list.addLast("Original");
        assertTrue(list.contains("Original"));

        assertEquals("Original", list.set(0, "Updated"));
        assertEquals("Updated", list.get(0));
        assertFalse(list.contains("Original"));
        assertTrue(list.contains("Updated"));
    }

    @Test
    @DisplayName("Custom iterator traversal")
    void testIterator() {
        list.addLast("1");
        list.addLast("2");
        list.addLast("3");

        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(",");
        }
        assertEquals("1,2,3,", sb.toString());
    }

    @Test
    @DisplayName("Boundary and invalid index exceptions")
    void testInvalidOperations() {
        assertThrows(IllegalArgumentException.class, () -> list.addFirst(null));
        assertThrows(IllegalArgumentException.class, () -> list.addLast(null));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }
}
