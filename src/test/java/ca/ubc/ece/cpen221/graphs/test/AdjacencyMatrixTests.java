package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class AdjacencyMatrixTests {
    Vertex<String> v1;
    Vertex<String> v2;
    Vertex<Character> v3;
    Vertex<Integer> v4;

    Graph g1;

    @Before
    public void initialize() {
        v1 = new Vertex<>("v1", "test");
        v2 = new Vertex<>("V2", "yike");
        v3 = new Vertex<>("v3", 'f');
        v4 = new Vertex<>("a7", 69);

        g1 = new AdjacencyMatrixGraph();

        g1.addVertex(v1);
        g1.addVertex(v2);
        g1.addVertex(v3);
        g1.addVertex(v4);

        g1.addEdge(v1, v2);
        g1.addEdge(v1, v3);
        g1.addEdge(v3, v2);

    }

    @Test
    public void vertexListConstructor() {
        Vertex v1 = new Vertex<>("v1", "test");
        Vertex v2 = new Vertex<>("V2", "yike");
        Vertex v3 = new Vertex<>("v3", 'f');
        Vertex v4 = new Vertex<>("a7", 69);

        ArrayList<Vertex> vertices = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));
        Graph g2 = new AdjacencyListGraph(vertices);

        g2.addEdge(v1, v2);
        g2.addEdge(v1, v3);
        g2.addEdge(v3, v2);
    }

    @Test
    public void emptyMatrixTest() {
        Graph g1 = new AdjacencyMatrixGraph();
        Graph g2 = new AdjacencyMatrixGraph();

        assertEquals(g1, g2);
    }

    @Test
    public void observerTest() {
        Graph g2 = new AdjacencyMatrixGraph((AdjacencyMatrixGraph) g1);
        g1.edgeExists(v1, v2);
        g1.edgeExists(v2, v3);
        g1.getNeighbors(v3);
        g1.getVertices();

        assertEquals(g1, g2);
    }


    @Test
    public void getVerticesTest() {
        assertEquals(List.of(v2, v4, v1, v3), g1.getVertices());
    }

    @Test
    public void getNeighboursTest() {
        assertEquals(List.of(v2, v3), g1.getNeighbors(v1));
    }

    @Test
    public void getNeighboursEmptyTest() {
        assertTrue(g1.getNeighbors(v4).isEmpty());
    }

    @Test
    public void edgeExistsTest() {
        assertTrue(g1.edgeExists(v3, v2) && g1.edgeExists(v1, v3) && !g1.edgeExists(v2, v1));
    }

}
