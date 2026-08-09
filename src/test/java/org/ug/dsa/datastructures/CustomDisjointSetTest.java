package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomDisjointSetTest {

    private CustomDisjointSet ds;

    @BeforeEach
    void setUp() {
        ds = new CustomDisjointSet(10); // elements 0..9
    }

    @Test
    void singleElementSet_isItsOwnRootAndComponent() {
        assertEquals(10, ds.getComponentCount());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, ds.find(i), "Element " + i + " should be its own root initially");
        }
    }

    @Test
    void makeSet_resetsElementToSingleton() {
        ds.union(3, 4);
        assertTrue(ds.connected(3, 4));

        ds.makeSet(3);
        assertFalse(ds.connected(3, 4), "After makeSet, 3 should no longer be connected to 4's old set");
    }

    @Test
    void unionOfTwoSets_makesThemConnected() {
        assertFalse(ds.connected(1, 2));

        boolean merged = ds.union(1, 2);

        assertTrue(merged);
        assertTrue(ds.connected(1, 2));
        assertEquals(ds.find(1), ds.find(2));
    }

    @Test
    void unionOfAlreadyConnectedElements_returnsFalse_noCycleFormed() {
        ds.union(1, 2);
        ds.union(2, 3);

        boolean merged = ds.union(1, 3);

        assertFalse(merged, "Union of already-connected elements should return false (cycle detected)");
        assertTrue(ds.connected(1, 3));
    }

    @Test
    void componentCount_decreasesOnlyOnSuccessfulUnion() {
        assertEquals(10, ds.getComponentCount());

        ds.union(0, 1);
        assertEquals(9, ds.getComponentCount());

        ds.union(2, 3);
        assertEquals(8, ds.getComponentCount());

        ds.union(0, 3);
        assertEquals(7, ds.getComponentCount());

        ds.union(1, 2);
        assertEquals(7, ds.getComponentCount(), "Component count should not change on a no-op union");
    }

    @Test
    void connectedComponents_afterSeveralUnions() {
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(5, 6);

        assertTrue(ds.connected(0, 2));
        assertTrue(ds.connected(5, 6));
        assertFalse(ds.connected(0, 5));
        assertFalse(ds.connected(3, 4));

        assertEquals(7, ds.getComponentCount());
    }

    @Test
    void pathCompression_flattensChainToRoot() {
        ds.union(0, 1);
        ds.union(1, 2);
        ds.union(2, 3);
        ds.union(3, 4);

        int root = ds.find(4);

        assertEquals(root, ds.find(0));
        assertEquals(root, ds.find(1));
        assertEquals(root, ds.find(2));
        assertEquals(root, ds.find(3));
        assertEquals(root, ds.find(4));
    }

    @Test
    void find_onInvalidIndex_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ds.find(-1));
        assertThrows(IllegalArgumentException.class, () -> ds.find(10));
    }

    @Test
    void kruskalStyleTrace_skipsEdgesThatWouldFormCycle() {
        int[][] edges = {
                {0, 1},
                {1, 2},
                {2, 3},
                {0, 2},
                {3, 4}
        };

        CustomDisjointSet kruskalDs = new CustomDisjointSet(5);
        int edgesAccepted = 0;

        for (int[] edge : edges) {
            if (kruskalDs.union(edge[0], edge[1])) {
                edgesAccepted++;
            }
        }

        assertEquals(4, edgesAccepted);
        assertEquals(1, kruskalDs.getComponentCount());
    }
}
