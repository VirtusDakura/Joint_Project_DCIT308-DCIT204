package org.ug.dsa.datastructures;

/**
 * Custom Disjoint Set (Union-Find) with path compression and union by rank.
 * Used by Kruskal's MST algorithm to detect cycles during edge selection.
 */
public class CustomDisjointSet {

    private int[] parent;
    private int[] rank;
    private int componentCount;

    /**
     * Creates a disjoint set with n elements, indices 0..n-1,
     * each initially its own set (calls makeSet for each element).
     */
    public CustomDisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        componentCount = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Adds a new singleton set containing x.
     * x must be a valid index within the current capacity.
     */
    public void makeSet(int x) {
        validate(x);
        for (int i = 0; i < parent.length; i++) {
            if (i != x && parent[i] == x) {
                parent[i] = i;
            }
        }
        parent[x] = x;
        rank[x] = 0;
    }

    /**
     * Finds the representative (root) of the set containing x,
     * compressing the path so every visited node points directly to the root.
     */
    public int find(int x) {
        validate(x);
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x];
    }

    /**
     * Unions the sets containing x and y, attaching the shorter tree
     * under the taller tree (union by rank).
     * Returns true if a union happened, false if x and y were already
     * in the same set (no-op — this is how Kruskal detects a cycle).
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false; // already connected -> would form a cycle
        }

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        componentCount--;
        return true;
    }

    /**
     * Checks whether x and y currently belong to the same set.
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    /**
     * Returns the number of disjoint sets remaining.
     */
    public int getComponentCount() {
        return componentCount;
    }

    private void validate(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Index out of bounds: " + x);
        }
    }
}