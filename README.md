# Ghana Smart Food & Parcel Delivery Operations Optimizer

> **University of Ghana — Department of Computer Science**  
> **DCIT 204 / DCIT 308: Data Structures & Algorithms I & II — Joint Semester Project**  
> **Academic Year**: 2025/2026  
> **Team Name**: MindCraft_Sub_1  

---

## Project Overview

The **Ghana Smart Food & Parcel Delivery Operations Optimizer** is a delivery logistics platform written in Java. It models delivery operations across Greater Accra (Legon Campus, Osu, East Legon, Spintex, Makola, Airport, Tema, etc.), connecting vendors to delivery zones through dispatch riders.

The system implements **14 custom data structures** and **12+ algorithms** built from scratch without using Java's built-in collection classes (`java.util.ArrayList`, `java.util.HashMap`, `java.util.PriorityQueue`, etc.).

---

## Team Members

### DCIT 308 (Level 300)
| Student Name | Student ID | Level |
| :--- | :---: | :---: |
| **Virtus Dakura** (Group Leader) | 22052950 | Level 300 |
| **Seglah Emmanuel** | 22144981 | Level 300 |
| **Collins Edumadze Egyir** | 22233318 | Level 300 |
| **Jessica Zunuo Puozaa** | 22120404 | Level 300 |
| **Rushdan Delimwine Antiku** | 22102540 | Level 300 |

### DCIT 204 (Level 200)
| Student Name | Student ID | Level |
| :--- | :---: | :---: |
| **Koufie, Shawn Ekow** | 22304100 | Level 200 |
| **Boame, Peter** | 22363563 | Level 200 |
| **Halim Hamidu Haruna** | 22119064 | Level 200 |
| **Forson, Alfred** | 22380272 | Level 200 |
| **Timothy Eli Abotsikpuia** | 22397906 | Level 200 |
| **Agbo, Jonathan** | 22034889 | Level 200 |
| **Asiedu, Emmanuel Dede** | 22319587 | Level 200 |
| **Serwaah, Richlove** | 22308313 | Level 200 |

---

## Project Documentation

All academic documentation and project deliverables are stored in this repository:

| Document | Format | Description & Link |
| :--- | :---: | :--- |
| **Submission Checklist & Cover Sheet** | DOCX | [docs/DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx](docs/DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx) — Completed cover sheet |
| **Technical Report** | Markdown / DOCX | [docs/TECHNICAL_REPORT.md](docs/TECHNICAL_REPORT.md) &bull; [docs/TECHNICAL_REPORT.docx](docs/TECHNICAL_REPORT.docx) — Full project report |
| **Algorithmic Counterexamples** | Markdown | [docs/COUNTEREXAMPLES.md](docs/COUNTEREXAMPLES.md) — Precondition failures for Binary Search & Greedy batching |
| **Trace Tables** | 7 Traces | [docs/trace-tables/](docs/trace-tables/) — Binary Search, Selection Sort, Insertion Sort, Merge Sort, Dijkstra, 0/1 Knapsack DP, Kruskal MST |
| **Proof Sketches** | 3 Proofs | [docs/proof-sketches/](docs/proof-sketches/) — Merge Sort loop invariant, QuickSort recurrence, Greedy vs DP |
| **Performance Graphs** | JPG & HTML | [docs/performance-graphs/index.html](docs/performance-graphs/index.html) &bull; [Graphs Folder](docs/performance-graphs/) — Six runtime experiments |
| **Benchmark Timing Data** | CSV | [data/algorithm_benchmark_runs.csv](data/algorithm_benchmark_runs.csv) — 55 measured runtime runs |
| **Database Schema** | SQL | [data/schema.sql](data/schema.sql) — DDL statements for tables and indexes |

---

## Quick Start

### 1. Compile and Run Automated Tests
```powershell
powershell -ExecutionPolicy Bypass -File .\build_and_test.ps1
```

### 2. Run the Interactive Console Application
```powershell
java -cp bin org.ug.dsa.Main
```

### 3. Run the Standalone Test Runner
```powershell
java -cp bin org.ug.dsa.TestRunner
```

---

## Repository Structure

```text
├── build_and_test.ps1                         # Build and test execution script
├── pom.xml                                    # Maven build configuration
├── README.md                                  # Project overview and documentation links
├── dsa_optimizer.db                           # SQLite database file
├── data/                                      # Data files and database scripts
│   ├── locations.csv                          # 52 delivery locations
│   ├── roads.csv                              # 120 road edges
│   ├── service_requests.csv                   # 345 delivery requests
│   ├── resources.csv                          # 31 delivery riders
│   ├── algorithm_benchmark_runs.csv           # 55 empirical runtime runs
│   └── schema.sql                             # Database schema definition
├── docs/                                      # Project documentation
│   ├── DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx # Completed submission checklist
│   ├── TECHNICAL_REPORT.md                    # Technical report in Markdown
│   ├── TECHNICAL_REPORT.docx                  # Technical report in Word DOCX format
│   ├── COUNTEREXAMPLES.md                     # Algorithmic counterexamples
│   ├── trace-tables/                          # 7 correctness trace tables
│   ├── proof-sketches/                        # 3 formal proof sketches
│   └── performance-graphs/                    # 6 benchmark plots and web report
│       ├── exp1_search_benchmark.jpg          # Linear vs Binary Search
│       ├── exp2_sorting_benchmark.jpg         # Comparison of 4 Sorting Algorithms
│       ├── exp3_hashtable_benchmark.jpg       # Hash Table Load Factor vs Latency
│       ├── exp4_tree_benchmark.jpg            # BST vs Red-Black Tree
│       ├── exp5_heap_benchmark.jpg            # Min-Heap Insert vs Extract-Min
│       ├── exp6_graph_benchmark.jpg           # Graph algorithms
│       └── index.html                         # Benchmark dashboard
└── src/
    ├── main/java/org/ug/dsa/
    │   ├── Main.java                          # Interactive console application
    │   ├── TestRunner.java                    # Standalone test suite runner
    │   ├── datastructures/                    # 14 custom data structures
    │   ├── algorithms/                        # Search, sort, graph, and optimization algorithms
    │   ├── services/                          # Routing, scheduling, indexing, reporting services
    │   ├── models/                            # Data models (Location, Road, Request, Resource)
    │   ├── database/                          # Database connection manager
    │   └── util/                              # Student index parameter formulas
    └── test/java/org/ug/dsa/                  # Unit test suites (42 tests, 0 failures)
```

---

## Summary of Implemented Work

- **14 Custom Data Structures**: Dynamic Array, Doubly Linked List with custom Iterator, Stack, Queue, Circular Queue, Deque, Min-Heap Priority Queue, Binary Search Tree, Red-Black Tree, B-Tree ($t=3$), Hash Table (Separate Chaining), Set, Map, Disjoint Set (Union-Find), and Weighted Graph (Adjacency List & Matrix).
- **Algorithms**: Linear Search, Binary Search (with precondition check), Selection Sort, Insertion Sort, Merge Sort, QuickSort, BFS, DFS, Dijkstra's Shortest Path, Kruskal's MST, Prim's MST, Greedy Dispatch Heuristic, and 0/1 Knapsack Dynamic Programming.
- **Verification**: 42 automated tests (all passing), 7 trace tables, 3 proof sketches, and 2 counterexamples.
- **Performance Experiments**: 55 measured runtime runs comparing theoretical Big-O complexity with actual execution time across input sizes from 100 to 10,000.