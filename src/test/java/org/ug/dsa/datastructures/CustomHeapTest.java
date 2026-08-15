package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomHeap (Min-Heap) Unit Tests")
class CustomHeapTest {

    private CustomHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new CustomHeap<>();
    }

    @Test
    @DisplayName("Empty heap invariants")
    void testEmptyHeap() {
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertThrows(NoSuchElementException.class, () -> heap.peekMin());
        assertThrows(NoSuchElementException.class, () -> heap.extractMin());
    }

    @Test
    @DisplayName("Insert and extractMin in sorted ascending order")
    void testInsertAndExtractOrder() {
        heap.insert(40);
        heap.insert(10);
        heap.insert(30);
        heap.insert(5);
        heap.insert(20);

        assertEquals(5, heap.size());
        assertEquals(5, heap.peekMin());

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("Bottom-up O(n) heapify on unsorted array")
    void testHeapify() {
        Integer[] unsorted = {99, 12, 45, 3, 67, 1, 88};
        CustomHeap<Integer> h = CustomHeap.heapify(unsorted);

        assertEquals(7, h.size());
        assertEquals(1, h.extractMin());
        assertEquals(3, h.extractMin());
        assertEquals(12, h.extractMin());
        assertEquals(45, h.extractMin());
        assertEquals(67, h.extractMin());
        assertEquals(88, h.extractMin());
        assertEquals(99, h.extractMin());
    }

    @Test
    @DisplayName("Auto-resizing when inserting beyond initial capacity")
    void testAutoResize() {
        for (int i = 50; i >= 1; i--) {
            heap.insert(i);
        }
        assertEquals(50, heap.size());
        assertEquals(1, heap.peekMin());

        for (int expected = 1; expected <= 50; expected++) {
            assertEquals(expected, heap.extractMin());
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    @DisplayName("Null item insertion throws IllegalArgumentException")
    void testNullInsert() {
        assertThrows(IllegalArgumentException.class, () -> heap.insert(null));
    }
}
