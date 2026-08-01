package org.ug.dsa.models;

/**
 * Represents a geographical node or facility in the service operations network.
 */
public record Location(
    String locationId,
    String name,
    String area,
    String locationType,
    double xCoord,
    double yCoord
) {
    @Override
    public String toString() {
        return String.format("%s (%s - %s)", name, area, locationType);
    }
}
