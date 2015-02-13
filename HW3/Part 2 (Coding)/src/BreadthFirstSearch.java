import java.util.List;

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

            // debug
            System.out.println("parent node");
            StateGeneration.displayState(SearchGeneration.currentNode.getState());

            // Check if frontier empty. If it is, exit.
            if (SearchGeneration.frontier.isEmpty()) {
                System.out.println("Frontier empty. Execution halted.");
                break;
            }

            // Pop node from frontier (FIFO) and assign as currentNode
            SearchGeneration.fifoPop();

            // Add the positions of currentNode to explored
            SearchGeneration.addCurrentToExplored();

            // debug
            System.out.println("possible parent actions:");
            for (Move act : SearchGeneration.currentNode.getState().getAllPossibleMoves()) {
                System.out.println(act.getPieceNum() + "," + act.getMoveId());
            }

            // Iterate through all possible actions for the current node
            List<Move> actions = SearchGeneration.currentNode.getState().getAllPossibleMoves();
            for (Move action : actions) {

                // Generate a child node with action applied
                SearchGeneration.childNode = NodeGeneration.cloneNode(MoveGeneration.applyMoveToNode(
                        SearchGeneration.currentNode, action));
                int[][] childPos = SearchGeneration.childNode.getState().getPositions();

                // debug
                System.out.println("child node");
                StateGeneration.displayState(SearchGeneration.childNode.getState());

                // Check if positions array of child node in frontier or explored
                boolean inFrontierExplored = false;

                for (Node thisNode : SearchGeneration.frontier) {
                    if (StateGeneration.comparePositions(childPos, thisNode.getState().getPositions())) {
                        inFrontierExplored = true;
                        System.out.println("in frontier");
                        break;
                    }
                }

                if (!inFrontierExplored) {
                    for (int[][] pos : SearchGeneration.explored) {
                        System.out.println("explored:");
                        for (int h = 0; h < StateGeneration.height; h++) {
                            for (int w = 0; w < StateGeneration.width; w++) {
                                System.out.print(pos[h][w] + ",");
                            }
                            System.out.print("\n");
                        }

                        if (StateGeneration.comparePositions(childPos, pos)) {
                            inFrontierExplored = true;
                            System.out.println("in explored");
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
