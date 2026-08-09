package org.ug.dsa.datastructures;

/**
 * Custom Circular Queue with wrap-around index handling.
 * Uses a fixed-size array with front and rear pointers that wrap around.
 *
 * Assigned to: Rushdan Delimwine Antiku (22102540)
 *
 * Required operations:
 *   - enqueue(T element)  : Add to the rear with wrap-around
 *   - dequeue()           : Remove and return from the front with wrap-around
 *   - peek()              : View front element without removing
 *   - isEmpty()
 *   - isFull()            : Check if queue is at capacity
 *   - size()
 *   - capacity()
 *
 * Evidence to produce:
 *   - Trace showing front/rear pointer wrap-around during enqueue/dequeue
 *   - Unit tests for wrap-around scenario, full queue behavior
 */
public class CustomCircularQueue<T> {

    private static final int DEFAULT_CAPACITY = 100;
    private T[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    /**
     * Initialize a circular queue with default capacity
     */
    @SuppressWarnings("unchecked")
    public CustomCircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Initialize a circular queue with specified capacity
     */
    @SuppressWarnings("unchecked")
    public CustomCircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Add an element to the rear of the queue (with wrap-around)
     */
    public void enqueue(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot enqueue null element");
        }
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        data[rear] = element;
        size++;
    }

    /**
     * Remove and return the front element (with wrap-around)
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue");
        }
        T element = data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    /**
     * View the front element without removing it
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty queue");
        }
        return data[front];
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if queue is full
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Get the number of elements in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Get the capacity of the queue
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Get current front pointer index
     */
    public int getFront() {
        return front;
    }

    /**
     * Get current rear pointer index
     */
    public int getRear() {
        return rear;
    }

    /**
     * Clear all elements from the queue
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            data[i] = null;
        }
        front = 0;
        rear = -1;
        size = 0;
    }
}
