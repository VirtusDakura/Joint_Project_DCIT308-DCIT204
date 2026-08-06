package org.ug.dsa.datastructures;



/**
 * Custom B-Tree for database index simulation.
 *
 * Assigned to: Jessica Zunuo Puozaa (22120404)
 *
 * Minimum degree (t) derived from index number: 22120404 % 3 = 0, so t = 3 + 0 = 3.
 * This means every node (except the root) holds between (t-1)=2 and (2t-1)=5 keys,
 * and has between t=3 and 2t=6 children.
 *
 * Required operations:
 *   - insert(K key, V value)  : Insert with node splitting on overflow
 *   - search(K key)
 *   - inorderTraversal()      : Sorted key output
 *   - height()
 *
 * Evidence to produce:
 *   - Search path trace
 *   - Sorted inorder output
 *   - Before/after node-splitting diagrams
 *   - Unit tests verifying splitting, search, and sorted traversal
 */
public class CustomBTree<K extends Comparable<K>, V> {

    private static final int MIN_DEGREE = 3; // t, derived from index number 22120404
    private static final int MAX_KEYS = 2 * MIN_DEGREE - 1; // 5
    private static final int MAX_CHILDREN = 2 * MIN_DEGREE; // 6

    private BTreeNode root;
    private int size;

    /**
     * A single B-Tree node. Holds up to MAX_KEYS sorted keys/values and,
     * if not a leaf, up to MAX_CHILDREN child pointers.
     */
    @SuppressWarnings("unchecked")
    private class BTreeNode {
        K[] keys;
        V[] values;
        BTreeNode[] children;
        int keyCount;   // how many keys are currently used
        boolean isLeaf;

        BTreeNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = (K[]) new Comparable[MAX_KEYS];
            this.values = (V[]) new Object[MAX_KEYS];
            this.children = new CustomBTree.BTreeNode[MAX_CHILDREN];
            this.keyCount = 0;
        }
    }

    public CustomBTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    // ---------- INSERT ----------

    public void insert(K key, V value) {
        if (root == null) {
            root = new BTreeNode(true);
            root.keys[0] = key;
            root.values[0] = value;
            root.keyCount = 1;
            size++;
            return;
        }

        // Check for existing key first (overwrite instead of duplicate insert)
        if (searchNode(root, key) != null) {
            updateValue(root, key, value);
            return;
        }

        if (root.keyCount == MAX_KEYS) {
            // Root is full: it must split, which grows the tree's height by 1.
            BTreeNode newRoot = new BTreeNode(false);
            newRoot.children[0] = root;
            splitChild(newRoot, 0);
            root = newRoot;
        }

        insertNonFull(root, key, value);
        size++;
    }

    private void updateValue(BTreeNode node, K key, V value) {
        int i = 0;
        while (i < node.keyCount && key.compareTo(node.keys[i]) > 0) {
            i++;
        }
        if (i < node.keyCount && key.compareTo(node.keys[i]) == 0) {
            node.values[i] = value;
            return;
        }
        updateValue(node.children[i], key, value);
    }

    /**
     * Inserts into a node that is guaranteed NOT to be full.
     * If a child we need to descend into IS full, we split it first.
     */
    private void insertNonFull(BTreeNode node, K key, V value) {
        int i = node.keyCount - 1;

        if (node.isLeaf) {
            // Shift keys/values right to make room, then insert in sorted position
            while (i >= 0 && key.compareTo(node.keys[i]) < 0) {
                node.keys[i + 1] = node.keys[i];
                node.values[i + 1] = node.values[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.values[i + 1] = value;
            node.keyCount++;
        } else {
            // Find which child to descend into
            while (i >= 0 && key.compareTo(node.keys[i]) < 0) {
                i--;
            }
            i++;

            if (node.children[i].keyCount == MAX_KEYS) {
                splitChild(node, i);
                if (key.compareTo(node.keys[i]) > 0) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key, value);
        }
    }

    /**
     * Splits the full child at index i of the given parent node.
     * The middle key of the full child moves UP into the parent.
     * The child's remaining keys are divided into two nodes: left and right.
     */
    private void splitChild(BTreeNode parent, int i) {
        BTreeNode fullChild = parent.children[i];
        BTreeNode newRightNode = new BTreeNode(fullChild.isLeaf);

        int mid = MIN_DEGREE - 1; // index of the middle key (e.g. index 2 when MAX_KEYS=5)

        // Copy the keys/values AFTER the middle into the new right node
        newRightNode.keyCount = MIN_DEGREE - 1;
        for (int j = 0; j < MIN_DEGREE - 1; j++) {
            newRightNode.keys[j] = fullChild.keys[mid + 1 + j];
            newRightNode.values[j] = fullChild.values[mid + 1 + j];
        }

        // If not a leaf, also move the corresponding children pointers
        if (!fullChild.isLeaf) {
            for (int j = 0; j < MIN_DEGREE; j++) {
                newRightNode.children[j] = fullChild.children[mid + 1 + j];
            }
        }

        K middleKey = fullChild.keys[mid];
        V middleValue = fullChild.values[mid];

        fullChild.keyCount = mid; // left node keeps only the first `mid` keys

        // Shift parent's children right to make room for the new right node
        for (int j = parent.keyCount; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = newRightNode;

        // Shift parent's keys right to make room for the middle key moving up
        for (int j = parent.keyCount - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
            parent.values[j + 1] = parent.values[j];
        }
        parent.keys[i] = middleKey;
        parent.values[i] = middleValue;
        parent.keyCount++;
    }

    // ---------- SEARCH ----------

    public V search(K key) {
        return searchNode(root, key);
    }

    private V searchNode(BTreeNode node, K key) {
        if (node == null) {
            return null;
        }

        int i = 0;
        while (i < node.keyCount && key.compareTo(node.keys[i]) > 0) {
            i++;
        }

        if (i < node.keyCount && key.compareTo(node.keys[i]) == 0) {
            return node.values[i]; // found
        }

        if (node.isLeaf) {
            return null; // not found, nowhere left to go
        }

        return searchNode(node.children[i], key);
    }

    // ---------- TRAVERSAL ----------

    public CustomList<K> inorderTraversal() {
        CustomList<K> result = new CustomDynamicArray<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(BTreeNode node, CustomList<K> result) {
        if (node == null) return;

        int i;
        for (i = 0; i < node.keyCount; i++) {
            if (!node.isLeaf) {
                inorderRecursive(node.children[i], result);
            }
            result.add(node.keys[i]);
        }
        if (!node.isLeaf) {
            inorderRecursive(node.children[i], result);
        }
    }

    // ---------- HEIGHT ----------

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(BTreeNode node) {
        if (node == null) {
            return -1;
        }
        if (node.isLeaf) {
            return 0;
        }
        return 1 + heightRecursive(node.children[0]);
    }

    // ---------- HELPERS FOR EVIDENCE / TESTS ----------

    public int getMinDegree() {
        return MIN_DEGREE;
    }

    /** Returns the number of keys stored in the root node (useful to observe splitting). */
    public int getRootKeyCount() {
        return root == null ? 0 : root.keyCount;
    }
}