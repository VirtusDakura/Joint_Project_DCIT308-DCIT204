package org.ug.dsa.datastructures;

import java.util.Arrays;

/**
 * Custom weighted graph for delivery route networks.
 * Maintains both Adjacency List and Adjacency Matrix representations.
 */
public class CustomGraph {

    /**
     * Edge representation holding destination location ID and distance/time weight.
     */
    public static class Edge {
        private final String target;
        private final double weight;

        public Edge(String target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        public String getTarget() {
            return target;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.format("%s(%.1f)", target, weight);
        }
    }

    private CustomDynamicArray<String> vertices;
    private CustomDynamicArray<CustomDynamicArray<Edge>> adjList;
    private double[][] adjMatrix;
    private int edgeCount;
    private int matrixCapacity;

    private static final int INITIAL_MATRIX_CAPACITY = 16;
    public static final double NO_EDGE = Double.POSITIVE_INFINITY;

    public CustomGraph() {
        this.vertices = new CustomDynamicArray<>();
        this.adjList = new CustomDynamicArray<>();
        this.matrixCapacity = INITIAL_MATRIX_CAPACITY;
        this.adjMatrix = new double[matrixCapacity][matrixCapacity];
        for (int i = 0; i < matrixCapacity; i++) {
            Arrays.fill(adjMatrix[i], NO_EDGE);
            adjMatrix[i][i] = 0.0;
        }
        this.edgeCount = 0;
    }

    // Registers a new location vertex and expands matrix capacity if required
    public int addVertex(String locationId) {
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Vertex locationId must not be null or empty.");
        }
        int existingIndex = indexOfVertex(locationId);
        if (existingIndex != -1) {
            return existingIndex;
        }

        int newIndex = vertices.size();
        vertices.add(locationId);
        adjList.add(new CustomDynamicArray<>());

        if (newIndex >= matrixCapacity) {
            resizeMatrix(matrixCapacity * 2);
        }

        return newIndex;
    }

    // Adds or updates a directed weighted edge in both list and matrix
    public void addEdge(String from, String to, double weight) {
        int u = addVertex(from);
        int v = addVertex(to);

        // Update Adjacency List
        CustomDynamicArray<Edge> neighbors = adjList.get(u);
        boolean updated = false;
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getTarget().equals(to)) {
                neighbors.set(i, new Edge(to, weight));
                updated = true;
                break;
            }
        }
        if (!updated) {
            neighbors.add(new Edge(to, weight));
            edgeCount++;
        }

        // Update Adjacency Matrix
        adjMatrix[u][v] = weight;
    }

    // Helper for bidirectional roads
    public void addUndirectedEdge(String from, String to, double weight) {
        addEdge(from, to, weight);
        addEdge(to, from, weight);
    }

    // Checks if a direct edge connects from -> to
    public boolean hasEdge(String from, String to) {
        int u = indexOfVertex(from);
        int v = indexOfVertex(to);
        if (u == -1 || v == -1) {
            return false;
        }
        return adjMatrix[u][v] != NO_EDGE && u != v;
    }

    // Returns edge weight or NO_EDGE if no connection exists
    public double getEdgeWeight(String from, String to) {
        int u = indexOfVertex(from);
        int v = indexOfVertex(to);
        if (u == -1 || v == -1) {
            return NO_EDGE;
        }
        return adjMatrix[u][v];
    }

    // Returns list of outgoing edges from location
    public CustomList<Edge> getNeighbors(String locationId) {
        int u = indexOfVertex(locationId);
        if (u == -1) {
            return new CustomDynamicArray<>();
        }
        return adjList.get(u);
    }

    public CustomList<String> getAllVertices() {
        return vertices;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int indexOfVertex(String locationId) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(locationId)) {
                return i;
            }
        }
        return -1;
    }

    public double[][] getAdjacencyMatrixCopy() {
        int n = vertices.size();
        double[][] copy = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(adjMatrix[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // Formats side-by-side list and matrix view for demonstration
    public String getAdjacencyListAndMatrixSideBySide() {
        StringBuilder sb = new StringBuilder();
        int n = vertices.size();

        sb.append("=== ADJACENCY LIST & MATRIX SIDE-BY-SIDE ===\n\n");
        sb.append(String.format("%-30s | %s%n", "ADJACENCY LIST", "ADJACENCY MATRIX"));
        sb.append("-".repeat(75)).append("\n");

        for (int i = 0; i < n; i++) {
            String vName = vertices.get(i);
            CustomDynamicArray<Edge> edges = adjList.get(i);

            StringBuilder listSb = new StringBuilder();
            listSb.append(String.format("%-12s -> [", vName));
            for (int j = 0; j < edges.size(); j++) {
                listSb.append(edges.get(j));
                if (j < edges.size() - 1) listSb.append(", ");
            }
            listSb.append("]");

            StringBuilder matrixSb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                double w = adjMatrix[i][j];
                if (w == NO_EDGE) {
                    matrixSb.append("  INF");
                } else {
                    matrixSb.append(String.format("%5.1f", w));
                }
            }

            sb.append(String.format("%-35s | %s%n", listSb.toString(), matrixSb.toString()));
        }
        return sb.toString();
    }

    // Doubles 2D matrix array capacity when vertex count exceeds current dimensions
    private void resizeMatrix(int newCap) {
        double[][] newMatrix = new double[newCap][newCap];
        for (int i = 0; i < newCap; i++) {
            Arrays.fill(newMatrix[i], NO_EDGE);
            newMatrix[i][i] = 0.0;
        }

        int oldSize = vertices.size();
        for (int i = 0; i < oldSize; i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, oldSize);
        }

        this.adjMatrix = newMatrix;
        this.matrixCapacity = newCap;
    }
}
