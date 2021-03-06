import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * Node.java: Constructor for a search node
 */

public class Node {
    private State currentState;
    private Node parent;
    private List<Move> action;
    private int pathCost;
    private int heuristic;
    private int costFn;

    public State getCurrentState() {
        return currentState;
    }

    public State getState() {
        return currentState;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getCostFn() {
        return costFn;
    }

    public Node getParent() {
        return parent;

    }

    public List<Move> getAction() {
        return action;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setAction(List<Move> action) {
        this.action = action;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void setCostFn(int costFn) {
        this.costFn = costFn;
    }

    public Node(State currentState, Node parent, List<Move> action, int pathCost) {
        this.currentState = currentState;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
        this.heuristic = 0;
        this.costFn = 0;
    }

    public Node(State currentState, Node parent, List<Move> action, int pathCost, int heuristic, int costFn) {
        this.currentState = currentState;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
        this.heuristic = heuristic;
        this.costFn = costFn;
    }

    public Node(State currentState) {
        this.currentState = currentState;
        this.parent = null;
        this.action = new ArrayList<Move>(0);
        this.pathCost = 0;
        this.heuristic = 0;
        this.costFn = 0;
    }

    public Node(Node oldNode) {
        this.currentState = new State(oldNode.currentState);
        this.parent = oldNode.parent;
        this.action = new ArrayList<Move>(oldNode.action);
        this.pathCost = oldNode.pathCost;
        this.heuristic = oldNode.heuristic;
        this.costFn = oldNode.costFn;

    }
}