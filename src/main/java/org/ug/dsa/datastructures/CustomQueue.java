package org.ug.dsa.datastructures;

/**
 * Custom FIFO Queue for first-come-first-served service request dispatch.
 *
 * Assigned to: Rushdan Delimwine Antiku (22102540)
 *
 * Required operations:
 *   - enqueue(T element)  : Add to the rear
 *   - dequeue()           : Remove and return from the front
 *   - peek()              : View front element without removing
 *   - isEmpty()
 *   - isFull()            : If using array-backed implementation
 *   - size()
 *
 * Evidence to produce:
 *   - Trace showing front/rear pointer movement after each enqueue/dequeue
 *   - Unit tests for enqueue until full, dequeue until empty, enqueue on full queue
 */
public class CustomQueue<T> {

    private static final int DEFAULT_CAPACITY = 100;
    private Node<T> front;
    private Node<T> rear;
    private int size;

    /**
     * Inner class representing a node in the queue
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Initialize an empty queue
     */
    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Add an element to the rear of the queue (FIFO)
     */
    public void enqueue(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot enqueue null element");
        }
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    /**
     * Remove and return the front element of the queue
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue");
        }
        T data = front.data;
        front = front.next;
        size--;
        if (isEmpty()) {
            rear = null;
        }
        return data;
    }

    /**
     * View the front element without removing it
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty queue");
        }
        return front.data;
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the number of elements in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Check if queue is full (always false for linked-list implementation)
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Clear all elements from the queue
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
