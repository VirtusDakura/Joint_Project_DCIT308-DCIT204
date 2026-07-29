package org.ug.dsa.models;

/**
 * Represents a weighted edge between two locations in the Ghanaian network.
 */
public record Road(
    String roadId,
    String fromLocationId,
    String toLocationId,
    double distanceKm,
    double travelTimeMin,
    double conditionWeight
) {
    /**
     * Calculates the effective traversal weight combining distance and condition.
     */
    public double getEffectiveWeight() {
        return distanceKm * conditionWeight;
    }
}
