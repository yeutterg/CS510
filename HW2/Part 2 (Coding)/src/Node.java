import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * Node.java: Constructor for a search node
 */

public class Node {
    public State currentState;
    public Node parent;
    public ArrayList<Move> action;
    public int pathCost;

    public Node(State currentState, Node parent, ArrayList<Move> action, int pathCost) {
        this.currentState = currentState;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }

    public Node(Node givenNode) {
        this.currentState = givenNode.getState();
        this.parent = givenNode.getParent();
        this.action = givenNode.getAction();
        this.pathCost = givenNode.getPathCost();
    }

    public State getState() {
        return currentState;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Move> getAction() {
        return action;
    }

    public int getPathCost() {
        return pathCost;
    }
}
