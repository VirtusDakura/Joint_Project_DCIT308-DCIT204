package org.ug.dsa.datastructures;

/**
 * Custom generic list interface representing linear custom data structures (Module M3).
 */
public interface CustomList<T> extends Iterable<T> {
    void add(T element);
    T get(int index);
    T remove(int index);
    int size();
    boolean isEmpty();
    void clear();
}
