package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.graphs.one.NoPathException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static ca.ubc.ece.cpen221.graphs.one.Algorithms.distance;
import static org.junit.Assert.*;

public class DistanceTest {

    Vertex<String> v1;
    Vertex<String> v2;
    Vertex<String> v3;
    Vertex<String> v4;
    Vertex<String> v5;
    Vertex<String> v6;
    Vertex<String> v7;
    Vertex<String> v8;
    Vertex<String> v9;
    Vertex<String> v10;

    Graph<String> g1;

    @Before
    public void initialize() {
        v1 = new Vertex<>("v1", "test");
        v2 = new Vertex<>("V2", "yike");
        v3 = new Vertex<>("v3", "f");
        v4 = new Vertex<>("a7", "69");
        v5 = new Vertex<>("yee", "nee");
        v6 = new Vertex<>("COD", "Cold War");
        v7 = new Vertex<>("zombies", "is cool");
        v8 = new Vertex<>("EEs", "are hard but rewarding");
        v9 = new Vertex<>("so", "many tests");
        v10 = new Vertex<>("final", "Where did that bring you? Back to me.");

        g1 = new AdjacencyListGraph<>();

        g1.addVertex(v1);
        g1.addVertex(v2);
        g1.addVertex(v3);
        g1.addVertex(v4);
        g1.addVertex(v5);
        g1.addVertex(v6);
        g1.addVertex(v7);
        g1.addVertex(v8);
        g1.addVertex(v9);
        g1.addVertex(v10);

        g1.addEdge(v1, v2);
        g1.addEdge(v1, v3);
        g1.addEdge(v2, v5);
        g1.addEdge(v3, v2);
        g1.addEdge(v3, v6);
        g1.addEdge(v6, v5);
        g1.addEdge(v6, v9);
        g1.addEdge(v5, v7);
        g1.addEdge(v7, v8);
        g1.addEdge(v8, v1);
        g1.addEdge(v9, v7);
        g1.addEdge(v9, v2);

        for (Vertex<String> v : g1.getVertices()) {
            if (v.equals(v10)) {

            } else {
                g1.addEdge(v, v10);
            }
        }
    }

    @Test
    public void shortestDistanceTest1() throws NoPathException {
        int expectedDistance = 1;
        assertEquals(expectedDistance, distance(g1, v1, v2));
    }

    @Test(expected = NoPathException.class)
    public void shortestDistanceTest2() throws NoPathException {
        distance(g1, v3, v4);
    }

    @Test
    public void shortestDistanceTest3() throws NoPathException {
        int expectedDistance = 4;
        assertEquals(expectedDistance, distance(g1, v8, v7));
    }

    @Test
    public void shortestDistanceTest4() throws NoPathException {
        int expectedDistance = 4;
        assertEquals(expectedDistance, distance(g1, v1, v8));
    }

    @Test
    public void shortestDistanceTest5() throws NoPathException {
        int expectedDistance = 1;
        assertEquals(expectedDistance, distance(g1, v8, v1));
    }

    @Test
    public void shortestDistanceTest6() throws NoPathException {
        int expectedDistance = 2;
        assertEquals(expectedDistance, distance(g1, v6, v7));
    }

    @Test
    public void shortestDistanceTest7() throws NoPathException {
        int expectedDistance = 5;
        assertEquals(expectedDistance, distance(g1, v9, v6));
    }

    @Test(expected = NoPathException.class)
    public void shortestDistanceTest8() throws NoPathException {
        distance(g1, v10, v1);
    }

    @Test
    public void shortestDistanceTest9() throws NoPathException {
        boolean rightDistance = true;
        for (Vertex<String> v : g1.getVertices()) {
            if (v.equals(v10)) {

            } else {
                if (distance(g1, v, v10) != 1) {
                    rightDistance = false;
                }
            }
        }
        assertTrue(rightDistance);
    }


    @Test
    public void shortestDistanceTest10() throws NoPathException {
        ArrayList<Vertex<Integer>> vList = new ArrayList<>();
        Graph<Integer> g1 = new AdjacencyListGraph<>();

        for (int i = 0; i < 7; i++) {
            Vertex<Integer> temp = new Vertex<>("v" + i, i);
            g1.addVertex(temp);
            vList.add(temp);
        }
        g1.addEdge(vList.get(0), vList.get(1));
        g1.addEdge(vList.get(0), vList.get(2));
        g1.addEdge(vList.get(1), vList.get(3));
        g1.addEdge(vList.get(1), vList.get(4));
        g1.addEdge(vList.get(2), vList.get(5));
        g1.addEdge(vList.get(2), vList.get(6));
        g1.addEdge(vList.get(6), vList.get(0));

        int expectedDistance = 3;

        assertEquals(expectedDistance, distance(g1, vList.get(6), vList.get(3)));
    }

    @Test
    public void shortestDistanceTest11() throws NoPathException {
        Graph<String> g2 = new AdjacencyMatrixGraph<>();

        ArrayList<Vertex<String>> g2Verticies = new ArrayList<>();
        g2Verticies.add(new Vertex<>("L0", "Contents0"));
        g2Verticies.add(new Vertex<>("L1", "Contents1"));
        g2.addVertex(g2Verticies.get(0));
        g2.addVertex(g2Verticies.get(1));

        g2.addEdge(g2Verticies.get(0), g2Verticies.get(1));
        g2.addEdge(g2Verticies.get(1), g2Verticies.get(0));

        int expectedDistance = 1;

        assertEquals(expectedDistance,
            distance(g2, g2Verticies.get(0), g2Verticies.get(1)));
    }


}


