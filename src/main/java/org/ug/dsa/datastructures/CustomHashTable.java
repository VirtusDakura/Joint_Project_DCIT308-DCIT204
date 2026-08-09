package org.ug.dsa.datastructures;

/**
 * Custom Hash Table using separate chaining for collision handling.
 *
 * Assigned to: Collins Edumadze Egyir (22233318)
 *
 * Required operations:
 *   - put(K key, V value)     : Insert or update a key-value pair
 *   - get(K key)              : Return value or null
 *   - remove(K key)           : Remove and return value
 *   - containsKey(K key)
 *   - size()
 *   - loadFactor()            : Current size / table capacity
 *   - collisionCount()        : Total collisions across all buckets
 *   - keys()                  : Return all keys
 *
 * Evidence to produce:
 *   - Collision statistics experiment for load factors at 100 to 20,000 keys
 *   - Unit tests for put/get/remove, duplicate key update, key not found, empty table
 */
public class CustomHashTable<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public CustomHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode() % capacity);
    }

    public void put(K key, V value) {
        if (key == null) return;
        if (loadFactor() > DEFAULT_LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        table[index] = new Entry<>(key, value, table[index]);
        size++;
    }

    public V get(K key) {
        if (key == null) return null;
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) return null;
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public double loadFactor() {
        return (double) size / capacity;
    }

    public int collisionCount() {
        int totalCollisions = 0;
        for (int i = 0; i < capacity; i++) {
            int bucketSize = 0;
            Entry<K, V> current = table[i];
            while (current != null) {
                bucketSize++;
                current = current.next;
            }
            if (bucketSize > 1) {
                totalCollisions += (bucketSize - 1);
            }
        }
        return totalCollisions;
    }

    public CustomList<K> keys() {
        CustomDynamicArray<K> keyList = new CustomDynamicArray<>(size > 0 ? size : 1);
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                keyList.add(current.key);
                current = current.next;
            }
        }
        return keyList;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        int newCapacity = oldCapacity * 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

}
