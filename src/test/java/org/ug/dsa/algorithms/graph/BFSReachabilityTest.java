package org.ug.dsa.algorithms.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ug.dsa.datastructures.CustomGraph;

import static org.junit.jupiter.api.Assertions.*;

public class BFSReachabilityTest {

    private CustomGraph graph;

    @BeforeEach
    public void setup() {
        graph = new CustomGraph();
        graph.addVertex("Legon");
        graph.addVertex("Osu");
        graph.addVertex("Tema");
        graph.addVertex("Madina");
        graph.addVertex("IsolatedZone");

        graph.addEdge("Legon", "Madina", 5.0);
        graph.addEdge("Madina", "Tema", 10.0);
        graph.addEdge("Legon", "Osu", 12.0);
    }

    @Test
    public void testReachableNodes() {
        BFSReachability.BFSResult res = BFSReachability.explore(graph, "Legon");
        assertTrue(BFSReachability.isReachable(graph, "Legon", "Madina"));
        assertTrue(BFSReachability.isReachable(graph, "Legon", "Tema"));
        assertTrue(BFSReachability.isReachable(graph, "Legon", "Osu"));
        assertFalse(BFSReachability.isReachable(graph, "Legon", "IsolatedZone"));
    }

    @Test
    public void testHopDistances() {
        BFSReachability.BFSResult res = BFSReachability.explore(graph, "Legon");
        int legonIdx = graph.indexOfVertex("Legon");
        int madinaIdx = graph.indexOfVertex("Madina");
        int temaIdx = graph.indexOfVertex("Tema");
        int isolatedIdx = graph.indexOfVertex("IsolatedZone");

        assertEquals(0, res.hopDistances()[legonIdx]);
        assertEquals(1, res.hopDistances()[madinaIdx]);
        assertEquals(2, res.hopDistances()[temaIdx]);
        assertEquals(-1, res.hopDistances()[isolatedIdx]);
    }
}
