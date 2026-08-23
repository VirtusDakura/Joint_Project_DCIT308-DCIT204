# Trace Table 07: Kruskal Minimum Spanning Tree (MST)
> **Algorithm**: Kruskal's MST Algorithm (`PrimKruskalMST.java`)  
> **Module**: M7 (Graph Route Engine)  
> **Helper Structure**: `CustomDisjointSet.java` (Union-Find with Path Compression & Union by Rank)  
> **Goal**: Connect 5 Greater Accra delivery hubs with minimal road infrastructure cost.

---

## 🗺️ Road Network Edges (Sorted in Ascending Order by Weight)
Vertices: $V = \{\text{Legon(0)}, \text{NightMarket(1)}, \text{Madina(2)}, \text{EastLegon(3)}, \text{Osu(4)}\}$ ($|V| = 5 \implies \text{Target MST Edges} = |V|-1 = 4$).

Sorted Edge List:
1. $(0, 1)$ with weight $w = 2.0$
2. $(2, 3)$ with weight $w = 2.0$
3. $(1, 2)$ with weight $w = 3.0$
4. $(0, 2)$ with weight $w = 6.0$
5. $(3, 4)$ with weight $w = 5.0$
6. $(0, 3)$ with weight $w = 8.0$

---

## 📊 Step-by-Step Kruskal Trace Table

| Step | Candidate Edge ($u, v$) | Weight ($w$) | `find(u)` | `find(v)` | Cycle Condition (`find(u) == find(v)`) | Decision | Disjoint Set Partitions | Total MST Cost |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **0** | — | — | — | — | — | Initial Sets | $\{0\}, \{1\}, \{2\}, \{3\}, \{4\}$ | $0.0$ |
| **1** | $(0, 1)$ | $2.0$ | $0$ | $1$ | $0 \ne 1 \implies \text{NO CYCLE}$ | **ACCEPT** (Edge 1/4) | $\{0, 1\}, \{2\}, \{3\}, \{4\}$ | $2.0$ |
| **2** | $(2, 3)$ | $2.0$ | $2$ | $3$ | $2 \ne 3 \implies \text{NO CYCLE}$ | **ACCEPT** (Edge 2/4) | $\{0, 1\}, \{2, 3\}, \{4\}$ | $4.0$ |
| **3** | $(1, 2)$ | $3.0$ | $0$ | $2$ | $0 \ne 2 \implies \text{NO CYCLE}$ | **ACCEPT** (Edge 3/4) | $\{0, 1, 2, 3\}, \{4\}$ | $7.0$ |
| **4** | $(0, 2)$ | $6.0$ | $0$ | $0$ | $0 == 0 \implies \mathbf{CYCLE!}$ | **REJECT (Skip edge)** | $\{0, 1, 2, 3\}, \{4\}$ | $7.0$ |
| **5** | $(3, 4)$ | $5.0$ | $0$ | $4$ | $0 \ne 4 \implies \text{NO CYCLE}$ | **ACCEPT** (Edge 4/4) | $\{0, 1, 2, 3, 4\}$ | $\mathbf{12.0}$ |

---

## 🏁 Result Summary
- **Spanning Edges Selected (4 edges)**:
  1. `Legon <--> NightMarket` ($2.0$)
  2. `Madina <--> EastLegon` ($2.0$)
  3. `NightMarket <--> Madina` ($3.0$)
  4. `EastLegon <--> Osu` ($5.0$)
- **Total Spanning Tree Cost**: **12.0 min**
- **Connected Components Remaining**: $1$ (All zones fully bridged).
