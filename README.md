# Ghana Smart Food & Parcel Delivery Operations Optimizer

> **University of Ghana — Department of Computer Science**  
> **DCIT 204 / DCIT 308: Data Structures & Algorithms I & II — Joint Semester Project**  
> **Academic Year**: 2025/2026

---

## 📌 Project Overview

The **Ghana Smart Food & Parcel Delivery Operations Optimizer** is an enterprise-grade urban logistics and delivery route optimization platform developed in Java. It models delivery operations across Greater Accra (Legon Campus, Osu, East Legon, Spintex, Makola, Airport, Tema, etc.), connecting vendors to delivery zones via dispatch riders.

The project implements **14 custom data structures** and **12+ algorithms** built entirely from scratch with **zero built-in Java collection classes** (`java.util.ArrayList`, `java.util.HashMap`, `java.util.PriorityQueue`, etc.).

---

## 📚 Complete Project Documentation Hub

All academic documentation and project deliverables required by the DCIT 204/308 joint syllabus are included directly in this repository:

| Document / Deliverable | Format | Description & Link |
| :--- | :---: | :--- |
| **Submission Checklist & Cover Sheet** | Markdown / DOCX | [CHECKLIST_COVER_SHEET.md](CHECKLIST_COVER_SHEET.md) &bull; [DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx](DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx) |
| **Comprehensive Technical Report** | Markdown / DOCX | [docs/TECHNICAL_REPORT.md](docs/TECHNICAL_REPORT.md) &bull; [docs/TECHNICAL_REPORT.docx](docs/TECHNICAL_REPORT.docx) (50KB+, 17 sections) |
| **Formal Counterexamples** | Markdown | [docs/COUNTEREXAMPLES.md](docs/COUNTEREXAMPLES.md) (Precondition failures for Binary Search & Greedy batching) |
| **Correctness Trace Tables** | 7 Traces | [docs/trace-tables/](docs/trace-tables/) (Binary Search, Selection, Insertion, Merge, Dijkstra, Knapsack DP, Kruskal) |
| **Formal Proof Sketches** | 3 Proofs | [docs/proof-sketches/](docs/proof-sketches/) (Merge Sort Invariant, QuickSort Recurrence, Greedy vs DP) |
| **Empirical Performance Graphs** | High-Res JPG & Web | [docs/performance-graphs/index.html](docs/performance-graphs/index.html) &bull; [Graphs Directory](docs/performance-graphs/) |
| **Raw Benchmark Timing Data** | CSV (55 runs) | [data/algorithm_benchmark_runs.csv](data/algorithm_benchmark_runs.csv) &bull; [docs/performance-graphs/benchmark_results.csv](docs/performance-graphs/benchmark_results.csv) |
| **Database Schema DDL Script** | SQL | [data/schema.sql](data/schema.sql) &bull; [schema.sql](schema.sql) (SQLite & PostgreSQL compatible) |
| **Joint DSA Project Brief** | DOCX | [docs/Joint_DSA_Project_Brief.docx](docs/Joint_DSA_Project_Brief.docx) |

---

## 👥 Project Team Roster & Oral Defense Matrix

Our team was organised into **5 Cross-Level Collaborative Mentorship Pods**, pairing DCIT 308 (Level 300) mentors with DCIT 204 (Level 200) mentees. Each member defends **1 Custom Data Structure** and **1 Algorithm** during the oral defense:

### DCIT 308 (Level 300 Mentors)
| Student Name | Student ID | Level | Assigned Data Structure | Assigned Algorithm |
| :--- | :---: | :---: | :--- | :--- |
| **Virtus Dakura** (Group Leader) | 22052950 | Level 300 | `CustomGraph` (Adjacency List & Matrix) | Dijkstra's Shortest Path Algorithm |
| **Seglah Emmanuel** | 22144981 | Level 300 | `CustomLinkedList` (with Custom Iterator) | Insertion Sort |
| **Collins Edumadze Egyir** | 22233318 | Level 300 | `CustomHashTable` (Separate Chaining) | Binary Search (with precondition check) |
| **Jessica Zunuo Puozaa** | 22120404 | Level 300 | `CustomBST` & `CustomRedBlackTree` | Selection Sort |
| **Rushdan Delimwine Antiku** | 22102540 | Level 300 | `CustomHeap` (Min-Heap Priority Queue) | Heap Priority Dispatch |

### DCIT 204 (Level 200 Mentees)
| Student Name | Student ID | Level | Assigned Data Structure | Assigned Algorithm |
| :--- | :---: | :---: | :--- | :--- |
| **Koufie, Shawn Ekow** | 22304100 | Level 200 | `CustomDynamicArray` | Merge Sort (Divide-and-Conquer) |
| **Boame, Peter** | 22363563 | Level 200 | `CustomQueue` | QuickSort (Median-of-Three) |
| **Halim Hamidu Haruna** | 22119064 | Level 200 | `CustomSet` & `CustomMap` | Linear Search (Comparison Counting) |
| **Forson, Alfred** | 22380272 | Level 200 | `CustomBTree` (order $t=3$) | 0/1 Knapsack Dynamic Programming |
| **Timothy Eli Abotsikpuia** | 22397906 | Level 200 | `CustomCircularQueue` | Database CRUD & Index Formulas |
| **Agbo, Jonathan** | 22034889 | Level 200 | `CustomStack` | BFS Reachability Traversal |
| **Asiedu, Emmanuel Dede** | 22319587 | Level 200 | `CustomDisjointSet` (Union-Find) | Kruskal's & Prim's Minimum Spanning Tree |
| **Serwaah, Richlove** | 22308313 | Level 200 | `CustomDeque` (Double-Ended Queue) | Greedy Priority Dispatch Heuristic |

