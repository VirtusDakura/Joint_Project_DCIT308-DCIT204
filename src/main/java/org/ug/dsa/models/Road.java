package org.ug.dsa.models;

/**
 * Represents a weighted road edge connecting two locations in the network.
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
     * Calculates effective traversal cost considering distance and road condition.
     */
    public double getEffectiveWeight() {
        return distanceKm * conditionWeight;
    }
}
