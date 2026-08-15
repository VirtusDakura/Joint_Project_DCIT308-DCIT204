package org.ug.dsa.services;

import org.ug.dsa.database.DatabaseManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Handles recording algorithm runtime metrics and exporting benchmark data.
 *
 * Implements Module M10 (Empirical Efficiency Lab) specifications:
 *   - logAlgorithmRun : Records execution time (ns) and memory usage (KB) into the database
 *   - exportRunsToCSV : Exports recorded metrics to CSV for Excel / plotting
 *   - printRunSummary : Outputs formatted ASCII summary table of logged runs
 */
public class ReportingService {

    private final DatabaseManager dbManager;

    public ReportingService() {
        this.dbManager = new DatabaseManager();
    }

    public ReportingService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Inserts an empirical algorithm run record into the database.
     */
    public boolean logAlgorithmRun(String algorithmName, int inputSize, long timeNs, long memoryKb) {
        String runId = "RUN-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 1000);
        String sql = "INSERT INTO algorithm_runs (runId, algorithmName, inputSize, timeNs, memoryKb, dateRun) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, runId);
            ps.setString(2, algorithmName);
            ps.setInt(3, inputSize);
            ps.setLong(4, timeNs);
            ps.setLong(5, memoryKb);
            ps.setString(6, LocalDateTime.now().toString());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Warning: Could not log algorithm run to database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exports all algorithm benchmark runs from the database into a CSV file.
     */
    public boolean exportRunsToCSV(String outputPath) {
        String sql = "SELECT runId, algorithmName, inputSize, timeNs, memoryKb, dateRun FROM algorithm_runs ORDER BY algorithmName, inputSize";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {

            writer.println("runId,algorithmName,inputSize,timeNs,timeMs,memoryKb,dateRun");
            int count = 0;
            while (rs.next()) {
                long ns = rs.getLong("timeNs");
                double ms = ns / 1_000_000.0;
                writer.printf("%s,%s,%d,%d,%.4f,%d,%s%n",
                        rs.getString("runId"),
                        rs.getString("algorithmName"),
                        rs.getInt("inputSize"),
                        ns,
                        ms,
                        rs.getLong("memoryKb"),
                        rs.getString("dateRun"));
                count++;
            }
            System.out.printf("Successfully exported %d benchmark records to %s%n", count, outputPath);
            return true;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to export algorithm runs to CSV: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prints a formatted summary of logged benchmark runs grouped by algorithm.
     */
    public void printRunSummary() {
        String sql = "SELECT algorithmName, COUNT(*) as runs, MIN(timeNs) as minNs, " +
                     "AVG(timeNs) as avgNs, MAX(timeNs) as maxNs, AVG(memoryKb) as avgMem " +
                     "FROM algorithm_runs GROUP BY algorithmName ORDER BY algorithmName";

        System.out.println("============================== ALGORITHM RUN SUMMARY ==============================");
        System.out.printf("%-25s | %-6s | %-12s | %-12s | %-12s | %-10s%n",
                "Algorithm", "Runs", "Min (ms)", "Avg (ms)", "Max (ms)", "Avg Memory");
        System.out.println("-".repeat(85));

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                System.out.printf("%-25s | %-6d | %-12.3f | %-12.3f | %-12.3f | %-8.1f KB%n",
                        rs.getString("algorithmName"),
                        rs.getInt("runs"),
                        rs.getDouble("minNs") / 1_000_000.0,
                        rs.getDouble("avgNs") / 1_000_000.0,
                        rs.getDouble("maxNs") / 1_000_000.0,
                        rs.getDouble("avgMem"));
            }
            if (!hasRows) {
                System.out.println("No benchmark runs recorded in database yet.");
            }
        } catch (SQLException e) {
            System.err.println("Could not load run summary: " + e.getMessage());
        }
        System.out.println("===================================================================================");
    }
}
