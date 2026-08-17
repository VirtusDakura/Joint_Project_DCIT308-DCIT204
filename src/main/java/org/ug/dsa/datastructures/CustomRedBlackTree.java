package org.ug.dsa.datastructures;

/**
 * Custom generic Red-Black Tree (Self-Balancing Binary Search Tree).
 *
 * Implements Module M3 balanced tree specifications:
 *   - Guarantees O(log n) worst-case search, insertion, and lookup
 *   - Maintains Red-Black invariants:
 *       1. Every node is either RED or BLACK.
 *       2. The root is always BLACK.
 *       3. Red nodes cannot have red children (no two consecutive red nodes).
 *       4. Every path from root to null contains the same number of black nodes (black-height).
 *   - Insertion with self-balancing rotations (left/right) and recoloring
 */
public class CustomRedBlackTree<K extends Comparable<K>, V> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    /**
     * Internal Red-Black Tree Node.
     */
    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;
        public Node<K, V> parent;
        public boolean color;

        public Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node<K, V> root;
    private int size;

    public CustomRedBlackTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a key-value pair into the Red-Black Tree and restores RB balance properties.
     */
    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        Node<K, V> node = new Node<>(key, value, RED);
        if (root == null) {
            node.color = BLACK;
            root = node;
            size++;
            return;
        }

        Node<K, V> parent = null;
        Node<K, V> current = root;

        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                // Key exists: update value and return
                current.value = value;
                return;
            }
        }

        node.parent = parent;
        if (key.compareTo(parent.key) < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        size++;

        // Restore Red-Black invariants
        fixAfterInsertion(node);
    }

    /**
     * Searches for a key in the tree.
     *
     * @return the associated value, or null if key does not exist
     */
    public V search(K key) {
        if (key == null) return null;
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

    public boolean containsKey(K key) {
        return search(key) != null;
    }

    private void fixAfterInsertion(Node<K, V> node) {
        Node<K, V> z = node;

        while (z != root && isRed(z.parent)) {
            if (z.parent == z.parent.parent.left) {
                Node<K, V> uncle = z.parent.parent.right;

                if (isRed(uncle)) {
                    // Case 1: Uncle is RED -> Recolor parent, uncle, and grandparent
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    // Case 2: Uncle is BLACK and z is a right child -> Left rotation
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    // Case 3: Uncle is BLACK and z is a left child -> Right rotation + recolor
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                // Symmetric cases (parent is right child of grandparent)
                Node<K, V> uncle = z.parent.parent.left;

                if (isRed(uncle)) {
                    // Case 1: Uncle is RED
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    // Case 2: Uncle is BLACK and z is a left child -> Right rotation
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    // Case 3: Uncle is BLACK and z is a right child -> Left rotation + recolor
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }

        root.color = BLACK;
    }

    private void rotateLeft(Node<K, V> x) {
        Node<K, V> y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node<K, V> y) {
        Node<K, V> x = y.left;
        y.left = x.right;

        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    private boolean isRed(Node<K, V> node) {
        return node != null && node.color == RED;
    }

    /**
     * Inorder traversal returning keys in sorted order.
     */
    public CustomList<K> inorderTraversal() {
        CustomDynamicArray<K> list = new CustomDynamicArray<>();
        inorderHelper(root, list);
        return list;
    }

    private void inorderHelper(Node<K, V> node, CustomDynamicArray<K> list) {
        if (node == null) return;
        inorderHelper(node.left, list);
        list.add(node.key);
        inorderHelper(node.right, list);
    }

    /**
     * Returns total tree height.
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node<K, V> node) {
        if (node == null) return -1;
        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    /**
     * Returns the black-height (number of black nodes along any path to a leaf).
     */
    public int blackHeight() {
        int count = 0;
        Node<K, V> current = root;
        while (current != null) {
            if (current.color == BLACK) {
                count++;
            }
            current = current.left;
        }
        return count;
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
