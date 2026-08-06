# Trace Table #3 – Insertion Sort (Food Delivery Orders)

## Module

M3 – Custom Stack for Audit & Undo Trail

## Algorithm

Insertion Sort

## Purpose

Sort food delivery orders by urgency.

Sorting rules:

1. Higher urgency comes first.
2. If two orders have the same urgency, the earlier deadline comes first.

---

## Sample Input

| Index | Order ID | Urgency | Deadline |
|------:|----------|:-------:|----------|
| 0 | ORD-101 | 3 | 12:50 |
| 1 | ORD-102 | 5 | 12:35 |
| 2 | ORD-103 | 2 | 13:10 |
| 3 | ORD-104 | 4 | 12:45 |
| 4 | ORD-105 | 5 | 12:25 |

Initial Array

```
[ORD-101(3), ORD-102(5), ORD-103(2), ORD-104(4), ORD-105(5)]
```

---

## Trace Table

| Pass | Key | Action | Resulting Array |
|------|-----|--------|-----------------|
| Initial | - | Original array | `[101(3),102(5),103(2),104(4),105(5)]` |
| Pass 1 | ORD-102 | Shift ORD-101 | `[102(5),101(3),103(2),104(4),105(5)]` |
| Pass 2 | ORD-103 | No shift required | `[102(5),101(3),103(2),104(4),105(5)]` |
| Pass 3 | ORD-104 | Shift ORD-103 and ORD-101 | `[102(5),104(4),101(3),103(2),105(5)]` |
| Pass 4 | ORD-105 | Shift ORD-103, ORD-101, ORD-104 and compare with ORD-102 (earlier deadline wins) | `[105(5),102(5),104(4),101(3),103(2)]` |

---

## Final Sorted Array

```
[ORD-105(5), ORD-102(5), ORD-104(4), ORD-101(3), ORD-103(2)]
```

---

## Stack Audit Trail

Every insertion operation can be recorded in the project's Custom Stack.

Example:

```
Push:
Moved ORD-102 to index 0

Push:
Moved ORD-104 to index 1

Push:
Moved ORD-105 to index 0
```

Undo simply pops the most recent operation.

```
Top
↓

Moved ORD-105
Moved ORD-104
Moved ORD-102
```

This demonstrates the Last-In First-Out (LIFO) property of the Custom Stack.

---

## Complexity Analysis

| Case | Time |
|------|------|
| Best | O(n) |
| Average | O(n²) |
| Worst | O(n²) |

Space Complexity:

```
O(1)
```

Stable:

```
Yes
```