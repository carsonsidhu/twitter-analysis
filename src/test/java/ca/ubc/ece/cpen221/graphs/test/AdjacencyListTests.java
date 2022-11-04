package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AdjacencyListTests {
    Vertex<String> v1;
    Vertex<String> v2;
    Vertex<String> v3;
    Vertex<String> v4;

    AdjacencyListGraph<String> g1;

    @Before
    public void initialize() {
        v1 = new Vertex<>("v1", "test");
        v2 = new Vertex<>("V2", "yike");
        v3 = new Vertex<>("v3", "f");
        v4 = new Vertex<>("a7", "69");

        g1 = new AdjacencyListGraph<>();

        g1.addVertex(v1);
        g1.addVertex(v2);
        g1.addVertex(v3);
        g1.addVertex(v4);

        g1.addEdge(v1, v2);
        g1.addEdge(v1, v3);
        g1.addEdge(v3, v2);

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

    @Test
    public void edgeAndVertexListConstructor() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("V2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("a7", "69");

        ArrayList<Vertex<String>> vertices = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));
        List<Vertex<String>> v1Edges = new ArrayList<>(Arrays.asList(v2, v3));
        List<Vertex<String>> v2Edges = new ArrayList<>(Collections.emptyList());
        List<Vertex<String>> v3Edges = new ArrayList<>(Collections.singletonList(v2));
        List<Vertex<String>> v4Edges = new ArrayList<>(Collections.emptyList());

        List<List<Vertex<String>>> edges =
            new ArrayList<>(Arrays.asList(v1Edges, v2Edges, v3Edges, v4Edges));

        AdjacencyListGraph<String> g2 = new AdjacencyListGraph<>(vertices, edges);

        assertTrue(g1.similar(g2));
    }

    @Test
    public void vertexListConstructor() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("V2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("a7", "69");

        ArrayList<Vertex<String>> vertices = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));
        AdjacencyListGraph<String> g2 = new AdjacencyListGraph<>(vertices);

        g2.addEdge(v1, v2);
        g2.addEdge(v1, v3);
        g2.addEdge(v3, v2);

        assertTrue(g1.similar(g2));
    }

    @Test
    public void emptyGraphTest() {
        AdjacencyListGraph<Integer> g1 = new AdjacencyListGraph<>();
        AdjacencyListGraph<Integer> g2 = new AdjacencyListGraph<>();

        assertTrue(g1.similar(g2));
    }

    @Test
    public void observerTest() {
        AdjacencyListGraph<String> g2 = new AdjacencyListGraph<>( g1);
        AdjacencyListGraph g3 = g1;

        g1.edgeExists(v1, v2);
        g1.edgeExists(v2, v3);
        g1.getNeighbors(v3);
        g1.getVertices();

        assertTrue(g1.similar(g2));
        assertTrue(g3.similar(g1));
    }

    @Test
    public void emptyVertexListTest() {
        LinkedList<Vertex<Integer>> vList = new LinkedList<>();

        AdjacencyListGraph<Integer> g1 = new AdjacencyListGraph<>(vList);
        AdjacencyListGraph<Integer> g2 = new AdjacencyListGraph<>();

        assertTrue(g1.similar(g2));
    }


    @Test
    public void emptyVertexListAndEdgeListTest() {
        LinkedList<Vertex<Integer>> vList = new LinkedList<>();
        ArrayList<List<Vertex<Integer>>> eList = new ArrayList<>();

        AdjacencyListGraph<Integer> g1 = new AdjacencyListGraph<>(vList, eList);
        AdjacencyListGraph<Integer> g2 = new AdjacencyListGraph<>();

        assertTrue(g1.similar(g2));
    }

    @Test
    public void doubleEdgeTest(){
        AdjacencyListGraph graph = new AdjacencyListGraph<String>();
        Vertex<String> v1 = new Vertex("1", "1");
        Vertex<String> v2 = new Vertex("2","2");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1,v2);
        graph.addEdge(v1,v2);

        assertTrue(graph.edgeExists(v1,v2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void duplicateVertexTest(){
        AdjacencyListGraph graph = new AdjacencyListGraph<String>();
        Vertex<String> v1 = new Vertex("1", "1");
        graph.addVertex(v1);
        graph.addVertex(v1);
    }

    @Test
    public void differentGraphTypesTest(){
        AdjacencyListGraph<Integer> g1 = new AdjacencyListGraph();
        AdjacencyMatrixGraph<Integer> g2 = new AdjacencyMatrixGraph<>();

        assertFalse(g1.similar(g2));

    }

}
