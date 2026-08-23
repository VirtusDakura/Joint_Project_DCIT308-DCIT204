# Proof Sketch 03: Dynamic Programming Correctness & Optimal Substructure
> **Department of Computer Science — University of Ghana**  
> **DCIT 204 / DCIT 308 Joint Semester Project**  
> **Section 10 Requirement**: *Proof sketch for Dynamic Programming and optimization correctness.*

---

## 1. Optimal Substructure of the 0/1 Knapsack Problem

### Theorem:
Let $S_k \subseteq \{1, 2, \dots, n\}$ be the optimal subset of delivery service requests that maximizes total urgency value $\sum_{j \in S_k} v_j$ within capacity budget $W$.
For the $n$-th request with weight $w_n$ and urgency value $v_n$:
1. If $n \notin S_k$, then $S_k$ is an optimal solution for the subproblem considering the first $n-1$ requests with capacity $W$.
2. If $n \in S_k$, then $S_k \setminus \{n\}$ is an optimal solution for the subproblem considering the first $n-1$ requests with capacity $W - w_n$.

### Proof by Contradiction:
- **Case 1 ($n \notin S_k$):** Suppose there exists another subset $S' \subseteq \{1, \dots, n-1\}$ with total weight $\le W$ such that $\sum_{j \in S'} v_j > \sum_{j \in S_k} v_j$. Then $S'$ would yield a higher total urgency than $S_k$ for the original problem with capacity $W$, contradicting the optimality of $S_k$.
- **Case 2 ($n \in S_k$):** The remaining requests in $S_k \setminus \{n\}$ have total weight $\le W - w_n$ and total value $V - v_n$. Suppose there exists another subset $S'' \subseteq \{1, \dots, n-1\}$ with total weight $\le W - w_n$ such that $\sum_{j \in S''} v_j > V - v_n$. Then $S'' \cup \{n\}$ would be valid for capacity $(W - w_n) + w_n = W$ and have total value $> (V - v_n) + v_n = V$, contradicting the optimality of $S_k$.

Therefore, the problem satisfies **Optimal Substructure**. $\blacksquare$

---

## 2. Dynamic Programming Recurrence & Overlapping Subproblems

Since the subproblem state is uniquely identified by the pair $(i, w)$ where $i \in [0, n]$ and $w \in [0, W]$, there are at most $(n+1)(W+1)$ distinct subproblems.

### Correctness of Memoized Tabulation:
$$dp[i][w] = \begin{cases} 
0 & \text{if } i = 0 \text{ or } w = 0 \\
dp[i-1][w] & \text{if } w < w_i \\
\max(dp[i-1][w], v_i + dp[i-1][w - w_i]) & \text{if } w \ge w_i 
\end{cases}$$

Every entry $dp[i][w]$ is populated only after its dependent values $dp[i-1][w]$ and $dp[i-1][w - w_i]$ have been computed. Hence, mathematical induction on the row index $i$ guarantees that $dp[n][W]$ holds the global optimum.

### Complexity:
- **Time Complexity:** $O(n \cdot W)$ pseudo-polynomial time.
- **Space Complexity:** $O(n \cdot W)$ matrix space.
