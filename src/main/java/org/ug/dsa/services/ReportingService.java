package org.ug.dsa.services;

/**
 * Handles recording algorithm runtime metrics and exporting benchmark data.
 *
 * Assigned to: Seglah Emmanuel (22144981)
 *
 * Required methods:
 *   - logAlgorithmRun(String algorithmName, int inputSize, long timeNs, long memoryKb)
 *       Inserts a run record into the algorithm_runs database table.
 *   - exportRunsToCSV(String outputPath)
 *       Exports all algorithm_runs to a CSV file for Excel/Google Sheets graphing.
 *       Columns: runId, algorithmName, inputSize, timeNs, memoryKb, dateRun
 *   - printRunSummary()
 *       Prints a formatted summary of all runs grouped by algorithm name.
 *
 * Usage: All team members will call logAlgorithmRun() after measuring their
 * algorithm's performance using System.nanoTime() and Runtime.getRuntime().
 *
 * Dependencies: java.sql.* (JDBC), java.io.* (CSV export) — both allowed.
 */
public class ReportingService {

    // TODO: Implement benchmark logging to database and CSV export.

}
