# Ghana Smart Service Operations Optimizer
> **University of Ghana - Department of Computer Science**  
> **Course**: DCIT 204 / DCIT 308: Data Structures & Algorithms I & II  
> **Language**: Java 17 | **Build Tool**: Maven | **Database**: PostgreSQL

---

## 📌 Project Overview
The **Ghana Smart Service Operations Optimizer** is a practical semester project integrating algorithm design, custom data structures, empirical runtime analysis, graph routing, and database persistence.

The system loads Ghanaian operational dataset records (Locations, Roads, Service Requests, Resources) from a database, loads them into custom-built data structures, executes optimized scheduling and routing algorithms, and records empirical performance metrics ($N$ vs $Time(ns)$ / $Memory(KB)$).

---

## 📁 Repository Directory Structure

```text
Joint_Project_DCIT308-DCIT204/
├── pom.xml                   # Maven project configuration (Java 17, PostgreSQL, JUnit 5)
├── docker-compose.yml        # PostgreSQL container setup
├── README.md
├── data/                     # Seed CSV datasets (locations, roads, requests, resources)
├── docs/                     # Project Brief & Kickoff Meeting Agenda
│   ├── MEETING_AGENDA.md
│   └── PROJECT_BRIEF_SUMMARY.md
└── src/
    ├── main/java/org/ug/dsa/
    │   ├── Main.java         # Application Entry Point
    │   ├── models/           # Location, Road, ServiceRequest, Resource
    │   ├── database/         # DatabaseManager & JDBC Persistence (M2)
    │   ├── datastructures/   # Custom LinkedList, Stack, Queue, Heap, BST, HashTable, Graph (M3)
    │   ├── algorithms/       # Search, Sort, Graph, Optimization Engines (M4, M7, M8)
    │   └── services/         # Scheduling, Routing, Reporting & CSV Exporter (M5, M6, M9)
    └── test/java/org/ug/dsa/ # JUnit 5 automated unit test suite
```

---

## 🛠️ Requirements & Quickstart

### Prerequisites
- **JDK 17** or higher installed
- **Apache Maven 3.8+** installed

### Build & Run Instructions
```bash
# 1. Clone repository
git clone <repo-url>
cd Joint_Project_DCIT308-DCIT204

# 2. Compile source code
mvn clean compile

# 3. Run automated JUnit tests
mvn test

# 4. Execute main application
mvn exec:java
```

### Database Setup
- **PostgreSQL**: Start PostgreSQL using Docker:
  ```bash
  docker compose up -d
  ```

---

## 🌿 Git & Team Workflow Guidelines
1. **Never commit directly to `main`**.
2. Create feature branches using the naming convention: `feature/M<module_number>-<feature_name>` (e.g., `feature/M3-custom-heap`).
3. Submit a **Pull Request (PR)** for review before merging into `main`.
4. Ensure all JUnit unit tests pass (`mvn test`) before submitting a PR.