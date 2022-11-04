package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ubc.ece.cpen221.graphs.one.Algorithms.commonDownstreamVertices;
import static org.junit.Assert.*;

public class DownstreamTests {
    public Graph<Integer> g1;
    public Graph<String> g2;
    public Graph<Integer> g3;
    public Graph<Integer> g4;
    public Graph<String> g5;
    public Graph<String> g6;

    @Before
    public void initialize() {
        ArrayList<Vertex<Integer>> vList = new ArrayList<>();
        g1 = new AdjacencyListGraph<>();

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

        g2 = new AdjacencyListGraph<>();

        ArrayList<Vertex<String>> g2Verticies = new ArrayList<>();
        g2Verticies.add(new Vertex<String>("L0", "Contents0"));
        g2Verticies.add(new Vertex<String>("L1", "Contents1"));
        g2.addVertex(g2Verticies.get(0));
        g2.addVertex(g2Verticies.get(1));

        g2.addEdge(g2Verticies.get(0), g2Verticies.get(1));
        g2.addEdge(g2Verticies.get(1), g2Verticies.get(0));

        g3 = new AdjacencyListGraph<>();
        ArrayList<Vertex<Integer>> g3Vertices = new ArrayList<>();
        g3Vertices.add(new Vertex<Integer>("L0", 0));
        g3Vertices.add(new Vertex<Integer>("L1", 1));
        g3Vertices.add(new Vertex<Integer>("L2", 2));
        g3Vertices.add(new Vertex<Integer>("L3", 3));
        g3.addVertex(g3Vertices.get(0));
        g3.addVertex(g3Vertices.get(1));
        g3.addVertex(g3Vertices.get(2));
        g3.addVertex(g3Vertices.get(3));

        g3.addEdge(g3Vertices.get(0), g3Vertices.get(1));
        g3.addEdge(g3Vertices.get(1), g3Vertices.get(2));
        g3.addEdge(g3Vertices.get(2), g3Vertices.get(3));
        g3.addEdge(g3Vertices.get(3), g3Vertices.get(0));

        g4 = new AdjacencyListGraph<>();
        g4.addVertex(g3Vertices.get(0));
        g4.addVertex(g3Vertices.get(1));
        g4.addVertex(g3Vertices.get(2));
        g4.addVertex(g3Vertices.get(3));

        g4.addEdge(g3Vertices.get(0), g3Vertices.get(1));
        g4.addEdge(g3Vertices.get(1), g3Vertices.get(2));
        g4.addEdge(g3Vertices.get(2), g3Vertices.get(3));
        g4.addEdge(g3Vertices.get(3), g3Vertices.get(0));
        g4.addEdge(g3Vertices.get(0), g3Vertices.get(2));
        g4.addEdge(g3Vertices.get(1), g3Vertices.get(3));

        Vertex<String> v1 = new Vertex<>("v1", "test");
        Vertex<String> v2 = new Vertex<>("v2", "yike");
        Vertex<String>v3 = new Vertex<>("v3", "f");
        Vertex<String>v4 = new Vertex<>("v4", "69");
        Vertex<String>v5 = new Vertex<>("v5", "nee");
        Vertex<String>v6 = new Vertex<>("v6", "Cold War");
        Vertex<String>v7 = new Vertex<>("v7", "is cool");
        Vertex<String>v8 = new Vertex<>("v8", "are hard but rewarding");
        Vertex<String> v9 = new Vertex<>("v9", "many tests");
        Vertex<String>v10 = new Vertex<>("v10", "Where did that bring you? Back to me.");

        g5 = new AdjacencyListGraph<>();

        g5.addVertex(v1);
        g5.addVertex(v2);
        g5.addVertex(v3);
        g5.addVertex(v4);
        g5.addVertex(v5);
        g5.addVertex(v6);
        g5.addVertex(v7);
        g5.addVertex(v8);
        g5.addVertex(v9);
        g5.addVertex(v10);

        g5.addEdge(v1, v2);
        g5.addEdge(v1, v3);
        g5.addEdge(v2, v5);
        g5.addEdge(v3, v2);
        g5.addEdge(v3, v6);
        g5.addEdge(v6, v5);
        g5.addEdge(v6, v9);
        g5.addEdge(v5, v7);
        g5.addEdge(v7, v8);
        g5.addEdge(v8, v1);
        g5.addEdge(v9, v7);
        g5.addEdge(v9, v2);

        for(Vertex<String> v : g5.getVertices()){
            if(v.equals(v10)){

            }else {
                g5.addEdge(v, v10);
            }
        }

        g6 = new AdjacencyListGraph<>();

        g6.addVertex(v1);
        g6.addVertex(v2);
        g6.addVertex(v3);
        g6.addVertex(v4);
        g6.addVertex(v5);
        g6.addVertex(v6);
        g6.addVertex(v7);
        g6.addVertex(v8);
        g6.addVertex(v9);
        g6.addVertex(v10);

        g6.addEdge(v1, v2);
        g6.addEdge(v1, v3);
        g6.addEdge(v2, v5);
        g6.addEdge(v3, v2);
        g6.addEdge(v3, v6);
        g6.addEdge(v6, v5);
        g6.addEdge(v6, v9);
        g6.addEdge(v5, v7);
        g6.addEdge(v7, v8);
        g6.addEdge(v8, v1);
        g6.addEdge(v9, v7);
        g6.addEdge(v9, v2);

        for(Vertex<String> v : g6.getVertices()){
            if(v.equals(v10)){

            }else {
                g6.addEdge(v, v10);
            }
        }
    }

