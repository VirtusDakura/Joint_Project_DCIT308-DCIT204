package org.ug.dsa.algorithms.graph;

import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;

import static org.junit.jupiter.api.Assertions.*;

public class DFSCycleDetectionTest {

    @Test
    public void testAcyclicGraph() {
        CustomGraph graph = new CustomGraph();
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("B", "C", 2.0);
        graph.addEdge("C", "D", 3.0);

        DFSCycleDetection.DFSResult res = DFSCycleDetection.analyzeGraph(graph);
        assertFalse(res.hasCycle());
        assertEquals(1, res.connectedComponentsCount());
    }

    @Test
    public void testCyclicGraph() {
        CustomGraph graph = new CustomGraph();
        graph.addEdge("Hub1", "Hub2", 2.0);
        graph.addEdge("Hub2", "Hub3", 3.0);
        graph.addEdge("Hub3", "Hub1", 4.0); // Cycle: 1 -> 2 -> 3 -> 1

        DFSCycleDetection.DFSResult res = DFSCycleDetection.analyzeGraph(graph);
        assertTrue(res.hasCycle());
    }

    @Test
    public void testDisconnectedComponents() {
        CustomGraph graph = new CustomGraph();
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("C", "D", 2.0);
        graph.addVertex("Isolated");

        DFSCycleDetection.DFSResult res = DFSCycleDetection.analyzeGraph(graph);
        assertEquals(3, res.connectedComponentsCount());
    }

    @Test
    public void testIterativeTraversal() {
        CustomGraph graph = new CustomGraph();
        graph.addEdge("1", "2", 1.0);
        graph.addEdge("1", "3", 1.0);

        CustomDynamicArray<String> order = DFSCycleDetection.traverseFrom(graph, "1");
        assertEquals(3, order.size());
        assertEquals("1", order.get(0));
    }
}
