package org.ug.dsa.datastructures;

/**
 * Custom Min-Heap / Priority Queue for urgent delivery order scheduling.
 *
 * Assigned to: Rushdan Delimwine Antiku (22102540)
 *
 * Required operations:
 *   - insert(T item)        : Add an element and maintain the heap property
 *   - extractMin()          : Remove and return the smallest element
 *   - peekMin()             : View the smallest element without removing it
 *   - heapify(T[] array)    : Build a heap from an existing array in O(n)
 *   - size(), isEmpty()
 *
 * Evidence to produce:
 *   - Dispatch order trace showing heap array state after each insert/extract
 *   - Performance experiment: measure insert/extractMin for 100 to 20,000 requests
 *   - Unit tests for empty heap, single element, heap property maintenance
 */
public class CustomHeap<T extends Comparable<T>> {

    // TODO: Implement the heap array, size tracking, and all required methods.
    // Hint: Use a Comparable[] array. Parent of index i is at (i-1)/2.
    //       Left child is at 2i+1, right child is at 2i+2.

}