---

## 🚀 Quick Start & Execution

### 1. Build and Run All Automated Tests
```powershell
powershell -ExecutionPolicy Bypass -File .\build_and_test.ps1
```

### 2. Launch the Application Interactive CLI
```powershell
java -cp bin org.ug.dsa.Main
```

### 3. Run Standalone Test Suite Runner
```powershell
java -cp bin org.ug.dsa.TestRunner
```

---

## 📁 Repository Structure

```text
├── build_and_test.ps1                         # Automated build and test script
├── pom.xml                                    # Maven project configuration
├── README.md                                  # Main documentation hub
├── CHECKLIST_COVER_SHEET.md                   # Project checklist & cover sheet (Markdown)
├── DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx # Filled cover sheet (DOCX)
├── schema.sql                                 # Relational database schema script
├── dsa_optimizer.db                           # Relational SQLite database
├── data/                                      # Datasets & database scripts
│   ├── locations.csv                          # 52 delivery locations
│   ├── roads.csv                              # 120 weighted road edges
│   ├── service_requests.csv                   # 345 service requests
│   ├── resources.csv                          # 31 delivery riders
│   ├── algorithm_benchmark_runs.csv           # 55 empirical benchmarking runs (CSV)
│   └── schema.sql                             # Schema DDL definitions
├── docs/                                      # Academic documentation
│   ├── TECHNICAL_REPORT.md                    # 17-section technical report (Markdown)
│   ├── TECHNICAL_REPORT.docx                  # Technical report (Microsoft Word DOCX)
│   ├── CHECKLIST_COVER_SHEET.md               # Copy of submission checklist
│   ├── DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx # Submission checklist
│   ├── Joint_DSA_Project_Brief.docx           # Official course assignment brief
│   ├── COUNTEREXAMPLES.md                     # Algorithmic counterexamples
│   ├── trace-tables/                          # Correctness trace tables (Traces 1 to 7)
│   ├── proof-sketches/                        # Loop invariant & recurrence proofs (Proofs 1 to 3)
│   └── performance-graphs/                    # Benchmark graphs & interactive dashboard
│       ├── exp1_search_benchmark.jpg          # Linear vs Binary Search
│       ├── exp2_sorting_benchmark.jpg         # Comparison of 4 Sorting Algorithms
│       ├── exp3_hashtable_benchmark.jpg       # Hash Table Load Factor vs Latency
│       ├── exp4_tree_benchmark.jpg            # BST vs Red-Black Tree Sequential Insert
│       ├── exp5_heap_benchmark.jpg            # Min-Heap Insert vs Extract-Min
│       ├── exp6_graph_benchmark.jpg           # BFS vs DFS vs Dijkstra vs MST
│       ├── benchmark_results.csv              # Raw benchmark timings
│       └── index.html                         # Interactive visual performance report
└── src/
    ├── main/java/org/ug/dsa/
    │   ├── Main.java                          # Interactive 11-module console application
    │   ├── TestRunner.java                    # CLI test suite runner
    │   ├── datastructures/                    # 14 custom data structures (0 built-in collections)
    │   ├── algorithms/                        # Search, sort, graph, and optimization algorithms
    │   ├── services/                          # Routing, scheduling, indexing, reporting services
    │   ├── models/                            # Domain models (Location, Road, Request, Resource)
    │   ├── database/                          # SQLite / PostgreSQL JDBC manager
    │   └── util/                              # Index-derived AI-resistance parameters
    └── test/java/org/ug/dsa/                  # JUnit 5 unit test suites (42 automated tests)
```

---

## 📊 Summary of Implemented Components

- **14 Custom Data Structures**: Dynamic Array, Doubly Linked List with Iterator, Stack, Queue, Circular Queue, Deque, Min-Heap Priority Queue, BST, Red-Black Tree, B-Tree ($t=3$), Hash Table (Separate Chaining), Set, Map, Disjoint Set (Union-Find), Weighted Graph (Adjacency List & Matrix).
- **Algorithms**: Linear Search, Binary Search (with precondition check), Selection Sort, Insertion Sort, Merge Sort, QuickSort, BFS, DFS, Dijkstra's Shortest Path, Kruskal's MST, Prim's MST, Greedy Dispatch Heuristic, and 0/1 Knapsack Dynamic Programming.
- **Verification**: 42 automated tests (0 failures), 7 trace tables, 3 proof sketches, and 2 formal counterexamples.
- **Empirical Benchmarks**: 55 runtime experiments comparing theoretical Big-O predictions with measured wall-clock latencies across input sizes $N \in [100, 10000]$.