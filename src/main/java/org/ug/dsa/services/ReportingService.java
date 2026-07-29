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
import java.util.UUID;

/**
 * Empirical benchmarking & report engine (Modules M9, M10).
 * Logs time (ns) and memory (KB) to algorithm_runs DB table, and exports CSV for Excel chart creation.
 */
public class ReportingService {

    public static void logAlgorithmRun(String algorithmName, int inputSize, long timeNs, long memoryKb) {
        String sql = """
            INSERT INTO algorithm_runs (run_id, algorithm_name, input_size, time_ns, memory_kb, date_run)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "RUN-" + UUID.randomUUID().toString().substring(0, 8));
            pstmt.setString(2, algorithmName);
            pstmt.setInt(3, inputSize);
            pstmt.setLong(4, timeNs);
            pstmt.setLong(5, memoryKb);
            pstmt.setString(6, LocalDateTime.now().toString());
            pstmt.executeUpdate();
            
            System.out.printf("[ReportingService] Recorded run: %s | N=%d | Time: %d ns | Memory: %d KB%n",
                    algorithmName, inputSize, timeNs, memoryKb);

        } catch (SQLException e) {
            System.err.println("[ReportingService] Error recording algorithm run: " + e.getMessage());
        }
    }

    public static void exportRunsToCSV(String exportFilePath) {
        String sql = "SELECT * FROM algorithm_runs ORDER BY algorithm_name, input_size";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
             PrintWriter writer = new PrintWriter(new FileWriter(exportFilePath))) {

            writer.println("run_id,algorithm_name,input_size,time_ns,memory_kb,date_run");
            while (rs.next()) {
                writer.printf("%s,%s,%d,%d,%d,%s%n",
                        rs.getString("run_id"),
                        rs.getString("algorithm_name"),
                        rs.getInt("input_size"),
                        rs.getLong("time_ns"),
                        rs.getLong("memory_kb"),
                        rs.getString("date_run"));
            }
            System.out.println("[ReportingService] Successfully exported benchmarking CSV to: " + exportFilePath);

        } catch (SQLException | IOException e) {
            System.err.println("[ReportingService] Error exporting CSV: " + e.getMessage());
        }
    }
}
