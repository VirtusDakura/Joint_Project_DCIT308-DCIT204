package org.ug.dsa.algorithms.graph;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomList;
import org.ug.dsa.datastructures.CustomQueue;

/**
 * Breadth-First Search (BFS) Reachability & Zone Coverage Engine.
 *
 * Explores the delivery network level by level from a source location.
 * Computes:
 *   1. All reachable location IDs.
 *   2. Unweighted hop distances (shortest path in terms of road hops).
 *   3. BFS traversal discovery order.
 *
 * Time Complexity: O(V + E)
 * Space Complexity: O(V) for visited array and CustomQueue.
 *
 * Strict Constraint: Uses custom data structures only (CustomQueue, CustomDynamicArray).
 */
public class BFSReachability {

    /**
     * Result wrapper for BFS exploration.
     */
    public record BFSResult(
            String sourceLocationId,
            CustomDynamicArray<String> reachableLocations,
            CustomDynamicArray<String> traversalOrder,
            int[] hopDistances,
            String[] predecessors) {
    }

    /**
     * Executes BFS traversal starting from a given source location.
     *
     * @param graph  The CustomGraph delivery network
     * @param source The starting location ID
     * @return BFSResult containing reachable nodes, hop distances, and traversal order
     */
    public static BFSResult explore(CustomGraph graph, String source) {
        if (graph == null || source == null) {
            throw new IllegalArgumentException("Graph and source must not be null.");
        }

        int numVertices = graph.getVertexCount();
        int srcIndex = graph.indexOfVertex(source);
        if (srcIndex == -1) {
            throw new IllegalArgumentException("Source location '" + source + "' not found in graph.");
        }

        boolean[] visited = new boolean[numVertices];
        int[] hops = new int[numVertices];
        String[] pred = new String[numVertices];

        for (int i = 0; i < numVertices; i++) {
            hops[i] = -1;
            pred[i] = null;
        }

        CustomDynamicArray<String> reachable = new CustomDynamicArray<>();
        CustomDynamicArray<String> order = new CustomDynamicArray<>();
        CustomQueue<Integer> queue = new CustomQueue<>();

        // Initialize source
        visited[srcIndex] = true;
        hops[srcIndex] = 0;
        queue.enqueue(srcIndex);

        while (!queue.isEmpty()) {
            int uIdx = queue.dequeue();
            String uName = graph.getAllVertices().get(uIdx);
            reachable.add(uName);
            order.add(uName);

            CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors(uName);
            for (int i = 0; i < neighbors.size(); i++) {
                CustomGraph.Edge edge = neighbors.get(i);
                int vIdx = graph.indexOfVertex(edge.getTarget());

                if (vIdx != -1 && !visited[vIdx]) {
                    visited[vIdx] = true;
                    hops[vIdx] = hops[uIdx] + 1;
                    pred[vIdx] = uName;
                    queue.enqueue(vIdx);
                }
            }
        }

        return new BFSResult(source, reachable, order, hops, pred);
    }

    /**
     * Checks if a destination is reachable from a given source.
     */
    public static boolean isReachable(CustomGraph graph, String source, String destination) {
        if (source.equals(destination)) return true;
        BFSResult res = explore(graph, source);
        for (int i = 0; i < res.reachableLocations().size(); i++) {
            if (res.reachableLocations().get(i).equals(destination)) {
                return true;
            }
        }
        return false;
    }
}
