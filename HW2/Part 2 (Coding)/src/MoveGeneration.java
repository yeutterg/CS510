import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * MoveGeneration.java: Change the state of the game by performing valid moves
 */

public class MoveGeneration {

    public static Piece[] allPieces = null;

    /*
     * Check for possible piece moves
     */

    public static boolean isMovePossible(Piece requestedPiece, char requestedMove) {
        // Check if move possible in current game state

        // Update possible moves for requested piece
        requestedPiece.setPossibleMoves(possiblePieceMoves(StateGeneration.gameState, requestedPiece.getPieceNum()));

        // Check if possible moves set contains the requested move
        if (requestedPiece.getPossibleMoves().contains(requestedMove)) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Move> allPossiblePieceMoves(State currentState) {
        // Update pieces array in state and return an ArrayList of all possible moves

        // Update allPieces for every piece currently in game state
        ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
        Piece[] currentPieces = currentState.getAllPieces();
        for (int i = 0; i < currentPieces.length; i++) {
            currentPieces[i].setPossibleMoves(new ArrayList<Character>()); // empty it first
            currentPieces[i].setPossibleMoves(possiblePieceMoves(currentState, currentPieces[i].getPieceNum()));
            for (char currentMoveId : currentPieces[i].getPossibleMoves()) {
                allPossibleMoves.add(new Move(currentPieces[i].getPieceNum(), currentMoveId));
            }
        }
        currentState.setAllPieces(currentPieces);

        return allPossibleMoves;
    }

    public static ArrayList<Character> possiblePieceMoves(State givenState, int pieceNum) {
        // Return all possible moves for a piece given a state

        // initialize possible moves array
        ArrayList<Character> possibleMovesArray = new ArrayList<Character>();

        // locate piece in game state
        ArrayList<int[]> pieceLocations = currentPieceLocations(givenState, pieceNum);

        // look for all zeros (empty) in game state
        ArrayList<int[]> zeroLocations = currentPieceLocations(givenState, 0);
        possibleMovesArray.addAll(compareLocations(pieceLocations, zeroLocations));

        // if piece = master (2), look for -1 values as well
        if (pieceNum == 2) {
            ArrayList<int[]> negOneLocations = currentPieceLocations(givenState, -1);
            possibleMovesArray.addAll(compareLocations(pieceLocations, negOneLocations));
        }

        // Only add move to possible result set if all cells of piece can freely move up or down
        // Set will prevent duplicates from being added
        Set<Character> possibleMovesSet = new HashSet<Character>();

        for (Character currentMove : possibleMovesArray) {
            if (pieceLocations.size() > 1) {
                // If more than one cell, we need to check
                int numInstances = 0;
                for (int i = 0; i < possibleMovesArray.size(); i++) {
                    // Find all instances of the same character
                    if (possibleMovesArray.get(i) == currentMove) {
                        numInstances++;
                    }
                    // Only add to output set if number of instances = size of piece
                    if (numInstances == pieceLocations.size()) {
                        possibleMovesSet.add(currentMove);
                    }
                }
            } else {
                // Otherwise, we need to just add the value to the output set
                possibleMovesSet.add(currentMove);
            }
        }

        // Convert Set back to ArrayList
        ArrayList<Character> possibleMovesFinal = new ArrayList<Character>(possibleMovesSet);

        return possibleMovesFinal;
    }

    public static ArrayList<Character> possiblePieceMoves(int[][] positions, int pieceNum) {
        // Return all possible moves for a piece given a game state

        // initialize possible moves array
        ArrayList<Character> possibleMovesArray = new ArrayList<Character>();

        // locate piece in game state
        ArrayList<int[]> pieceLocations = currentPieceLocations(positions, pieceNum);

        // look for all zeros (empty) in game state
        ArrayList<int[]> zeroLocations = currentPieceLocations(positions, 0);
        possibleMovesArray.addAll(compareLocations(pieceLocations, zeroLocations));

        // if piece = master (2), look for -1 values as well
        if (pieceNum == 2) {
            ArrayList<int[]> negOneLocations = currentPieceLocations(positions, -1);
            possibleMovesArray.addAll(compareLocations(pieceLocations, negOneLocations));
        }

        // Only add move to possible result set if all cells of piece can freely move up or down
        // Set will prevent duplicates from being added
        Set<Character> possibleMovesSet = new HashSet<Character>();

        for (Character currentMove : possibleMovesArray) {
            if (pieceLocations.size() > 1) {
                // If more than one cell, we need to check
                int numInstances = 0;
                for (int i = 0; i < possibleMovesArray.size(); i++) {
                    // Find all instances of the same character
                    if (possibleMovesArray.get(i) == currentMove) {
                        numInstances++;
                    }
                    // Only add to output set if number of instances = size of piece
                    if (numInstances == pieceLocations.size()) {
                        possibleMovesSet.add(currentMove);
                    }
                }
            } else {
                // Otherwise, we need to just add the value to the output set
                possibleMovesSet.add(currentMove);
            }
        }

        // Convert Set back to ArrayList
        ArrayList<Character> possibleMovesFinal = new ArrayList<Character>(possibleMovesSet);

        return possibleMovesFinal;
    }

    /*
     * Move application
     */

    public static void applyMove(State givenState, Move requestedMove) {
        // Apply requested move to given state

        // Find index of piece in allPieces array
        int pieceNum = requestedMove.getPieceNum();
        char moveId = requestedMove.getMoveId();
        int arrayIndex = 0;
        for (Piece currentPiece : givenState.getAllPieces()) {
            if (currentPiece.getPieceNum() == pieceNum) {
                break;
            }
            arrayIndex++;
        }

        // Move piece over cell(s), fill old location with zeros
        ArrayList<int[]> originalLocations = currentPieceLocations(givenState, pieceNum);
        ArrayList<Character> possibleMoves = possiblePieceMoves(givenState, pieceNum);
        int numRight = 0; // number of times moved right
        int hTrack = 0; // keep track of changes in height

        if (isMovePossible(givenState.getAllPieces()[arrayIndex], moveId)) {
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
                    givenState.positions[h-1][w] = pieceNum;
                } else if (moveId == 'd') {
                    // Move down
                    givenState.positions[h+1][w] = pieceNum;
                } else if (moveId == 'l') {
                    // Move left
                    givenState.positions[h][w-1] = pieceNum;
                } else if (moveId == 'r') {
                    // Move right
                    givenState.positions[h][w+1] = pieceNum;
                    numRight++;
                }

                // empty moved from cell, unless we move right >1x
                if (moveId != 'r' || numRight <= 1) {
                    givenState.positions[h][w] = 0; // Make moved from cell empty
                }
            }
        }
        StateGeneration.gameState = givenState;
    }

    public static State applyMoveCloning(State givenState, Move requestedMove) {
        // Clone input state, apply requested move to new state

        // Clone state
        State newState = StateGeneration.cloneState(givenState);

        // Find index of piece in allPieces array
        int pieceNum = requestedMove.getPieceNum();
        char moveId = requestedMove.getMoveId();
        int arrayIndex = 0;
        for (Piece currentPiece : givenState.getAllPieces()) {
            if (currentPiece.getPieceNum() == pieceNum) {
                break;
            }
            arrayIndex++;
        }

        // Move piece over cell(s), fill old location with zeros
        ArrayList<int[]> originalLocations = currentPieceLocations(newState, pieceNum);
        ArrayList<Character> possibleMoves = possiblePieceMoves(newState, pieceNum);
        int numRight = 0; // number of times moved right
        int hTrack = 0; // keep track of changes in height

        if (isMovePossible(allPieces[arrayIndex], moveId)) {
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
                    newState.positions[h-1][w] = pieceNum;
                } else if (moveId == 'd') {
                    // Move down
                    newState.positions[h+1][w] = pieceNum;
                } else if (moveId == 'l') {
                    // Move left
                    newState.positions[h][w-1] = pieceNum;
                } else if (moveId == 'r') {
                    // Move right
                    newState.positions[h][w+1] = pieceNum;
                    numRight++;
                }

                // empty moved from cell, unless we move right >1x
                if (moveId != 'r' || numRight <= 1) {
                    newState.positions[h][w] = 0; // Make moved from cell empty
                }
            }
        }
        return newState;
    }

    /*
     * Piece (integer in state) location tracking and comparison
     */

    public static ArrayList<int[]> currentPieceLocations(State givenState, int pieceNum) {
        // Find and return coordinates of specific value in specified game state

        ArrayList<int[]> locations = new ArrayList<int[]>();
        int w = 0;
        int h = 0;
        for (int[] row : givenState.getPositions()) {
            for (int num : row) {
                if (num == pieceNum) {
                    locations.add(new int[] {h, w});
                }
                w++;
            }
            h++;
            w = 0;
        }

        return locations;
    }

    public static ArrayList<int[]> currentPieceLocations(int[][] positions, int pieceNum) {
        // Find and return coordinates of specific value in specified game state

        ArrayList<int[]> locations = new ArrayList<int[]>();
        int w = 0;
        int h = 0;
        for (int[] row : positions) {
            for (int num : row) {
                if (num == pieceNum) {
                    locations.add(new int[] {h, w});
                }
                w++;
            }
            h++;
            w = 0;
        }

        return locations;
    }

    public static ArrayList<Character> compareLocations(ArrayList<int[]> pieceLocations,
                                                    ArrayList<int[]> targetLocations) {
        // Compare two states and output possible moves (i.e. it is possible for
        // piece to move to target)

        ArrayList<Character> possibleMoves = new ArrayList<Character>();

        for (int[] cellPiece : pieceLocations) {
            int wPiece = cellPiece[1];
            int hPiece = cellPiece[0];
            for (int[] cellTarget : targetLocations) {
                int wTarget = cellTarget[1];
                int hTarget = cellTarget[0];

                if ((wTarget == (wPiece + 1)) && (hTarget == hPiece)) {
                    // Possible to move right
                    possibleMoves.add('r');
                } else if ((wTarget == (wPiece - 1)) && (hTarget == hPiece)) {
                    // Possible to move left
                    possibleMoves.add('l');
                } else if ((hTarget == (hPiece + 1)) && (wTarget == wPiece)) {
                    // Possible to move down
                    possibleMoves.add('d');
                } else if ((hTarget == (hPiece - 1)) && (wTarget == wPiece)) {
                    // Possible to move up
                    possibleMoves.add('u');
                }
            }

        }

        return possibleMoves;
    }

    /*
     * Random move generation
     */

    public static Move generateRandomMove(State givenState) {
        // Find all possible moves in board, and pick one at random

        // Update all possible moves in board and get an ArrayList of them
        ArrayList<Move> allPossibleMoves = allPossiblePieceMoves(givenState);

        // Pick a random index and return the move
        return allPossibleMoves.get(new Random().nextInt(allPossibleMoves.size()));
    }
}
