package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomBST Unit Tests")
class CustomBSTTest {

    private CustomBST<Integer, String> bst;

    @BeforeEach
    void setUp() {
        bst = new CustomBST<>();
    }

    @Test
    @DisplayName("Empty BST invariants")
    void testEmptyTree() {
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        assertEquals(-1, bst.height());
        assertNull(bst.search(10));
        assertNull(bst.minimum());
        assertNull(bst.maximum());
    }

    @Test
    @DisplayName("Insert, search, and update existing key")
    void testInsertAndSearch() {
        bst.insert(50, "Root");
        bst.insert(30, "Left");
        bst.insert(70, "Right");

        assertEquals(3, bst.size());
        assertEquals("Root", bst.search(50));
        assertEquals("Left", bst.search(30));
        assertEquals("Right", bst.search(70));
        assertNull(bst.search(999));

        // Overwrite key
        bst.insert(50, "UpdatedRoot");
        assertEquals(3, bst.size());
        assertEquals("UpdatedRoot", bst.search(50));
    }

    @Test
    @DisplayName("Inorder traversal returns keys in sorted ascending order")
    void testInorderTraversal() {
        int[] keys = {50, 20, 80, 10, 30, 60, 90};
        for (int k : keys) {
            bst.insert(k, "Val-" + k);
        }

        CustomList<Integer> sorted = bst.inorderTraversal();
        assertEquals(7, sorted.size());

        int[] expected = {10, 20, 30, 50, 60, 70, 80, 90};
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i) < sorted.get(i + 1), "Inorder must be strictly ascending");
        }
        assertEquals(10, bst.minimum());
        assertEquals(90, bst.maximum());
    }

    @Test
    @DisplayName("Delete leaf node, single-child node, and two-children node")
    void testDeleteCases() {
        // Build tree:
        //        50
        //      /    \
        //     30     70
        //    /  \      \
        //   20  40      80
        bst.insert(50, "V50");
        bst.insert(30, "V30");
        bst.insert(70, "V70");
        bst.insert(20, "V20");
        bst.insert(40, "V40");
        bst.insert(80, "V80");

        // 1. Delete leaf node (20)
        assertEquals("V20", bst.delete(20));
        assertNull(bst.search(20));
        assertEquals(5, bst.size());

        // 2. Delete single-child node (70 has only right child 80)
        assertEquals("V70", bst.delete(70));
        assertNull(bst.search(70));
        assertEquals("V80", bst.search(80));
        assertEquals(4, bst.size());

        // 3. Delete two-children root node (50)
        assertEquals("V50", bst.delete(50));
        assertNull(bst.search(50));
        assertEquals(3, bst.size());
        assertEquals("V30", bst.search(30));
        assertEquals("V40", bst.search(40));
        assertEquals("V80", bst.search(80));
    }

    @Test
    @DisplayName("Height calculation")
    void testHeight() {
        bst.insert(50, "A");
        assertEquals(0, bst.height());

        bst.insert(30, "B");
        assertEquals(1, bst.height());

        bst.insert(20, "C");
        assertEquals(2, bst.height());
    }
}
