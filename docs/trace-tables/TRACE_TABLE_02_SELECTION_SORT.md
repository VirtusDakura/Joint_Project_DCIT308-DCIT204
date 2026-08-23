# Trace Table 02: Selection Sort on Delivery Urgency Priorities
> **Algorithm**: Selection Sort (`SelectionSort.java`)  
> **Module**: M4 (Searching and Sorting Engine)  
> **Initial Input**: Array of 5 Service Request urgency values: $[3, 1, 5, 2, 4]$  
> **Target Order**: Ascending numerical order $[1, 2, 3, 4, 5]$

---

## 📊 Step-by-Step Selection Sort Trace

| Pass ($i$) | Initial Subarray State | $minIdx$ Scan & Inner Comparisons | Min Value Found | Swap Action ($A[i] \leftrightarrow A[minIdx]$) | Array State After Pass | Comparisons | Swaps |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **$i = 0$** | $[\mathbf{3}, 1, 5, 2, 4]$ | Compare $3$ with $1$ ($min=1$ at idx 1)<br>Compare $1$ with $5$<br>Compare $1$ with $2$<br>Compare $1$ with $4$ | $1$ (at index 1) | Swap $A[0] (3) \leftrightarrow A[1] (1)$ | $[\mathbf{1}, 3, 5, 2, 4]$ | 4 | 1 |
| **$i = 1$** | $[1, \mathbf{3}, 5, 2, 4]$ | Compare $3$ with $5$<br>Compare $3$ with $2$ ($min=2$ at idx 3)<br>Compare $2$ with $4$ | $2$ (at index 3) | Swap $A[1] (3) \leftrightarrow A[3] (2)$ | $[1, \mathbf{2}, 5, 3, 4]$ | 3 | 1 |
| **$i = 2$** | $[1, 2, \mathbf{5}, 3, 4]$ | Compare $5$ with $3$ ($min=3$ at idx 3)<br>Compare $3$ with $4$ | $3$ (at index 3) | Swap $A[2] (5) \leftrightarrow A[3] (3)$ | $[1, 2, \mathbf{3}, 5, 4]$ | 2 | 1 |
| **$i = 3$** | $[1, 2, 3, \mathbf{5}, 4]$ | Compare $5$ with $4$ ($min=4$ at idx 4) | $4$ (at index 4) | Swap $A[3] (5) \leftrightarrow A[4] (4)$ | $[1, 2, 3, \mathbf{4}, \mathbf{5}]$ | 1 | 1 |

---

## ⏱️ Operation Summary
- **Total Comparisons**: $4 + 3 + 2 + 1 = 10 = \frac{n(n-1)}{2} \implies O(n^2)$
- **Total Swaps**: $4 \le n - 1 \implies O(n)$
- **In-Place Space**: $O(1)$ auxiliary memory.
