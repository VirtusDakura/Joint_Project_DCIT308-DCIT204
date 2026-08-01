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

    // TODO: Implement the bucket array (array of linked chains), hash function,
    //       and all required methods.
    // Hint: Each bucket is a linked list of Entry(key, value, next) nodes.
    //       Hash index = Math.abs(key.hashCode() % capacity).

}
