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
        // Solve the sliding blocks puzzle using a breadth-first strategy
        // Queue order is FIFO

        // Initialize search and perform initial goal test
        boolean goalReached = false;
        if (SearchGeneration.initializeSearch(givenState)) {
            goalReached = true; // if initial goal test is positive
        }

        // Loop through search until goal reached
        while (!goalReached) {

            // Check if frontier empty
            if (SearchGeneration.frontier.isEmpty()) {
                System.out.println("Execution halted. Frontier is empty.");
                break; // if frontier empty, break out of loop
            }

            // Choose shallowest node in frontier through "pop"
            SearchGeneration.currentNode = SearchGeneration.fifoPop(SearchGeneration.frontier);

            // Add node state to explored
            SearchGeneration.explored.add(SearchGeneration.currentNode.currentState);

            // Iterate through all moves in the current node
            for (Move currentMove : SearchGeneration.currentNode.getState().getAllPossibleMoves()) {

                // Initialize current child node
                Node currentChildNode = SearchGeneration.childNode(SearchGeneration.currentNode, currentMove);

                // Check if the state of child is not in explored
                boolean inExplored = false;
                for (State thisState : SearchGeneration.explored) {
                    if (thisState.getPositions() == currentChildNode.getState().getPositions()) {
                        inExplored = true;
                        break;
                    }
                }

                // Also check if state of child is not in frontier
                boolean inFrontier = false;
                for (Node thisNode : SearchGeneration.frontier) {
                    if (thisNode.getState().getPositions() == currentChildNode.getState().getPositions()) {
                        inFrontier = true;
                        break;
                    }
                }

                // If not in explored or frontier see if goal reached. If not goal, add to frontier
                if (!inFrontier && !inExplored) {

                    // Perform goal test on child state and return solution if goal reached
                    if (StateGeneration.checkPuzzleComplete(currentChildNode.getState())) {
                        SearchGeneration.printSolution(SearchGeneration.currentNode);
                        goalReached =  true; // if goal reached, instruct search class to exit
                        break;
                    }

                    // Insert child node into frontier
                    SearchGeneration.frontier.add(currentChildNode);
                }
            }
        }




    }
}
