package org.ug.dsa.models;

import java.time.LocalDateTime;

/**
 * Represents a delivery order submitted to the dispatch system.
 */
public record ServiceRequest(
    String requestId,
    String sourceLocationId,
    String destinationLocationId,
    String category,
    int urgency,
    LocalDateTime timeSubmitted,
    LocalDateTime deadline,
    String status
) implements Comparable<ServiceRequest> {

    @Override
    public int compareTo(ServiceRequest other) {
        if (this.urgency != other.urgency) {
            return Integer.compare(other.urgency, this.urgency);
        }
        return this.deadline.compareTo(other.deadline);
    }
}
