package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomStackTest {

    private CustomStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new CustomStack<>();
    }

    @Test
    void testPushAndPop() {
        stack.push("Balme Library");
        stack.push("UG Hospital");

        assertEquals(2, stack.size());
        assertEquals("UG Hospital", stack.pop());
        assertEquals("Balme Library", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        assertThrows(IllegalStateException.class, () -> stack.pop());
    }
}
