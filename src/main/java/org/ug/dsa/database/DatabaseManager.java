package org.ug.dsa.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import org.ug.dsa.datastructures.CustomDynamicArray;
import org.ug.dsa.models.Location;
import org.ug.dsa.models.Resource;
import org.ug.dsa.models.Road;
import org.ug.dsa.models.ServiceRequest;

/**
 * Manages SQL database connections and table initialization.
 * Supports PostgreSQL with automatic SQLite fallback.
 *
 * Managed tables:
 *   - locations       : locationId, name, area, type, latitude, longitude
 *   - roads           : fromLocationId, toLocationId, distance, travelTime, roadConditionWeight
 *   - service_requests: requestId, source, destination, category, urgency, timeSubmitted, deadline, status
 *   - resources       : resourceId, type, homeLocation, capacity, availabilityStatus
 *   - algorithm_runs  : runId, algorithmName, inputSize, timeNs, memoryKb, dateRun
 *   - audit_events    : eventId, eventType, description, timestamp
 */
public class DatabaseManager {

    private static Connection connection;
    private static final String SQLITE_URL = "jdbc:sqlite:dsa_optimizer.db";
    private static boolean isPostgres = false;

    /**
     * Get or establish a database connection.
     * Tries PostgreSQL first, falls back to SQLite if not available.
     */
    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        // Try PostgreSQL first
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        try {
            if (dbUrl != null && dbUser != null && dbPass != null) {
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                isPostgres = true;
                System.out.println("[DB] Connected to PostgreSQL");
                return connection;
            }
        } catch (SQLException e) {
            System.out.println("[DB] PostgreSQL connection failed, attempting SQLite fallback...");
        }

