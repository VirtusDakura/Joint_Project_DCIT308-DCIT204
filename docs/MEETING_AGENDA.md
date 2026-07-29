# Kickoff Meeting Agenda & Team Signup Sheet
> **DCIT 204 / DCIT 308 Joint DSA Project: Ghana Smart Service Operations Optimizer**

---

## 🕒 Meeting Schedule & Structure (60 Minutes Total)

### 1. Project Scope & Assessment Overview (10 Mins)
- **Goal**: Build an end-to-end service operations optimization platform in Java 17.
- **Dataset Requirements**: Minimum 50 locations, 100 roads, 300 service requests, 30 resources, 30 algorithm runs.
- **Key Grading Criteria**: Custom data structure implementations, graph routing, empirical analysis, database loader, and individual defense.

---

### 2. Local Ghanaian Context Voting (10 Mins)
*Vote on one Ghanaian context for our dataset names and operational constraints:*
- [ ] **Option 1: UG Legon Campus Operations Hub** (Balme Library, CS Dept, UGMC, Hostels, Shuttle stops, Maintenance requests)
- [ ] **Option 2: Greater Accra Emergency & Medical Logistics** (Korle Bu, Ridge Hospital, UGMC, Ambulance & Pharmacy dispatch)
- [ ] **Option 3: Accra Municipal Waste & Sanitation Routing** (Collection zones, Truck routes, Priority areas)

---

### 3. Student Index Number Collection (5 Mins)
*The project brief requires at least 3 algorithm parameters to be derived from member index numbers.*

| # | Student Full Name | Student Index Number | GitHub Username | Sub-Team / Module Role |
|---|-------------------|----------------------|-----------------|------------------------|
| 1 | *(Leader Name)*   |                      |                 | Lead / Repo Admin      |
| 2 |                   |                      |                 |                        |
| 3 |                   |                      |                 |                        |
| 4 |                   |                      |                 |                        |
| 5 |                   |                      |                 |                        |
| 6 |                   |                      |                 |                        |
| 7 |                   |                      |                 |                        |
| 8 |                   |                      |                 |                        |
| 9 |                   |                      |                 |                        |
| 10|                   |                      |                 |                        |
| 11|                   |                      |                 |                        |
| 12|                   |                      |                 |                        |

---

### 4. Sub-Team & Module Roles (20 Mins)

#### Team 1: Database & Data Loader (Modules M1, M2)
- **Members**: 
- **Deliverables**: PostgreSQL & SQLite schema DDL, CSV data parser, database loader into custom DSA.

#### Team 2: Custom Data Structure Library (Module M3)
- **Members**: 
- **Deliverables**: Implement generic `LinkedList`, `Stack`, `Queue`, `Heap`, `BST`, `HashTable`, `DisjointSet`, `Graph`.

#### Team 3: Search, Sort & Indexing Engine (Modules M4, M6)
- **Members**: 
- **Deliverables**: Implement linear/binary search, MergeSort, QuickSort, and BST tree indexing.

#### Team 4: Graph Route & Optimization Engine (Modules M7, M8)
- **Members**: 
- **Deliverables**: Implement BFS, DFS, Dijkstra's algorithm, Prim/Kruskal MST, and Greedy vs DP optimization.

#### Team 5: Empirical Benchmarking & Report Lead (Modules M9, M10)
- **Members**: 
- **Deliverables**: Benchmark execution time ($ns$) and memory ($KB$), export CSVs, generate Excel charts, assemble final technical report.

---

### 5. Individual Defense Mapping (10 Mins)
*Every student must defend 1 Data Structure + 1 Algorithm during oral presentation.*

| Student Name | Assigned Data Structure | Assigned Algorithm |
|--------------|-------------------------|--------------------|
|              | Min-Heap                | Dijkstra's         |
|              | Custom Graph            | BFS / DFS          |
|              | Binary Search Tree      | QuickSort          |
|              | Custom Hash Table       | Binary Search      |
|              | Disjoint Set (Union-Find)| Kruskal's MST      |

---

### 6. Action Items & Next Steps (5 Mins)
- [ ] Clone repo & verify `mvn compile` runs cleanly.
- [ ] Sub-teams create feature branches and begin initial draft implementations.
- [ ] Next Sync Meeting: *(Set Date & Time)*
