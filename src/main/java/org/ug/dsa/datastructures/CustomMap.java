package org.ug.dsa.datastructures;

/**
 * Custom generic map implementation.
 * Wraps CustomHashTable to provide key-value storage.
 *
 * Required by DCIT 308 course constraints.
 */
public class CustomMap<K, V> {

    private CustomHashTable<K, V> hashTable;

    public CustomMap() {
        this.hashTable = new CustomHashTable<>();
    }

    public CustomMap(int initialCapacity) {
        this.hashTable = new CustomHashTable<>(initialCapacity);
    }

    public void put(K key, V value) {
        hashTable.put(key, value);
    }

    public V get(K key) {
        return hashTable.get(key);
    }

    public V remove(K key) {
        return hashTable.remove(key);
    }

    public boolean containsKey(K key) {
        return hashTable.containsKey(key);
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

    public CustomList<K> keySet() {
        return hashTable.keys();
    }

    public double loadFactor() {
        return hashTable.loadFactor();
    }
}
