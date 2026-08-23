# Trace Table 01: Binary Search on Sorted Service Requests
> **Algorithm**: Binary Search (`BinarySearch.java`)  
> **Module**: M4 (Searching and Sorting Engine)  
> **Dataset**: Service Request Urgency Levels (Sorted in Ascending Order)

---

## 🎯 Search Scenario
- **Sorted Array $A$**: $[1, 2, 2, 3, 4, 4, 5, 5, 5]$ ($N = 9$)
- **Target Value $T$**: $4$
- **Precondition Verification**: $\text{isSorted}(A) = \text{TRUE}$

---

## 📊 Step-by-Step Trace Table

| Iteration | $low$ | $high$ | $mid = \lfloor low + \frac{high - low}{2} \rfloor$ | $A[mid]$ | Comparison ($T \text{ vs } A[mid]$) | Decision / Action | Search Space Remaining |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Initial** | 0 | 8 | — | — | — | Verify precondition $\to$ PASSED | $[1, 2, 2, 3, 4, 4, 5, 5, 5]$ |
| **Pass 1** | 0 | 8 | $0 + \lfloor 8/2 \rfloor = 4$ | $4$ | $4 == 4 \implies \mathbf{MATCH}$ | Return index $4$ | Target Found at Index 4 |

### Secondary Trace (Searching for $T = 3$):
| Iteration | $low$ | $high$ | $mid$ | $A[mid]$ | Comparison ($T \text{ vs } A[mid]$) | Decision / Action | Next Range |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Pass 1** | 0 | 8 | 4 | 4 | $3 < 4 \implies T < A[mid]$ | Eliminate right half $\to high = mid - 1 = 3$ | Indices $0 \dots 3$ |
| **Pass 2** | 0 | 3 | 1 | 2 | $3 > 2 \implies T > A[mid]$ | Eliminate left half $\to low = mid + 1 = 2$ | Indices $2 \dots 3$ |
| **Pass 3** | 2 | 3 | 2 | 2 | $3 > 2 \implies T > A[mid]$ | Eliminate left half $\to low = mid + 1 = 3$ | Index 3 |
| **Pass 4** | 3 | 3 | 3 | 3 | $3 == 3 \implies \mathbf{MATCH}$ | Return index $3$ | **FOUND** |

---

## ⏱️ Primitive Operation Count Summary
- Total Comparisons in Best Case: $1$ (Target at midpoint) $\implies O(1)$
- Total Comparisons in Worst Case: $\lfloor \log_2(9) \rfloor + 1 = 4$ comparisons $\implies O(\log n)$
