package org.ug.dsa.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomGraph Unit Tests")
class CustomGraphTest {

    private CustomGraph graph;

    @BeforeEach
    void setUp() {
        graph = new CustomGraph();
    }

    @Test
    @DisplayName("Should initialize an empty graph")
    void testEmptyGraph() {
        assertEquals(0, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
        assertTrue(graph.getAllVertices().isEmpty());
    }

    @Test
    @DisplayName("Should add vertices without duplicate indices")
    void testAddVertices() {
        int idx1 = graph.addVertex("Legon Hall");
        int idx2 = graph.addVertex("Commonwealth Hall");
        int idx3 = graph.addVertex("Legon Hall"); // Duplicate

        assertEquals(0, idx1);
        assertEquals(1, idx2);
        assertEquals(0, idx3);
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    @DisplayName("Should add directed edges and update both Adjacency List and Matrix")
    void testAddDirectedEdge() {
        graph.addEdge("Legon Hall", "Night Market", 1.5);

        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
        assertTrue(graph.hasEdge("Legon Hall", "Night Market"));
        assertFalse(graph.hasEdge("Night Market", "Legon Hall"));

        assertEquals(1.5, graph.getEdgeWeight("Legon Hall", "Night Market"));
        assertEquals(CustomGraph.NO_EDGE, graph.getEdgeWeight("Night Market", "Legon Hall"));
    }

    @Test
    @DisplayName("Should add undirected edges in both directions")
    void testAddUndirectedEdge() {
        graph.addUndirectedEdge("Bush Canteen", "JAG Hall", 2.3);

        assertEquals(2, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertTrue(graph.hasEdge("Bush Canteen", "JAG Hall"));
        assertTrue(graph.hasEdge("JAG Hall", "Bush Canteen"));
        assertEquals(2.3, graph.getEdgeWeight("Bush Canteen", "JAG Hall"));
        assertEquals(2.3, graph.getEdgeWeight("JAG Hall", "Bush Canteen"));
    }

    @Test
    @DisplayName("Should retrieve correct neighbors for a vertex")
    void testGetNeighbors() {
        graph.addEdge("Madina Market", "Atomic Junction", 3.0);
        graph.addEdge("Madina Market", "Legon Campus", 4.5);

        CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors("Madina Market");
        assertEquals(2, neighbors.size());
        assertEquals("Atomic Junction", neighbors.get(0).getTarget());
        assertEquals(3.0, neighbors.get(0).getWeight());
        assertEquals("Legon Campus", neighbors.get(1).getTarget());
        assertEquals(4.5, neighbors.get(1).getWeight());
    }

    @Test
    @DisplayName("Should handle disconnected vertices and self loops correctly")
    void testDisconnectedGraphAndSelfLoop() {
        graph.addVertex("Isolated Node");
        assertEquals(1, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());

        graph.addEdge("Isolated Node", "Isolated Node", 0.0);
        assertEquals(0.0, graph.getEdgeWeight("Isolated Node", "Isolated Node"));
    }

    @Test
    @DisplayName("Should render side-by-side adjacency list and matrix string without errors")
    void testSideBySideRendering() {
        graph.addEdge("NodeA", "NodeB", 5.0);
        graph.addEdge("NodeB", "NodeC", 10.0);

        String output = graph.getAdjacencyListAndMatrixSideBySide();
        assertNotNull(output);
        assertTrue(output.contains("ADJACENCY LIST"));
        assertTrue(output.contains("ADJACENCY MATRIX"));
        assertTrue(output.contains("NodeA"));
        assertTrue(output.contains("NodeB"));
        assertTrue(output.contains("NodeC"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding null or empty vertex")
    void testInvalidVertexInput() {
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(null));
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex("   "));
    }
}
