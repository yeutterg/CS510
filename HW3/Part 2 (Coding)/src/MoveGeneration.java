import java.util.ArrayList;
import java.util.Random;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * MoveGeneration.java: Add nodes to the game by performing valid moves
 */

public class MoveGeneration {

    /*
     * Generate new node with moves applied
     */

    public static Node applyMoveToNode(Node inputNode, Move requestedMove) {
        // Generate a new node by applying requested move

        // Clone inputNode
        Node newNode = NodeGeneration.cloneNode(inputNode);

        // Get state of input node
        State inputState = StateGeneration.cloneState(newNode.getState());

        // Increment path cost
        int newPathCost = newNode.getPathCost() + 1;

        // Add requestedMove to list of actions
        ArrayList<Move> newAction = newNode.getAction();
        newAction.add(requestedMove);

        // Get new state with move applied
        State newState = applyMoveToState(inputState, requestedMove);

        // Return new node with inputNode as the parent
        return NodeGeneration.generateNewNode(newState, inputNode, newAction, newPathCost);
    }

    /*
     * Apply moves to generate a new state
     */

    public static State applyMoveToState(State inputState, Move requestedMove) {
        // Generate a new state by applying requested move

        // Check if move possible
        if (StateGeneration.isMovePossible(inputState, requestedMove.getPieceNum(), requestedMove.getMoveId())) {

            int[][] positions = inputState.getPositions();
            int pieceNum = requestedMove.getPieceNum();
            char moveId = requestedMove.getMoveId();

            // Find index of piece in allPieces array
            int arrayIndex = 0;
            for (int currentPiece : inputState.getAllPieces()) {
                if (currentPiece == pieceNum) {
                    break;
                }
                arrayIndex++;
            }

            // Move piece over cell(s), fill old location with zeros
            ArrayList<int[]> originalLocations = StateGeneration.currentPieceLocations(positions, pieceNum);
            ArrayList<Character> possibleMoves = StateGeneration.getPossiblePieceMoves(positions, pieceNum);
            int numRight = 0; // number of times moved right
            int hTrack = 0; // keep track of changes in height

            for (int[] location : originalLocations) {
                int w = location[1];
                int h = location[0];

                if (hTrack != h) {
                    // reset numRight
                    hTrack = h;
                    numRight = 0;
                }

                if (moveId == 'u') {
                    // Move up
                    positions[h-1][w] = pieceNum;
                } else if (moveId == 'd') {
                    // Move down
                    positions[h+1][w] = pieceNum;
                } else if (moveId == 'l') {
                    // Move left
                    positions[h][w-1] = pieceNum;
                } else if (moveId == 'r') {
                    // Move right
                    positions[h][w+1] = pieceNum;
                    numRight++;
                }

                // empty moved from cell, unless we move right >1x
                if (moveId != 'r' || numRight <= 1) {
                    positions[h][w] = 0; // Make moved from cell empty
                }
            }

            // Generate new state with move applied
            return StateGeneration.generateNewState(positions, inputState.getWidth(), inputState.getHeight());
        } else {
            System.out.println("Move is not valid.");
            return null;
        }
    }

    /*
     * Random move generation
     */

    public static Move generateRandomMove(State inputState) {
        // Find all possible moves in board, and pick one at random

        // Update all possible moves in board and get an ArrayList of them
        ArrayList<Move> allPossibleMoves = StateGeneration.getAllPossibleMoves(inputState.getPositions(),
                inputState.getAllPieces());

        // Pick a random index and return the move
        return allPossibleMoves.get(new Random().nextInt(allPossibleMoves.size()));
    }

}