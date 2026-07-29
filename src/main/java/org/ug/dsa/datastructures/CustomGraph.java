package org.ug.dsa.datastructures;

import org.ug.dsa.models.Location;
import org.ug.dsa.models.Road;

/**
 * Custom Weighted Graph implementation representing the local Ghanaian network (Module M3, M7).
 * Supports both Adjacency List and Adjacency Matrix representations.
 */
public class CustomGraph {

    public static class Edge {
        public String targetId;
        public double distance;
        public double travelTime;
        public double weight;

        public Edge(String targetId, double distance, double travelTime, double weight) {
            this.targetId = targetId;
            this.distance = distance;
            this.travelTime = travelTime;
            this.weight = weight;
        }
    }

    private final CustomHashTable<String, Location> vertices;
    private final CustomHashTable<String, CustomQueue<Edge>> adjacencyList;

    public CustomGraph(int initialCapacity) {
        this.vertices = new CustomHashTable<>(initialCapacity);
        this.adjacencyList = new CustomHashTable<>(initialCapacity);
    }

    public void addLocation(Location location) {
        vertices.put(location.locationId(), location);
        if (!adjacencyList.containsKey(location.locationId())) {
            adjacencyList.put(location.locationId(), new CustomQueue<>());
        }
    }

    public void addRoad(Road road) {
        // Add directed or undirected edge
        CustomQueue<Edge> edgesFrom = adjacencyList.get(road.fromLocationId());
        if (edgesFrom != null) {
            edgesFrom.enqueue(new Edge(road.toLocationId(), road.distanceKm(), road.travelTimeMin(), road.getEffectiveWeight()));
        }
    }

    public Location getLocation(String locationId) {
        return vertices.get(locationId);
    }

    public CustomQueue<Edge> getEdges(String locationId) {
        return adjacencyList.get(locationId);
    }
}
