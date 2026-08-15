package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomDeque;
import org.ug.dsa.datastructures.CustomDynamicArray;
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
     * Dispatches the highest urgency order regardless of submission time.
     *
     * Drains the deque to locate the highest-priority order (lowest compareTo
     * value = highest urgency, then earliest deadline), removes it, and
     * re-enqueues all remaining orders in their original relative order.
     *
     * Time complexity: O(n) — will be replaced with O(log n) CustomHeap
     * extraction once Issue #2.1 (CustomHeap) is implemented.
     *
     * @return the highest-urgency order, or null if no orders are pending
     */
    public ServiceRequest dispatchPriority() {
        if (orderDeque.isEmpty()) {
            return null;
        }

        // Drain all orders into a temporary array for random-access scanning
        CustomDynamicArray<ServiceRequest> temp = new CustomDynamicArray<>();
        while (!orderDeque.isEmpty()) {
            temp.add(orderDeque.removeFront());
        }

        // Find highest priority (lowest compareTo = highest urgency + earliest deadline)
        int bestIdx = 0;
        for (int i = 1; i < temp.size(); i++) {
            if (temp.get(i).compareTo(temp.get(bestIdx)) < 0) {
                bestIdx = i;
            }
        }

        ServiceRequest best = temp.remove(bestIdx);

        // Re-enqueue remaining orders, preserving their relative order
        for (int i = 0; i < temp.size(); i++) {
            orderDeque.addRear(temp.get(i));
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
