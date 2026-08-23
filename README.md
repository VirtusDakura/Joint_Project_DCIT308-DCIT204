# Ghana Smart Food & Parcel Delivery Operations Optimizer

> **University of Ghana — Department of Computer Science**  
> **DCIT 204 / DCIT 308: Data Structures & Algorithms I & II — Joint Semester Project**  
> **Academic Year**: 2025/2026

---

## 📌 Project Overview

The **Ghana Smart Food & Parcel Delivery Operations Optimizer** is an urban delivery logistics system built in Java. It models delivery operations across Greater Accra (Legon Campus, Osu, East Legon, Spintex, Makola, etc.), connecting vendors to delivery zones via dispatch riders.

The project implements **13 custom data structures** and **12+ algorithms** built entirely from scratch with **no built-in Java collection classes** (`java.util.ArrayList`, `java.util.HashMap`, `java.util.PriorityQueue`, etc.).

---

## 👥 Project Team

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

## 🚀 Quick Start

### 1. Build and Run All Automated Tests
```powershell
powershell -ExecutionPolicy Bypass -File .\build_and_test.ps1
```

### 2. Launch the Application
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
├── build_and_test.ps1             # Build and test execution script
├── pom.xml                        # Maven configuration file
├── README.md                      # Project documentation
├── data/                          # CSV seed datasets
│   ├── locations.csv              # 52 delivery locations
│   ├── roads.csv                  # 120 weighted road edges
│   ├── service_requests.csv       # 345 service requests
│   └── resources.csv              # 31 delivery riders
├── docs/                          # Academic documentation
│   ├── TECHNICAL_REPORT.md        # Technical project report
│   ├── COUNTEREXAMPLES.md         # Algorithmic counterexamples
│   ├── trace-tables/              # Trace tables (Traces 1 to 7)
│   └── proof-sketches/            # Proof sketches (Proofs 1 to 3)
└── src/
    ├── main/java/org/ug/dsa/
    │   ├── Main.java              # Interactive console application
    │   ├── TestRunner.java        # CLI test suite runner
    │   ├── datastructures/        # 14 custom data structures
    │   ├── algorithms/            # Search, sort, graph, and optimization algorithms
    │   ├── services/              # Routing, scheduling, indexing, reporting services
    │   ├── models/                # Domain models (Location, Road, Request, Resource)
    │   ├── database/              # SQLite / PostgreSQL JDBC database manager
    │   └── util/                  # Index-derived parameters
    └── test/java/org/ug/dsa/      # JUnit 5 unit test suites
```

---

## 📊 Summary of Implemented Components

- **14 Custom Data Structures**: Dynamic Array, Doubly Linked List with Iterator, Stack, Queue, Circular Queue, Deque, Min-Heap Priority Queue, BST, Red-Black Tree, B-Tree ($t=3$), Hash Table (Separate Chaining), Set, Map, Disjoint Set (Union-Find), Weighted Graph (Adjacency List & Matrix).
- **Algorithms**: Linear Search, Binary Search (with precondition check), Selection Sort, Insertion Sort, Merge Sort, QuickSort, BFS, DFS, Dijkstra's Shortest Path, Kruskal's MST, Prim's MST, Greedy Dispatch Heuristic, and 0/1 Knapsack Dynamic Programming.
- **Verification**: 42 automated tests (0 failures), 7 trace tables, 3 proof sketches, and 2 formal counterexamples.