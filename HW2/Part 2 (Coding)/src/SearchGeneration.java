import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * SearchGeneration.java: Functions to facilitate search
 */

public class SearchGeneration {

    public Node currentNode;
    public ArrayList<Node> frontier;
    public ArrayList<Node> explored;

    public static Node childNode(Node parent, Move action) {
        // Given a parent node and a (legal) action, return the child node

        // Add to action
        ArrayList<Move> newAction = parent.getAction();
        newAction.add(action);

        // Increment path cost
        int newPathCost = parent.getPathCost() + 1;

        // Perform move and add to new state
        State newState = MoveGeneration.applyMoveCloning(parent.getState(), action);

        // Return the child node
        return new Node(newState, parent, newAction, newPathCost);
    }


}
