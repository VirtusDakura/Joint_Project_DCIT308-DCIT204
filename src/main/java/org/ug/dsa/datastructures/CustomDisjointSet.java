package org.ug.dsa.datastructures;

/**
 * Custom Disjoint Set (Union-Find) with path compression and union by rank.
 * Used by Kruskal's MST algorithm to detect cycles during edge selection.
 *
 * Assigned to: Seglah Emmanuel (22144981)
 *
 * Required operations:
 *   - makeSet(int x)
 *   - find(int x)             : With path compression
 *   - union(int x, int y)     : By rank or size
 *   - connected(int x, int y) : Check if two elements are in the same set
 *   - getComponentCount()     : Number of disjoint sets remaining
 *
 * Evidence to produce:
 *   - Kruskal connectivity trace showing union/find calls as edges are processed
 *   - Demonstrate path compression flattening the tree
 *   - Unit tests for single element set, union of two sets, connected components count
 */
public class CustomDisjointSet {

    // TODO: Implement the parent[] and rank[] arrays, and all required methods.
    // Hint: Initially parent[i] = i (each element is its own set).
    //       find() should compress the path to the root.
    //       union() should attach the shorter tree under the taller tree.

}
