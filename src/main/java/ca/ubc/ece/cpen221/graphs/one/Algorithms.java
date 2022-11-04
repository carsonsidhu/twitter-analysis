package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Algorithms {

    /**
     * *********************** Algorithms ****************************
     *
     * Please see the README for this machine problem for a more detailed
     * specification of the behavior of each method that one should
     * implement.
     */

    // Reference: http://cseweb.ucsd.edu/~kube/cls/100/Lectures/lec12/lec12.pdf

    /**
     * Finds the shortest distance from vertex a to vertex b within a graph.
     * Distance is defined as the minimum number of edges that must be traversed to get from
     * one vertex to another
     *
     * @param graph graph the graph that contains the vertices
     * @param a     the vertex to start at
     *              must be in graph
     * @param b     the vertex to end at
     *              must be in graph
     * @param <T>   the type of elements in the graph and vertices
     * @return the shortest distance from a to b as defined above
     * if there is no possible path from a to b, returns infinity Integer.MAX_VALUE
     */
    public static <T> int distance(Graph<T> graph, Vertex<T> a, Vertex<T> b){
        // Note that this method can be invoked as follows:
        //      Algorithms.<String>shortestDistance(g, a, b)
        // when the graph contains vertices of type Vertex<String>.
        // The compiler can also perform type inference so that we can simply use:
        //      Algorithms.shortestDistance(g, a, b)

        if (a.equals(b)) {
            return 0;
        }

        int tierSize = 1;
        int d = 1;
        ArrayDeque<Vertex<T>> vertexQueue = new ArrayDeque<>();
        HashSet<Vertex<T>> checkedV = new HashSet<>();
        vertexQueue.add(a);
        checkedV.add(a);
        int newTierSize;

        while (!vertexQueue.isEmpty()) {
            newTierSize = 0;
            for (int i = 0; i < tierSize; i++) {

                Vertex<T> currentV = vertexQueue.poll();

                List<Vertex<T>> currentNeighbours = graph.getNeighbors(currentV);

                if (currentNeighbours.contains(b)) {
                    return d;
                }
                for (Vertex<T> v : currentNeighbours) {
                    if (!checkedV.contains(v)) {
                        vertexQueue.add(v);
                        newTierSize++;
                        checkedV.add(currentV);
                    }
                }
            }
            d++;
            tierSize = newTierSize;
        }

        throw new NoPathException("No path between vertex a and b");
    }


    /**
     * Perform a complete depth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a DFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param graph the graph to search
     * @param <T>   the type of the contents of the vertices of the graph
     * @return A set of lists, each of which is the depth-first search
     * starting at one of the vertices
     */
    public static <T> Set<List<Vertex<T>>> depthFirstSearch(Graph<T> graph) {
        List<Vertex<T>> allVerticies = graph.getVertices();
        Set<List<Vertex<T>>> output = new HashSet<>();
        for (Vertex<T> headVertex : allVerticies) {
            Set<Vertex<T>> addedVerticies = new HashSet<>();
            output.add(dfsAtHead(graph, headVertex, addedVerticies));
        }
        return output;
    }

    /**
     * @param graph         The graph to search
     * @param head          The vertex to start from
     * @param addedVertices Any vertices which have already been accounted for and should be ignored
     * @param <T>           The type of the contents of the vertices of the graph
     * @return A list of vertices representing the depth-first search from the head, ignoring already
     * added vertices
     */
    private static <T> List<Vertex<T>> dfsAtHead(Graph<T> graph, Vertex<T> head,
                                                 Set<Vertex<T>> addedVertices) {
        List<Vertex<T>> neighbors = new ArrayList<>(graph.getNeighbors(head));
        List<Vertex<T>> output = new ArrayList<>();
        if (!addedVertices.contains(head)) {
            output.add(head);
            addedVertices.add(head);
        }
        if (neighbors.size() == 0) {
            return output;
        }
        neighbors.sort(new LexicographicComparator<T>());
        for (Vertex<T> neighbor : neighbors) {
            if (!addedVertices.contains(neighbor)) {
                List<Vertex<T>> subGraph = dfsAtHead(graph, neighbor, addedVertices);
                output.addAll(subGraph);
                addedVertices.addAll(subGraph);
            }
        }
        return output;
    }

    /**
     * Perform a complete breadth first search of the given
     * graph. Start with the search at each vertex of the
     * graph and create a list of the vertices visited.
     * Return a set where each element of the set is a list
     * of elements seen by starting a BFS at a specific
     * vertex of the graph (the number of elements in the
     * returned set should correspond to the number of graph
     * vertices).
     *
     * @param graph The graph to search
     * @param <T>   The type of the contents stored in the vertices of the graph
     * @return A set of lists, each of which is the breadth-first search
     * starting at one of the vertices
     */
    public static <T> Set<List<Vertex<T>>> breadthFirstSearch(Graph<T> graph) {
        List<Vertex<T>> allVerticies = graph.getVertices();
        Set<List<Vertex<T>>> output = new HashSet<>();
        for (Vertex<T> headVertex : allVerticies) {
            List<Vertex<T>> queue = new LinkedList<>();
            List<Vertex<T>> listForHead = new ArrayList<>();
            listForHead.add(headVertex);
            queue.add(headVertex);
            boolean done = false;
            while (!done) {
                done = true;
                List<Vertex<T>> possibleNeighbors = new ArrayList<>();
                for (Vertex<T> queuedVertex : queue) {
                    possibleNeighbors.addAll(graph.getNeighbors(queuedVertex));
                }
                queue.clear();
                for (Vertex<T> possibleNeighbor : possibleNeighbors) {
                    if (!listForHead.contains(possibleNeighbor)) {
                        queue.add(possibleNeighbor);
                        listForHead.add(possibleNeighbor);
                        done = false;
                    }
                }
                queue.sort(new LexicographicComparator<T>());
            }
            output.add(listForHead);
        }
        return output;
    }

    /**
     * Computes the diameter of a graph, defined as the maximum finite distance among pairs of vertices.
     * If there are no finite distances between vertices then the diameter of the graph is infinite.
     *
     * @param graph the graph to find the diameter of
     *              is not null or empty
     * @param <T>   the type of elements in the graph and vertices
     * @return the diameter of the graph as defined above
     * if there are no finite distances between vertices returns Integer.MAX_VALUE
     * to represent that the graph diameter is infinite
     */
    public static <T> int diameter(Graph<T> graph) {

        if (graph.getVertices().size() == 1) {
            return 0;
        }

        Set<List<Vertex<T>>> bFResults = breadthFirstSearch(graph);
        int diameter = -1;

        for (List<Vertex<T>> l : bFResults) {
            int currentD = -1;

            try {
                currentD = distance(graph, l.get(0), l.get(l.size() - 1));
            } catch (NoPathException e) {

            }

            diameter = Math.max(diameter, currentD);
        }
        if (diameter == 0) {
            return Integer.MAX_VALUE;
        }
        return diameter;
    }

    /**
     * Given a graph G containing two distinct vertices a and b, this method returns a list of all vertices
     * u such that there is an edge from both u to a and u to b
     *
     * @param G   the graph we are checking for edges within
     * @param a   a vertex within graph G which cannot be equal to b
     * @param b   a vertex within graph G which cannot be equal to a
     * @param <T> the type of elements in the graph and vertex
     * @return a list of all vertices u such that there is an edge from both u to a and u to b
     */
    public static <T> List<Vertex<T>> commonUpstreamVertices(Graph<T> G, Vertex<T> a, Vertex<T> b)
        throws IllegalArgumentException {
        if (a == b) {
            throw new IllegalArgumentException("a and b cannot be the same vertex!");
        }

        List<Vertex<T>> upstreamVertices = new ArrayList<>();
        List<Vertex<T>> sortedList = G.getVertices();

        for (int i = 0; i < sortedList.size(); i++) {
            if (G.edgeExists(sortedList.get(i), a) && G.edgeExists(sortedList.get(i), b)) {
                upstreamVertices.add(sortedList.get(i));
            }
        }

        return upstreamVertices;
    }

    /**
     * Given a graph G containing two distinct vertices a and b, this method returns a list of all vertices
     * u such that there is an edge from both a to u and b to u
     *
     * @param G   the graph which we are checking for edges within
     * @param a   a vertex within graph G which cannot be equal to b
     * @param b   a vertex within graph G which cannot be equal to a
     * @param <T> the type of elements in the graph and vertex
     * @return a list of all vertices u such that there is an edge from both a to u and b to u
     */
    public static <T> List<Vertex<T>> commonDownstreamVertices(Graph<T> G, Vertex<T> a, Vertex<T> b)
        throws IllegalArgumentException {
        if (a == b) {
            throw new IllegalArgumentException("a and b cannot be the same vertex!");
        }

        List<Vertex<T>> downstreamVertices = new ArrayList<>();
        List<Vertex<T>> sortedList = G.getVertices();

        for (int i = 0; i < sortedList.size(); i++) {
            if (G.edgeExists(a, sortedList.get(i)) && G.edgeExists(b, sortedList.get(i))) {
                downstreamVertices.add(sortedList.get(i));
            }
        }

        return downstreamVertices;
    }
}
