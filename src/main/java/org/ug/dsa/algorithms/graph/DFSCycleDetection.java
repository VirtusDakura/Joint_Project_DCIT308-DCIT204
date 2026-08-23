package org.ug.dsa.algorithms.graph;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomList;
import org.ug.dsa.datastructures.CustomStack;

/**
 * Depth-First Search (DFS) Cycle Detection & Traversal Engine.
 *
 * Traverses delivery routes deeply to:
 *   1. Detect circular road routing loops (cycles via back-edges).
 *   2. Determine connected component counts and reachability.
 *   3. Record DFS discovery order and topological-style paths.
 *
 * Time Complexity: O(V + E)
 * Space Complexity: O(V) for recursion / CustomStack.
 *
 * Strict Constraint: Uses custom data structures only (CustomStack, CustomDynamicArray).
 */
public class DFSCycleDetection {

    /**
     * Result wrapper for DFS cycle detection and component analysis.
     */
    public record DFSResult(
            boolean hasCycle,
            CustomDynamicArray<String> cyclePath,
            CustomDynamicArray<String> traversalOrder,
            int connectedComponentsCount) {
    }

    private enum NodeState {
        UNVISITED, VISITING, VISITED
    }

    /**
     * Performs a full DFS scan over the graph to detect cycles and count components.
     */
    public static DFSResult analyzeGraph(CustomGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null.");
        }

        int n = graph.getVertexCount();
        NodeState[] state = new NodeState[n];
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            state[i] = NodeState.UNVISITED;
            parent[i] = -1;
        }

        CustomDynamicArray<String> traversalOrder = new CustomDynamicArray<>();
        CustomDynamicArray<String> cyclePath = new CustomDynamicArray<>();
        boolean[] cycleFound = new boolean[]{false};
        int componentCount = 0;

        for (int i = 0; i < n; i++) {
            if (state[i] == NodeState.UNVISITED) {
                componentCount++;
                dfsVisit(graph, i, state, parent, traversalOrder, cyclePath, cycleFound);
            }
        }

        return new DFSResult(cycleFound[0], cyclePath, traversalOrder, componentCount);
    }

    private static void dfsVisit(
            CustomGraph graph,
            int u,
            NodeState[] state,
            int[] parent,
            CustomDynamicArray<String> order,
            CustomDynamicArray<String> cyclePath,
            boolean[] cycleFound) {

        state[u] = NodeState.VISITING;
        String uName = graph.getAllVertices().get(u);
        order.add(uName);

        CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors(uName);
        for (int i = 0; i < neighbors.size(); i++) {
            CustomGraph.Edge edge = neighbors.get(i);
            int v = graph.indexOfVertex(edge.getTarget());
            if (v == -1) continue;

            if (state[v] == NodeState.VISITING) {
                // Back-edge found -> cycle detected!
                if (!cycleFound[0]) {
                    cycleFound[0] = true;
                    // Reconstruct cycle path
                    cyclePath.add(graph.getAllVertices().get(v));
                    cyclePath.add(uName);
                }
            } else if (state[v] == NodeState.UNVISITED) {
                parent[v] = u;
                dfsVisit(graph, v, state, parent, order, cyclePath, cycleFound);
            }
        }

        state[u] = NodeState.VISITED;
    }

    /**
     * Iterative DFS traversal from a specific start node using CustomStack.
     */
    public static CustomDynamicArray<String> traverseFrom(CustomGraph graph, String startNode) {
        if (graph == null || startNode == null) {
            throw new IllegalArgumentException("Graph and startNode must not be null.");
        }

        int startIdx = graph.indexOfVertex(startNode);
        if (startIdx == -1) {
            throw new IllegalArgumentException("Start node '" + startNode + "' not found.");
        }

        int n = graph.getVertexCount();
        boolean[] visited = new boolean[n];
        CustomDynamicArray<String> order = new CustomDynamicArray<>();
        CustomStack<Integer> stack = new CustomStack<>();

        stack.push(startIdx);

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                String uName = graph.getAllVertices().get(u);
                order.add(uName);

                CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors(uName);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int v = graph.indexOfVertex(neighbors.get(i).getTarget());
                    if (v != -1 && !visited[v]) {
                        stack.push(v);
                    }
                }
            }
        }

        return order;
    }
}
