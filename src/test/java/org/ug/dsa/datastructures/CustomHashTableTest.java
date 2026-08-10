package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomHashTableTest {

    private CustomHashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new CustomHashTable<>(4);
    }

    @Test
    void testPutAndGet() {
        table.put("one", 1);
        table.put("two", 2);
        assertEquals(1, table.get("one"));
        assertEquals(2, table.get("two"));
    }

    @Test
    void testDuplicateKeyUpdate() {
        table.put("one", 1);
        table.put("one", 11);
        assertEquals(11, table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testRemove() {
        table.put("one", 1);
        table.put("two", 2);
        assertEquals(1, table.remove("one"));
        assertNull(table.get("one"));
        assertEquals(1, table.size());
    }

    @Test
    void testKeyNotFound() {
        assertNull(table.get("missing"));
    }

    @Test
    void testResize() {
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);
        table.put("four", 4);
        table.put("five", 5); 
        assertEquals(5, table.size());
        assertEquals(5, table.get("five"));
        assertEquals(1, table.get("one"));
    }

    @Test
    void testKeys() {
        table.put("a", 1);
        table.put("b", 2);
        CustomList<String> keys = table.keys();
        assertEquals(2, keys.size());
        boolean foundA = false;
        boolean foundB = false;
        for (String key : keys) {
            if (key.equals("a")) foundA = true;
            if (key.equals("b")) foundB = true;
        }
        assertTrue(foundA && foundB);
    }
}
