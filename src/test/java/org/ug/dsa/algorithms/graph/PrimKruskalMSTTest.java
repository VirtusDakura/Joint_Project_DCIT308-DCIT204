package org.ug.dsa.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomGraph;

import static org.junit.jupiter.api.Assertions.*;

public class PrimKruskalMSTTest {

    private CustomGraph graph;

    @BeforeEach
    public void setup() {
        graph = new CustomGraph();
        // 4-vertex diamond graph:
        // A -1- B
        // A -3- C
        // B -2- D
        // C -4- D
        // B -5- C
        graph.addUndirectedEdge("A", "B", 1.0);
        graph.addUndirectedEdge("A", "C", 3.0);
        graph.addUndirectedEdge("B", "D", 2.0);
        graph.addUndirectedEdge("C", "D", 4.0);
        graph.addUndirectedEdge("B", "C", 5.0);
    }

    @Test
    public void testKruskalMST() {
        PrimKruskalMST.MSTResult result = PrimKruskalMST.kruskalMST(graph);
        assertTrue(result.isFullyConnected());
        assertEquals(3, result.mstEdges().size()); // V - 1 = 4 - 1 = 3 edges
        // Expected MST edges: A-B (1.0), B-D (2.0), A-C (3.0) -> Total weight = 6.0
        assertEquals(6.0, result.totalCost(), 0.001);
    }

    @Test
    public void testPrimMST() {
        PrimKruskalMST.MSTResult result = PrimKruskalMST.primMST(graph, "A");
        assertTrue(result.isFullyConnected());
        assertEquals(3, result.mstEdges().size());
        assertEquals(6.0, result.totalCost(), 0.001);
    }

    @Test
    public void testDisconnectedGraphMST() {
        CustomGraph disconnected = new CustomGraph();
        disconnected.addUndirectedEdge("A", "B", 1.0);
        disconnected.addVertex("IsolatedNode");

        PrimKruskalMST.MSTResult result = PrimKruskalMST.kruskalMST(disconnected);
        assertFalse(result.isFullyConnected());
        assertEquals(1, result.mstEdges().size());
    }
}
