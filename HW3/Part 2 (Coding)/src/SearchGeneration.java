import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * SearchGeneration.java: Functions to facilitate search
 */

public class SearchGeneration {

    public static Node currentNode;
    public static Node currentChildNode;
    public static int[][] positions;
    public static ArrayList<Node> frontier;
    public static ArrayList<int[][]> explored;

    public static Node childNode(Node parent, Move action) {
        // Given a parent node and a (legal) action, return the child node
        // positions is a hack for now, fix later

        // Add to action
        ArrayList<Move> newAction = parent.getAction();
        newAction.add(action);

        // Increment path cost
        int newPathCost = parent.getPathCost() + 1;

        // Perform move and add to new state
        State newState = new State(MoveGeneration.applyMoveCloning(new State(parent.getState()), action));

        // Normalize state
        newState = StateGeneration.normalizeState(newState);

        // Return the child node
        return new Node(newState, parent, newAction, newPathCost);
    }

    public static boolean initializeSearch(State givenState) {
        // Set up a search problem

        // Normalize State
        State.Builder newStateBuilder = new State.Builder(givenState);
        newState = StateGeneration.normalizeState(givenState);

        // Set initial node to loaded in state with path cost = 0
        currentNode = new Node(newState, null, new ArrayList<Move>(), 0);

        // Perform goal test
        if (StateGeneration.checkPuzzleComplete(givenState)) {
            SearchGeneration.printSolution(SearchGeneration.currentNode);
            return true; // if goal reached, instruct search class to exit
        }

        // Apply initial node to frontier, initialize empty explored set
        frontier = new ArrayList<Node>();
        frontier.add(currentNode);
        explored = new ArrayList<int[][]>();

        return false;
    }

    public static void printSolution(Node givenNode) {
        // If a solution reached, print all moves required to solve state
        // and the final state of the puzzle

        // Print moves
        for (Move currentMove : givenNode.getAction()) {
            System.out.println("(" + currentMove.getPieceNum() + ", " + currentMove.getMoveId() + ")");
        }

        // Print state
        StateGeneration.displayState(givenNode.getState());
    }

    public static Node lifoPop(ArrayList<Node> selectedArrayList) {
        // "pop" an item from selected ArrayList
        // return the value of the last item in the array,
        // and remove it from the array
        return selectedArrayList.remove(selectedArrayList.size() - 1);
    }

    public static Node fifoPop(ArrayList<Node> selectedArrayList) {
        // "pop" an item from selected ArrayList in a reverse-fifo configuration
        // i.e. ArrayList is LIFO, but we take element 0 instead of the last element
        // return the value of the first item in the array,
        // and remove it from the array
        return selectedArrayList.remove(0);
    }


}
