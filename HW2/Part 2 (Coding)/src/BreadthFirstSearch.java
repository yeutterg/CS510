/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * BreadthFirstSearch.java: Apply breadth first search algorithm to find solution of legal moves
 */

public class BreadthFirstSearch {

    public static void main(String fileName) {
        // Load in a state
        State newState = StateGeneration.loadState(fileName);

        // Execute breadth-first search (HW2 2.1)
        breadthFirstProblem(newState);
    }

    public static void breadthFirstProblem(State givenState) {
        // Solve problem via breadth-first search with a FIFO queue

        // Initialize node, explored, and frontier; perform initial goal test
        boolean goalReached = false;
        if (SearchGeneration.initializeSearch(givenState)) {
            goalReached = true;
        }

        // Loop through problem until goal reached
        while (!goalReached) {

            // Check if frontier empty. If it is, exit
            if (SearchGeneration.frontier.isEmpty()) {
                System.out.println("Frontier empty. Execution halted.");
                break;
            }

            // Pop node from FIFO frontier and assign as current
            SearchGeneration.currentNode = SearchGeneration.fifoPop(SearchGeneration.frontier);

            // Add current node state to explored set
            SearchGeneration.explored.add(SearchGeneration.currentNode.getState());

            // Loop through each possible action for the current node
            System.out.println("possible moves " + MoveGeneration.allPossiblePieceMoves(SearchGeneration.currentNode.getState()).size());
            for (Move currentMove : MoveGeneration.allPossiblePieceMoves(SearchGeneration.currentNode.getState())) {
                System.out.println("(" + currentMove.getPieceNum() + ", " + currentMove.getMoveId() + ")");
            }

            for (Move currentMove : SearchGeneration.currentNode.getState().getAllPossibleMoves()) {
                System.out.println("current move: ");
                System.out.println("(" + currentMove.getPieceNum() + ", " + currentMove.getMoveId() + ")");

                // Get the child node for the specified move
                SearchGeneration.currentChildNode = SearchGeneration.childNode(SearchGeneration.currentNode,
                        currentMove);

                // Check if the child node is in explored
                boolean inExploredFrontier = false;
                for (int i = 0; i < SearchGeneration.explored.size(); i++) {
                    if (StateGeneration.compareStates(SearchGeneration.currentChildNode.getState(),
                            SearchGeneration.explored.get(i))) {
                        inExploredFrontier = true;

                        System.out.println("in explored");
                    }
                }

                // Check if child node is in frontier
                if (!inExploredFrontier) {
                    for (int i = 0; i < SearchGeneration.frontier.size(); i++) {
                        if (StateGeneration.compareStates(SearchGeneration.currentChildNode.getState(),
                                SearchGeneration.frontier.get(i).getState())) {
                            inExploredFrontier = true;

                            System.out.println("in frontier");
                        }
                    }
                }

                // If not in explored or frontier, perform goal test on child node
                if (!inExploredFrontier) {

                    System.out.println("a");

                    // If goal reached, print solution and exit
                    if (StateGeneration.checkPuzzleComplete(SearchGeneration.currentChildNode.getState())) {
                        SearchGeneration.printSolution(SearchGeneration.currentChildNode);
                        goalReached = true;
                        break;
                    }

                    // Otherwise, add child node to the frontier
                    SearchGeneration.frontier.add(SearchGeneration.currentChildNode);
                }
            }
        }
    }
}
