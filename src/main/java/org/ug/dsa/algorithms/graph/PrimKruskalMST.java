package org.ug.dsa.algorithms.graph;

import org.ug.dsa.algorithms.sorting.QuickSort;
import org.ug.dsa.datastructures.CustomDisjointSet;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.datastructures.CustomList;

/**
 * Minimum Spanning Tree (MST) Optimization Engine: Prim and Kruskal Algorithms.
 *
 * Connects all local locations in the delivery network with the minimum total
 * infrastructure road weight / travel distance without cycles.
 *
 * Kruskal Algorithm:
 *   - Greedy edge-centric approach.
 *   - Sorts all edges and uses CustomDisjointSet (Union-Find) to prevent cycles.
 *   - Time Complexity: O(E log E) or O(E log V).
 *
 * Prim Algorithm:
 *   - Greedy vertex-centric cut approach.
 *   - Grows the MST from a starting node using CustomHeap (Min-Heap).
 *   - Time Complexity: O(E log V).
 *
 * Strict Constraint: Uses custom data structures only (CustomDisjointSet, CustomHeap, CustomDynamicArray).
 */
public class PrimKruskalMST {

    /**
     * Undirected graph edge with natural ordering by weight.
     */
    public static class MSTEdge implements Comparable<MSTEdge> {
        private final String u;
        private final String v;
        private final double weight;

        public MSTEdge(String u, String v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public String getU() {
            return u;
        }

        public String getV() {
            return v;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public int compareTo(MSTEdge other) {
            return Double.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("%s <--> %s (weight: %.2f)", u, v, weight);
        }
    }

    /**
     * Result bundle for Minimum Spanning Tree evaluation.
     */
    public record MSTResult(
            String algorithmName,
            CustomDynamicArray<MSTEdge> mstEdges,
            double totalCost,
            boolean isFullyConnected,
            int vertexCount) {

        public String renderSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("=== %s MINIMUM SPANNING TREE ===%n", algorithmName.toUpperCase()));
            sb.append(String.format("Total Spanning Network Cost : %.2f%n", totalCost));
            sb.append(String.format("Edges in MST                : %d (Expected: %d)%n", mstEdges.size(), vertexCount - 1));
            sb.append(String.format("Fully Connected Coverage   : %s%n%n", isFullyConnected ? "YES (All zones spanned)" : "NO (Disconnected components)"));
            sb.append("MST Backbone Connections:\n");
            for (int i = 0; i < mstEdges.size(); i++) {
                sb.append(String.format("  [%2d] %s%n", (i + 1), mstEdges.get(i)));
            }
            return sb.toString();
        }
    }

    /**
     * Kruskal's MST Algorithm using CustomDisjointSet (Union-Find).
     */
    public static MSTResult kruskalMST(CustomGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null.");
        }

        int numVertices = graph.getVertexCount();
        if (numVertices <= 1) {
            return new MSTResult("Kruskal", new CustomDynamicArray<>(), 0.0, true, numVertices);
        }

        // 1. Collect unique undirected edges
        CustomDynamicArray<MSTEdge> allEdges = new CustomDynamicArray<>();
        for (int i = 0; i < numVertices; i++) {
            String uName = graph.getAllVertices().get(i);
            CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors(uName);
            for (int j = 0; j < neighbors.size(); j++) {
                CustomGraph.Edge edge = neighbors.get(j);
                String vName = edge.getTarget();
                int vIdx = graph.indexOfVertex(vName);
                if (i < vIdx) { // Avoid duplicate undirected edges
                    allEdges.add(new MSTEdge(uName, vName, edge.getWeight()));
                }
            }
        }

        // 2. Sort edges by weight in ascending order using QuickSort
        QuickSort.sort(allEdges);

        // 3. Initialize CustomDisjointSet
        CustomDisjointSet disjointSet = new CustomDisjointSet(numVertices);
        CustomDynamicArray<MSTEdge> mstEdges = new CustomDynamicArray<>();
        double totalCost = 0.0;

        for (int i = 0; i < allEdges.size(); i++) {
            MSTEdge edge = allEdges.get(i);
            int uIdx = graph.indexOfVertex(edge.getU());
            int vIdx = graph.indexOfVertex(edge.getV());

            if (disjointSet.find(uIdx) != disjointSet.find(vIdx)) {
                disjointSet.union(uIdx, vIdx);
                mstEdges.add(edge);
                totalCost += edge.getWeight();

                if (mstEdges.size() == numVertices - 1) {
                    break;
                }
            }
        }

        boolean isConnected = (mstEdges.size() == numVertices - 1) || (numVertices <= 1);
        return new MSTResult("Kruskal", mstEdges, totalCost, isConnected, numVertices);
    }

    /**
     * Prim's MST Algorithm using CustomHeap (Min-Heap).
     */
    public static MSTResult primMST(CustomGraph graph, String startVertex) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null.");
        }

        int n = graph.getVertexCount();
        if (n <= 1) {
            return new MSTResult("Prim", new CustomDynamicArray<>(), 0.0, true, n);
        }

        int startIdx = (startVertex != null) ? graph.indexOfVertex(startVertex) : 0;
        if (startIdx == -1) startIdx = 0;

        boolean[] inMST = new boolean[n];
        CustomDynamicArray<MSTEdge> mstEdges = new CustomDynamicArray<>();
        CustomHeap<MSTEdge> minHeap = new CustomHeap<>();
        double totalCost = 0.0;

        // Add initial edges from start node
        inMST[startIdx] = true;
        addVertexEdgesToHeap(graph, startIdx, inMST, minHeap);

        while (!minHeap.isEmpty() && mstEdges.size() < n - 1) {
            MSTEdge edge = minHeap.extractMin();
            int uIdx = graph.indexOfVertex(edge.getU());
            int vIdx = graph.indexOfVertex(edge.getV());

            int nextVertex = -1;
            if (inMST[uIdx] && !inMST[vIdx]) {
                nextVertex = vIdx;
            } else if (!inMST[uIdx] && inMST[vIdx]) {
                nextVertex = uIdx;
            }

            if (nextVertex != -1) {
                inMST[nextVertex] = true;
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                addVertexEdgesToHeap(graph, nextVertex, inMST, minHeap);
            }
        }

        boolean isConnected = (mstEdges.size() == n - 1);
        return new MSTResult("Prim", mstEdges, totalCost, isConnected, n);
    }

    private static void addVertexEdgesToHeap(CustomGraph graph, int uIdx, boolean[] inMST, CustomHeap<MSTEdge> heap) {
        String uName = graph.getAllVertices().get(uIdx);
        CustomList<CustomGraph.Edge> neighbors = graph.getNeighbors(uName);
        for (int i = 0; i < neighbors.size(); i++) {
            CustomGraph.Edge edge = neighbors.get(i);
            int vIdx = graph.indexOfVertex(edge.getTarget());
            if (vIdx != -1 && !inMST[vIdx]) {
                heap.insert(new MSTEdge(uName, edge.getTarget(), edge.getWeight()));
            }
        }
    }
}
