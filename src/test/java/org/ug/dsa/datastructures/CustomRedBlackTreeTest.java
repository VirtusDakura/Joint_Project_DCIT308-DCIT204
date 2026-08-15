package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomRedBlackTree Unit Tests")
class CustomRedBlackTreeTest {

    private CustomRedBlackTree<Integer, String> rbTree;

    @BeforeEach
    void setUp() {
        rbTree = new CustomRedBlackTree<>();
    }

    @Test
    @DisplayName("Empty tree invariants")
    void testEmptyTree() {
        assertTrue(rbTree.isEmpty());
        assertEquals(0, rbTree.size());
        assertEquals(-1, rbTree.height());
        assertNull(rbTree.search(10));
    }

    @Test
    @DisplayName("Insert, search, and update in Red-Black tree")
    void testInsertAndSearch() {
        rbTree.insert(10, "Ten");
        rbTree.insert(20, "Twenty");
        rbTree.insert(30, "Thirty");

        assertEquals(3, rbTree.size());
        assertEquals("Ten", rbTree.search(10));
        assertEquals("Twenty", rbTree.search(20));
        assertEquals("Thirty", rbTree.search(30));
        assertNull(rbTree.search(99));

        // Overwrite existing
        rbTree.insert(20, "Twenty-Updated");
        assertEquals(3, rbTree.size());
        assertEquals("Twenty-Updated", rbTree.search(20));
    }

    @Test
    @DisplayName("Self-balancing ensures logarithmic height under sequential ascending insertion")
    void testSelfBalancingHeight() {
        // In a plain BST, inserting 1..15 in order yields height 14 (degenerate linked list).
        // In a Red-Black Tree, height is bounded by 2 * log2(N + 1) <= 2 * 4 = 8.
        for (int i = 1; i <= 15; i++) {
            rbTree.insert(i, "V-" + i);
        }

        assertEquals(15, rbTree.size());
        assertTrue(rbTree.height() <= 6, "RB Tree height must remain bounded (actual: " + rbTree.height() + ")");
        assertTrue(rbTree.blackHeight() > 0, "Black height must be positive");
    }

    @Test
    @DisplayName("Inorder traversal produces sorted ascending elements")
    void testInorder() {
        int[] data = {45, 12, 89, 34, 7, 23, 67, 1, 99};
        for (int v : data) {
            rbTree.insert(v, "Val" + v);
        }

        CustomList<Integer> sorted = rbTree.inorderTraversal();
        assertEquals(data.length, sorted.size());

        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i) < sorted.get(i + 1));
        }
    }

    @Test
    @DisplayName("Null key insertion throws IllegalArgumentException")
    void testNullInsert() {
        assertThrows(IllegalArgumentException.class, () -> rbTree.insert(null, "Val"));
    }
}
