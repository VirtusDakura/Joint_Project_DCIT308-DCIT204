package org.ug.dsa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages database connection and DDL schema creation.
 * Primary: PostgreSQL (jdbc:postgresql://localhost:5432/dsa_optimizer)
 * Fallback: SQLite (jdbc:sqlite:dsa_optimizer.db)
 */
public class DatabaseManager {

    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/dsa_optimizer";
    private static final String POSTGRES_USER = "ug_dsa_user";
    private static final String POSTGRES_PASS = "dsa_password123";

    private static final String SQLITE_URL = "jdbc:sqlite:dsa_optimizer.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Attempt PostgreSQL connection
            return DriverManager.getConnection(POSTGRES_URL, POSTGRES_USER, POSTGRES_PASS);
        } catch (SQLException e) {
            System.out.println("[DatabaseManager] PostgreSQL connection failed. Falling back to SQLite local database...");
            return DriverManager.getConnection(SQLITE_URL);
        }
    }

    public static void initializeTables() {
        String createLocations = """
            CREATE TABLE IF NOT EXISTS locations (
                location_id VARCHAR(20) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                area VARCHAR(100) NOT NULL,
                location_type VARCHAR(50),
                x_coord DOUBLE PRECISION,
                y_coord DOUBLE PRECISION
            );
            """;

        String createRoads = """
            CREATE TABLE IF NOT EXISTS roads (
                road_id VARCHAR(20) PRIMARY KEY,
                from_location_id VARCHAR(20) REFERENCES locations(location_id),
                to_location_id VARCHAR(20) REFERENCES locations(location_id),
                distance_km DOUBLE PRECISION NOT NULL,
                travel_time_min DOUBLE PRECISION NOT NULL,
                condition_weight DOUBLE PRECISION DEFAULT 1.0
            );
            """;

        String createRequests = """
            CREATE TABLE IF NOT EXISTS service_requests (
                request_id VARCHAR(20) PRIMARY KEY,
                source_location_id VARCHAR(20) REFERENCES locations(location_id),
                destination_location_id VARCHAR(20) REFERENCES locations(location_id),
                category VARCHAR(50),
                urgency INT CHECK (urgency BETWEEN 1 AND 5),
                time_submitted VARCHAR(50),
                deadline VARCHAR(50),
                status VARCHAR(20) DEFAULT 'NEW'
            );
            """;

        String createResources = """
            CREATE TABLE IF NOT EXISTS resources (
                resource_id VARCHAR(20) PRIMARY KEY,
                resource_type VARCHAR(50),
                home_location_id VARCHAR(20) REFERENCES locations(location_id),
                capacity INT DEFAULT 1,
                availability_status VARCHAR(20) DEFAULT 'AVAILABLE'
            );
            """;

        String createAlgorithmRuns = """
            CREATE TABLE IF NOT EXISTS algorithm_runs (
                run_id VARCHAR(50) PRIMARY KEY,
                algorithm_name VARCHAR(100) NOT NULL,
                input_size INT NOT NULL,
                time_ns BIGINT NOT NULL,
                memory_kb BIGINT NOT NULL,
                date_run VARCHAR(50) NOT NULL
            );
            """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createLocations);
            stmt.execute(createRoads);
            stmt.execute(createRequests);
            stmt.execute(createResources);
            stmt.execute(createAlgorithmRuns);
            System.out.println("[DatabaseManager] Database schema initialized successfully.");
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Error initializing database tables: " + e.getMessage());
        }
    }
}
