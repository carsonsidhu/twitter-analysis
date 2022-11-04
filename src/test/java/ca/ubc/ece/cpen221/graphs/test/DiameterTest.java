package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.graphs.one.NoPathException;
import org.junit.Test;

import java.util.ArrayList;

import static ca.ubc.ece.cpen221.graphs.one.Algorithms.diameter;
import static ca.ubc.ece.cpen221.graphs.one.Algorithms.distance;
import static org.junit.Assert.*;

public class DiameterTest {

    @Test
    public void diameterTest1() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("v2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("v4", "69");
        Vertex<String> v5 = new Vertex<>("v5", "nee");
        Vertex<String> v6 = new Vertex<>("v6", "Cold War");
        Vertex<String> v7 = new Vertex<>("v7", "is cool");
        Vertex<String> v8 = new Vertex<>("v8", "are hard but rewarding");
        Vertex<String> v9 = new Vertex<>("v9", "many tests");
        Vertex<String> v10 = new Vertex<>("v10", "Where did that bring you? Back to me.");

        Graph<String> g1 = new AdjacencyListGraph<>();

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

        int expectedDiameter = 7;
        assertEquals(expectedDiameter, diameter(g1));
    }

    @Test
    public void diameterTest2() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("v2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("v4", "69");

        Graph<String> g1 = new AdjacencyListGraph<>();

        g1.addVertex(v1);
        g1.addVertex(v2);
        g1.addVertex(v3);
        g1.addVertex(v4);

        g1.addEdge(v1, v2);
        g1.addEdge(v1, v3);
        g1.addEdge(v3, v2);

        int expectedDiameter = 1;
        assertEquals(expectedDiameter, diameter(g1));
    }

    @Test
    public void diameterTest3() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("v2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("v4", "69");

        Graph<String> g1 = new AdjacencyListGraph<>();

        g1.addVertex(v1);
        g1.addVertex(v2);
        g1.addVertex(v3);
        g1.addVertex(v4);

        int expected = Integer.MAX_VALUE;

        assertEquals(expected, diameter(g1));
    }

    @Test
    public void diameterTest4() {
        ArrayList<String> TAs = new ArrayList<>();
        TAs.add("Alberto");
        TAs.add("Victoria");

        Vertex<ArrayList<String>> v1 = new Vertex<>("incomplete TA list", TAs);

        Graph g = new AdjacencyMatrixGraph();
        g.addVertex(v1);

        int expectedDiameter = 0;

        assertEquals(expectedDiameter, diameter(g));
    }

    @Test
    public void diameterTest5() {
        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("v2", "yike");
        Vertex<String> v3 = new Vertex<>("v3", "f");
        Vertex<String> v4 = new Vertex<>("v4", "69");
        Vertex<String> v5 = new Vertex<>("v5", "nee");
        Vertex<String> v6 = new Vertex<>("v6", "Cold War");
        Vertex<String> v7 = new Vertex<>("v7", "is cool");
        Vertex<String> v8 = new Vertex<>("v8", "are hard but rewarding");
        Vertex<String> v9 = new Vertex<>("v9", "many tests");
        Vertex<String> v10 = new Vertex<>("v10", "Where did that bring you? Back to me.");

        Graph<String> g1 = new AdjacencyListGraph<>();

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
        g1.addEdge(v3, v2);
        g1.addEdge(v3, v5);
        g1.addEdge(v5, v6);
        g1.addEdge(v6, v7);
        g1.addEdge(v7, v8);
        g1.addEdge(v8, v9);

        int expectedDiameter = 6;

        assertEquals(expectedDiameter, diameter(g1));
    }

    @Test
    public void DiameterTest6() {
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

        int expectedDiameter = 4;
        assertEquals(expectedDiameter, diameter(g1));
    }

    @Test
    public void DiameterTest7() throws NoPathException {
        Graph<String> g2 = new AdjacencyMatrixGraph<>();

        ArrayList<Vertex<String>> g2Verticies = new ArrayList<>();
        g2Verticies.add(new Vertex<>("L0", "Contents0"));
        g2Verticies.add(new Vertex<>("L1", "Contents1"));
        g2.addVertex(g2Verticies.get(0));
        g2.addVertex(g2Verticies.get(1));

        g2.addEdge(g2Verticies.get(0), g2Verticies.get(1));
        g2.addEdge(g2Verticies.get(1), g2Verticies.get(0));

        int expectedDiameter = 1;

        assertEquals(expectedDiameter,
            distance(g2, g2Verticies.get(0), g2Verticies.get(1)));
    }


}