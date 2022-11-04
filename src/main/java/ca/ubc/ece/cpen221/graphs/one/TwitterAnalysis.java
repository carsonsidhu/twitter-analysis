package ca.ubc.ece.cpen221.graphs.one;

import ca.ubc.ece.cpen221.graphs.core.Graph;
import ca.ubc.ece.cpen221.graphs.core.Vertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An engine to analyze a twitter network given in a text document. It is interacted with from the command line arguments.
 * It can find common influencers or num-retweets for a given graph
 *
 * AF:
 * twitterGraph represents the Twitter network. Vertices logs all of the added vertices.
 *
 * RI:
 * twitterGraph must be a directed single-edge graph
 * vertices must contain the vertices from twitterGraph in no particular order
 */
public class TwitterAnalysis {

    private final Graph<String> twitterGraph = new AdjacencyListGraph<>();
    private final Map<String, Vertex<String>> vertices = new HashMap<>();
    private final BufferedReader reader;
    private static final boolean DEBUG = false;

    private void checkRep(){
        if(DEBUG){
            assert(twitterGraph.getVertices().size()==vertices.size());
            for(Vertex<String> v: twitterGraph.getVertices()){
                assert(vertices.containsKey(v.getLabel()));
            }
        }
    }

    /**
     * Creates a TwitterAnalysis instance from the graph stored in the given file
     * @param fileName the file to read the graph from
     * @throws IOException if the file cannot be found
     */
    public TwitterAnalysis(String fileName) throws IOException {
        try{
            reader = new BufferedReader(new FileReader(fileName));
        }
        catch(FileNotFoundException e){
            throw new IOException("File Not Found");
        }
        checkRep();
    }

    /**
     * Determines the common influencers of two distinct users. They must be different users and in the graph. If
     * they have no common influencers, an empty set is returned
     * @param userA one user
     * @param userB another distinct user
     * @return an unordered set of common influencers to both userA and userB
     */
    public Set<String> commonInfluencers(String userA, String userB) {
        Set<String> output = new HashSet<>();
        for (Vertex<String> temp : Algorithms
            .commonDownstreamVertices(twitterGraph, vertices.get(userA), vertices.get(userB))) {
            output.add(temp.getLabel());
        }
        checkRep();
        return output;
    }

    /**
     * Determines the number of retweets necessary for a tweet from UserA to reach UserB's feed
     * @param userA one user
     * @param userB another distinct user
     * @return the number retweets it would take for a tweet by userA to reach userB's feed
     * @throws NoPathException when there is no possible path for the tweet to UserB
     */
    public int numRetweets(String userA, String userB) throws NoPathException {
        checkRep();
        return Math.max(Algorithms.distance(twitterGraph, vertices.get(userA), vertices.get(userB))-1,0);
    }

    /**
     *
     * @param args the input arguments "fileName, numRetweets/commonInfluencers, UserA, UserB"
     *             Where the first is the filename where the graph is stored,
     *             The second argument is either "numRetweets" or "commonInfluencers" indicating the method to run on the graph
     *             UserA is one user. The source in numRetweets.
     *             UserB is another. The recipient in numRetweets.
     * Output: The numRetweets is output as a single left-aligned integer to the terminal.
     *         The commonInfluencers are printed one ID per line to the terminal.
     *
     * Possible alternate messages on the terminal:
     *             "The 2 users are the same, a post cannot be retweeted to its author" for userA and userB the same for numRetweets
     *             "Failed to Find Graph File" for a missing file.
     *             "Cannot Retweet from " + userA + " to " + userB" for no complete path from A to B to retweet
     */
    public static void main(String[] args) {

        String fileName = args[0];
        boolean isNumRetweets = args[1].equals("numRetweets");
        String userA = args[2];
        String userB = args[3];
        try {
            TwitterAnalysis ta = new TwitterAnalysis(fileName);

            Object[] objLines = ta.reader.lines().toArray();
            String[] lines = new String[objLines.length];
            for(int i=0; i<lines.length; i++){
                lines[i] = (String)objLines[i];
            }
            for (String line : lines) {
                String followedLabel = line.substring(0, line.indexOf('-') - 1);
                String followerLabel = line.substring(line.indexOf('-') + 3);

                Vertex<String> followedVertex = new Vertex<>(followedLabel, followedLabel);
                Vertex<String> followerVertex = new Vertex<>(followerLabel, followerLabel);

                Vertex<String> followedVertexFromMap = ta.vertices.putIfAbsent(followedLabel, followedVertex);
                Vertex<String> followerVertexFromMap = ta.vertices.putIfAbsent(followerLabel, followerVertex);

                if(followedVertexFromMap==null){
                    ta.twitterGraph.addVertex(followedVertex);
                    followedVertexFromMap=followedVertex;
                }
                if(followerVertexFromMap==null){
                    ta.twitterGraph.addVertex(followerVertex);
                    followerVertexFromMap=followerVertex;
                }
                ta.twitterGraph.addEdge(followedVertexFromMap, followerVertexFromMap);
            }
            if (isNumRetweets) {
                if(userA.equals(userB)){
                    System.err.println("The 2 users are the same, a post cannot be retweeted to its author");
                }
                else{
                    System.out.println(ta.numRetweets(userA, userB));
                }
            } else {
                for (String temp : ta.commonInfluencers(userA, userB)) {
                    System.out.println(temp);
                }
            }
            ta.checkRep();
        } catch (IOException e) {
            System.err.println("Failed to Find Graph File");
        } catch (NoPathException e) {
            System.err.println("Cannot Retweet from " + userA + " to " + userB);
        }
    }
}
