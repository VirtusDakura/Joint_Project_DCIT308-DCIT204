package org.ug.dsa.datastructures;

import java.util.NoSuchElementException;

/**
 * Custom generic Min-Heap / Priority Queue implementation.
 *
 * Implements Module M3 and M5 specifications for priority scheduling:
 *   - insert(T item)     : Adds an element with O(log n) sift-up
 *   - extractMin()       : Removes and returns the smallest element with O(log n) sift-down
 *   - peekMin()          : Returns the smallest element in O(1) without removing
 *   - heapify(T[] array) : Builds a valid heap from an existing array in O(n) time
 *   - size(), isEmpty(), clear()
 *
 * Internal array dynamically doubles in capacity when full.
 */
public class CustomHeap<T extends Comparable<T>> {

    private static final int INITIAL_CAPACITY = 16;
    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomHeap() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public CustomHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = INITIAL_CAPACITY;
        }
        this.heap = (T[]) new Comparable[initialCapacity];
        this.size = 0;
    }

    /**
     * Constructs a min-heap from an existing array using bottom-up heapify in O(n).
     */
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> CustomHeap<E> heapify(E[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }
        CustomHeap<E> h = new CustomHeap<>(Math.max(INITIAL_CAPACITY, array.length * 2));
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException("Array contains null element at index " + i);
            }
            h.heap[i] = array[i];
        }
        h.size = array.length;

        // Sift down from last non-leaf node up to root: O(n) total time
        for (int i = (h.size / 2) - 1; i >= 0; i--) {
            h.siftDown(i);
        }
        return h;
    }

    /**
     * Inserts an element into the min-heap.
     */
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert null item into heap.");
        }
        if (size == heap.length) {
            resize(heap.length * 2);
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Removes and returns the minimum element (root of the min-heap).
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    /**
     * Returns the minimum element without removing it.
     */
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.heap = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    private void siftUp(int index) {
        int current = index;
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (heap[current].compareTo(heap[parent]) < 0) {
                swap(current, parent);
                current = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int current = index;
        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }

            if (smallest != current) {
                swap(current, smallest);
                current = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newHeap = (T[]) new Comparable[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        this.heap = newHeap;
    }
}
