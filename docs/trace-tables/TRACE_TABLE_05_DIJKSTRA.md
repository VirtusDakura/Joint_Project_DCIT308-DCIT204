# Trace Table 05: Dijkstra Shortest Route Path Calculation
> **Algorithm**: Dijkstra Single-Source Shortest Path (`DijkstraAlgorithm.java`)  
> **Module**: M7 (Graph Route Engine)  
> **Scenario**: Dispatching courier from **Legon Hall (Hub)** across local Greater Accra destinations.

---

## 🗺️ Graph Topology & Weighted Road Edges
- Vertices: $V = \{\text{Legon (L)}, \text{NightMarket (N)}, \text{Madina (M)}, \text{EastLegon (E)}, \text{Osu (O)}\}$
- Edges:
  - $(L, N) = 2.0 \text{ min}$, $(L, M) = 6.0 \text{ min}$, $(L, E) = 8.0 \text{ min}$
  - $(N, M) = 3.0 \text{ min}$, $(M, E) = 2.0 \text{ min}$, $(E, O) = 5.0 \text{ min}$

---

## 📊 Step-by-Step Dijkstra Trace Table

| Step | Settled Vertex ($u$) | Min Dist to $u$ | Neighbors Relaxed ($v$) | Edge Weight $w(u,v)$ | Old $dist[v]$ | New $dist[v] = \min(dist[v], dist[u] + w)$ | Predecessor $prev[v]$ | Settled Set ($S$) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **0 (Init)** | — | — | — | — | — | $dist[L]=0, \text{others}=\infty$ | All $-1$ | $\emptyset$ |
| **1** | **Legon (L)** | $0.0$ | NightMarket (N)<br>Madina (M)<br>EastLegon (E) | $2.0$<br>$6.0$<br>$8.0$ | $\infty$<br>$\infty$<br>$\infty$ | $0 + 2.0 = \mathbf{2.0}$<br>$0 + 6.0 = \mathbf{6.0}$<br>$0 + 8.0 = \mathbf{8.0}$ | $prev[N]=L$<br>$prev[M]=L$<br>$prev[E]=L$ | $\{L\}$ |
| **2** | **NightMarket (N)** | $2.0$ | Madina (M) | $3.0$ | $6.0$ | $\min(6.0, 2.0 + 3.0) = \mathbf{5.0}$ | $prev[M]=N$ *(Updated)* | $\{L, N\}$ |
| **3** | **Madina (M)** | $5.0$ | EastLegon (E) | $2.0$ | $8.0$ | $\min(8.0, 5.0 + 2.0) = \mathbf{7.0}$ | $prev[E]=M$ *(Updated)* | $\{L, N, M\}$ |
| **4** | **EastLegon (E)** | $7.0$ | Osu (O) | $5.0$ | $\infty$ | $7.0 + 5.0 = \mathbf{12.0}$ | $prev[O]=E$ | $\{L, N, M, E\}$ |
| **5** | **Osu (O)** | $12.0$ | — | — | — | No unvisited neighbors | — | $\{L, N, M, E, O\}$ |

---

## 🏁 Final Shortest Paths & Route Reconstruction

| Destination | Shortest Distance / Time | Predecessor Path Reconstruction (Reverse traversal of $prev[]$) | Final Route Itinerary |
| :--- | :--- | :--- | :--- |
| **Legon** | $0.0 \text{ min}$ | $[L]$ | **Legon** |
| **NightMarket** | $2.0 \text{ min}$ | $N \leftarrow L$ | **Legon $\to$ NightMarket** |
| **Madina** | $5.0 \text{ min}$ | $M \leftarrow N \leftarrow L$ | **Legon $\to$ NightMarket $\to$ Madina** |
| **EastLegon** | $7.0 \text{ min}$ | $E \leftarrow M \leftarrow N \leftarrow L$ | **Legon $\to$ NightMarket $\to$ Madina $\to$ EastLegon** |
| **Osu** | $12.0 \text{ min}$ | $O \leftarrow E \leftarrow M \leftarrow N \leftarrow L$ | **Legon $\to$ NightMarket $\to$ Madina $\to$ EastLegon $\to$ Osu** |
