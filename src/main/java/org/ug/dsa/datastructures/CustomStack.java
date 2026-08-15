package org.ug.dsa.datastructures;

/**
 * Custom generic LIFO Stack implementation for undo/audit operations
 * and recursion simulation.
 */
public class CustomStack<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private Object[] elements;
    private int top; // index of the next free slot (also = current size)
    private final int capacity;

    public CustomStack() {
        this(DEFAULT_CAPACITY);
    }

    public CustomStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.top = 0;
    }

    /**
     * Pushes an element onto the top of the stack.
     * Throws IllegalStateException if the stack is full.
     */
    public void push(T element) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full (capacity " + capacity + ")");
        }
        elements[top] = element;
        top++;
    }

    /**
     * Removes and returns the top element.
     * Throws IllegalStateException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        top--;
        T value = (T) elements[top];
        elements[top] = null; // avoid holding a stale reference
        return value;
    }

    /**
     * Returns the top element without removing it.
     * Throws IllegalStateException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek an empty stack");
        }
        return (T) elements[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean isFull() {
        return top == capacity;
    }

    public int size() {
        return top;
    }
}