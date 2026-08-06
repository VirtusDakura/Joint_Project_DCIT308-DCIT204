# Trace Table #4 – Merge Sort (Food Delivery Orders)

## Modules

M3 & M6

- Custom BST
- Red-Black Tree
- B-Tree

---

## Algorithm

Merge Sort

---

## Purpose

Sort delivery orders by urgency using Divide and Conquer.

Sorting Rules

1. Higher urgency first.
2. Earlier deadline if urgency is equal.

---

## Sample Input

```
[101(3),102(5),103(2),104(4),105(1),106(5)]
```

---

# Divide Phase

```
[101,102,103,104,105,106]

↓

[101,102,103]     [104,105,106]

↓

[101,102] [103]   [104,105] [106]

↓

[101] [102] [103] [104] [105] [106]
```

---

# Merge Phase

| Merge Step | Result |
|------------|--------|
| Merge 101 & 102 | `[102,101]` |
| Merge with 103 | `[102,101,103]` |
| Merge 104 & 105 | `[104,105]` |
| Merge with 106 | `[106,104,105]` |
| Final Merge | `[102,106,104,101,103,105]` |

---

## Final Sorted Array

```
[102(5),106(5),104(4),101(3),103(2),105(1)]
```

---

## Relationship with the Tree Structures

After Merge Sort finishes, the sorted records may be inserted into:

### Binary Search Tree

The BST stores the orders in sorted order for efficient searching.

### Red-Black Tree

Maintains balance automatically while preserving sorted order.

### B-Tree

Supports efficient indexing of large numbers of delivery orders.

---

## Complexity Analysis

| Case | Time |
|------|------|
| Best | O(n log n) |
| Average | O(n log n) |
| Worst | O(n log n) |

Space Complexity

```
O(n)
```

Stable

```
Yes
```