# UNIVERSITY OF GHANA
## DEPARTMENT OF COMPUTER SCIENCE
### DCIT 204 / DCIT 308: Data Structures & Algorithms I & II
### Joint Semester Project Submission Checklist & Cover Sheet

---

## 📋 Project Information

| Field | Details |
| :--- | :--- |
| **Team Name** | **Ghana Smart Logistics Team** (Group 5 / MindCraft) |
| **Selected Ghana Context** | Courier and Food Delivery Service in Greater Accra (Accra Central, Legon Campus, East Legon, Osu, Spintex, Makola, Airport, Tema, etc.) |
| **Organisation / Problem Modelled** | **Ghana Smart Food & Parcel Delivery Operations Optimizer** |
| **Database Used** | **SQLite** (`dsa_optimizer.db`) with PostgreSQL schema compatibility |
| **Programming Language & Version** | **Java (JDK 22 / JDK 17+ compatible)**; pure custom data structures without built-in collections |
| **Total Records in Dataset** | **548 total records** across 4 operational entities:<br>• **52 Locations** (`data/locations.csv`)<br>• **120 Weighted Roads** (`data/roads.csv`)<br>• **345 Service Requests** (`data/service_requests.csv`)<br>• **31 Delivery Resources** (`data/resources.csv`) |
| **Repository Name / URL** | [`VirtusDakura/Joint_Project_DCIT308-DCIT204`](https://github.com/VirtusDakura/Joint_Project_DCIT308-DCIT204) |

---

## 👥 Project Team Roster & Defense Assignments

### DCIT 308 (Level 300 Mentors)
| Student Name | Student ID | Level | Assigned Data Structure | Assigned Algorithm |
| :--- | :---: | :---: | :--- | :--- |
| **Virtus Dakura** (Group Leader) | 22052950 | Level 300 | `CustomGraph` (Adjacency List & Matrix) | Dijkstra's Shortest Path Algorithm |
| **Seglah Emmanuel** | 22144981 | Level 300 | `CustomLinkedList` (with Custom Iterator) | Insertion Sort |
| **Collins Edumadze Egyir** | 22233318 | Level 300 | `CustomHashTable` (Separate Chaining) | Binary Search (with precondition check) |
| **Jessica Zunuo Puozaa** | 22120404 | Level 300 | `CustomBST` & `CustomRedBlackTree` | Selection Sort |
| **Rushdan Delimwine Antiku** | 22102540 | Level 300 | `CustomHeap` (Min-Heap Priority Queue) | Heap-based Priority Dispatch |

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

## ✅ Submission Checklist & Evidence Map

| # | Requirement | Status | Exact Evidence Location in Repository |
| :-: | :--- | :-: | :--- |
| 1 | **Local dataset with data dictionary** | ✅ Met | • `data/locations.csv` (52 locations)<br>• `data/roads.csv` (120 road edges)<br>• `data/service_requests.csv` (345 requests)<br>• `data/resources.csv` (31 riders)<br>• Data dictionary documented in `docs/TECHNICAL_REPORT.md` (Section 10) |
| 2 | **Database schema and seed data** | ✅ Met | • `data/schema.sql` and `schema.sql`<br>• `dsa_optimizer.db` (SQLite relational database)<br>• `src/main/java/org/ug/dsa/database/DatabaseManager.java` |
| 3 | **Custom data structures implemented** | ✅ Met | `src/main/java/org/ug/dsa/datastructures/` (14 custom structures built from scratch, 0 built-in collections):<br>1. `CustomDynamicArray.java`<br>2. `CustomLinkedList.java`<br>3. `CustomStack.java`<br>4. `CustomQueue.java`<br>5. `CustomCircularQueue.java`<br>6. `CustomDeque.java`<br>7. `CustomHeap.java`<br>8. `CustomBST.java`<br>9. `CustomRedBlackTree.java`<br>10. `CustomBTree.java`<br>11. `CustomHashTable.java`<br>12. `CustomSet.java`<br>13. `CustomMap.java`<br>14. `CustomDisjointSet.java`<br>15. `CustomGraph.java` |
| 4 | **Searching and sorting algorithms** | ✅ Met | • Search: `src/main/java/org/ug/dsa/algorithms/search/` (`LinearSearch.java`, `BinarySearch.java`)<br>• Sort: `src/main/java/org/ug/dsa/algorithms/sorting/` (`SelectionSort.java`, `InsertionSort.java`, `MergeSort.java`, `QuickSort.java`) |
| 5 | **Graph algorithms implemented** | ✅ Met | `src/main/java/org/ug/dsa/algorithms/graph/`:<br>• `BFSReachability.java`<br>• `DFSCycleDetection.java`<br>• `DijkstraAlgorithm.java`<br>• `PrimKruskalMST.java`<br>• Adjacency List & Matrix representations in `CustomGraph.java` |
| 6 | **Greedy and DP algorithms** | ✅ Met | • Greedy: `src/main/java/org/ug/dsa/algorithms/optimization/GreedyBatching.java`<br>• DP: `src/main/java/org/ug/dsa/algorithms/optimization/DynamicProgrammingBatching.java`<br>• Counterexamples: `docs/COUNTEREXAMPLES.md` |
| 7 | **Correctness tests and trace tables** | ✅ Met | • 42 automated tests (0 failures): `src/test/java/org/ug/dsa/` and `src/main/java/org/ug/dsa/TestRunner.java`<br>• 7 Trace tables: `docs/trace-tables/` (Traces 1 to 7)<br>• 3 Proof sketches: `docs/proof-sketches/` (Proofs 1 to 3) |
| 8 | **Performance CSV and graphs** | ✅ Met | • Raw CSV results (55 runs): `data/algorithm_benchmark_runs.csv`<br>• 6 High-resolution plotted graphs: `docs/performance-graphs/exp1_search_benchmark.jpg` through `exp6_graph_benchmark.jpg`<br>• Interactive web dashboard: `docs/performance-graphs/index.html` |
| 9 | **Technical report** | ✅ Met | • Full Technical Report (Markdown): `docs/TECHNICAL_REPORT.md` (988 lines, 17 sections)<br>• Microsoft Word Report: `docs/TECHNICAL_REPORT.docx`<br>• Filled Cover Sheet: `DCIT_308_Industry_Project_Checklist_Cover_Sheet.docx` |
| 10 | **Demo video / oral defense prepared** | ✅ Met | • Interactive CLI with 11 functional modules: `src/main/java/org/ug/dsa/Main.java`<br>• Individual defense matrices, pod collaboration architecture, and question responses in `docs/TECHNICAL_REPORT.md` (Sections 12 & 13) |

---

*Compiled and verified for submission by Virtus Dakura, Group Leader.*
