package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomHashTable;
import org.ug.dsa.models.Location;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.ServiceRequest;

/**
 * System service for fast indexing and lookup of operational entities.
 * Utilizes custom Hash Tables to provide O(1) average-time lookups for locations, resources, and orders.
 */
public class IndexingService {

    private final CustomHashTable<String, Location> locationIndex;
    private final CustomHashTable<String, Resource> resourceIndex;
    private final CustomHashTable<String, ServiceRequest> requestIndex;

    public IndexingService() {
        this.locationIndex = new CustomHashTable<>();
        this.resourceIndex = new CustomHashTable<>();
        this.requestIndex = new CustomHashTable<>();
    }

    /**
     * Indexes a location by its unique ID.
     */
    public void indexLocation(Location location) {
        if (location != null) {
            locationIndex.put(location.locationId(), location);
        }
    }

    /**
     * Indexes a delivery resource (rider/vehicle) by its unique ID.
     */
    public void indexResource(Resource resource) {
        if (resource != null) {
            resourceIndex.put(resource.resourceId(), resource);
        }
    }

    /**
     * Indexes a service request (order) by its unique ID.
     */
    public void indexRequest(ServiceRequest request) {
        if (request != null) {
            requestIndex.put(request.requestId(), request);
        }
    }

    public Location getLocation(String id) {
        return locationIndex.get(id);
    }

    public Resource getResource(String id) {
        return resourceIndex.get(id);
    }

    public ServiceRequest getRequest(String id) {
        return requestIndex.get(id);
    }

    public boolean containsLocation(String id) {
        return locationIndex.containsKey(id);
    }

    public boolean containsResource(String id) {
        return resourceIndex.containsKey(id);
    }

    public boolean containsRequest(String id) {
        return requestIndex.containsKey(id);
    }

    public int getLocationCount() {
        return locationIndex.size();
    }

    public int getResourceCount() {
        return resourceIndex.size();
    }

    public int getRequestCount() {
        return requestIndex.size();
    }

    public void clearAll() {
        this.locationIndex.clear();
        this.resourceIndex.clear();
        this.requestIndex.clear();
    }
}
