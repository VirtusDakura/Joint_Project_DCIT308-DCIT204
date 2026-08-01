package org.ug.dsa.models;

/**
 * Represents a delivery rider or vehicle resource available for dispatch assignments.
 */
public record Resource(
    String resourceId,
    String resourceType,
    String homeLocationId,
    int capacity,
    String availabilityStatus
) {}
