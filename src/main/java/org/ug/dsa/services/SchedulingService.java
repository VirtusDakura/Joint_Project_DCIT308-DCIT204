package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomDeque;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.models.ServiceRequest;

/**
 * Service for managing order scheduling and dispatch using custom data structures.
 */
public class SchedulingService {

    private final CustomDeque<ServiceRequest> orderDeque;
    private int totalSubmitted;

    public SchedulingService() {
        this.orderDeque = new CustomDeque<>();
        this.totalSubmitted = 0;
    }

    /**
     * Submits a standard order to the rear of the queue (FIFO).
     */
    public void submitOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        orderDeque.addRear(request);
        totalSubmitted++;
    }

    /**
     * Inserts an urgent order directly to the front of the queue.
     */
    public void insertUrgentOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        orderDeque.addFront(request);
        totalSubmitted++;
    }

    /**
     * Dispatches the next order in FIFO order.
     */
    public ServiceRequest dispatchFIFO() {
        if (orderDeque.isEmpty()) {
            return null;
        }
        return orderDeque.removeFront();
    }

    /**
     * Dispatches the highest urgency order using CustomHeap extraction.
     */
    public ServiceRequest dispatchPriority() {
        if (orderDeque.isEmpty()) {
            return null;
        }

        CustomHeap<ServiceRequest> priorityHeap = new CustomHeap<>();
        CustomDynamicArray<ServiceRequest> remaining = new CustomDynamicArray<>();

        while (!orderDeque.isEmpty()) {
            priorityHeap.insert(orderDeque.removeFront());
        }

        ServiceRequest best = priorityHeap.extractMin();

        while (!priorityHeap.isEmpty()) {
            remaining.add(priorityHeap.extractMin());
        }

        for (int i = 0; i < remaining.size(); i++) {
            orderDeque.addRear(remaining.get(i));
        }

        return best;
    }

    public int getPendingCount() {
        return orderDeque.size();
    }

    public boolean isEmpty() {
        return orderDeque.isEmpty();
    }

    public int getTotalSubmitted() {
        return totalSubmitted;
    }
}
