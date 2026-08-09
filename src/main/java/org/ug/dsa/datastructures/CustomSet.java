package org.ug.dsa.datastructures;

import java.util.Iterator;

/**
 * Custom generic set implementation.
 * Uses CustomHashTable internally to ensure uniqueness of elements.
 *
 * Required by DCIT 308 course constraints.
 */
public class CustomSet<T> implements Iterable<T> {

    private CustomHashTable<T, Object> hashTable;
    private static final Object DUMMY = new Object();

    public CustomSet() {
        this.hashTable = new CustomHashTable<>();
    }

    public CustomSet(int initialCapacity) {
        this.hashTable = new CustomHashTable<>(initialCapacity);
    }

    public void add(T element) {
        hashTable.put(element, DUMMY);
    }

    public boolean contains(T element) {
        return hashTable.containsKey(element);
    }

    public void remove(T element) {
        hashTable.remove(element);
    }

    public int size() {
        return hashTable.size();
    }

    public boolean isEmpty() {
        return hashTable.size() == 0;
    }

    public void clear() {
        this.hashTable = new CustomHashTable<>();
    }

    @Override
    public Iterator<T> iterator() {
        return hashTable.keys().iterator();
    }
}
