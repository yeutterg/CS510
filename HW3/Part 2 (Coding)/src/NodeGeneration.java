import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * NodeGeneration.java: Represent graph search nodes in the sliding block puzzle problem
 */

public class NodeGeneration {

    /*
     * Build or clone (immutable) nodes
     */

    public static Node loadInitialNode(String fileName) {
        // Load, build, and return initial game node
        State initState = StateGeneration.loadInitialState(fileName);
        return new Node(initState);
    }

    public static Node generateNewNode(State nodeState, Node parentNode, List<Move> action, int pathCost) {
        // Generate a new node
        return new Node(nodeState, parentNode, action, pathCost);
    }

    public static Node cloneNode(Node inputNode) {
        return new Node(inputNode);
    }
}
