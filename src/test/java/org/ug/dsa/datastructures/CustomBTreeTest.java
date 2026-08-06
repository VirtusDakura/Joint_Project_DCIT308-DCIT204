package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomBTreeTest {

    private CustomBTree<Integer, String> tree;

    @BeforeEach
    void setUp() {
        tree = new CustomBTree<>();
    }

    // ---------- EMPTY TREE ----------

    @Test
    void testEmptyTreeIsEmpty() {
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void testSearchOnEmptyTreeReturnsNull() {
        assertNull(tree.search(10));
    }

    // ---------- SINGLE NODE ----------

    @Test
    void testInsertSingleKey() {
        tree.insert(10, "Balme Library");

        assertEquals(1, tree.size());
        assertEquals("Balme Library", tree.search(10));
        assertEquals(0, tree.height()); // single leaf root
    }

    // ---------- DUPLICATE KEY ----------

    @Test
    void testInsertDuplicateKeyOverwritesValue() {
        tree.insert(10, "UG Hospital");
        tree.insert(10, "Legon Hospital");

        assertEquals(1, tree.size());
        assertEquals("Legon Hospital", tree.search(10));
    }

    // ---------- SEARCH HIT/MISS ----------

    @Test
    void testSearchHit() {
        tree.insert(50, "Legon Hall");
        tree.insert(30, "Commonwealth Hall");
        tree.insert(70, "Volta Hall");

        assertEquals("Commonwealth Hall", tree.search(30));
    }

    @Test
    void testSearchMiss() {
        tree.insert(50, "Legon Hall");
        tree.insert(30, "Commonwealth Hall");

        assertNull(tree.search(999));
    }

    // ---------- SORTED INORDER TRAVERSAL ----------

    @Test
    void testInorderTraversalReturnsSortedKeys() {
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10, 90};
        for (int k : keys) {
            tree.insert(k, "Location-" + k);
        }

        CustomList<Integer> traversal = tree.inorderTraversal();
        int[] expected = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        assertEquals(expected.length, traversal.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], traversal.get(i));
        }
    }

    // ---------- NODE SPLITTING (core checklist requirement) ----------
    // With t = 3, MAX_KEYS = 2t-1 = 5. Inserting a 6th key into a single
    // leaf root MUST trigger a split: the root goes from holding 5 keys
    // directly to holding just 1 key (the pushed-up middle), now pointing
    // to two children instead of being a leaf.

    @Test
    void testRootSplitsWhenOverflowing() {
        // Insert exactly MAX_KEYS (5) keys: root should still be a single
        // leaf-like node holding all 5 keys, no split yet.
        for (int i = 1; i <= 5; i++) {
            tree.insert(i, "Location-" + i);
        }
        assertEquals(5, tree.getRootKeyCount(), "Root should hold all 5 keys before overflow");

        // Insert the 6th key: this must trigger a split.
        tree.insert(6, "Location-6");

        assertEquals(1, tree.getRootKeyCount(),
            "After splitting, the root should hold only the single pushed-up middle key");
        assertEquals(6, tree.size());

        // All 6 keys must still be findable after the split
        for (int i = 1; i <= 6; i++) {
            assertEquals("Location-" + i, tree.search(i));
        }
    }

    @Test
    void testMultipleSplitsKeepTreeSearchable() {
        // Insert enough keys to force multiple splits at different levels
        for (int i = 1; i <= 30; i++) {
            tree.insert(i, "Location-" + i);
        }

        assertEquals(30, tree.size());

        // Every key must still be findable after repeated splitting
        for (int i = 1; i <= 30; i++) {
            assertEquals("Location-" + i, tree.search(i));
        }

        // Sorted order must be preserved despite all the splitting/restructuring
        CustomList<Integer> traversal = tree.inorderTraversal();
        for (int i = 0; i < traversal.size(); i++) {
            assertEquals(i + 1, traversal.get(i));
        }
    }

    // ---------- MINIMUM DEGREE (index-number-derived parameter) ----------

    @Test
    void testMinimumDegreeMatchesIndexDerivedValue() {
        // t = 3 + (22120404 % 3) = 3 + 0 = 3
        assertEquals(3, tree.getMinDegree());
    }

    // ---------- HEIGHT GROWS SLOWLY (evidence for height comparison) ----------

    @Test
    void testHeightStaysLowWithManyInserts() {
        for (int i = 1; i <= 30; i++) {
            tree.insert(i, "Location-" + i);
        }

        // With t=3 (up to 5 keys/6 children per node), 30 keys should fit
        // in a very shallow tree — height should be small (2 or less).
        assertTrue(tree.height() <= 2,
            "B-Tree height " + tree.height() + " is higher than expected for 30 keys with t=3");
    }
}