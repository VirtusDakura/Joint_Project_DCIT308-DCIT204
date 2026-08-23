# UNIVERSITY OF GHANA
# DEPARTMENT OF COMPUTER SCIENCE

---

## DCIT 204 / DCIT 308: Data Structures and Algorithms I & II
## Joint DSA Semester Project — Technical Report

---

### **Project Title**: Ghana Smart Food & Parcel Delivery Operations Optimizer
### **Group Leader**: Virtus Dakura (22052950)
### **Semester**: Second Semester, 2025/2026 Academic Year

---

## Table of Contents

1. [Executive Summary](#1-executive-summary)
2. [Introduction and Problem Statement](#2-introduction-and-problem-statement)
3. [System Architecture](#3-system-architecture)
4. [Custom Data Structures](#4-custom-data-structures)
5. [Algorithms and Strategies](#5-algorithms-and-strategies)
6. [Optimization Module: Greedy vs Dynamic Programming](#6-optimization-module-greedy-vs-dynamic-programming)
7. [Correctness Verification](#7-correctness-verification)
   - 7.1 Trace Tables
   - 7.2 Proof Sketches
   - 7.3 Counterexamples
8. [AI-Resistance: Index Number Parameter Derivations](#8-ai-resistance-index-number-parameter-derivations)
9. [Empirical Efficiency Experiments](#9-empirical-efficiency-experiments)
10. [Dataset Description](#10-dataset-description)
11. [Testing and Quality Assurance](#11-testing-and-quality-assurance)
12. [Team Collaboration and Cross-Level Mentorship](#12-team-collaboration-and-cross-level-mentorship)
13. [Oral Defense Preparation](#13-oral-defense-preparation)
14. [Lessons Learned](#14-lessons-learned)
15. [Conclusion](#15-conclusion)
16. [References](#16-references)
17. [Appendices](#17-appendices)

---

## 1. Executive Summary

This report documents the design, implementation, testing, and analysis of the **Ghana Smart Food & Parcel Delivery Operations Optimizer**, a Java console-based system that models real-world delivery logistics in Greater Accra. The project fulfils all requirements of the DCIT 204/308 Joint DSA Semester Project brief, implementing **13 custom data structures** and **12+ algorithms** entirely from scratch without using any built-in Java collection classes (`java.util.ArrayList`, `java.util.HashMap`, `java.util.PriorityQueue`, etc.).

The system manages a Ghana-based delivery network comprising **52 locations**, **120 roads**, **345 service requests**, and **31 delivery resources** (riders and vehicles), loaded from CSV datasets. It supports route optimisation via Dijkstra's shortest path, delivery batching via Greedy and Dynamic Programming heuristics, database persistence via SQLite/PostgreSQL, and a comprehensive empirical benchmarking laboratory.

All code has been verified with **42 automated unit test assertions** across **15 test suites** with **zero failures**.

---

## 2. Introduction and Problem Statement

### 2.1 Background

Food and parcel delivery logistics in urban Ghanaian cities such as Accra face challenges including traffic congestion, inefficient route planning, unbalanced dispatch scheduling, and limited rider capacity. A data-structures-driven operations optimizer can address these by modelling the road network as a weighted graph, applying shortest path algorithms for routing, and using priority-based scheduling to maximise urgency fulfilment within resource constraints.

### 2.2 Project Objectives

1. **Model a Ghana delivery network** with 50+ locations and 100+ roads as a weighted graph.
2. **Implement 13 custom data structures** from scratch in Java with no built-in library dependencies.
3. **Implement all required algorithms**: Linear Search, Binary Search, Selection Sort, Insertion Sort, Merge Sort, QuickSort, BFS, DFS, Dijkstra, Prim, Kruskal, Greedy, and Dynamic Programming.
4. **Demonstrate correctness** through trace tables, proof sketches, and counterexamples.
5. **Conduct empirical efficiency experiments** comparing theoretical Big-O predictions with measured runtimes.
6. **Derive algorithm parameters** from team member university index numbers (AI-resistance measure).

### 2.3 Scope and Constraints

- **Language**: Java (JDK 22), console-based interactive application.
- **No Built-in Collections**: All data containers are hand-coded. Only `java.util.Scanner` (I/O), `java.util.Arrays` (array fill/copy utilities), and `java.util.NoSuchElementException` (exception type) are used.
- **Database**: SQLite (local file `dsa_optimizer.db`) with automatic fallback to in-memory CSV parsing.
- **Dataset**: CSV files for locations, roads, service requests, and resources modelling Greater Accra delivery zones.

---

## 3. System Architecture

### 3.1 Package Structure

```
src/main/java/org/ug/dsa/
├── Main.java                          # Interactive 11-option console application
├── TestRunner.java                    # Standalone CLI test suite runner (42 tests)
├── datastructures/                    # 13 custom data structures
│   ├── CustomDynamicArray.java        # Auto-resizing array list
│   ├── CustomLinkedList.java          # Doubly linked list with custom iterator
│   ├── CustomStack.java              # LIFO stack
│   ├── CustomQueue.java              # FIFO queue
│   ├── CustomCircularQueue.java      # Wrap-around circular queue
│   ├── CustomDeque.java              # Double-ended queue
│   ├── CustomHeap.java               # Min-Heap priority queue
│   ├── CustomBST.java                # Binary search tree
│   ├── CustomRedBlackTree.java       # Self-balancing red-black tree
│   ├── CustomBTree.java              # B-tree (minimum degree t=3)
│   ├── CustomHashTable.java          # Hash table with separate chaining
│   ├── CustomSet.java                # Set abstraction over CustomHashTable
│   ├── CustomMap.java                # Map abstraction over CustomHashTable
│   ├── CustomDisjointSet.java        # Union-Find with path compression
│   ├── CustomGraph.java              # Adjacency List + Matrix graph
│   └── CustomList.java               # Generic list interface
├── algorithms/
│   ├── search/
│   │   ├── LinearSearch.java          # Sequential search with comparison count
│   │   └── BinarySearch.java          # Binary search with precondition check
│   ├── sorting/
│   │   ├── SelectionSort.java         # In-place selection sort
│   │   ├── InsertionSort.java         # Adaptive insertion sort
│   │   ├── MergeSort.java             # Stable divide-and-conquer merge sort
│   │   └── QuickSort.java            # Median-of-three QuickSort
│   ├── graph/
│   │   ├── BFSReachability.java       # BFS zone reachability
│   │   ├── DFSCycleDetection.java     # DFS cycle and component detection
│   │   ├── DijkstraAlgorithm.java     # Single-source shortest path
│   │   └── PrimKruskalMST.java        # Kruskal and Prim MST
│   └── optimization/
│       ├── GreedyBatching.java        # Greedy priority dispatch heuristic
│       └── DynamicProgrammingBatching.java  # 0/1 Knapsack DP solver
├── services/
│   ├── RoutingService.java            # Graph algorithm facade
│   ├── SchedulingService.java         # Priority scheduling service
│   ├── IndexingService.java           # Search and indexing service
│   └── ReportingService.java          # Empirical benchmarks & CSV export
├── database/
│   └── DatabaseManager.java           # SQLite/PostgreSQL JDBC persistence
├── models/
│   ├── Location.java                  # Location record
│   ├── Road.java                      # Road (edge) record
│   ├── ServiceRequest.java            # Delivery request record
│   └── Resource.java                  # Rider/vehicle resource record
└── util/
    └── IndexParameters.java           # AI-resistance index number formulas
```

### 3.2 Console Application Menu

The `Main.java` class provides an interactive 11-option console menu:

| Option | Function | Modules Covered |
|:---:|:---|:---|
| 1 | Load & Display Ghana Delivery Dataset | M1, M2 |
| 2 | Manage Database (CRUD Operations) | M2 |
| 3 | Search Engine (Linear & Binary Search) | M4 |
| 4 | Sorting Demonstrations (Selection, Insertion, Merge, QuickSort) | M4 |
| 5 | Custom Data Structure Showcase | M3 |
| 6 | Graph Routing Engine (Dijkstra, BFS, DFS, MST) | M5, M6 |
| 7 | Priority Scheduling & Dispatch | M5 |
| 8 | Greedy vs Dynamic Programming Optimization | M7, M8 |
| 9 | Empirical Efficiency Benchmark Lab | M10 |
| 10 | Run Self-Check Verification Tests | M9 |
| 11 | Exit | — |

### 3.3 Data Flow

```
CSV Files (data/*.csv)
       │
       ▼
 DatabaseManager.java ──► SQLite / PostgreSQL
       │
       ▼
 CustomDynamicArray<Location/Road/Request/Resource>
       │
       ├──► IndexingService (Search & Sort)
       ├──► RoutingService (Graph Algorithms)
       ├──► SchedulingService (Priority Dispatch)
       ├──► GreedyBatching / DynamicProgrammingBatching (Optimization)
       └──► ReportingService (Empirical Benchmarks & CSV Export)
```

---

## 4. Custom Data Structures

All data structures are implemented entirely from scratch without using any `java.util` collection classes. Each structure is a generic class supporting type-safe operations.

### 4.1 CustomDynamicArray<T>

**Purpose**: Auto-resizing array-backed list for storing locations, roads, requests, and resources.

**Key Operations**:
- `add(T item)`: Appends element; doubles internal array capacity when full.
- `get(int index)` / `set(int index, T item)`: O(1) indexed access.
- `remove(int index)`: Shifts elements left; O(n) worst case.
- `size()`: Returns current element count.

**Complexity**: Access O(1), Insertion (amortised) O(1), Removal O(n).

**Design Decision**: The doubling strategy (`newCapacity = oldCapacity * 2`) ensures amortised O(1) insertions. This is the same strategy used by Java's `ArrayList` internally, but our implementation avoids importing it.

---

### 4.2 CustomLinkedList<T>

**Purpose**: Doubly linked list for order queues where frequent insertions and deletions at arbitrary positions are required.

**Key Operations**:
- `addFirst(T)` / `addLast(T)`: O(1) head/tail insertion.
- `insertAfter(T existing, T newItem)`: Inserts after a specific node.
- `remove(T item)`: Traverses and removes first occurrence.
- Custom `Iterator<T>` for traversal without `java.util.Iterator`.

**Complexity**: Insertion O(1) at ends, Search O(n), Deletion O(n).

---

### 4.3 CustomStack<T>

**Purpose**: LIFO stack for DFS traversal, undo operations, and audit trail logging.

**Key Operations**: `push(T)`, `pop()`, `peek()`, `isEmpty()`, `size()`.

**Implementation**: Array-backed with dynamic resizing.

**Complexity**: All operations O(1) amortised.

---

### 4.4 CustomQueue<T> and CustomCircularQueue<T>

**Purpose**: FIFO queue for BFS traversal and order processing pipelines.

**CustomQueue**: Linked-list-backed with `enqueue(T)` and `dequeue()` in O(1).

**CustomCircularQueue**: Array-backed with wrap-around modular indexing `(front + 1) % capacity`. When the queue is full, it signals overflow rather than silently overwriting.

**Complexity**: Enqueue O(1), Dequeue O(1).

---

### 4.5 CustomDeque<T>

**Purpose**: Double-ended queue supporting insertion and removal at both front and rear.

**Key Operations**: `addFront(T)`, `addRear(T)`, `removeFront()`, `removeRear()`.

**Complexity**: All operations O(1) amortised.

---

### 4.6 CustomHeap<T extends Comparable<T>>

**Purpose**: Min-Heap priority queue for Dijkstra's algorithm and priority-based dispatch scheduling.

**Key Operations**:
- `insert(T item)`: Adds element and restores heap order via `siftUp()`. O(log n).
- `extractMin()`: Removes root (minimum), replaces with last element, restores via `siftDown()`. O(log n).
- `peekMin()`: Returns minimum without removal. O(1).
- `heapify(T[] array)`: Bottom-up heap construction. O(n).

**Implementation Details**: Array-backed. Parent of node at index `i` is `(i-1)/2`. Children are at `2i+1` and `2i+2`. The array dynamically doubles when capacity is exhausted.

**Complexity**: Insert O(log n), Extract-Min O(log n), Peek O(1), Heapify O(n).

---

### 4.7 CustomBST<K extends Comparable<K>, V>

**Purpose**: Binary search tree for ordered indexing of delivery locations and service requests.

**Key Operations**: `insert(K, V)`, `search(K)`, `inorderTraversal()`, `height()`.

**Complexity**: Average O(log n), Worst case O(n) for skewed trees (motivating the use of Red-Black Trees).

---

### 4.8 CustomRedBlackTree<K extends Comparable<K>, V>

**Purpose**: Self-balancing binary search tree guaranteeing O(log n) worst-case operations, used as an alternative high-performance index when BST degrades on sorted input.

**Red-Black Invariants Maintained**:
1. Every node is either RED or BLACK.
2. The root is always BLACK.
3. Red nodes cannot have red children (no two consecutive reds).
4. Every path from root to null contains the same number of black nodes (black-height property).

**Key Operations**: `insert(K, V)` with automatic left/right rotations and recoloring, `search(K)`, `inorderTraversal()`, `blackHeight()`.

**Complexity**: All operations O(log n) guaranteed.

---

### 4.9 CustomBTree<K extends Comparable<K>, V>

**Purpose**: Multi-way search tree simulating database page indexing. Minimum degree `t = 3` (derived from index number — see Section 8), meaning every non-root node holds between 2 and 5 keys and between 3 and 6 children.

**Key Operations**: `insert(K, V)` with `splitChild()` when a node reaches maximum capacity, `search(K)`, `inorderTraversal()`.

**Node Splitting**: When a node reaches `2t - 1 = 5` keys, it splits into two nodes of `t - 1 = 2` keys each, promoting the median key to the parent.

**Complexity**: All operations O(log_t n) where t is the minimum degree.

---

### 4.10 CustomHashTable<K, V>

**Purpose**: Hash-based key-value store for O(1) average-case location and request lookups.

**Collision Resolution**: Separate chaining — each bucket is a singly linked list of `Entry<K, V>` nodes.

**Hash Function**: `(key.hashCode() & 0x7FFFFFFF) % capacity` — bitwise AND with `0x7FFFFFFF` ensures non-negative indices.

**Load Factor & Resizing**: Default load factor threshold is 0.75. When exceeded, the table doubles its capacity and rehashes all entries.

**Key Operations**: `put(K, V)`, `get(K)`, `remove(K)`, `containsKey(K)`, `keys()`, `loadFactor()`, `collisionCount()`.

**Complexity**: Average O(1), Worst O(n) with severe collisions.

---

### 4.11 CustomSet<T> and CustomMap<K, V>

**Purpose**: Set and Map abstractions built on top of `CustomHashTable`.

- **CustomSet**: Uses `CustomHashTable<T, Boolean>` internally. Operations: `add(T)`, `contains(T)`, `remove(T)`, `size()`.
- **CustomMap**: Delegates directly to `CustomHashTable<K, V>`. Operations: `put(K, V)`, `get(K)`, `containsKey(K)`, `keys()`.

**Complexity**: Same as `CustomHashTable` — average O(1).

---

### 4.12 CustomDisjointSet (Union-Find)

**Purpose**: Tracks connected components for Kruskal's MST algorithm — detecting whether adding an edge would create a cycle.

**Key Operations**:
- `makeSet(int x)`: Initialises element as its own representative.
- `find(int x)`: Returns the set representative with **path compression** — every node visited during traversal is directly linked to the root.
- `union(int x, int y)`: Merges sets using **union by rank** — the shorter tree is attached under the taller tree.
- `connected(int x, int y)`: Checks if two elements are in the same set.

**Complexity**: Nearly O(α(n)) per operation where α is the inverse Ackermann function (effectively constant for all practical inputs).

---

### 4.13 CustomGraph

**Purpose**: Weighted graph modelling the Ghana road network, maintaining **both** adjacency list and adjacency matrix representations simultaneously.

**Key Operations**:
- `addVertex(String locationId)`: Registers a location node.
- `addEdge(String from, String to, double weight)` / `addUndirectedEdge(...)`: Adds weighted connections to both representations.
- `getNeighbors(String locationId)`: Returns adjacency list neighbours (for Dijkstra, BFS, DFS).
- `getAdjacencyMatrixCopy()`: Returns matrix copy (for matrix-based algorithms).
- `getAdjacencyListAndMatrixSideBySide()`: Formatted dual-view display for demonstration.

**Design Decision**: Maintaining both representations allows direct demonstration of the trade-offs between adjacency list (space-efficient for sparse graphs, O(V + E)) and adjacency matrix (fast edge lookup, O(V²) space).

**Complexity**: Adjacency List — Space O(V + E), Edge Lookup O(degree). Adjacency Matrix — Space O(V²), Edge Lookup O(1).

---

## 5. Algorithms and Strategies

### 5.1 Searching Algorithms

#### 5.1.1 Linear Search (`LinearSearch.java`)

Sequential scan through an array or `CustomDynamicArray`, comparing each element against the target. Returns the index of the first match or -1 if not found.

**Metrics Tracked**: Total comparison count per search operation.

**Complexity**: Best O(1), Average O(n/2), Worst O(n).

**Application**: Used when data is unsorted or the dataset is small enough that the overhead of sorting is not justified.

#### 5.1.2 Binary Search (`BinarySearch.java`)

Iterative binary search with **mandatory precondition verification**. Before executing the binary partition loop, the method checks `isSorted()` to confirm the array satisfies the monotonic ordering invariant. If the precondition fails, the search halts safely and reports `preconditionMet = false`.

**Precondition**: ∀i, j such that 0 ≤ i < j < n ⟹ A[i] ≤ A[j].

**Implementation**: Uses `mid = low + (high - low) / 2` to avoid integer overflow.

**Complexity**: O(log n).

**Application**: Used on sorted service request urgency arrays and sorted location indices for fast lookup.

---

### 5.2 Sorting Algorithms

#### 5.2.1 Selection Sort (`SelectionSort.java`)

In-place comparison sort that divides the array into sorted and unsorted regions. On each pass, finds the minimum element in the unsorted region and swaps it into the sorted region.

**Metrics Tracked**: Comparison count and swap count.

**Stability**: Not stable (swapping can change relative order of equal elements).

**Complexity**: O(n²) for all cases (best, average, worst).

#### 5.2.2 Insertion Sort (`InsertionSort.java`)

Adaptive in-place sort that builds the sorted portion one element at a time. For each new element, shifts larger elements rightward until the correct insertion position is found.

**Stability**: Stable — equal elements maintain their relative order.

**Adaptiveness**: On nearly-sorted input, runs in O(n) time (only a few shifts per element).

**Complexity**: Best O(n), Average O(n²), Worst O(n²).

#### 5.2.3 Merge Sort (`MergeSort.java`)

Stable divide-and-conquer sort. Recursively halves the array, sorts each half, then merges the two sorted halves into a single sorted array.

**Stability**: Stable — when two elements compare as equal during merge, the left-half element is copied first.

**Complexity**: O(n log n) for all cases. Space: O(n) auxiliary.

**Recurrence**: T(n) = 2T(n/2) + O(n) → O(n log n) by the Master Theorem.

#### 5.2.4 QuickSort (`QuickSort.java`)

In-place partitioning sort using **median-of-three pivot selection** to avoid worst-case O(n²) behaviour on sorted or nearly-sorted input. Uses Lomuto partitioning.

**Median-of-Three**: Selects the median of `A[low]`, `A[mid]`, `A[high]` as the pivot, reducing the probability of unbalanced partitions.

**Metrics Tracked**: Comparison count, swap count, and maximum recursion depth.

**Complexity**: Best/Average O(n log n), Worst O(n²) (rare with median-of-three). Space: O(log n) stack frames.

---

### 5.3 Graph Traversal Algorithms

#### 5.3.1 Breadth-First Search — BFS (`BFSReachability.java`)

Explores all locations reachable from a source vertex level by level, using a `CustomQueue` as the frontier.

**Output**: Set of reachable vertices and their hop distances (number of edges from source).

**Application**: Determining which delivery zones can be reached from a dispatch hub, and computing the fewest-hops path.

**Complexity**: O(V + E).

#### 5.3.2 Depth-First Search — DFS (`DFSCycleDetection.java`)

Explores the road network depth-first using a `CustomStack`, tracking vertex states (WHITE = unvisited, GREY = in current path, BLACK = fully explored).

**Cycle Detection**: A back edge to a GREY vertex indicates a cycle in the directed graph.

**Connected Components**: Counts the number of disconnected subgraphs.

**Application**: Detecting circular delivery routes and verifying network connectivity.

**Complexity**: O(V + E).

---

### 5.4 Shortest Path Algorithm

#### 5.4.1 Dijkstra's Algorithm (`DijkstraAlgorithm.java`)

Computes single-source shortest paths on non-negatively weighted graphs using a `CustomHeap` (Min-Heap) as the priority queue.

**Algorithm Steps**:
1. Initialise all distances to ∞ except source = 0.
2. Insert source into the min-heap.
3. Extract the vertex `u` with minimum distance.
4. For each neighbour `v` of `u`, if `dist[u] + weight(u,v) < dist[v]`, update `dist[v]` and insert `v` into the heap.
5. Repeat until the heap is empty.

**Path Reconstruction**: Maintains a `predecessors[]` array. The path from source to any destination is recovered by backtracking through predecessors.

**Output**: `ShortestPathResult` record containing distance table, predecessor array, path builder, and a `renderDistanceTable()` method for formatted display.

**Complexity**: O((V + E) log V) with a binary heap.

---

### 5.5 Minimum Spanning Tree Algorithms

#### 5.5.1 Kruskal's Algorithm (`PrimKruskalMST.java`)

Builds the MST by sorting all edges by weight using QuickSort, then greedily adding edges that do not form a cycle (verified via `CustomDisjointSet`).

**Algorithm Steps**:
1. Sort all edges by weight using `QuickSort`.
2. For each edge (u, v, w) in sorted order:
   - If `find(u) ≠ find(v)` (different components), add edge to MST and `union(u, v)`.
   - Otherwise, skip (would create a cycle).
3. Stop when MST has V-1 edges.

**Complexity**: O(E log E) dominated by sorting. Union-Find operations are nearly O(α(n)).

#### 5.5.2 Prim's Algorithm (`PrimKruskalMST.java`)

Builds the MST by growing a single tree from an arbitrary starting vertex, always adding the cheapest edge connecting the tree to a non-tree vertex.

**Implementation**: Uses `CustomHeap` (Min-Heap) to efficiently select the minimum-weight crossing edge.

**Complexity**: O((V + E) log V) with a binary heap.

---

## 6. Optimization Module: Greedy vs Dynamic Programming

### 6.1 Greedy Priority-Based Dispatch (`GreedyBatching.java`)

**Strategy**: Sort pending delivery requests by urgency density (urgency / capacity cost) in descending order using custom `MergeSort`. For each destination group, greedily assign the first available rider whose capacity can accommodate the batch.

**Greedy Choice Property**: At each step, select the order with the highest marginal urgency density. This is locally optimal but not guaranteed globally optimal under capacity constraints.

**Steps**:
1. Filter requests with status = "NEW".
2. Sort by urgency descending using `MergeSort` (no `Collections.sort`).
3. Group by destination using `CustomMap` (no `LinkedHashMap`).
4. For each group, assign the first available rider with sufficient capacity.

### 6.2 0/1 Knapsack Dynamic Programming (`DynamicProgrammingBatching.java`)

**Strategy**: Given a set of delivery requests each with a weight (capacity cost) and a value (urgency), and a rider with limited capacity W, find the subset of requests that maximises total urgency without exceeding capacity.

**Recurrence Relation**:
```
dp[i][w] = max(dp[i-1][w], v_i + dp[i-1][w - w_i])    if w_i ≤ w
dp[i][w] = dp[i-1][w]                                   otherwise
```

**Backtracking**: After filling the DP table, the algorithm traces back from `dp[n][W]` to identify which requests were selected.

**Table Rendering**: The `renderTable()` method outputs the full dp[][] grid for direct use as a trace table.

**Complexity**: O(n × W) time and space, where n is the number of requests and W is the rider capacity.

### 6.3 Greedy Failure Counterexample

The Greedy heuristic can be demonstrably sub-optimal. Consider:

| Order | Weight | Urgency | Density |
|:---:|:---:|:---:|:---:|
| REQ-A | 30 | 60 | 2.00 |
| REQ-B | 25 | 45 | 1.80 |
| REQ-C | 25 | 45 | 1.80 |

With rider capacity W = 50:
- **Greedy** selects REQ-A (density 2.00) first → remaining capacity 20 → neither REQ-B nor REQ-C fits → **Total urgency: 60**.
- **DP** considers all subsets → selects {REQ-B, REQ-C} → weight 50/50 → **Total urgency: 90 (+50% improvement)**.

This counterexample is formally documented in `docs/COUNTEREXAMPLES.md` and executable via Menu Option 8.

---

## 7. Correctness Verification

### 7.1 Trace Tables (7 Tables — Brief Requires ≥ 6)

Each trace table tracks variable states, comparisons, and decisions across every iteration of the algorithm on concrete input data.

| Trace # | Algorithm | Input | Location |
|:---:|:---|:---|:---|
| 1 | Binary Search | Sorted urgency array [1,2,2,3,4,4,5,5,5], Target=4 | `docs/trace-tables/TRACE_TABLE_01_BINARY_SEARCH.md` |
| 2 | Selection Sort | Urgency array [3,1,4,1,5,9,2] | `docs/trace-tables/TRACE_TABLE_02_SELECTION_SORT.md` |
| 3 | Insertion Sort | Urgency array [5,3,1,4,2] | `docs/trace-tables/TRACE_TABLE_03_INSERTION_SORT.md` |
| 4 | Merge Sort | Array [38,27,43,3,9,82,10] | `docs/trace-tables/TRACE_TABLE_04_MERGE_SORT.md` |
| 5 | Dijkstra's Algorithm | 5-node delivery zone subgraph | `docs/trace-tables/TRACE_TABLE_05_DIJKSTRA.md` |
| 6 | 0/1 Knapsack DP | 4 delivery orders, capacity W=7 | `docs/trace-tables/TRACE_TABLE_06_DP_KNAPSACK.md` |
| 7 | Kruskal's MST | 5-node, 7-edge delivery network | `docs/trace-tables/TRACE_TABLE_07_KRUSKAL_MST.md` |

### 7.2 Proof Sketches (3 Proofs — Brief Requires ≥ 3)

| Proof # | Statement | Technique | Location |
|:---:|:---|:---|:---|
| 1 | Merge Sort's merge step correctly produces a sorted output | Loop Invariant (Init/Maintenance/Termination) | `docs/proof-sketches/PROOF_SKETCH_01_MERGE_LOOP_INVARIANT.md` |
| 2 | QuickSort's expected time complexity is O(n log n) | Recurrence Relation via Master Theorem | `docs/proof-sketches/PROOF_SKETCH_02_QUICKSORT_RECURRENCE.md` |
| 3 | Greedy can fail on 0/1 Knapsack where DP succeeds | Optimal Substructure + Counterexample | `docs/proof-sketches/PROOF_SKETCH_03_GREEDY_VS_DP.md` |

### 7.3 Counterexamples (2 — Brief Requires ≥ 2)

| # | Counterexample | Demonstrates |
|:---:|:---|:---|
| 1 | Binary Search on unsorted array [50,10,40,20,80,30] for target 10 | Precondition violation — algorithm returns NOT FOUND for an element that exists at index 1 |
| 2 | Greedy density dispatch vs. 0/1 Knapsack DP on 3-order batching | Greedy achieves urgency 60 vs DP's optimal 90 (+50% gap) |

Full derivations with step-by-step execution traces are in `docs/COUNTEREXAMPLES.md`.

---

## 8. AI-Resistance: Index Number Parameter Derivations

As required by Section 2(iii) of the brief, the following algorithm parameters are derived from team member university index numbers, making the codebase unique to our team. The formulas are implemented in `IndexParameters.java`.

### Parameter 1: Hash Table Base Size
- **Source Index**: Virtus Dakura — 22052950
- **Formula**: `16 + (sumOfDigits(22052950) % 17)`
- **Calculation**: Sum of digits = 2+2+0+5+2+9+5+0 = 25. 25 % 17 = 8. Result: **24**.
- **Usage**: Initial bucket count for `CustomHashTable`.

### Parameter 2: Priority Urgency Multiplier
- **Source Index**: Alfred Forson — 22380272
- **Formula**: `1.0 + (22380272 % 7) * 0.15`
- **Calculation**: 22380272 % 7 = 3. 1.0 + 3×0.15 = **1.45**.
- **Usage**: Multiplied with base urgency scores in scheduling and dispatch.

### Parameter 3: B-Tree Minimum Degree (t)
- **Source Index**: Jessica Puozaa — 22120404
- **Formula**: `3 + (22120404 % 3)`
- **Calculation**: 22120404 % 3 = 0. Result: **t = 3**.
- **Usage**: `CustomBTree` node capacity: min keys = t-1 = 2, max keys = 2t-1 = 5.

### Parameter 4: Traffic Penalty Factor
- **Source Index**: Timothy Abotsikpuia — 22397906
- **Formula**: `1.0 + (22397906 % 5) * 0.2`
- **Calculation**: 22397906 % 5 = 1. 1.0 + 1×0.2 = **1.2**.
- **Usage**: Applied as a multiplicative penalty to road weights during peak traffic hours.

---

## 9. Empirical Efficiency Experiments

The `ReportingService.java` implements **6 empirical benchmark experiments** (Module M10) that measure actual execution time against theoretical Big-O predictions.

### Experiment 1: Search Benchmark — Linear vs Binary Search
- **Methodology**: Search for a known target in arrays of increasing size (100, 500, 1000, 5000, 10000 elements).
- **Expected**: Linear Search scales linearly O(n); Binary Search remains O(log n).
- **Measurement**: `System.nanoTime()` before and after each search operation.

### Experiment 2: Sorting Benchmark — Selection vs Insertion vs Merge vs QuickSort
- **Methodology**: Sort random integer arrays of increasing size.
- **Expected**: Selection/Insertion sort show O(n²) growth; Merge/QuickSort show O(n log n).

### Experiment 3: Hash Table Load Factor vs Collisions
- **Methodology**: Insert increasing numbers of keys and measure collision count at each load factor milestone.
- **Expected**: Collisions increase as load factor approaches and exceeds 0.75.

### Experiment 4: Tree Benchmark — BST vs Red-Black Tree
- **Methodology**: Insert n sorted keys into both a BST and a Red-Black Tree, then measure tree height and search time.
- **Expected**: BST degrades to O(n) height on sorted input; Red-Black Tree maintains O(log n).

### Experiment 5: Heap Priority Dispatch
- **Methodology**: Insert n elements into `CustomHeap`, then extract all in priority order, measuring total time.
- **Expected**: O(n log n) total for n insertions followed by n extractions.

### Experiment 6: Graph Algorithm Benchmark — BFS, DFS, Dijkstra, Kruskal, Prim
- **Methodology**: Run each algorithm on the full 52-node, 120-edge Ghana road network and measure execution time.
- **Expected**: BFS/DFS in O(V+E); Dijkstra in O((V+E) log V); Kruskal in O(E log E).

All results can be exported to CSV via `data/benchmarks_export.csv` for external plotting and analysis.

---

## 10. Dataset Description

The system operates on a realistic Ghana-based delivery dataset stored as CSV files:

| Dataset | File | Records | Brief Requirement |
|:---|:---|:---:|:---:|
| **Locations** | `data/locations.csv` | 52 | ≥ 50 |
| **Roads** | `data/roads.csv` | 120 | ≥ 100 |
| **Service Requests** | `data/service_requests.csv` | 345 | ≥ 300 |
| **Resources (Riders)** | `data/resources.csv` | 31 | ≥ 30 |

### Location Coverage

The dataset models delivery zones across Greater Accra and surrounding areas, including:
- **University of Ghana / Legon Campus**: Main campus zones, lecture areas, halls of residence.
- **Commercial Hubs**: Accra Mall, West Hills Mall, Osu Oxford Street, Makola Market, Kaneshie Market.
- **Residential Areas**: East Legon, Madina, Adenta, Tema, Teshie, Dansoman, Achimota.
- **Transport Nodes**: Kotoka International Airport, Tema Harbour, Circle Interchange, Kwame Nkrumah Interchange.

### Road Network

Each road connects two locations with a weight representing travel time in minutes. Roads model real Accra traffic conditions including main highways (N1, George Walker Bush Highway) and local routes.

---

## 11. Testing and Quality Assurance

### 11.1 Automated Test Suite

The `TestRunner.java` class executes **42 test assertions** across **15 test suites** covering all custom data structures and algorithms:

| Suite # | Test Suite | Assertions | Status |
|:---:|:---|:---:|:---:|
| 1 | CustomDynamicArray Tests | 3 | ✅ PASSED |
| 2 | CustomLinkedList Tests | 3 | ✅ PASSED |
| 3 | CustomStack Tests | 3 | ✅ PASSED |
| 4 | CustomQueue & CircularQueue Tests | 3 | ✅ PASSED |
| 5 | CustomDeque Tests | 3 | ✅ PASSED |
| 6 | CustomHeap (Priority Queue) Tests | 3 | ✅ PASSED |
| 7 | CustomBST & CustomRedBlackTree Tests | 3 | ✅ PASSED |
| 8 | CustomBTree Tests | 2 | ✅ PASSED |
| 9 | CustomHashTable & Set/Map Tests | 3 | ✅ PASSED |
| 10 | CustomDisjointSet (Union-Find) Tests | 3 | ✅ PASSED |
| 11 | CustomGraph (List & Matrix) Tests | 3 | ✅ PASSED |
| 12 | LinearSearch & BinarySearch Tests | 3 | ✅ PASSED |
| 13 | Sorting Suite (Selection, Insertion, Merge, QuickSort) | 3 | ✅ PASSED |
| 14 | Graph Suite (BFS, DFS, Dijkstra, Kruskal, Prim) | 2 | ✅ PASSED |
| 15 | Optimization Suite (Greedy & 0/1 Knapsack DP) | 2 | ✅ PASSED |
| | **TOTAL** | **42** | **0 FAILURES** |

### 11.2 JUnit Test Classes

In addition to the standalone `TestRunner`, JUnit 5 test classes are provided in `src/test/java/` for IDE-based testing:

- `LinearSearchTest.java`
- `BinarySearchTest.java`
- `SelectionSortTest.java`
- `QuickSortTest.java`
- `BFSReachabilityTest.java`
- `DFSCycleDetectionTest.java`
- `DijkstraAlgorithmTest.java`
- `PrimKruskalMSTTest.java`

### 11.3 Build and Test Command

```powershell
powershell -ExecutionPolicy Bypass -File .\build_and_test.ps1
```

This script compiles all Java source files, runs the `TestRunner`, and reports results.

---

## 12. Team Collaboration and Cross-Level Mentorship

### 12.1 Collaboration Philosophy

Our team was organised into **5 Cross-Level Collaborative Mentorship Pods**, where each DCIT 308 (Level 300) student served as a lead mentor paired with 1–2 DCIT 204 (Level 200) students. Rather than segregating work by course level (structures vs algorithms), each pod worked across the full stack: co-developing custom data structures, implementing algorithms that use those structures, writing tests, and producing documentation.

### 12.2 Pod Structure

| Pod | Mentor (308) | Mentee(s) (204) | Focus Area |
|:---:|:---|:---|:---|
| **Pod 1** | Seglah Emmanuel (22144981) | Koufie Shawn (22304100), Boame Peter (22363563) | Linear Structures & Sorting |
| **Pod 2** | Collins Egyir (22233318) | Halim Haruna (22119064) | Hashing & Search Indexing |
| **Pod 3** | Jessica Puozaa (22120404) | Alfred Forson (22380272) | Tree Indexing & DP |
| **Pod 4** | Rushdan Antiku (22102540) | Timothy Abotsikpuia (22397906), Jonathan Agbo (22034889) | Priority Dispatch & Database |
| **Pod 5** | Virtus Dakura (22052950) | Emmanuel Asiedu (22319587), Richlove Serwaah (22308313) | Graph Routing & Optimization |

### 12.3 Individual Defense Assignments

Each team member defends **1 Custom Data Structure** and **1 Algorithm** during the oral defense:

| Member | Custom Data Structure | Algorithm |
|:---|:---|:---|
| Seglah Emmanuel | CustomLinkedList | Insertion Sort |
| Koufie Shawn | CustomDynamicArray | Merge Sort |
| Boame Peter | CustomQueue | QuickSort |
| Collins Egyir | CustomHashTable | Binary Search |
| Halim Haruna | CustomSet & CustomMap | Linear Search |
| Jessica Puozaa | CustomBST & CustomRedBlackTree | Selection Sort |
| Alfred Forson | CustomBTree | 0/1 Knapsack DP |
| Rushdan Antiku | CustomHeap | Heap Priority Dispatch |
| Timothy Abotsikpuia | CustomCircularQueue | Database CRUD & Index Formulas |
| Jonathan Agbo | CustomStack | BFS Reachability |
| Virtus Dakura | CustomGraph | Dijkstra's Algorithm |
| Emmanuel Asiedu | CustomDisjointSet | Kruskal's & Prim's MST |
| Richlove Serwaah | CustomDeque | Greedy Priority Dispatch |

---

## 13. Oral Defense Preparation

### Key Questions and Prepared Responses

**Q1: "How did Level 200s and Level 300s collaborate?"**

> Our team operated in 5 Cross-Level Mentorship Pods. Each Level 300 senior mentored 1–2 Level 200 juniors across full-stack feature modules. For example, in Pod 5, Virtus Dakura (300) mentored Emmanuel Asiedu and Richlove Serwaah (200) across CustomGraph, Dijkstra's algorithm, Kruskal/Prim MST, and Greedy dispatch optimization. Every member defends 1 data structure and 1 algorithm.

**Q2: "What is Richlove's Greedy algorithm?"**

> Richlove implemented the Greedy Priority-Based Request Scheduling & Dispatch Heuristic (Earliest Deadline First / Maximum Urgency Density). This is the canonical textbook greedy strategy from Cormen Chapter 16. She also documented Counterexample #2, demonstrating that on multi-capacity batching, greedy achieves urgency 60 while Dynamic Programming achieves the global optimum of 90 — a 50% improvement.

**Q3: "Why not use Java's built-in collections?"**

> The brief explicitly requires all data containers to be implemented from scratch. We coded 13 custom structures with full generic type support, proper error handling, and dynamic resizing. The only java.util imports are Scanner (console I/O), Arrays (array fill utility), and NoSuchElementException (exception type).

**Q4: "How did you verify correctness?"**

> Three layers: (1) 42 automated test assertions across 15 suites with zero failures, (2) 7 hand-traced trace tables showing variable states at each iteration, and (3) 3 formal proof sketches using loop invariants and recurrence relations. Plus 2 counterexamples demonstrating algorithm failure under violated preconditions.

---

## 14. Lessons Learned

1. **Custom Implementations Deepen Understanding**: Building `CustomHeap` from scratch — implementing `siftUp`, `siftDown`, and bottom-up `heapify` — gave us far deeper understanding of priority queue mechanics than simply calling `PriorityQueue.add()`.

2. **Dual Graph Representations Reveal Trade-offs**: Maintaining both adjacency list and adjacency matrix simultaneously allowed us to directly compare space usage and access patterns, making the theoretical trade-offs tangible.

3. **Greedy vs DP Is Not Theoretical**: The counterexample with 3 delivery orders (Greedy=60 vs DP=90) demonstrated in running code that greedy's local optimality can miss the global optimum by 50%. This lesson is directly applicable to real logistics dispatch.

4. **Red-Black Trees Are Hard But Worthwhile**: Implementing insertion with rotations and recoloring was the most complex single coding task. However, seeing the BST degrade to O(n) height on sorted input while the Red-Black Tree maintained O(log n) made the effort worthwhile.

5. **Cross-Level Mentorship Works**: Having Level 300 students explain tree rotations or Dijkstra's relaxation step to Level 200 students forced deeper understanding on both sides. Teaching is learning.

---

## 15. Conclusion

The Ghana Smart Food & Parcel Delivery Operations Optimizer successfully demonstrates the practical application of 13 custom data structures and 12+ algorithms to a real-world logistics problem. Every component was implemented from scratch in Java without relying on built-in collection libraries, and verified through automated testing, formal proofs, and empirical benchmarks.

The system meets or exceeds every quantitative threshold specified in the DCIT 204/308 Joint DSA Project Brief:

| Metric | Required | Delivered |
|:---|:---:|:---:|
| Locations | ≥ 50 | **52** |
| Roads | ≥ 100 | **120** |
| Service Requests | ≥ 300 | **345** |
| Resources | ≥ 30 | **31** |
| Custom Data Structures | 13 categories | **13 classes** |
| Algorithms | 12+ | **12+ implemented** |
| Unit Tests | ≥ 40 | **42** |
| Trace Tables | ≥ 6 | **7** |
| Proof Sketches | ≥ 3 | **3** |
| Counterexamples | ≥ 2 | **2** |
| Index Number Parameters | ≥ 3 | **4** |

The project demonstrates both theoretical understanding and practical engineering competence in Data Structures and Algorithms.

---

## 16. References

1. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2022). *Introduction to Algorithms* (4th ed.). MIT Press.
2. Sedgewick, R., & Wayne, K. (2011). *Algorithms* (4th ed.). Addison-Wesley.
3. Kleinberg, J., & Tardos, É. (2006). *Algorithm Design*. Pearson.
4. Goodrich, M. T., & Tamassia, R. (2014). *Data Structures and Algorithms in Java* (6th ed.). Wiley.
5. DCIT 204/308 Joint DSA Semester Project Brief, Department of Computer Science, University of Ghana, 2025/2026.

---

## 17. Appendices

### Appendix A: How to Build and Run

```powershell
# Compile and test
powershell -ExecutionPolicy Bypass -File .\build_and_test.ps1

# Run interactive console
java -cp bin org.ug.dsa.Main

# Run standalone test suite
java -cp bin org.ug.dsa.TestRunner
```

### Appendix B: File Inventory

| Category | Count | Location |
|:---|:---:|:---|
| Custom Data Structures | 16 files | `src/main/java/org/ug/dsa/datastructures/` |
| Algorithm Classes | 10 files | `src/main/java/org/ug/dsa/algorithms/` |
| Service Classes | 4 files | `src/main/java/org/ug/dsa/services/` |
| Model Classes | 4 files | `src/main/java/org/ug/dsa/models/` |
| Database | 1 file | `src/main/java/org/ug/dsa/database/` |
| Utility | 1 file | `src/main/java/org/ug/dsa/util/` |
| JUnit Tests | 8 files | `src/test/java/org/ug/dsa/algorithms/` |
| Console App | 1 file | `src/main/java/org/ug/dsa/Main.java` |
| Test Runner | 1 file | `src/main/java/org/ug/dsa/TestRunner.java` |
| CSV Datasets | 4 files | `data/` |
| Trace Tables | 7 files | `docs/trace-tables/` |
| Proof Sketches | 3 files | `docs/proof-sketches/` |
| Counterexamples | 1 file | `docs/COUNTEREXAMPLES.md` |

### Appendix C: Complexity Summary Table

| Algorithm | Best Case | Average Case | Worst Case | Space |
|:---|:---:|:---:|:---:|:---:|
| Linear Search | O(1) | O(n) | O(n) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| QuickSort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| BFS | O(V+E) | O(V+E) | O(V+E) | O(V) |
| DFS | O(V+E) | O(V+E) | O(V+E) | O(V) |
| Dijkstra (Heap) | O((V+E) log V) | O((V+E) log V) | O((V+E) log V) | O(V) |
| Kruskal's MST | O(E log E) | O(E log E) | O(E log E) | O(V+E) |
| Prim's MST (Heap) | O((V+E) log V) | O((V+E) log V) | O((V+E) log V) | O(V) |
| Greedy Dispatch | O(n log n) | O(n log n) | O(n log n) | O(n) |
| 0/1 Knapsack DP | O(nW) | O(nW) | O(nW) | O(nW) |

### Appendix D: Data Structure Complexity Summary

| Data Structure | Insert | Search | Delete | Access | Space |
|:---|:---:|:---:|:---:|:---:|:---:|
| CustomDynamicArray | O(1)* | O(n) | O(n) | O(1) | O(n) |
| CustomLinkedList | O(1) | O(n) | O(n) | O(n) | O(n) |
| CustomStack | O(1)* | — | O(1)* | O(1) | O(n) |
| CustomQueue | O(1) | — | O(1) | O(1) | O(n) |
| CustomCircularQueue | O(1) | — | O(1) | O(1) | O(n) |
| CustomDeque | O(1)* | — | O(1)* | O(1) | O(n) |
| CustomHeap | O(log n) | — | O(log n) | O(1)† | O(n) |
| CustomBST | O(log n)‡ | O(log n)‡ | O(log n)‡ | — | O(n) |
| CustomRedBlackTree | O(log n) | O(log n) | O(log n) | — | O(n) |
| CustomBTree (t=3) | O(log n) | O(log n) | O(log n) | — | O(n) |
| CustomHashTable | O(1)§ | O(1)§ | O(1)§ | — | O(n) |
| CustomDisjointSet | O(α(n)) | O(α(n)) | — | — | O(n) |
| CustomGraph (List) | O(1) | O(deg) | O(deg) | — | O(V+E) |
| CustomGraph (Matrix) | O(1) | O(1) | O(1) | O(1) | O(V²) |

\* Amortised. † Peek-min only. ‡ Average case; worst O(n) for skewed trees. § Average case; worst O(n) with collisions.

---

*Report prepared by the Ghana Smart Delivery Optimizer Team, Department of Computer Science, University of Ghana.*
