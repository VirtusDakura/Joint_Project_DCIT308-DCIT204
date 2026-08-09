package org.ug.dsa.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CustomDeque (Double-Ended Queue)
 */
public class CustomDequeTest {

    private CustomDeque<String> deque;

    @BeforeEach
    void setUp() {
        deque = new CustomDeque<>();
    }

    @Test
    void testAddFrontAndRemoveFront() {
        deque.addFront("Order1");
        deque.addFront("Order2");
        deque.addFront("Order3");

        assertEquals(3, deque.size());
        assertEquals("Order3", deque.removeFront());
        assertEquals("Order2", deque.removeFront());
        assertEquals("Order1", deque.removeFront());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testAddRearAndRemoveRear() {
        deque.addRear("Location A");
        deque.addRear("Location B");
        deque.addRear("Location C");

        assertEquals(3, deque.size());
        assertEquals("Location C", deque.removeRear());
        assertEquals("Location B", deque.removeRear());
        assertEquals("Location A", deque.removeRear());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testMixedFrontAndRearOperations() {
        deque.addFront("Priority1");    // front: [Priority1]
        deque.addRear("Normal1");       // front: [Priority1, Normal1]
        deque.addFront("Priority2");    // front: [Priority2, Priority1, Normal1]
        deque.addRear("Normal2");       // front: [Priority2, Priority1, Normal1, Normal2]

        assertEquals(4, deque.size());
        assertEquals("Priority2", deque.removeFront());
        assertEquals("Normal2", deque.removeRear());
        assertEquals("Priority1", deque.removeFront());
        assertEquals("Normal1", deque.removeRear());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testPeekFrontAndPeekRear() {
        deque.addFront("Front");
        deque.addRear("Rear");

        assertEquals("Front", deque.peekFront());
        assertEquals("Rear", deque.peekRear());
        assertEquals(2, deque.size());

        // Peek should not remove
        assertEquals("Front", deque.peekFront());
        assertEquals("Rear", deque.peekRear());
        assertEquals(2, deque.size());
    }

    @Test
    void testRemoveFrontOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> deque.removeFront());
    }

    @Test
    void testRemoveRearOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> deque.removeRear());
    }

    @Test
    void testPeekFrontOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> deque.peekFront());
    }

    @Test
    void testPeekRearOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, () -> deque.peekRear());
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> deque.addFront(null));
        assertThrows(IllegalArgumentException.class, () -> deque.addRear(null));
    }

    @Test
    void testSingleElement() {
        deque.addFront("Only");
        assertEquals(1, deque.size());
        assertEquals("Only", deque.peekFront());
        assertEquals("Only", deque.peekRear());
        assertEquals("Only", deque.removeFront());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testSingleElementRemoveRear() {
        deque.addRear("Only");
        assertEquals(1, deque.size());
        assertEquals("Only", deque.peekFront());
        assertEquals("Only", deque.peekRear());
        assertEquals("Only", deque.removeRear());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testAlternatingAddOperations() {
        deque.addFront("A");
        deque.addRear("B");
        deque.addFront("C");
        deque.addRear("D");

        assertEquals(4, deque.size());
        // Order should be: C, A, B, D
        assertEquals("C", deque.removeFront());
        assertEquals("D", deque.removeRear());
        assertEquals("A", deque.removeFront());
        assertEquals("B", deque.removeRear());
    }

    @Test
    void testClear() {
        deque.addFront("A");
        deque.addRear("B");
        deque.addFront("C");
        assertEquals(3, deque.size());
        deque.clear();
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    void testEmergencyOrderInsertion() {
        // Simulate normal order queue
        deque.addRear("Order1");
        deque.addRear("Order2");
        deque.addRear("Order3");

        // Emergency order inserted at front
        deque.addFront("EMERGENCY");

        assertEquals("EMERGENCY", deque.removeFront());
        assertEquals("Order1", deque.removeFront());
    }

    @Test
    void testWithIntegers() {
        CustomDeque<Integer> intDeque = new CustomDeque<>();
        intDeque.addFront(10);
        intDeque.addRear(20);
        intDeque.addFront(5);

        assertEquals(5, intDeque.removeFront());
        assertEquals(20, intDeque.removeRear());
        assertEquals(10, intDeque.removeFront());
        assertTrue(intDeque.isEmpty());
    }
}
