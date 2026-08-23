# Proof Sketch 02: QuickSort Partitioning Invariant & Recurrence Analysis
> **Department of Computer Science — University of Ghana**  
> **DCIT 204 / DCIT 308 Joint Semester Project**  
> **Section 10 Requirement**: *Proof sketches for selected algorithms (Divide-and-Conquer / Induction).*

---

## 1. Loop Invariant for QuickSort Partitioning

### Invariant Statement:
At the start of each iteration of the `for` loop in `partition(A, low, high)` with pivot $p = A[high]$ and pointer indices $low \le k \le j$:
1. If $low \le k \le i$, then $A[k] \le p$. (All elements in the lower partition are $\le$ pivot).
2. If $i + 1 \le k < j$, then $A[k] > p$. (All elements in the upper partition are $>$ pivot).
3. If $k = high$, then $A[k] = p$. (Pivot remains at the rightmost boundary).

### Proof by Mathematical Induction:
- **Initialization (Base Case):** Prior to the first iteration, $j = low$ and $i = low - 1$. The ranges $low \dots i$ and $i+1 \dots j-1$ are empty. Thus, the condition holds vacuously.
- **Maintenance (Inductive Step):** In iteration $j$, $A[j]$ is examined:
  - **Case 1 ($A[j] > p$):** Only $j$ increments. The element $A[j]$ naturally enters the second partition $i+1 \dots j$, maintaining property (2).
  - **Case 2 ($A[j] \le p$):** $i$ increments, $A[i]$ is swapped with $A[j]$, and $j$ increments. The swapped element $A[i] \le p$ enters the lower partition, while the displaced element $A[j] > p$ enters the upper partition. Invariant is preserved.
- **Termination:** At termination, $j = high$. All elements $low \dots high-1$ are partitioned. Swapping $A[i+1]$ with $A[high]$ places the pivot at index $i+1$ in its exact sorted final position. $\blacksquare$

---

## 2. Asymptotic Complexity via Recurrence Relations

### Best/Average Case Recurrence (Balanced Splits):
When median-of-three pivot selection yields approximately balanced halves:
$$T(n) = 2T(n/2) + \Theta(n)$$

Applying the **Master Theorem** ($a = 2, b = 2, f(n) = \Theta(n)$):
$$\log_b a = \log_2 2 = 1 \implies f(n) = \Theta(n^{\log_b a}) \implies T(n) = \mathbf{\Theta(n \log n)}$$

### Worst Case Recurrence (Degenerate Splits):
If the pivot is always the extreme minimum or maximum:
$$T(n) = T(n-1) + T(0) + \Theta(n) = \sum_{k=1}^n \Theta(k) = \mathbf{\Theta(n^2)}$$
*Mitigation:* Median-of-three pivot selection avoids the $O(n^2)$ worst case on sorted/reverse-sorted inputs.
