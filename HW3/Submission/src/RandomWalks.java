/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * RandomWalks.java: Apply random walks algorithm to play the game using legal moves
 */

public class RandomWalks {

    public static void main(String[] args) {

        // input file
        String inFile = args[0];

        // See if N (depth limit) was defined
        if (args.length > 1) {
            // Execute random walks (HW1, 2.F)
            int N = Integer.parseInt(args[1]);
            randomWalksProblem(inFile, N);
        } else {
            // default size of N = 100
            randomWalksProblem(inFile, 100);
        }
    }

    public static void randomWalksProblem(String inFile, int N) {
        /*
		 * Given a state and positive integer N:
		 * 1. Generate all possible moves in the board
		 * 2. Select one at random
		 * 3. Execute it
		 * 4. Normalize the resulting game state
		 * 5. If goal reached, or if N moves have been executed, stop
		 * 6. Otherwise, go back to 1
		 */

        // Initialize game state
        State gameState = StateGeneration.loadInitialState(inFile);
        int w = gameState.getWidth();
        int h = gameState.getHeight();

        // Print initial state
        StateGeneration.displayState(gameState);

        for (int i = 0; i < N; i++) {

            // Select a random (possible) move
            Move thisMove = MoveGeneration.generateRandomMove(gameState);

            // Print the selected move
            System.out.println("\n(" + thisMove.getPieceNum() + ", " + thisMove.getMoveId() + ")\n");

            // Apply the selected move
            gameState = MoveGeneration.applyMoveToState(gameState, thisMove);

            // Normalize the resulting game state
            gameState.setPositions(StateGeneration.normalizeState(w, h, gameState.getPositions()));

            // Print the current game state
            StateGeneration.displayState(gameState);

            // Check if goal reached
            if (StateGeneration.checkPuzzleComplete(gameState)) {
                System.out.println("\nGoal reached.");
                break;
            }
        }
    }
}
