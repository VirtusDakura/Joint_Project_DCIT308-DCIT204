# Trace Table 06: 0/1 Knapsack Dynamic Programming Order Optimization
> **Algorithm**: 0/1 Knapsack Dynamic Programming (`DynamicProgrammingBatching.java`)  
> **Module**: M8 (Optimization Engine)  
> **Problem**: Selecting highest total urgency batch for a rider with Capacity $W = 6$.

---

## 📦 Request Dataset
- Request 1 ($i=1$): Load Weight $w_1 = 2$, Urgency Value $v_1 = 3$
- Request 2 ($i=2$): Load Weight $w_2 = 3$, Urgency Value $v_2 = 4$
- Request 3 ($i=3$): Load Weight $w_3 = 4$, Urgency Value $v_3 = 5$
- Request 4 ($i=4$): Load Weight $w_4 = 5$, Urgency Value $v_4 = 8$

---

## 📊 DP State Tabulation Matrix ($dp[i][w]$)
Recurrence Relation:
$$dp[i][w] = \begin{cases} dp[i-1][w] & \text{if } w < w_i \\ \max(dp[i-1][w], v_i + dp[i-1][w - w_i]) & \text{if } w \ge w_i \end{cases}$$

| State ($i \downarrow \backslash w \to$) | $w = 0$ | $w = 1$ | $w = 2$ | $w = 3$ | $w = 4$ | $w = 5$ | $w = 6$ | Decisions / Formula |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **$i = 0$ (Empty)** | 0 | 0 | 0 | 0 | 0 | 0 | 0 | Base cases |
| **$i = 1$ ($w_1=2, v_1=3$)** | 0 | 0 | **3** | 3 | 3 | 3 | 3 | $w \ge 2 \implies \max(0, 3+0) = 3$ |
| **$i = 2$ ($w_2=3, v_2=4$)** | 0 | 0 | 3 | **4** | 4 | **7** | 7 | At $w=5: \max(3, 4+dp[1][2]=4+3) = \mathbf{7}$ |
| **$i = 3$ ($w_3=4, v_3=5$)** | 0 | 0 | 3 | 4 | 4 | 7 | **8** | At $w=6: \max(7, 5+dp[2][2]=5+3) = \mathbf{8}$ |
| **$i = 4$ ($w_4=5, v_4=8$)** | 0 | 0 | 3 | 4 | 4 | **8** | **8** | At $w=6: \max(8, 8+dp[3][1]=8+0) = \mathbf{8}$ |

---

## 🔍 Backtracking & Solution Reconstruction
Starting from $dp[4][6] = 8$:
1. $dp[4][6] (8) == dp[3][6] (8) \implies$ **Request 4 was EXCLUDED**. (Remaining $w = 6$)
2. $dp[3][6] (8) \ne dp[2][6] (7) \implies$ **Request 3 was INCLUDED** ($w_3 = 4, v_3 = 5$). (Remaining $w = 6 - 4 = 2$)
3. $dp[2][2] (3) == dp[1][2] (3) \implies$ **Request 2 was EXCLUDED**. (Remaining $w = 2$)
4. $dp[1][2] (3) \ne dp[0][2] (0) \implies$ **Request 1 was INCLUDED** ($w_1 = 2, v_1 = 3$). (Remaining $w = 2 - 2 = 0$)

### Optimal Output Batch:
- **Selected Orders**: `[Request 1, Request 3]`
- **Total Capacity Used**: $2 + 4 = 6 / 6$ ($100\%$ capacity utilization).
- **Total Maximum Urgency Value**: $3 + 5 = \mathbf{8}$.
