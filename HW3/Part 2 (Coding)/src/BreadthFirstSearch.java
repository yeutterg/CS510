import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * BreadthFirstSearch.java: Apply breadth first search algorithm to find solution of legal moves
 */

public class BreadthFirstSearch {

    public static void main(String[] args) {
        // Initialize search
        boolean goalReached = SearchGeneration.initializeSearch(args[0]);

        // Loop through search until goal reached
        while (!goalReached) {

            // Check if frontier empty. If it is, exit.
            if (SearchGeneration.frontier.isEmpty()) {
                System.out.println("Frontier empty. Execution halted.");
                break;
            }

            // Pop node from frontier (FIFO) and assign as currentNode
            SearchGeneration.fifoPop();

            // Add the positions of currentNode to explored
            SearchGeneration.addCurrentToExplored();

            // Iterate through all possible actions for the current node
            ArrayList<Move> actions = SearchGeneration.currentNode.getState().getAllPossibleMoves();
            for (Move action : actions) {

                // Generate a child node with action applied
                SearchGeneration.childNode = MoveGeneration.applyMoveToNode(SearchGeneration.currentNode, action);
                int[][] childPos = SearchGeneration.childNode.getState().getPositions();

                // Check if positions array of child node in frontier or explored
                boolean inFrontierExplored = false;

                for (Node thisNode : SearchGeneration.frontier) {
                    if (StateGeneration.comparePositions(childPos, thisNode.getState().getPositions())) {
                        inFrontierExplored = true;
                        break;
                    }
                }

                if (!inFrontierExplored) {
                    for (int[][] pos : SearchGeneration.explored) {
                        if (StateGeneration.comparePositions(childPos, pos)) {
                            inFrontierExplored = true;
                            break;
                        }
                    }
                }

                // If child state not in explored or frontier
                if(!inFrontierExplored) {

                    // Perform goal test
                    if (StateGeneration.checkPuzzleComplete(SearchGeneration.childNode.getState())) {
                        SearchGeneration.printSolution(SearchGeneration.childNode);
                        goalReached = true;
                        break;
                    }

                    // If goal not reached, add the child node to the frontier
                    SearchGeneration.frontier.add(SearchGeneration.childNode);
                }
            }
        }
        System.out.println("Search terminated.");
    }
}
