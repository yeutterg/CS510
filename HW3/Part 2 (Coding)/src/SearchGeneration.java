import java.util.*;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * SearchGeneration.java: Functions to facilitate search
 */

public class SearchGeneration {

    public static List<Node> frontier = new ArrayList<Node>();
    public static Set<int[][]> explored = new HashSet<int[][]>();
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

        // Apply initial node to frontier
        frontier.add(NodeGeneration.cloneNode(currentNode));

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
        currentNode = frontier.remove(frontier.size() - 1);
    }

    public static void fifoPop() {
        // Remove first value in frontier and assign to currentNode
        currentNode = frontier.remove(0);
    }

    public static void lowFPop() {
        // Remove the node with the lowest cost function from the frontier and assign it to currentNode

        // Find node in frontier with lowest cost function
        int index = 0;
        for (int i = 0; i < frontier.size(); i++) {
            if (frontier.get(i).getCostFn() < index) {
                index = i;
            }
        }

        // Remove node from frontier and assign to currentNode
        currentNode = frontier.remove(0);
    }

    /*
     * Handle explored List
     */

    public static void addCurrentToExplored() {
        // Add position array of current node to the explored list
        explored.add(StateGeneration.clonePositionArray(currentNode.getState().getPositions()));
    }

    /*
     * Heuristic functions
     */

    public static void computeManhattanHeuristicCost(Node inputNode) {
        // Compute the Manhattan Distance Heuristic and Total Cost for the given node
        inputNode.setHeuristic(manhattanDistance(inputNode.getState()));
        inputNode.setCostFn(inputNode.getPathCost() + inputNode.getHeuristic());
    }


    public static int manhattanDistance(State inputState) {
        // Compute the Manhattan/Grid distance heuristic given a state
        // If piece or goal have more than one position, we take the lowest value

        // Compute all possible distances
        int[][] pos = inputState.getPositions();
        List<Integer> dist = new ArrayList<Integer>();
        for (int[] twoLoc : StateGeneration.currentPieceLocations(pos, 2)) {
            for (int[] goalLoc : StateGeneration.currentPieceLocations(pos, -1)) {
                dist.add(Math.abs(goalLoc[0] - twoLoc[0]) + Math.abs(goalLoc[1] - twoLoc[1]));
            }
        }

        try {
            // Find lowest value in dist and return it
            int minIndex = dist.indexOf(Collections.min(dist));
            return dist.get(minIndex);
        } catch (java.util.NoSuchElementException e) {
            // If there is a NoSuchElementException, we probably reached the goal
            return 0;
        }
    }

}