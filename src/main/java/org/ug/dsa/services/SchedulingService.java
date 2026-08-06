package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomList;
import org.ug.dsa.models.ServiceRequest;

/**
 * Scheduling service supporting FIFO, priority, and emergency dispatch order rules.
 */
public class SchedulingService {

    private final CustomDynamicArray<ServiceRequest> pendingOrders;

    public SchedulingService() {
        this.pendingOrders = new CustomDynamicArray<>();
    }

    // Submits standard order to queue tail
    public void submitOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        pendingOrders.add(request);
    }

    // Emergency front insertion for high urgency orders
    public void insertUrgentOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        pendingOrders.insert(0, request);
    }

    // Dispatches oldest order (FIFO)
    public ServiceRequest dispatchFIFO() {
        if (pendingOrders.isEmpty()) {
            return null;
        }
        return pendingOrders.remove(0);
    }

    // Dispatches highest urgency order
    public ServiceRequest dispatchPriority() {
        if (pendingOrders.isEmpty()) {
            return null;
        }

        int highestIdx = 0;
        ServiceRequest highest = pendingOrders.get(0);

        for (int i = 1; i < pendingOrders.size(); i++) {
            ServiceRequest candidate = pendingOrders.get(i);
            if (candidate.compareTo(highest) < 0) {
                highest = candidate;
                highestIdx = i;
            }
        }

        return pendingOrders.remove(highestIdx);
    }

    public int getPendingCount() {
        return pendingOrders.size();
    }

    public boolean isEmpty() {
        return pendingOrders.isEmpty();
    }

    public CustomList<ServiceRequest> getAllPendingOrders() {
        return pendingOrders;
    }
}
