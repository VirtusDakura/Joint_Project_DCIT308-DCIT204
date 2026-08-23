package org.ug.dsa.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraAlgorithmTest {

    private CustomGraph graph;

    @BeforeEach
    public void setup() {
        graph = new CustomGraph();
        graph.addEdge("A", "B", 4.0);
        graph.addEdge("A", "C", 2.0);
        graph.addEdge("C", "B", 1.0);
        graph.addEdge("B", "D", 5.0);
        graph.addEdge("C", "D", 8.0);
        graph.addVertex("Unreachable");
    }

    @Test
    public void testShortestDistances() {
        DijkstraAlgorithm.ShortestPathResult res = DijkstraAlgorithm.computeShortestPaths(graph, "A");
        assertEquals(0.0, res.getDistanceTo("A"), 0.001);
        assertEquals(2.0, res.getDistanceTo("C"), 0.001);
        // A -> C (2.0) + C -> B (1.0) = 3.0 (faster than direct A -> B which is 4.0)
        assertEquals(3.0, res.getDistanceTo("B"), 0.001);
        assertEquals(8.0, res.getDistanceTo("D"), 0.001); // A -> C -> B -> D (2+1+5 = 8)
        assertEquals(Double.POSITIVE_INFINITY, res.getDistanceTo("Unreachable"));
    }

    @Test
    public void testPathReconstruction() {
        DijkstraAlgorithm.ShortestPathResult res = DijkstraAlgorithm.computeShortestPaths(graph, "A");
        CustomDynamicArray<String> path = res.getPathTo("B");
        assertEquals(3, path.size());
        assertEquals("A", path.get(0));
        assertEquals("C", path.get(1));
        assertEquals("B", path.get(2));
    }

    @Test
    public void testDistanceTableRender() {
        DijkstraAlgorithm.ShortestPathResult res = DijkstraAlgorithm.computeShortestPaths(graph, "A");
        String table = res.renderDistanceTable();
        assertNotNull(table);
        assertTrue(table.contains("Location"));
        assertTrue(table.contains("A"));
        assertTrue(table.contains("B"));
    }
}
