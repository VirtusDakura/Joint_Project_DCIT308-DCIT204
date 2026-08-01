# Ghana Smart Food & Parcel Delivery System
> **University of Ghana — Department of Computer Science**  
> **Course**: DCIT 204 / DCIT 308: Data Structures & Algorithms I & II  
> **Group Leader**: Virtus Dakura (22052950)  
> **Setting**: Ghana Food & Parcel Delivery Services  

---

## 📌 Project Overview
The **Ghana ChowExpress Food Delivery Optimizer** is a service operations platform tailored to Ghanaian food delivery workflows. It connects vendors (Papaye, Bush Canteen, Buka, Gobə Joint) to customer delivery zones (hostels, office hubs, campus stops) via dispatch riders.

The platform loads operational data from a database into custom-built data structures, applies algorithms (searching, sorting, graph routing, greedy/DP optimization), verifies correctness with trace tables and unit tests, and measures empirical runtime efficiency.

---

## 📁 Repository Directory Structure

```text
Joint_Project_DCIT308-DCIT204/
├── pom.xml                        # Maven build config (Java 17, PostgreSQL, JUnit 5)
├── README.md
├── data/                          # Operational CSV seed datasets
│   ├── locations.csv              # 50+ Ghanaian locations
│   ├── roads.csv                  # 100+ weighted road edges
│   ├── service_requests.csv       # 300+ food delivery orders
│   └── resources.csv              # 30+ delivery riders
├── docs/                          # Project documentation
│   ├── PROJECT_CHARTER.md         # Team charter, sprint plan, submission items
│   ├── PROJECT_BRIEF_SUMMARY.md   # Key constraints from the project brief
│   ├── TEAM_ROSTER_AND_DEFENSE.md # Roster + oral defense pairing matrix
│   └── TASK_DISTRIBUTION.md       # Individual member task assignments
└── src/
    ├── main/java/org/ug/dsa/
    │   ├── Main.java              # Console menu entry point
    │   ├── models/                # Data models: Location, Road, ServiceRequest, Resource
    │   ├── database/              # DatabaseManager (PostgreSQL/SQLite JDBC)
    │   ├── datastructures/        # DCIT 308: Custom data structures
    │   │   ├── CustomDynamicArray.java
    │   │   ├── CustomLinkedList.java
    │   │   ├── CustomStack.java
    │   │   ├── CustomQueue.java
    │   │   ├── CustomCircularQueue.java
    │   │   ├── CustomDeque.java
    │   │   ├── CustomHeap.java
    │   │   ├── CustomBST.java
    │   │   ├── CustomRedBlackTree.java
    │   │   ├── CustomBTree.java
    │   │   ├── CustomHashTable.java
    │   │   ├── CustomSet.java
    │   │   ├── CustomMap.java
    │   │   ├── CustomDisjointSet.java
    │   │   └── CustomGraph.java
    │   ├── algorithms/            # DCIT 204: Algorithm implementations
    │   │   ├── search/            # LinearSearch, BinarySearch
    │   │   ├── sorting/           # SelectionSort, InsertionSort, MergeSort, QuickSort
    │   │   ├── graph/             # BFS, DFS, Dijkstra, Prim, Kruskal
    │   │   └── optimization/      # GreedyBatching, DynamicProgrammingBatching
    │   ├── services/              # Scheduling, Routing, Reporting & CSV export
    │   └── util/                  # IndexParameters (index-number-derived constants)
    └── test/java/org/ug/dsa/      # JUnit 5 unit tests (40+ required)
```

---

## 🛠️ Quickstart

### Compilation & Execution
```bash
# 1. Compile Java source code
javac -d bin $(find src/main/java -name "*.java")

# 2. Run the console application
java -cp bin org.ug.dsa.Main

# 3. Or using Maven
mvn clean compile exec:java
```

### Database
- Connects to PostgreSQL when available (`DB_URL`, `DB_USER`, `DB_PASS` environment variables).
- Automatically falls back to local SQLite (`dsa_optimizer.db`) for offline development.

---

## 🛡️ Oral Defense
Every team member must defend **1 data structure** and **1 algorithm** during the oral presentation. See [TEAM_ROSTER_AND_DEFENSE.md](docs/TEAM_ROSTER_AND_DEFENSE.md) for exact pairings.