package org.ug.dsa.algorithms.graph;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.datastructures.CustomList;

import java.util.Arrays;

/**
 * Dijkstra's Shortest Path Algorithm Implementation.
 * Computes single-source shortest paths on weighted road networks using a Min-Heap.
 */
public class DijkstraAlgorithm {

    /**
     * Heap entry node holding vertex index and current shortest distance.
     */
    public static class PathNode implements Comparable<PathNode> {
        private final int vertexIndex;
        private final double distance;

        public PathNode(int vertexIndex, double distance) {
            this.vertexIndex = vertexIndex;
            this.distance = distance;
        }

        public int getVertexIndex() {
            return vertexIndex;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public int compareTo(PathNode other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    /**
     * Complete shortest path result containing distances, predecessors, and path builder.
     */
    public record ShortestPathResult(
            String source,
            double[] distances,
            int[] predecessors,
            CustomGraph graph) {

        public boolean hasPathTo(String destination) {
            int targetIdx = graph.indexOfVertex(destination);
            if (targetIdx == -1) return false;
            return distances[targetIdx] < Double.POSITIVE_INFINITY;
        }

        public double getDistanceTo(String destination) {
            int targetIdx = graph.indexOfVertex(destination);
            if (targetIdx == -1) return Double.POSITIVE_INFINITY;
            return distances[targetIdx];
        }

        public CustomDynamicArray<String> getPathTo(String destination) {
            int targetIdx = graph.indexOfVertex(destination);
            CustomDynamicArray<String> path = new CustomDynamicArray<>();
            if (targetIdx == -1 || distances[targetIdx] == Double.POSITIVE_INFINITY) {
                return path;
            }

            CustomDynamicArray<Integer> reverseIndices = new CustomDynamicArray<>();
            for (int at = targetIdx; at != -1; at = predecessors[at]) {
                reverseIndices.add(at);
            }

            // Reverse to get source -> destination
            for (int i = reverseIndices.size() - 1; i >= 0; i--) {
                path.add(graph.getAllVertices().get(reverseIndices.get(i)));
            }

            return path;
        }

        public String renderDistanceTable() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s | %-12s | %-15s%n", "Location", "Min Cost/Time", "Predecessor"));
            sb.append("-".repeat(48)).append("\n");

            for (int i = 0; i < graph.getVertexCount(); i++) {
                String locName = graph.getAllVertices().get(i);
                double d = distances[i];
                String dStr = (d == Double.POSITIVE_INFINITY) ? "INF" : String.format("%.2f min", d);
                String pStr = (predecessors[i] == -1) ? "None" : graph.getAllVertices().get(predecessors[i]);
                sb.append(String.format("%-15s | %-12s | %-15s%n", locName, dStr, pStr));
            }
            return sb.toString();
        }
    }

    /**
     * Runs Dijkstra's algorithm from a single source location.
     */
    public static ShortestPathResult computeShortestPaths(CustomGraph graph, String source) {
        if (graph == null || source == null) {
            throw new IllegalArgumentException("Graph and source must not be null.");
        }

        int n = graph.getVertexCount();
        int srcIdx = graph.indexOfVertex(source);
        if (srcIdx == -1) {
            throw new IllegalArgumentException("Source location '" + source + "' not found in graph.");
        }

        double[] dist = new double[n];
        int[] prev = new int[n];
        boolean[] settled = new boolean[n];

        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);

        dist[srcIdx] = 0.0;
        CustomHeap<PathNode> minHeap = new CustomHeap<>();
        minHeap.insert(new PathNode(srcIdx, 0.0));

        while (!minHeap.isEmpty()) {
            PathNode current = minHeap.extractMin();
            int u = current.getVertexIndex();

            if (settled[u]) continue;
            settled[u] = true;

            String uName = graph.getAllVertices().get(u);
            CustomList<CustomGraph.Edge> edges = graph.getNeighbors(uName);

            for (int i = 0; i < edges.size(); i++) {
                CustomGraph.Edge edge = edges.get(i);
                int v = graph.indexOfVertex(edge.getTarget());
                if (v == -1 || settled[v]) continue;

                double weight = edge.getWeight();
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    minHeap.insert(new PathNode(v, dist[v]));
                }
            }
        }

        return new ShortestPathResult(source, dist, prev, graph);
    }
}
