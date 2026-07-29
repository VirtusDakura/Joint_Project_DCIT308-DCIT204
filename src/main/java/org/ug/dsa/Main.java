package org.ug.dsa;

import org.ug.dsa.database.DatabaseManager;
import org.ug.dsa.datastructures.CustomHeap;
import org.ug.dsa.models.Location;
import org.ug.dsa.services.ReportingService;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println(" Ghana Smart Service Operations Optimizer (DCIT 204/308) ");
        System.out.println("==========================================================");

        // 1. Initialize Database Tables
        System.out.println("\n[1] Initializing Database Schema...");
        DatabaseManager.initializeTables();

        // 2. Demonstrate Custom Data Structure
        System.out.println("\n[2] Testing Custom Data Structure (Min-Heap)...");
        CustomHeap<Integer> heap = new CustomHeap<>(10);
        heap.insert(45);
        heap.insert(12);
        heap.insert(89);
        heap.insert(5);
        System.out.println("Extracted minimum value: " + heap.extractMin()); // Should print 5

        // 3. Record Sample Empirical Benchmark
        System.out.println("\n[3] Recording Empirical Benchmark Metric...");
        long startTime = System.nanoTime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // Simulate operation
        Location sampleLoc = new Location("L001", "Balme Library", "Legon", "Library", 5.650, 0.190);
        System.out.println("Loaded Sample Location: " + sampleLoc);

        long endTime = System.nanoTime();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        ReportingService.logAlgorithmRun("MinHeapInsert", 100, (endTime - startTime), Math.max(1, (endMemory - startMemory) / 1024));

        // 4. Export Benchmark CSV for Report Charts
        System.out.println("\n[4] Exporting Benchmarking CSV for Excel Charts...");
        ReportingService.exportRunsToCSV("data/algorithm_runs_export.csv");

        System.out.println("\n[SUCCESS] Scaffold verification complete!");
    }
}
