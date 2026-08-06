package org.ug.dsa.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom generic dynamic array implementation.
 * Handles automatic capacity expansion and shrinking.
 */
public class CustomDynamicArray<T> implements CustomList<T> {

    private static final int INITIAL_CAPACITY = 4;
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomDynamicArray() {
        this.data = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public CustomDynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = INITIAL_CAPACITY;
        }
        this.data = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    // Appends element to end of list, doubling capacity if full
    @Override
    public void add(T element) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = element;
    }

    // Inserts element at specific index and shifts existing items right
    public void insert(int index, T element) {
        checkIndexForInsert(index);
        if (size == data.length) {
            resize(data.length * 2);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    public T set(int index, T element) {
        checkIndex(index);
        T oldVal = data[index];
        data[index] = element;
        return oldVal;
    }

    // Removes element at index and shifts items left
    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;

        // Shrinks array when utilization drops to 25%
        if (size > 0 && size <= data.length / 4 && data.length / 2 >= INITIAL_CAPACITY) {
            resize(data.length / 2);
        }

        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        this.data = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArr = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = data[i];
        }
        this.data = newArr;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }

    private void checkIndexForInsert(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for insert at size " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[cursor++];
            }
        };
    }
}
