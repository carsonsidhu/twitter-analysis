package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.Comparator;

/**
 * Compares the labels of two vertices lexicographically
 *
 * @param <T> The type stored as content in the vertices being compared
 */
public class LexicographicComparator<T> implements Comparator<Vertex<T>> {

    /**
     * @param a The first vertex to compare
     * @param b The second vertex to compare
     * @return the lexicographic similarity of a compared to b
     */
    public int compare(Vertex<T> a, Vertex<T> b) {
        return a.getLabel().compareTo(b.getLabel());
    }
}
