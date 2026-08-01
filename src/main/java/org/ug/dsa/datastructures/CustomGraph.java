package org.ug.dsa.datastructures;

import org.ug.dsa.models.Location;
import org.ug.dsa.models.Road;

/**
 * Custom Weighted Graph representing the local Ghanaian delivery road network.
 * Must support both Adjacency List and Adjacency Matrix representations.
 *
 * Assigned to: Virtus Dakura (22052950)
 *
 * Required operations:
 *   - addVertex(String locationId)
 *   - addEdge(String from, String to, double weight)
 *   - addUndirectedEdge(String from, String to, double weight)
 *   - getEdgeWeight(String from, String to)
 *   - getNeighbours(String locationId)
 *   - getVertexCount(), getEdgeCount()
 *   - hasEdge(String from, String to)
 *   - getAllVertices()
 *
 * This graph is used by the DCIT 204 team's BFS, DFS, Dijkstra, Prim, and Kruskal implementations.
 *
 * Evidence to produce:
 *   - Print adjacency list and adjacency matrix side-by-side for a small example
 *   - Unit tests for empty graph, single vertex, self-loop, disconnected components
 */
public class CustomGraph {

    // TODO: Implement vertex storage, edge storage (adjacency list AND/OR matrix),
    //       and all required methods.
    // Hint: You can use CustomHashTable for vertex lookups and store edge lists.

}
