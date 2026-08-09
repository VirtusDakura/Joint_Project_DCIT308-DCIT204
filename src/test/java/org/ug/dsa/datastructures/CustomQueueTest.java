package org.ug.dsa.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CustomQueue (FIFO)
 */
public class CustomQueueTest {

    private CustomQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new CustomQueue<>();
    }

    @Test
    void testEnqueueAndDequeue() {
        queue.enqueue("Order1");
        queue.enqueue("Order2");
        queue.enqueue("Order3");

        assertEquals(3, queue.size());
        assertEquals("Order1", queue.dequeue());
        assertEquals("Order2", queue.dequeue());
        assertEquals("Order3", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testPeekDoesNotRemove() {
        queue.enqueue("Location A");
        assertEquals("Location A", queue.peek());
        assertEquals(1, queue.size());
        assertEquals("Location A", queue.peek());
    }

    @Test
    void testDequeueOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    @Test
    void testPeekOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    @Test
    void testEnqueueNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
    }

    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue("Item");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testClear() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        assertEquals(3, queue.size());
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testIsFull() {
        // Linked-list implementation should never be full
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        assertFalse(queue.isFull());
    }

    @Test
    void testFIFOOrder() {
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        assertEquals("First", queue.dequeue());
        assertEquals("Second", queue.dequeue());
        assertEquals("Third", queue.dequeue());
    }

    @Test
    void testWithIntegers() {
        CustomQueue<Integer> intQueue = new CustomQueue<>();
        intQueue.enqueue(10);
        intQueue.enqueue(20);
        intQueue.enqueue(30);

        assertEquals(10, intQueue.dequeue());
        assertEquals(20, intQueue.peek());
        assertEquals(2, intQueue.size());
    }
}
