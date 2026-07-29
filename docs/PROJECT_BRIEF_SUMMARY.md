# DCIT 204/308 Joint Semester Project Summary
> **Department of Computer Science - University of Ghana**

## 🎯 Key Project Constraints & Rules
1. **Core Language**: Java 17 (Maven build system).
2. **Database**: PostgreSQL / SQLite integration via JDBC.
3. **No External Collections**: Built-in Java `java.util` collections (`ArrayList`, `HashMap`, `PriorityQueue`) CANNOT be used for core algorithmic processing. All linear, tree, heap, graph, and hash data structures must be custom-built from scratch!
4. **Minimum Dataset Sizes**:
   - **Locations**: 50 records minimum
   - **Roads / Edges**: 100 records minimum
   - **Service Requests**: 300 records minimum
   - **Resources**: 30 records minimum
   - **Algorithm Benchmark Runs**: 30 records minimum
5. **Ghana Localisation**: All data must represent real Ghanaian names, places, and constraints.
6. **Index Number Integration**: At least 3 algorithm parameters must be mathematically derived from team members' Student Index Numbers.
7. **Empirical Benchmarking**: Measure execution time in nanoseconds (`System.nanoTime()`) and memory usage in KB (`Runtime.getRuntime()`), storing results in the `algorithm_runs` table.
8. **Oral Defense**: Every member will independently defend 1 data structure and 1 algorithm during final grading.
