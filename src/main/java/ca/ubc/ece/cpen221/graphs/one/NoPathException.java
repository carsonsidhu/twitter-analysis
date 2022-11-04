package ca.ubc.ece.cpen221.graphs.one;

/** Exception thrown when there is no valid path between the start vertex
 * and the end vertex in the shortest distance method.
 */


public class NoPathException extends RuntimeException {

    /** Constructs a MalformedExpressionException with the detail message.
     */
    public NoPathException( String message ) {
        super( message );
    }

}
