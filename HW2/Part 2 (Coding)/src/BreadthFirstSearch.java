import java.util.Arrays;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * BreadthFirstSearch.java: Apply breadth first search algorithm to find solution of legal moves
 */

public class BreadthFirstSearch {

    private static int[][] currentNodePositions;

    public static void main(String[] args) {
        // Load in a state
        State newState = StateGeneration.loadState(args[0]);

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
            currentNodePositions = SearchGeneration.currentNode.getState().getPositions();

            // Add current node state to explored set
            SearchGeneration.explored.add(currentNodePositions);

            // Iterate through all possible moves in current node/state
            for (Move currentMove : SearchGeneration.currentNode.getState().getAllPossibleMoves()) {

                // Get the child node for the specified move
                SearchGeneration.currentChildNode = SearchGeneration.childNode(SearchGeneration.currentNode,
                        currentMove);

                // Check if the child node is in explored
                boolean inExploredFrontier = false;
                for (int i = 0; i < SearchGeneration.explored.size(); i++) {
                    int [][] exploredPositions = SearchGeneration.explored.get(i);
                    if (StateGeneration.compareStates(currentNodePositions, exploredPositions)) {
                        inExploredFrontier = true;
                        break;
                    }
                }

                // Check if child node is in frontier
                if (!inExploredFrontier) {
                    for (int i = 0; i < SearchGeneration.frontier.size(); i++) {
                        int [][] frontierPositions = SearchGeneration.frontier.get(i).getState().getPositions();
                        if (StateGeneration.compareStates(currentNodePositions, frontierPositions)) {
                            inExploredFrontier = true;
                            break;
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
