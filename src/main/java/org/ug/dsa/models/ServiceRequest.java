package org.ug.dsa.models;

import java.time.LocalDateTime;

/**
 * Represents an operational request submitted to the optimizer.
 */
public record ServiceRequest(
    String requestId,
    String sourceLocationId,
    String destinationLocationId,
    String category,
    int urgency, // 1 (lowest) to 5 (highest)
    LocalDateTime timeSubmitted,
    LocalDateTime deadline,
    String status // NEW, DISPATCHED, COMPLETED, CANCELLED
) implements Comparable<ServiceRequest> {
    
    @Override
    public int compareTo(ServiceRequest other) {
        // Higher urgency prioritized first
        if (this.urgency != other.urgency) {
            return Integer.compare(other.urgency, this.urgency);
        }
        // Earlier deadline prioritized next
        return this.deadline.compareTo(other.deadline);
    }
}
