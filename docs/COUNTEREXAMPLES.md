# Formal Algorithmic Counterexamples & Precondition Proofs
> **Department of Computer Science — University of Ghana**  
> **DCIT 204 / DCIT 308 Joint Semester Project**  
> **Team Name**: MindCraft_Sub_1  

---

## 🚫 Counterexample 1: Precondition Violation on Binary Search (Searching in Unsorted Sequences)

### 1. Theoretical Precondition
Binary Search fundamentally relies on the **Monotonic Ordering Invariant**:
$$\forall i, j \text{ such that } 0 \le i < j < n \implies A[i] \le A[j]$$

When comparing target $T$ with the midpoint element $M = A[mid]$, the algorithm makes a binary elimination decision:
- If $T < M$, discard the upper half $[mid, high]$ because sortedness guarantees $\forall k \ge mid, A[k] \ge M > T$.
- If $T > M$, discard the lower half $[low, mid]$ because sortedness guarantees $\forall k \le mid, A[k] \le M < T$.

### 2. Concrete Counterexample Failure Case
Consider the unsorted array of request urgencies:
$$A = [50, 10, 40, 20, 80, 30]$$
Target to find: $T = 10$ (which clearly exists at index $i = 1$).

**Step-by-Step Execution of Unchecked Binary Search:**
1. **Pass 1:** $low = 0, high = 5 \implies mid = 0 + (5-0)/2 = 2$.
   - Midpoint value: $A[2] = 40$.
   - Comparison: $T (10) < A[2] (40) \implies$ Algorithm assumes $T$ must lie in the left half $[0, 1]$.
   - Update: $high = mid - 1 = 1$.
2. **Pass 2:** $low = 0, high = 1 \implies mid = 0 + (1-0)/2 = 0$.
   - Midpoint value: $A[0] = 50$.
   - Comparison: $T (10) < A[0] (50) \implies$ Algorithm assumes $T$ must lie in the left half $[0, -1]$.
   - Update: $high = mid - 1 = -1$.
3. **Termination:** $low (0) > high (-1) \implies$ Loop terminates.
   - **Result returned:** $-1$ (NOT FOUND).

### 3. Impact & Handling
Because the precondition $A[0] \le A[1] \le \dots$ was violated ($A[0] = 50 > A[1] = 10$), the algorithm eliminated index 1 incorrectly.
**Implementation in `BinarySearch.java`:**
The method `BinarySearch.search()` runs an `isSorted()` verification before binary partitioning. If the array is not sorted, it halts safely with `preconditionMet = false`.

---

## 🚫 Counterexample 2: Greedy Priority Choice Failure vs. 0/1 Knapsack Dynamic Programming

### 1. Theoretical Limitation of the Greedy Choice Property
A Greedy algorithm makes the locally best choice at each step (such as highest urgency density or earliest deadline) without looking ahead at capacity consequences. In the **0/1 Knapsack Problem** (where items cannot be divided), greedy can fail because taking a single high-density item may leave an awkward amount of leftover space that cannot fit other useful items.

### 2. Concrete Delivery Batching Counterexample
Suppose a delivery rider has a total carrying capacity of:
$$W = 50 \text{ weight units}$$

Consider three pending customer orders with urgency values $v_i$ and capacity loads $w_i$:
| Order ID | Category | Capacity Load ($w_i$) | Urgency Value ($v_i$) | Urgency Density ($v_i / w_i$) |
| :--- | :--- | :--- | :--- | :--- |
| **REQ-A** | Catering / Family Pack | $w_1 = 30$ | $v_1 = 60$ | **2.00** (Highest) |
| **REQ-B** | Pharmacy / Parcel | $w_2 = 25$ | $v_2 = 45$ | **1.80** |
| **REQ-C** | Express Food Order | $w_3 = 25$ | $v_3 = 45$ | **1.80** |

### 3. Execution Comparison: Greedy vs. Dynamic Programming

#### Approach 1: Greedy Dispatch Heuristic (Priority by Urgency Density)
1. **Step 1:** Select highest density order $\to$ **REQ-A** ($w_1 = 30, v_1 = 60$).
   - Remaining rider capacity: $50 - 30 = 20$.
2. **Step 2:** Next best density is REQ-B ($w_2 = 25$). However, $w_2 (25) > 20$. REQ-B cannot fit.
3. **Step 3:** Next is REQ-C ($w_3 = 25$). $w_3 (25) > 20$. REQ-C cannot fit.
- **Greedy Outcome:**
  - Selected Orders: `[REQ-A]`
  - Total Urgency Value Achieved: **60**
  - Capacity Utilized: $30 / 50$ (20 units unused).

#### Approach 2: 0/1 Knapsack Dynamic Programming (`DynamicProgrammingBatching.java`)
The DP solver considers all subset combinations using the recurrence relation:
$$dp[i][w] = \max(dp[i-1][w], v_i + dp[i-1][w - w_i])$$

- Optimal Subset Selected: `[REQ-B, REQ-C]`
- Total Weight Used: $25 + 25 = 50 / 50$ (100% capacity utilized).
- Total Urgency Value Achieved: $45 + 45 = \mathbf{90}$.

$$\text{DP Optimal Value (90)} > \text{Greedy Value (60)} \implies \mathbf{+50\% \text{ Improvement}}$$

### 4. Conclusion
- **Greedy is optimal for Fractional Knapsack and unit-duration scheduling**, but **sub-optimal for 0/1 multi-capacity batching**.
- Dynamic Programming finds the global optimum by evaluating overlapping subproblems and memoizing states in $O(n \cdot W)$ time.
