package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomGraph;
import org.ug.dsa.datastructures.CustomList;

/**
 * High-level routing service for querying network connectivity and route costs.
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
     * Returns the underlying graph structure.
     */
    public CustomGraph getGraph() {
        return graph;
    }
}
