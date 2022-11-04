package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;
import ca.ubc.ece.cpen221.graphs.one.AdjacencyListGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

public class GraphAssembler {
    public static Graph<String> assembler(String fileName) {
        final Graph<String> graph = new AdjacencyListGraph<>();
        final Map<String, Vertex<String>> vertices = new HashMap<>();
        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            Object[] objLines = reader.lines().toArray();
            String[] lines = new String[objLines.length];
            for (int i = 0; i < lines.length; i++) {
                lines[i] = (String) objLines[i];
            }
            for (String line : lines) {
                String followedLabel = line.substring(0, line.indexOf('-') - 1);
                String followerLabel = line.substring(line.indexOf('-') + 3);

                vertices.putIfAbsent(followedLabel, new Vertex<>(followedLabel, followedLabel));
                vertices.putIfAbsent(followerLabel, new Vertex<>(followerLabel, followerLabel));
            }
            for (Map.Entry<String, Vertex<String>> vertex : vertices.entrySet()) {
                graph.addVertex(vertex.getValue());
            }
            for (String line : lines) {
                String followedLabel = line.substring(0, line.indexOf('-') - 1);
                String followerLabel = line.substring(line.indexOf('-') + 3);

                graph.addEdge(vertices.get(followedLabel), vertices.get(followerLabel));
            }
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(new IOException());
        }
        return graph;
    }
}
