package org.ug.dsa.datastructures;

/**
 * Custom Min-Heap / Priority Queue implementation for urgent scheduling and Dijkstra's algorithm (Module M3).
 */
public class CustomHeap<T extends Comparable<T>> {

    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomHeap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    public void insert(T item) {
        if (size >= heap.length) {
            resize();
        }
        heap[size] = item;
        size++;
        heapifyUp(size - 1);
    }

    public T extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        heapifyDown(0);
        return min;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (index * 2 + 1 < size) {
            int left = index * 2 + 1;
            int right = left + 1;
            int smallest = left;

            if (right < size && heap[right].compareTo(heap[left]) < 0) {
                smallest = right;
            }

            if (heap[smallest].compareTo(heap[index]) < 0) {
                swap(index, smallest);
                index = smallest;
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
    private void resize() {
        T[] old = heap;
        heap = (T[]) new Comparable[old.length * 2];
        System.arraycopy(old, 0, heap, 0, old.length);
    }
}
