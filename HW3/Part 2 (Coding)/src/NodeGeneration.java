import java.util.ArrayList;

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
        return new Node.Builder(initState).build();
    }

    public static Node generateNewNode(State nodeState, Node parentNode, ArrayList<Move> action, int pathCost) {
        // Generate a new node

        return new Node.Builder(nodeState).parent(parentNode).action(action).pathCost(pathCost).build();
    }
}