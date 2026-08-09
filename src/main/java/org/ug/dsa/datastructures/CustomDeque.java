package org.ug.dsa.datastructures;

/**
 * Custom Double-Ended Queue (Deque) for emergency order insertions.
 * Supports adding and removing from both ends.
 *
 * Assigned to: Rushdan Delimwine Antiku (22102540)
 *
 * Required operations:
 *   - addFront(T element) : Add to the front
 *   - addRear(T element)  : Add to the rear
 *   - removeFront()       : Remove and return from the front
 *   - removeRear()        : Remove and return from the rear
 *   - peekFront()         : View front element without removing
 *   - peekRear()          : View rear element without removing
 *   - isEmpty()
 *   - size()
 *
 * Use Case: Emergency orders can be inserted at the front of the dispatch queue
 *
 * Evidence to produce:
 *   - Trace showing front/rear operations with priority insertion
 *   - Unit tests for addFront/removeFront, addRear/removeRear, and mixed operations
 */
public class CustomDeque<T> {

    private Node<T> front;
    private Node<T> rear;
    private int size;

    /**
     * Inner class representing a doubly-linked node in the deque
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Initialize an empty deque
     */
    public CustomDeque() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Add an element to the front of the deque
     */
    public void addFront(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element");
        }
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
        size++;
    }

    /**
     * Add an element to the rear of the deque
     */
    public void addRear(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element");
        }
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            newNode.prev = rear;
            rear = newNode;
        }
        size++;
    }

    /**
     * Remove and return the front element
     */
    public T removeFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove from an empty deque");
        }
        T data = front.data;
        if (front == rear) {
            front = rear = null;
        } else {
            front = front.next;
            front.prev = null;
        }
        size--;
        return data;
    }

    /**
     * Remove and return the rear element
     */
    public T removeRear() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove from an empty deque");
        }
        T data = rear.data;
        if (front == rear) {
            front = rear = null;
        } else {
            rear = rear.prev;
            rear.next = null;
        }
        size--;
        return data;
    }

    /**
     * View the front element without removing it
     */
    public T peekFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty deque");
        }
        return front.data;
    }

    /**
     * View the rear element without removing it
     */
    public T peekRear() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty deque");
        }
        return rear.data;
    }

    /**
     * Check if deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the number of elements in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements from the deque
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