    @Test
    public void emptyListTest() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g5, g5.getVertices().get(0), g5.getVertices().get(1));

        List<Vertex<String>> expectedList = new ArrayList<>();

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void sameVertexTest() {
        boolean thrown = false;
        try {
            List<Vertex<String>> downstreamList =
                commonDownstreamVertices(g5, g5.getVertices().get(0), g5.getVertices().get(0));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void downstreamTest_g4() {
        List<Vertex<Integer>>
            downstreamList = commonDownstreamVertices(g4, g4.getVertices().get(0), g4.getVertices().get(1));

        List<Vertex<Integer>> expectedList = new ArrayList<>();
        expectedList.add(g4.getVertices().get(2));

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void downstreamTest_g5() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g5, g5.getVertices().get(0), g5.getVertices().get(2));

        List<Vertex<String>> expectedList = new ArrayList<>();
        expectedList.add(g5.getVertices().get(1));

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void downstreamTest_g6_1() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g6, g6.getVertices().get(0), g6.getVertices().get(3));

        List<Vertex<String>> expectedList = new ArrayList<>();
        expectedList.add(g6.getVertices().get(1));
        expectedList.add(g6.getVertices().get(2));

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void downstreamTest_g6_2() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g6, g6.getVertices().get(2), g6.getVertices().get(6));

        List<Vertex<String>> expectedList = new ArrayList<>();
        expectedList.add(g6.getVertices().get(1));
        expectedList.add(g6.getVertices().get(5));

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void downstreamTest_g6_3() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g6, g6.getVertices().get(5), g6.getVertices().get(9));

        List<Vertex<String>> expectedList = new ArrayList<>();
        expectedList.add(g6.getVertices().get(1));
        expectedList.add(g6.getVertices().get(7));

        assertEquals(downstreamList, expectedList);
    }

    @Test
    public void downstreamTest_g6_4() {
        List<Vertex<String>> downstreamList = commonDownstreamVertices(g6, g6.getVertices().get(0), g6.getVertices().get(9));

        List<Vertex<String>> expectedList = new ArrayList<>();
        expectedList.add(g6.getVertices().get(1));
        expectedList.add(g6.getVertices().get(2));

        assertEquals(downstreamList, expectedList);
    }

}