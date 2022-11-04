package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.Algorithms;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SearchTests {

    public Graph<String> g1 = GraphAssembler.assembler("datasets/g1.txt");
    public Graph<String> g2 = GraphAssembler.assembler("datasets/g2.txt");
    public Graph<String> g3 = GraphAssembler.assembler("datasets/g3.txt");
    public Graph<String> g4 = GraphAssembler.assembler("datasets/g4.txt");

    @Test
    public void bfsG1() {

        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 2, 3, 4, 5, 6};
        int[] list1 = {1, 3, 4};
        int[] list2 = {2, 5, 6, 0, 1, 3, 4};
        int[] list3 = {3};
        int[] list4 = {4};
        int[] list5 = {5};
        int[] list6 = {6, 0, 1, 2, 3, 4, 5};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);
        lists.add(list6);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.breadthFirstSearch(g1);
        assertEquals(expected, output);
    }

    @Test
    public void dfsG1() {

        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 3, 4, 2, 5, 6};
        int[] list1 = {1, 3, 4};
        int[] list2 = {2, 5, 6, 0, 1, 3, 4};
        int[] list3 = {3};
        int[] list4 = {4};
        int[] list5 = {5};
        int[] list6 = {6, 0, 1, 3, 4, 2, 5};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);
        lists.add(list6);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.depthFirstSearch(g1);
        assertEquals(expected, output);
    }

    @Test
    public void bfsG2() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1};
        int[] list1 = {1, 0};

        lists.add(list0);
        lists.add(list1);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.breadthFirstSearch(g2);
        assertEquals(expected, output);
    }

    @Test
    public void dfsG2() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1};
        int[] list1 = {1, 0};

        lists.add(list0);
        lists.add(list1);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.depthFirstSearch(g2);
        assertEquals(expected, output);
    }

    @Test
    public void bfsG3() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 2, 3};
        int[] list1 = {1, 2, 3, 0};
        int[] list2 = {2, 3, 0, 1};
        int[] list3 = {3, 0, 1, 2};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.breadthFirstSearch(g3);
        assertEquals(expected, output);
    }

    @Test
    public void dfsG3() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 2, 3};
        int[] list1 = {1, 2, 3, 0};
        int[] list2 = {2, 3, 0, 1};
        int[] list3 = {3, 0, 1, 2};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.depthFirstSearch(g3);
        assertEquals(expected, output);
    }

    @Test
    public void bfsG4() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 2, 3};
        int[] list1 = {1, 2, 3, 0};
        int[] list2 = {2, 3, 0, 1};
        int[] list3 = {3, 0, 1, 2};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.breadthFirstSearch(g4);
        assertEquals(expected, output);
    }

    @Test
    public void dfsG4() {
        Set<List<Vertex<String>>> expected = new HashSet<>();
        ArrayList<int[]> lists = new ArrayList<>();

        int[] list0 = {0, 1, 2, 3};
        int[] list1 = {1, 2, 3, 0};
        int[] list2 = {2, 3, 0, 1};
        int[] list3 = {3, 0, 1, 2};

        lists.add(list0);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        for (int[] tempList : lists) {
            List<Vertex<String>> arrayList = new ArrayList<>();
            for (int tempEntry : tempList) {
                arrayList.add(new Vertex<>("" + tempEntry, "" + tempEntry));
            }
            expected.add(arrayList);
        }

        Set<List<Vertex<String>>> output = Algorithms.breadthFirstSearch(g4);
        assertEquals(expected, output);
    }

}
