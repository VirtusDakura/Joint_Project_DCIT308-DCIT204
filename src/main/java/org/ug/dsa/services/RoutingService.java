package org.ug.dsa.services;

import org.ug.dsa.algorithms.graph.BFSReachability;
import org.ug.dsa.algorithms.graph.DFSCycleDetection;
import org.ug.dsa.algorithms.graph.DijkstraAlgorithm;
import org.ug.dsa.algorithms.graph.PrimKruskalMST;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomList;

/**
 * High-level routing and network analysis service.
 *
 * Integrates graph algorithms:
 *   - Dijkstra single-source shortest path
 *   - BFS zone reachability & hop distance
 *   - DFS cycle detection & connectivity
 *   - Kruskal and Prim Minimum Spanning Trees (MST)
 */
public class RoutingService {

    private final CustomGraph graph;

    public RoutingService(CustomGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("CustomGraph instance cannot be null.");
        }
        this.graph = graph;
    }

    /**
     * Gets the direct travel weight / cost between two locations.
     */
    public double getDirectRouteCost(String fromLocationId, String toLocationId) {
        return graph.getEdgeWeight(fromLocationId, toLocationId);
    }

    /**
     * Checks if a direct road connection exists between two locations.
     */
    public boolean hasDirectRoute(String fromLocationId, String toLocationId) {
        return graph.hasEdge(fromLocationId, toLocationId);
    }

    /**
     * Retrieves all outgoing road connections from a given location.
     */
    public CustomList<CustomGraph.Edge> getOutgoingRoutes(String locationId) {
        return graph.getNeighbors(locationId);
    }

    /**
     * Computes the shortest travel route between two locations using Dijkstra's Algorithm.
     */
    public DijkstraAlgorithm.ShortestPathResult findShortestPathsFrom(String sourceLocationId) {
        return DijkstraAlgorithm.computeShortestPaths(graph, sourceLocationId);
    }

    /**
     * Computes point-to-point shortest route itinerary.
     */
    public CustomDynamicArray<String> getOptimalRoute(String sourceLocationId, String destinationLocationId) {
        DijkstraAlgorithm.ShortestPathResult result = findShortestPathsFrom(sourceLocationId);
        return result.getPathTo(destinationLocationId);
    }

    /**
     * Determines all reachable delivery locations from a dispatch hub using BFS.
     */
    public BFSReachability.BFSResult getReachableZones(String hubLocationId) {
        return BFSReachability.explore(graph, hubLocationId);
    }

    /**
     * Analyzes road network loops and cycles using DFS.
     */
    public DFSCycleDetection.DFSResult detectNetworkCycles() {
        return DFSCycleDetection.analyzeGraph(graph);
    }

    /**
     * Computes Minimum Spanning Tree using Kruskal's algorithm (Disjoint Set).
     */
    public PrimKruskalMST.MSTResult computeKruskalMST() {
        return PrimKruskalMST.kruskalMST(graph);
    }

    /**
     * Computes Minimum Spanning Tree using Prim's algorithm (Min-Heap).
     */
    public PrimKruskalMST.MSTResult computePrimMST(String rootLocationId) {
        return PrimKruskalMST.primMST(graph, rootLocationId);
    }

    /**
     * Returns the underlying graph structure.
     */
    public CustomGraph getGraph() {
        return graph;
    }
}
