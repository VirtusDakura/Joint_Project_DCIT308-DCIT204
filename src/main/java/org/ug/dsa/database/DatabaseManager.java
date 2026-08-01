package org.ug.dsa.database;

/**
 * Manages SQL database connections and table initialization.
 * Supports PostgreSQL with automatic SQLite fallback.
 *
 * Assigned to: Rushdan Delimwine Antiku (22102540)
 *
 * Required tables:
 *   - locations       : locationId, name, area, type, latitude, longitude
 *   - roads           : fromLocationId, toLocationId, distance, travelTime, roadConditionWeight
 *   - service_requests: requestId, source, destination, category, urgency, timeSubmitted, deadline, status
 *   - resources       : resourceId, type, homeLocation, capacity, availabilityStatus
 *   - algorithm_runs  : runId, algorithmName, inputSize, timeNs, memoryKb, dateRun
 *   - audit_events    : eventId, eventType, description, timestamp
 *
 * Required methods:
 *   - getConnection()       : Try PostgreSQL first, fall back to SQLite (dsa_optimizer.db)
 *   - initializeTables()    : CREATE TABLE IF NOT EXISTS for all 6 tables
 *   - insertLocation(...)   / getAllLocations()
 *   - insertRoad(...)       / getAllRoads()
 *   - insertServiceRequest(...) / getAllServiceRequests()
 *   - insertResource(...)   / getAllResources()
 *   - insertAlgorithmRun(...)   / getAllAlgorithmRuns()
 *   - importFromCSV(String tableName, String csvPath) : Bulk import CSV seed data
 *
 * Dependencies: java.sql.* (JDBC is allowed as built-in Java utility)
 *
 * Hint: Use environment variables DB_URL, DB_USER, DB_PASS for PostgreSQL.
 *       If connection fails, catch SQLException and connect to "jdbc:sqlite:dsa_optimizer.db".
 */
public class DatabaseManager {

    // TODO: Implement database connection, schema DDL, and CRUD operations.

}