        // Fallback to SQLite
        try {
            connection = DriverManager.getConnection(SQLITE_URL);
            isPostgres = false;
            System.out.println("[DB] Connected to SQLite: " + SQLITE_URL);
            return connection;
        } catch (SQLException e) {
            throw new SQLException("Failed to establish database connection", e);
        }
    }

    /**
     * Close the database connection
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("[DB] Connection closed");
        }
    }

    /**
     * Initialize all required tables with CREATE TABLE IF NOT EXISTS
     */
    public static void initializeTables() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        // Create locations table
        String createLocations = "CREATE TABLE IF NOT EXISTS locations (" +
                "locationId TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "area TEXT, " +
                "type TEXT, " +
                "latitude REAL, " +
                "longitude REAL)";
        stmt.execute(createLocations);
        System.out.println("[DB] Created/verified table: locations");

        // Create roads table
        String createRoads = "CREATE TABLE IF NOT EXISTS roads (" +
                "roadId TEXT PRIMARY KEY, " +
                "fromLocationId TEXT NOT NULL, " +
                "toLocationId TEXT NOT NULL, " +
                "distance REAL, " +
                "travelTime REAL, " +
                "roadConditionWeight REAL, " +
                "FOREIGN KEY(fromLocationId) REFERENCES locations(locationId), " +
                "FOREIGN KEY(toLocationId) REFERENCES locations(locationId))";
        stmt.execute(createRoads);
        System.out.println("[DB] Created/verified table: roads");

        // Create service_requests table
        String createServiceRequests = "CREATE TABLE IF NOT EXISTS service_requests (" +
                "requestId TEXT PRIMARY KEY, " +
                "source TEXT NOT NULL, " +
                "destination TEXT NOT NULL, " +
                "category TEXT, " +
                "urgency INTEGER, " +
                "timeSubmitted TEXT, " +
                "deadline TEXT, " +
                "status TEXT)";
        stmt.execute(createServiceRequests);
        System.out.println("[DB] Created/verified table: service_requests");

        // Create resources table
        String createResources = "CREATE TABLE IF NOT EXISTS resources (" +
                "resourceId TEXT PRIMARY KEY, " +
                "type TEXT, " +
                "homeLocation TEXT, " +
                "capacity REAL, " +
                "availabilityStatus TEXT, " +
                "FOREIGN KEY(homeLocation) REFERENCES locations(locationId))";
        stmt.execute(createResources);
        System.out.println("[DB] Created/verified table: resources");

        // Create algorithm_runs table
        String createAlgorithmRuns = "CREATE TABLE IF NOT EXISTS algorithm_runs (" +
                "runId TEXT PRIMARY KEY, " +
                "algorithmName TEXT NOT NULL, " +
                "inputSize INTEGER, " +
                "timeNs BIGINT, " +
                "memoryKb REAL, " +
                "dateRun TEXT)";
        stmt.execute(createAlgorithmRuns);
        System.out.println("[DB] Created/verified table: algorithm_runs");

        // Create audit_events table
        String createAuditEvents = "CREATE TABLE IF NOT EXISTS audit_events (" +
                "eventId TEXT PRIMARY KEY, " +
                "eventType TEXT NOT NULL, " +
                "description TEXT, " +
                "timestamp TEXT)";
        stmt.execute(createAuditEvents);
        System.out.println("[DB] Created/verified table: audit_events");

        stmt.close();
    }

    /**
     * Insert a location into the database
     */
    public static void insertLocation(String locationId, String name, String area, String type, double latitude, double longitude) throws SQLException {
        String sql = "INSERT OR IGNORE INTO locations (locationId, name, area, type, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, locationId);
        pstmt.setString(2, name);
        pstmt.setString(3, area);
        pstmt.setString(4, type);
        pstmt.setDouble(5, latitude);
        pstmt.setDouble(6, longitude);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Get all locations from the database
     */
    public static CustomDynamicArray<Location> getAllLocations() throws SQLException {
        CustomDynamicArray<Location> locations = new CustomDynamicArray<>();
        String sql = "SELECT * FROM locations";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Location loc = new Location(
                    rs.getString("locationId"),
                    rs.getString("name"),
                    rs.getString("area"),
                    rs.getString("type"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );
            locations.add(loc);
        }

        rs.close();
        stmt.close();
        return locations;
    }

    /**
     * Insert a road into the database
     */
    public static void insertRoad(String roadId, String fromLocationId, String toLocationId, double distance, double travelTime, double roadConditionWeight) throws SQLException {
        String sql = "INSERT OR IGNORE INTO roads (roadId, fromLocationId, toLocationId, distance, travelTime, roadConditionWeight) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, roadId);
        pstmt.setString(2, fromLocationId);
        pstmt.setString(3, toLocationId);
        pstmt.setDouble(4, distance);
        pstmt.setDouble(5, travelTime);
        pstmt.setDouble(6, roadConditionWeight);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Get all roads from the database
     */
    public static CustomDynamicArray<Road> getAllRoads() throws SQLException {
        CustomDynamicArray<Road> roads = new CustomDynamicArray<>();
        String sql = "SELECT * FROM roads";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Road road = new Road(
                    rs.getString("roadId"),
                    rs.getString("fromLocationId"),
                    rs.getString("toLocationId"),
                    rs.getDouble("distance"),
                    rs.getDouble("travelTime"),
                    rs.getDouble("roadConditionWeight")
            );
            roads.add(road);
        }

        rs.close();
        stmt.close();
        return roads;
    }

    /**
     * Insert a service request into the database
     */
    public static void insertServiceRequest(String requestId, String source, String destination, String category, int urgency, String timeSubmitted, String deadline, String status) throws SQLException {
        String sql = "INSERT OR IGNORE INTO service_requests (requestId, source, destination, category, urgency, timeSubmitted, deadline, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, requestId);
        pstmt.setString(2, source);
        pstmt.setString(3, destination);
        pstmt.setString(4, category);
        pstmt.setInt(5, urgency);
        pstmt.setString(6, timeSubmitted);
        pstmt.setString(7, deadline);
        pstmt.setString(8, status);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Get all service requests from the database
     */
    public static CustomDynamicArray<ServiceRequest> getAllServiceRequests() throws SQLException {
        CustomDynamicArray<ServiceRequest> requests = new CustomDynamicArray<>();
        String sql = "SELECT * FROM service_requests";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            LocalDateTime submitted = LocalDateTime.parse(rs.getString("timeSubmitted"));
            LocalDateTime deadline = LocalDateTime.parse(rs.getString("deadline"));
            
            ServiceRequest req = new ServiceRequest(
                    rs.getString("requestId"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getString("category"),
                    rs.getInt("urgency"),
                    submitted,
                    deadline,
                    rs.getString("status")
            );
            requests.add(req);
        }

        rs.close();
        stmt.close();
        return requests;
    }

    /**
     * Insert a resource into the database
     */
    public static void insertResource(String resourceId, String type, String homeLocation, int capacity, String availabilityStatus) throws SQLException {
        String sql = "INSERT OR IGNORE INTO resources (resourceId, type, homeLocation, capacity, availabilityStatus) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, resourceId);
        pstmt.setString(2, type);
        pstmt.setString(3, homeLocation);
        pstmt.setInt(4, capacity);
        pstmt.setString(5, availabilityStatus);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Get all resources from the database
     */
    public static CustomDynamicArray<Resource> getAllResources() throws SQLException {
        CustomDynamicArray<Resource> resources = new CustomDynamicArray<>();
        String sql = "SELECT * FROM resources";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Resource res = new Resource(
                    rs.getString("resourceId"),
                    rs.getString("type"),
                    rs.getString("homeLocation"),
                    rs.getInt("capacity"),
                    rs.getString("availabilityStatus")
            );
            resources.add(res);
        }

        rs.close();
        stmt.close();
        return resources;
    }

    /**
     * Insert an algorithm run record
     */
    public static void insertAlgorithmRun(String runId, String algorithmName, int inputSize, long timeNs, double memoryKb, String dateRun) throws SQLException {
        String sql = "INSERT OR IGNORE INTO algorithm_runs (runId, algorithmName, inputSize, timeNs, memoryKb, dateRun) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, runId);
        pstmt.setString(2, algorithmName);
        pstmt.setInt(3, inputSize);
        pstmt.setLong(4, timeNs);
        pstmt.setDouble(5, memoryKb);
        pstmt.setString(6, dateRun);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Insert an audit event
     */
    public static void insertAuditEvent(String eventId, String eventType, String description, String timestamp) throws SQLException {
        String sql = "INSERT INTO audit_events (eventId, eventType, description, timestamp) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);
        pstmt.setString(1, eventId);
        pstmt.setString(2, eventType);
        pstmt.setString(3, description);
        // Use current timestamp if not provided
        String ts = timestamp != null ? timestamp : String.valueOf(System.currentTimeMillis());
        pstmt.setString(4, ts);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * Import data from CSV file into a specified table
     * CSV format expected: header in first row, comma-separated values
     */
    public static void importFromCSV(String tableName, String csvPath) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String line = reader.readLine(); // Skip header
        int importedCount = 0;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Handle CSV with quoted values
            
            // Trim quotes if present
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
                if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                    values[i] = values[i].substring(1, values[i].length() - 1);
                }
            }

            try {
                switch (tableName.toLowerCase()) {
                    case "locations":
                        if (values.length >= 6) {
                            insertLocation(values[0], values[1], values[2], values[3],
                                    Double.parseDouble(values[4]), Double.parseDouble(values[5]));
                            importedCount++;
                        }
                        break;
                    case "roads":
                        if (values.length >= 5) {
                            // Generate roadId as fromLocationId-toLocationId
                            String roadId = values[0] + "-" + values[1];
                            insertRoad(roadId, values[0], values[1],
                                    Double.parseDouble(values[2]), Double.parseDouble(values[3]),
                                    values.length > 4 ? Double.parseDouble(values[4]) : 1.0);
                            importedCount++;
                        }
                        break;
                    case "service_requests":
                        if (values.length >= 8) {
                            insertServiceRequest(values[0], values[1], values[2], values[3],
                                    Integer.parseInt(values[4]), values[5], values[6], values[7]);
                            importedCount++;
                        }
                        break;
                    case "resources":
                        if (values.length >= 5) {
                            insertResource(values[0], values[1], values[2],
                                    Integer.parseInt(values[3]), values[4]);
                            importedCount++;
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("[DB] Skipping row due to format error: " + line);
            }
        }

        reader.close();
        System.out.println("[DB] Imported " + importedCount + " rows into " + tableName);
    }

    /**
     * Truncate a table (delete all rows)
     */
    public static void truncateTable(String tableName) throws SQLException {
        String sql = "DELETE FROM " + tableName;
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println("[DB] Truncated table: " + tableName);
    }

    /**
     * Check if database is using PostgreSQL or SQLite
     */
    public static boolean isPostgresDatabase() {
        return isPostgres;
    }
}
