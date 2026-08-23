package org.ug.dsa.datastructures;

/**
 * Generic list interface for custom linear collections.
 */
public interface CustomList<T> extends Iterable<T> {
    void add(T element);
    T get(int index);
    T remove(int index);
    int size();
    boolean isEmpty();
    void clear();
}
