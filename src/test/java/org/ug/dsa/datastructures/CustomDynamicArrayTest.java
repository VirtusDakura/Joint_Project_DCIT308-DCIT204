package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomDynamicArray Tests")
class CustomDynamicArrayTest {

    private CustomDynamicArray<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomDynamicArray<>();
    }

    @Test
    @DisplayName("Should initialize empty list with initial capacity 4")
    void testInitialization() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(4, list.capacity());
    }

    @Test
    @DisplayName("Should add elements and double capacity when full")
    void testAddAndResizeUp() {
        assertEquals(4, list.capacity());
        list.add("Legon Hall");
        list.add("Akuafo Hall");
        list.add("Volta Hall");
        list.add("Commonwealth Hall");

        assertEquals(4, list.size());
        assertEquals(4, list.capacity());

        // 5th element triggers resize from 4 to 8
        list.add("Mensah Sarbah Hall");
        assertEquals(5, list.size());
        assertEquals(8, list.capacity());
        assertEquals("Mensah Sarbah Hall", list.get(4));
    }

    @Test
    @DisplayName("Should insert elements at head, middle, and tail")
    void testInsert() {
        list.add("Point A");
        list.add("Point C");

        // Insert in middle
        list.insert(1, "Point B");
        assertEquals("Point A", list.get(0));
        assertEquals("Point B", list.get(1));
        assertEquals("Point C", list.get(2));
        assertEquals(3, list.size());

        // Insert at head
        list.insert(0, "Start");
        assertEquals("Start", list.get(0));

        // Insert at tail
        list.insert(list.size(), "End");
        assertEquals("End", list.get(list.size() - 1));
    }

    @Test
    @DisplayName("Should update elements via set and retrieve via get")
    void testSetAndGet() {
        list.add("Papaye");
        list.add("Bush Canteen");

        String oldVal = list.set(1, "Buka Restaurant");
        assertEquals("Bush Canteen", oldVal);
        assertEquals("Buka Restaurant", list.get(1));
    }

    @Test
    @DisplayName("Should remove elements and shrink capacity when size <= 25% of capacity")
    void testRemoveAndResizeDown() {
        for (int i = 0; i < 9; i++) {
            list.add("Item " + i);
        }
        // Capacity expanded to 16
        assertEquals(9, list.size());
        assertEquals(16, list.capacity());

        // Remove down to 4 items (25% of 16)
        for (int i = 8; i >= 4; i--) {
            list.remove(i);
        }
        assertEquals(4, list.size());
        // Capacity shrinks from 16 to 8
        assertEquals(8, list.capacity());
    }

    @Test
    @DisplayName("Should throw IndexOutOfBoundsException for invalid indices")
    void testIndexOutOfBoundsExceptions() {
        list.add("Location 1");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, "Invalid"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(10, "Invalid"));
    }

    @Test
    @DisplayName("Should clear all elements and reset size")
    void testClear() {
        list.add("Order 1");
        list.add("Order 2");
        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(4, list.capacity());
    }

    @Test
    @DisplayName("Should iterate through elements using for-each loop")
    void testIterator() {
        list.add("Osu");
        list.add("Spintex");
        list.add("East Legon");

        StringBuilder sb = new StringBuilder();
        for (String location : list) {
            sb.append(location).append(",");
        }

        assertEquals("Osu,Spintex,East Legon,", sb.toString());
    }
}
