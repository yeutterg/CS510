import java.util.ArrayList;
import java.util.Arrays;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * SearchGeneration.java: Functions to facilitate search
 */

public class SearchGeneration {

    public static ArrayList<Node> frontier;
    public static ArrayList<int[][]> explored;
    public static Node currentNode;
    public static Node childNode;

    /*
     * Run search
     */

    public static boolean initializeSearch(String fileName) {
        // Start a search problem

        // Set up initial node
        currentNode = NodeGeneration.loadInitialNode(fileName);

        // Perform goal test on initial state
        if (StateGeneration.checkPuzzleComplete(currentNode.getState())) {
            SearchGeneration.printSolution(SearchGeneration.currentNode);
            return true; // if goal reached, instruct search class to exit
        }

        // Apply initial node to frontier, initialize empty explored set
        frontier = new ArrayList<Node>();
        frontier.add(NodeGeneration.cloneNode(currentNode));
        explored = new ArrayList<int[][]>();

        return false;
    }

    /*
     * Print game solution
     */

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

    /*
     * Pop nodes from frontier and assign to currentNode
     */

    public static void lifoPop() {
        // Remove final value in frontier and assign to currentNode
        currentNode = NodeGeneration.cloneNode(frontier.remove(frontier.size() - 1));
    }

    public static void fifoPop() {
        // Remove first value in frontier and assign to currentNode
        currentNode = NodeGeneration.cloneNode(frontier.remove(0));
    }

    /*
     * Handled explored ArrayList
     */

    public static void addCurrentToExplored() {
        explored.add(StateGeneration.clonePositionArray(currentNode.getState().getPositions()));
    }

}