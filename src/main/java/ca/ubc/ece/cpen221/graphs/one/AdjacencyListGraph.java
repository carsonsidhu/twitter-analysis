package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;


import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;


/******************************************************************************
 *  Dependencies: Graph.java Vertex.java
 *
 *  A data type that represents a Graph using Adjacency Lists.
 *
 ******************************************************************************/


public class AdjacencyListGraph<T> implements Graph<T> {

    // AF:
    // Each Vertex in vertexList represents a vertex in the graph
    // Each list of vertices in edgeList represents the vertices that the given
    // vertex has an edge to in the graph
    // edgeList and vertexList are related by insertion order.
    // For example if vertex V is inserted 4th into the graph, it will have index 3 in vertexList,
    // and the list of vertices at index 3 of edgeList is all the vertices that V has an edge to

    //RI: size of vertexList always equals the size of edgeList
    // list of vertices in edgeList for a given vertex cannot contain that given vertex
    // list of vertices in edgeList for a given vertex cannot contain a vertex that is not in the graph
    // list of vertices in edgeList cannot contain duplicates of any vertex i.e. there can only be one
    // edge from one vertex to another

    private final ArrayList<Vertex<T>> vertexList;
    private final ArrayList<ArrayList<Vertex<T>>> edgeList;
    private static final boolean DEBUG = true;

    /**
     * Checks the rep invariant of this graph as defined in the above comment
     */
    private void checkRep() {
        if (DEBUG) {
            assert vertexList.size() == edgeList.size();
            int i = 0;
            for (ArrayList<Vertex<T>> list : edgeList) {
                assert !list.contains(vertexList.get(i));
                i++;
                HashSet<Vertex<T>> vertexSet = new HashSet<>();
                for (Vertex<T> v : list) {
                    assert (vertexList.contains(v));
                    vertexSet.add(v);
                }
                assert (vertexSet.size() == list.size());
            }
        }
    }

    /**
     * Creates an empty AdjacencyListGraph
     */
    public AdjacencyListGraph() {
        this.vertexList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        checkRep();
    }

    /**
     * Creates a copy of an existing AdjacencyListGraph
     *
     * @param a the graph to be copied
     */
    public AdjacencyListGraph(AdjacencyListGraph<T> a) {
        this.vertexList = new ArrayList<>(a.vertexList);
        this.edgeList = new ArrayList<>(a.edgeList);
        checkRep();
    }


    /**
     * Creates a new AdjacencyListGraph from a list of vertices
     *
     * @param vertexList the list of vertices in the graph
     *                   is not null
     */
    public AdjacencyListGraph(List<Vertex<T>> vertexList) {
        this.vertexList = new ArrayList<>(vertexList);

        this.edgeList = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); i++) {
            edgeList.add(new ArrayList<>());
        }
        checkRep();
    }

    /**
     * Creates a new AdjacencyListGraph object given a set of vertices and set of edges
     *
     * @param vertexList a list of the vertices in the graph
     *                   contains no duplicate vertices and is not null
     * @param edgeList   a list of lists of vertices that represents the edges from
     *                   a given vertex. The index of the given vertex must correspond to
     *                   its index in vertexList. Must be the same size as vertexList
     */
    public AdjacencyListGraph(List<Vertex<T>> vertexList, List<List<Vertex<T>>> edgeList) {
        this.vertexList = new ArrayList<>(vertexList);

        this.edgeList = new ArrayList<>(edgeList.size());

        for (List<Vertex<T>> list : edgeList) {
            ArrayList<Vertex<T>> tempList = new ArrayList<>(list);
            this.edgeList.add(tempList);
        }
        checkRep();
    }

    /**
     * Adds a vertex to the graph.
     * <p>
     * Precondition: v is not already a vertex in the graph
     * </p>
     */
    public void addVertex(Vertex<T> v) {
        checkRep();
        if(vertexList.contains(v)){
            throw new IllegalArgumentException("Vertex is already in graph");
        }else {
            vertexList.add(v);
            edgeList.add(new ArrayList<>());
            checkRep();
        }
    }

    /**
     * Adds an edge from v1 to v2.
     * <p>
     * Precondition: v1 and v2 are vertices in the graph
     * </p>
     * If edge between v1 and v2 already exists does nothing
     */
    public void addEdge(Vertex<T> v1, Vertex<T> v2) {
        checkRep();
        if(!edgeExists(v1,v2)) {
            edgeList.get(getIndex(v1)).add(v2);
        }
        checkRep();
    }

    /**
     * Check if there is an edge from v1 to v2.
     * <p>
     * Precondition: v1 and v2 are vertices in the graph
     * </p>
     * <p>
     * Postcondition: return true iff an edge from v1 connects to v2
     * </p>
     */
    public boolean edgeExists(Vertex<T> v1, Vertex<T> v2) {
        checkRep();
        if (edgeList.get(getIndex(v1)).contains(v2)) {
            checkRep();
            return true;
        }

        checkRep();
        return false;
    }

    /**
     * Get an array containing all vertices adjacent to v.
     * <p>
     * Precondition: v is a vertex in the graph
     * </p>
     * <p>
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     * </p>
     */
    public List<Vertex<T>> getNeighbors(Vertex<T> v) {
        checkRep();
        ArrayList<Vertex<T>> edgesV = new ArrayList<>(edgeList.get(getIndex(v)));

        checkRep();
        return Collections.unmodifiableList(edgesV);
    }

    /**
     * Get all vertices in the graph.
     * <p>
     * Postcondition: returns a list containing all vertices in the graph,
     * sorted by label in non-descending order.
     * This method should return a list of size 0 iff the graph has no vertices.
     * </p>
     */
    public List<Vertex<T>> getVertices() {
        checkRep();

        ArrayList<Vertex<T>> sortedList = new ArrayList<>(vertexList);

        LexicographicComparator<T> comparator = new LexicographicComparator<>();
        sortedList.sort(comparator);


        checkRep();

        return sortedList;
    }


    /**
     * Finds the index of a given vertex in the list
     *
     * @param v vertex to find index of
     *          must be in graph
     * @return index of v
     */
    private int getIndex(Vertex<T> v) {
        return vertexList.indexOf(v);
    }


    /**
     * Checks if two instances of AdjacencyListGraph objects are "similar" as defined by
     * observational equality
     *
     * @return true if this AdjacencyListGraph is equal to the obj, as defined by observational
     * equality
     * Otherwise return false
     */
    public boolean similar(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdjacencyListGraph)) {
            return false;
        }
        AdjacencyListGraph<T> other = (AdjacencyListGraph<T>) obj;
        return (edgeList.equals(other.edgeList) && vertexList.equals(other.vertexList));
    }
}
