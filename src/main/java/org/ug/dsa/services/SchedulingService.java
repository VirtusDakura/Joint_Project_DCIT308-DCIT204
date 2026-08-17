package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomDeque;
import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.models.ServiceRequest;

/**
 * Scheduling service supporting FIFO, priority, and emergency dispatch order rules.
 *
 * Demonstrates Module M5 dispatch strategies using custom data structures:
 *   - CustomDeque : Primary order store supporting both FIFO rear-insertion
 *                   and emergency front-insertion.
 *   - FIFO dispatch : removeFront() from the deque.
 *   - Priority dispatch : Linear scan for highest urgency.
 *                   (Will use CustomHeap once Issue #2.1 is implemented.)
 *
 * No java.util collections are used.
 */
public class SchedulingService {

    // Primary order store — Deque supports both FIFO addRear and urgent addFront
    private final CustomDeque<ServiceRequest> orderDeque;
    private int totalSubmitted;

    public SchedulingService() {
        this.orderDeque = new CustomDeque<>();
        this.totalSubmitted = 0;
    }

    /**
     * Submits a standard order to the rear of the dispatch queue (FIFO).
     * Uses CustomDeque.addRear() for standard FIFO insertion.
     */
    public void submitOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        orderDeque.addRear(request);
        totalSubmitted++;
    }

    /**
     * Emergency front insertion for high urgency orders.
     * Uses CustomDeque.addFront() to bypass normal FIFO ordering.
     */
    public void insertUrgentOrder(ServiceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ServiceRequest must not be null.");
        }
        orderDeque.addFront(request);
        totalSubmitted++;
    }

    /**
     * Dispatches the oldest order using FIFO rule.
     * Removes from front of the deque (first-in, first-out).
     *
     * @return the dispatched order, or null if no orders are pending
     */
    public ServiceRequest dispatchFIFO() {
        if (orderDeque.isEmpty()) {
            return null;
        }
        return orderDeque.removeFront();
    }

    /**
     * Dispatches the highest urgency order using CustomHeap priority extraction.
     *
     * @return the highest-urgency order, or null if no orders are pending
     */
    public ServiceRequest dispatchPriority() {
        if (orderDeque.isEmpty()) {
            return null;
        }

        // Drain all orders into a CustomHeap for priority extraction
        CustomHeap<ServiceRequest> priorityHeap = new CustomHeap<>();
        CustomDynamicArray<ServiceRequest> remaining = new CustomDynamicArray<>();

        while (!orderDeque.isEmpty()) {
            priorityHeap.insert(orderDeque.removeFront());
        }

        ServiceRequest best = priorityHeap.extractMin();

        // Drain remaining from heap and restore to deque
        while (!priorityHeap.isEmpty()) {
            remaining.add(priorityHeap.extractMin());
        }

        for (int i = 0; i < remaining.size(); i++) {
            orderDeque.addRear(remaining.get(i));
        }

        return best;
    }

    /**
     * Returns the number of orders currently pending dispatch.
     */
    public int getPendingCount() {
        return orderDeque.size();
    }

    /**
     * Checks whether the dispatch queue is empty.
     */
    public boolean isEmpty() {
        return orderDeque.isEmpty();
    }

    /**
     * Returns the total number of orders submitted since service creation.
     */
    public int getTotalSubmitted() {
        return totalSubmitted;
    }
}
