package org.ug.dsa.datastructures;

/**
 * Custom generic Binary Search Tree for tree-based indexing over locations and requests.
 *
 * Implements Module M3 and M6 tree index specifications:
 *   - insert(K key, V value) : Inserts key-value pair, updating value if key exists
 *   - search(K key)          : Returns value or null in O(h) time
 *   - delete(K key)          : Deletes node handling leaf, 1-child, and 2-children cases
 *   - inorderTraversal()     : Returns keys in ascending sorted order
 *   - preorderTraversal(), postorderTraversal()
 *   - minimum(), maximum(), height(), size(), isEmpty(), clear()
 */
public class CustomBST<K extends Comparable<K>, V> {

    /**
     * Internal BST Node holding key, value, and left/right child pointers.
     */
    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<K, V> root;
    private int size;

    public CustomBST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair into the BST. If the key already exists, updates the value.
     */
    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
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
            // Overwrite existing key value
            current.value = value;
        }
        return current;
    }

    /**
     * Searches for a key and returns its value, or null if not found.
     */
    public V search(K key) {
        if (key == null) return null;
        Node<K, V> node = searchNode(root, key);
        return node == null ? null : node.value;
    }

    public boolean containsKey(K key) {
        return search(key) != null;
    }

    private Node<K, V> searchNode(Node<K, V> current, K key) {
        if (current == null) return null;
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            return searchNode(current.left, key);
        } else if (cmp > 0) {
            return searchNode(current.right, key);
        } else {
            return current;
        }
    }

    /**
     * Deletes a key from the BST.
     *
     * @return the value of the removed node, or null if key was not found
     */
    public V delete(K key) {
        if (key == null) return null;
        V existing = search(key);
        if (existing != null) {
            root = deleteRecursive(root, key);
            size--;
        }
        return existing;
    }

    private Node<K, V> deleteRecursive(Node<K, V> current, K key) {
        if (current == null) return null;

        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = deleteRecursive(current.left, key);
        } else if (cmp > 0) {
            current.right = deleteRecursive(current.right, key);
        } else {
            // Node with only one child or no child
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            Node<K, V> successor = minNode(current.right);
            current.key = successor.key;
            current.value = successor.value;
            // Delete the inorder successor
            current.right = deleteRecursive(current.right, successor.key);
        }
        return current;
    }

    /**
     * Returns the minimum key stored in the BST.
     */
    public K minimum() {
        if (isEmpty()) return null;
        return minNode(root).key;
    }

    private Node<K, V> minNode(Node<K, V> node) {
        Node<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Returns the maximum key stored in the BST.
     */
    public K maximum() {
        if (isEmpty()) return null;
        Node<K, V> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    /**
     * Inorder traversal: Left -> Root -> Right (produces sorted keys).
     */
    public CustomList<K> inorderTraversal() {
        CustomDynamicArray<K> result = new CustomDynamicArray<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node<K, V> node, CustomDynamicArray<K> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.key);
        inorderHelper(node.right, result);
    }

    /**
     * Preorder traversal: Root -> Left -> Right.
     */
    public CustomList<K> preorderTraversal() {
        CustomDynamicArray<K> result = new CustomDynamicArray<>();
        preorderHelper(root, result);
        return result;
    }

    private void preorderHelper(Node<K, V> node, CustomDynamicArray<K> result) {
        if (node == null) return;
        result.add(node.key);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    /**
     * Postorder traversal: Left -> Right -> Root.
     */
    public CustomList<K> postorderTraversal() {
        CustomDynamicArray<K> result = new CustomDynamicArray<>();
        postorderHelper(root, result);
        return result;
    }

    private void postorderHelper(Node<K, V> node, CustomDynamicArray<K> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.key);
    }

    /**
     * Returns the height of the BST (0 for single-node tree, -1 for empty tree).
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node<K, V> node) {
        if (node == null) return -1;
        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }
}
