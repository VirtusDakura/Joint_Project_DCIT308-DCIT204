# Proof Sketch #1 – Loop Invariant for the Merge Step

## Algorithm

Merge Sort

## Project

Food Delivery and Route Optimisation System

---

# Statement

The merge procedure correctly combines two already sorted subarrays into one sorted array.

---

# Preconditions

Before the merge step begins:

- The left half is sorted.
- The right half is sorted.
- Both halves contain valid ServiceRequest objects.
- Orders are compared using `ServiceRequest.compareTo()`.

---

# Loop Invariant

At the beginning of every iteration of the merge loop,

> The portion of the output array that has already been written contains the smallest (highest priority) elements in their correct sorted order.

The remaining elements in both halves have not yet been processed.

---

# Initialization

Before the first comparison,

- No elements have been copied.
- Therefore the output portion is empty.

An empty array is already sorted.

Hence, the invariant holds before the loop begins.

---

# Maintenance

Assume the invariant is true before an iteration.

The algorithm compares the first remaining element of both halves.

The smaller (higher priority) element is copied into the destination array.

The corresponding pointer advances.

The destination index also advances.

Therefore:

- The output remains sorted.
- Exactly one additional correct element has been placed.

The invariant still holds.

---

# Termination

The loop stops when one half becomes empty.

The remaining elements of the other half are already sorted.

They are copied directly to the destination array.

Every element appears exactly once.

No element is lost.

No new element is introduced.

Therefore the merged array is correctly sorted.

---

# Correctness

Since:

1. The invariant is true before the loop,
2. It remains true after every iteration, and
3. It guarantees a sorted output when the loop terminates,

the merge procedure is correct.

---

# Stability

When two delivery orders compare as equal, the implementation copies the element from the left half first.

Therefore Merge Sort preserves the relative order of equal elements.

Hence Merge Sort is a **stable sorting algorithm**.

---

# Complexity

Time Complexity

```
O(n log n)
```

Space Complexity

```
O(n)
```