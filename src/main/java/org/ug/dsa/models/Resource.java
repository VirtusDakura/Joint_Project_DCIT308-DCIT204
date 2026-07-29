package org.ug.dsa.models;

/**
 * Represents a fleet asset, vehicle, officer, or staff member available for assignment.
 */
public record Resource(
    String resourceId,
    String resourceType,
    String homeLocationId,
    int capacity,
    String availabilityStatus // AVAILABLE, ASSIGNED, MAINTENANCE
) {}
