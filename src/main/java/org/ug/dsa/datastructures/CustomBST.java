package org.ug.dsa.datastructures;

/**
 * Custom Binary Search Tree for indexing service requests and locations (Module M3, M6).
 */
public class CustomBST<K extends Comparable<K>, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;
    private int size;

    public void insert(K key, V value) {
        root = insertRecursive(root, key, value);
    }

    private Node<K, V> insertRecursive(Node<K, V> current, K key, V value) {
        if (current == null) {
            size++;
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = insertRecursive(current.left, key, value);
        } else if (cmp > 0) {
            current.right = insertRecursive(current.right, key, value);
        } else {
            current.value = value; // Update value
        }
        return current;
    }

    public V search(K key) {
        Node<K, V> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public boolean contains(K key) {
        return search(key) != null;
    }

    public int size() {
        return size;
    }
}
