package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Dependencies: Graph.java Vertex.java
 *
 *  A data type that represents a Graph using Adjacency Matrices.
 *
 ******************************************************************************/

public class AdjacencyMatrixGraph<T> implements Graph<T> {

    // AF:
    // Each Vertex in vertexList represents a vertex in the graph.
    // The adjacency matrix is a square N × N matrix A such that its element Aij is 1
    // when there is an edge from the vertex found at index i in vertexList
    // to the vertex found at index j in vertexList, and 0 when there is no edge.
    // vertexList and adjacencyMatrix are related by insertion order.
    // For example if vertex V is inserted 4th into the graph, it will have index 3 in vertexList,
    // and would occupy row of index 3 in adjacencyMatrix should we make an edge from that vertex
    // to another

    //RI: size of vertexList is always less than or equal to length of adjacencyMatrix
    // All integers corresponding to adjacencyMatrix[i][i] = 0, since a vertex
    // cannot have a directed connection to itself in the context of this MP

    private static final int INITIALSIZE = 1;
    private int[][] adjacencyMatrix;
    private ArrayList<Vertex<T>> vertexList;
    private boolean DEBUG = false;

    private void checkRep() {
        assert vertexList.size() <= adjacencyMatrix.length;

        if (DEBUG) {
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                assert adjacencyMatrix[i][i] == 0;
            }
        }
    }

    /**
     * Creates an empty AdjacencyMatrixGraph object when no parameters are passed
     */
    public AdjacencyMatrixGraph() {
        this.adjacencyMatrix = new int[INITIALSIZE][INITIALSIZE];
        this.vertexList = new ArrayList<>();
        checkRep();
    }

    /**
     * Creates an AdjacencyMatrixGraph object when a pre-existing AdjacencyMatrixGraph is
     * passed as a parameter
     */
    public AdjacencyMatrixGraph(AdjacencyMatrixGraph a) {
        this.adjacencyMatrix = a.adjacencyMatrix;
        this.vertexList = new ArrayList<>(a.vertexList);
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
        vertexList.add(v);
        if (vertexList.size() > adjacencyMatrix.length) {
            adjacencyMatrix = increaseMatrixSize(adjacencyMatrix);
        }
        checkRep();
    }

    /**
     * Adds an edge from v1 to v2.
     * <p>
     * Precondition: v1 and v2 are vertices in the graph
     * </p>
     */
    public void addEdge(Vertex<T> v1, Vertex<T> v2) {
        checkRep();

        int v1Location = getIndex(v1);
        int v2Location = getIndex(v2);

        adjacencyMatrix[v1Location][v2Location] = 1;
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
        int v1Location = 0;
        int v2Location = 0;

        v1Location = getIndex(v1);
        v2Location = getIndex(v2);

        if (adjacencyMatrix[v1Location][v2Location] == 1) {
            checkRep();
            return true;
        } else {
            checkRep();
            return false;
        }
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
        int startPos = getIndex(v);
        List<Vertex<T>> neighbours = new ArrayList<>();
        List<Integer> neighbourPos = new ArrayList<>();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[startPos][i] == 1) {
                neighbourPos.add(i);
            }
        }

        for (int i = 0; i < neighbourPos.size(); i++) {
            neighbours.add(vertexList.get(neighbourPos.get(i)));
        }

        checkRep();
        return neighbours;
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
    private int getIndex(Vertex v) {
        return vertexList.indexOf(v);
    }

    /**
     * Returns a copy of the matrix passed as a parameter, with its size doubled
     *
     * @param matrix a 2D int array wto have its size doubled
     * @return a copy of the 2D int array passed as a parameter, with
     * twice as many rows and columns
     */
    private int[][] increaseMatrixSize(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length * 2][matrix.length * 2];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                newMatrix[row][col] = matrix[row][col];
            }
        }
        return newMatrix;
    }

    /**
     * Checks observational equality strictly of an AdjacencyMatrixGraph
     * @param obj an object of type AdjacencyMatrixGraph
     * @return true  if both AdjacencyMatrixGraphs have the same vertexList
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdjacencyMatrixGraph)) {
            return false;
        }
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return (vertexList.equals(other.vertexList));
    }

}
