package org.ug.dsa.services;

import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.datastructures.CustomQueue;
import org.ug.dsa.models.ServiceRequest;

/**
 * Service scheduling engine implementing FIFO queue and priority queue dispatch rules (Module M5).
 */
public class SchedulingService {

    private final CustomQueue<ServiceRequest> fifoQueue = new CustomQueue<>();
    private final CustomHeap<ServiceRequest> priorityQueue = new CustomHeap<>(100);

    public void submitRequest(ServiceRequest request) {
        fifoQueue.enqueue(request);
        priorityQueue.insert(request);
    }

    public ServiceRequest getNextFifoRequest() {
        return fifoQueue.isEmpty() ? null : fifoQueue.dequeue();
    }

    public ServiceRequest getNextPriorityRequest() {
        return priorityQueue.isEmpty() ? null : priorityQueue.extractMin();
    }
}
