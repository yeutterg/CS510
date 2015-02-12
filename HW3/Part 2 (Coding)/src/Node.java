import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * Node.java: Builder for a search node
 */

public class Node {
    private final State currentState;
    private final Node parent;
    private final ArrayList<Move> action;
    private final int pathCost;

    public static class Builder {
        // Required parameters
        private final State currentState;
        private final ArrayList<Move> action;

        // Optional parameters initialized to default values
        private Node parent = null;
        private int pathCost = 1;

        public Builder(State currentState, ArrayList<Move> action) {
            this.currentState = currentState;
            this.action = action;
        }

        public Builder parent(Node assignedParent) {
            parent = assignedParent;
            return this;
        }

        public Builder pathCost(int val) {
            pathCost = val;
            return this;
        }

        protected State getState() {
            return currentState;
        }

        protected Node getParent() {
            return parent;
        }

        protected ArrayList<Move> getAction() {
            return action;
        }

        protected int getPathCost() {
            return pathCost;
        }

        public Node build() {
            return new Node(this);
        }
    }

    private Node(Builder builder) {
        currentState = builder.currentState;
        parent = builder.parent;
        action = builder.action;
        pathCost = builder.pathCost;
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