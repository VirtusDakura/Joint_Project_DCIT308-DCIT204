package org.ug.dsa.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CustomCircularQueue with wrap-around
 */
public class CustomCircularQueueTest {

    private CustomCircularQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new CustomCircularQueue<>(5);
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
    void testWrapAroundEnqueue() {
        // Fill the circular queue
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");

        assertTrue(queue.isFull());
        assertEquals(5, queue.size());

        // Dequeue some elements
        queue.dequeue();
        queue.dequeue();

        // Enqueue new elements (should wrap around)
        queue.enqueue("F");
        queue.enqueue("G");

        assertEquals(5, queue.size());
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
        assertEquals("E", queue.dequeue());
        assertEquals("F", queue.dequeue());
        assertEquals("G", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testPeekDoesNotRemove() {
        queue.enqueue("First");
        assertEquals("First", queue.peek());
        assertEquals(1, queue.size());
        assertEquals("First", queue.peek());
    }

    @Test
    void testIsFull() {
        assertFalse(queue.isFull());
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        assertTrue(queue.isFull());
    }

    @Test
    void testEnqueueOnFullThrowsException() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        assertThrows(IllegalStateException.class, () -> queue.enqueue("F"));
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
    void testCapacity() {
        assertEquals(5, queue.capacity());
    }

    @Test
    void testGetFrontAndRearPointers() {
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals(0, queue.getFront());
        assertEquals(1, queue.getRear());

        queue.dequeue();
        assertEquals(1, queue.getFront());

        queue.enqueue("C");
        assertEquals(1, queue.getFront());
        assertEquals(2, queue.getRear());
    }

    @Test
    void testClear() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testSmallCapacity() {
        CustomCircularQueue<Integer> smallQueue = new CustomCircularQueue<>(2);
        smallQueue.enqueue(1);
        smallQueue.enqueue(2);
        assertTrue(smallQueue.isFull());

        assertEquals(1, smallQueue.dequeue());
        assertFalse(smallQueue.isFull());

        smallQueue.enqueue(3);
        assertEquals(2, smallQueue.dequeue());
        assertEquals(3, smallQueue.dequeue());
        assertTrue(smallQueue.isEmpty());
    }

    @Test
    void testInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new CustomCircularQueue<>(0));
        assertThrows(IllegalArgumentException.class, () -> new CustomCircularQueue<>(-1));
    }
}
