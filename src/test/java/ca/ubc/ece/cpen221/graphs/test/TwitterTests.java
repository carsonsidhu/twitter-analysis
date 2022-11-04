package ca.ubc.ece.cpen221.graphs.test;

import ca.ubc.ece.cpen221.graphs.one.TwitterAnalysis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TwitterTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setup(){

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void reset(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test1(){
        String[] args = {"datasets/g1.txt","numRetweets","2","3"};
        TwitterAnalysis.main(args);
        assertEquals("3",outContent.toString().trim());
    }

    @Test
    public void sameUserRT(){
        String[] args = {"datasets/g2.txt","numRetweets","0","0"};
        TwitterAnalysis.main(args);
        assertEquals("The 2 users are the same, a post cannot be retweeted to its author",errContent.toString().trim());
    }

    @Test
    public void noFile(){
        String[] args = {"nonexistentFile.txt","numRetweets","0","0"};
        TwitterAnalysis.main(args);
        assertEquals("Failed to Find Graph File",errContent.toString().trim());
    }

    @Test
    public void noPath(){
        String[] args = {"datasets/g1.txt","numRetweets","3","1"};
        TwitterAnalysis.main(args);
        assertEquals("Cannot Retweet from 3 to 1",errContent.toString().trim());
    }

    @Test
    public void commonInfluencers(){
        String[] args = {"datasets/g6.txt","commonInfluencers","0","1"};
        TwitterAnalysis.main(args);
        assertEquals("4",outContent.toString().trim());
    }

    @Test
    public void commonInfluencers2(){
        String[] args = {"datasets/g5.txt","commonInfluencers","0","1"};
        TwitterAnalysis.main(args);
        assertEquals("34",outContent.toString().replaceAll("(?:\\n|\\r)", ""));
    }
}
