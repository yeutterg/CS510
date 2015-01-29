/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * GamePlay.java: Apply various algorithms to play the game using legal moves
 */

public class GamePlay {

    public static void main(String[] args) {
        // Load game state
        StateGeneration.loadGameState("D:/Academic/Winter 2015/CS 510 Artificial Intelligence/Homework/HW1/HW1/SBP-level3.txt");

        // Execute random walks (HW1, 2.F)
        randomWalks(StateGeneration.gameState, 100);

        // Execute breadth-first search (HW1, 2.A)

    }

    public static void randomWalks(State givenState, int N) {
        /*
		 * Given a state and positive integer N:
		 * 1. Generate all possible moves in the board
		 * 2. Select one at random
		 * 3. Execute it
		 * 4. Normalize the resulting game state
		 * 5. If goal reached, or if N moves have been executed, stop
		 * 6. Otherwise, go back to 1
		 */

        // Print initial game state
        StateGeneration.displayGameState();

        for (int i = 0; i < N; i++) {

            // Select a random (possible) move
            Move selectedMove = MoveGeneration.generateRandomMove(givenState);

            // Print the selected move
            System.out.println("\n(" + selectedMove.getPieceNum() + ", " + selectedMove.getMoveId() + ")\n");

            // Apply the selected move
            MoveGeneration.applyMove(givenState, selectedMove);

            // Normalize the resulting game state
            StateGeneration.gameState = StateGeneration.normalizeState(StateGeneration.gameState);

            // Print the current game state
            StateGeneration.displayGameState();

            // Check if goal reached
            if (StateGeneration.checkPuzzleComplete(StateGeneration.gameState)) {
                System.out.println("\nGoal reached.");
                break;
            }
        }

        System.out.println("\nGame terminated.");
    }

    public static void breadthFirstSearch(State givenState) {
        // Given an input state, apply a breadth-first search to solve the puzzle

        // Keep track of states visited so far

    }
}
