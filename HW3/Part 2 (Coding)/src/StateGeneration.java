import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * StateGeneration.java: Represent states in the sliding block puzzle problem
 */

public class StateGeneration {

    public static int width;
    public static int height;

    /*
     * Build or clone (immutable) states
     */

    public static State loadInitialState(String fileName) {
        // Load, build, and return initial game state

        // Load positions array
        int[][] positions = loadPositions(fileName);

        // Build and return new state
        return generateNewState(positions, width, height);
    }

    public static State generateNewState(int[][] pos, int w, int h) {
        // Generate a new state

        // Normalize positions array
        pos = normalizeState(w, h, pos);

        // Get all pieces in positions array
        List<Integer> allPieces = getAllPieces(pos);

        // Get all possible moves for each piece in positions array
        List<Move> possibleMoves = getAllPossibleMoves(pos, allPieces);

        // Build and return new state
        return new State(pos, w, h, allPieces, possibleMoves);
    }

    public static State cloneState(State inputState) {
        // Clone a state
        return new State(inputState);
    }

    /*
     * Load position arrays
     */

    public static int[][] clonePositionArray(int[][] src) {
        // Clone a position array

        int[][] out = new int[height][width];
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[i].length; j++) {
                out[i][j] = src[i][j];
            }
        }
        return out;
    }

    public static int[][] loadPositions(String fileName) {
        // Load predefined positions from a text file and return it

        // Initialize scanner and input array, input file
        List<Integer> rawInput = new ArrayList<Integer>();
        try {
            // Scan in file
            // sc = scanner for lines within file
            // sc2 = scanner for values within each line
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) {
                // Scan in line to obtain individual values
                Scanner sc2 = new Scanner(sc.next());
                // Use comma as delimiter
                sc2.useDelimiter(",");
                while (sc2.hasNextInt()) {
                    // add value to rawInput
                    rawInput.add(sc2.nextInt());
                }
                sc2.close(); // close scanner
            }
            sc.close(); // close scanner

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }

        // Parse input and return it
        return parseInputToPositions(rawInput); // pass ArrayList to be parsed
    }

    public static int[][] parseInputToPositions(List<Integer> input) {
        // Parse ArrayList input to the integer matrix gameState

        // Populate gameState with values
        int w = 0;
        int h = 0;
        width = input.get(0);
        height = input.get(1);
        int[][] positionArray = new int[height][width];
        for (int i = 2; i < input.size(); i++) {
            if (w < width) {
                // fill row
                positionArray[h][w] = input.get(i);
                w++;
            } else {
                // next row
                w = 0;
                h++;
                positionArray[h][w] = input.get(i);
                w++;
            }
        }

        return positionArray;
    }

    /*
     * Locate pieces within position arrays
     */

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

    public static ArrayList<Integer> getAllPieces(int[][] positions) {
        // Find all pieces in position array and determine possible moves

        // find all pieces in current state (value >= 2)
        ArrayList<Integer> currentPieces = new ArrayList<Integer>();
        for (int[] row : positions) {
            for (int value : row) {
                if (!currentPieces.contains(value) && (value >= 2)) {
                    currentPieces.add(value);
                }
            }
        }

        return currentPieces;
    }

    /*
     * Determine possible moves for pieces in position array
     */

    public static boolean isMovePossible(State inputState, int pieceNum, char moveId) {
        // Check if move possible in given state

        List<Move> allPossibleMoves = inputState.getAllPossibleMoves();
        for (Move currentMove : allPossibleMoves) {
            if ((pieceNum == currentMove.getPieceNum()) && (moveId == currentMove.getMoveId())) {
                return true;
            }
        }
        return false;
    }

    public static List<Move> getAllPossibleMoves(int[][] positions, List<Integer> allPieces) {
        // Find all possible moves for all pieces in given positions array

        ArrayList<Move> moves = new ArrayList<Move>(0);
        for (int i = 0; i < allPieces.size(); i++) {
            ArrayList<Character> possiblePieceMoves = getPossiblePieceMoves(positions, allPieces.get(i));
            for (int j = 0; j < possiblePieceMoves.size(); j++) {
                moves.add(new Move(allPieces.get(i), possiblePieceMoves.get(j)));
            }
        }

        return moves;
    }

    public static ArrayList<Character> getPossiblePieceMoves(int[][] positions, int pieceNum) {
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

        return possibleMovesArray;
    }

    public static ArrayList<Character> compareLocations(List<int[]> pieceLocations, List<int[]> targetLocations) {
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
     * Normalize position arrays
     */

    public static int[][] normalizeState(int wVal, int hVal, int[][] pos) {
        // Normalize state

        int nextIdx = 3;
        for (int h = 0; h < hVal; h++) {
            for (int w = 0; w < wVal; w++) {
                if (pos[h][w] == nextIdx) {
                    nextIdx++;
                } else if (pos[h][w] > nextIdx) {
                    pos = swapIdx(nextIdx, pos[h][w], wVal, hVal, pos);
                    nextIdx++;
                }
            }
        }
        return pos;
    }

    public static int[][] swapIdx(int idx1, int idx2, int wVal, int hVal, int[][] pos) {
        // Swap values to aid in normalizing state

        for (int h = 0; h < hVal; h++) {
            for (int w = 0; w < wVal; w++) {
                if (pos[h][w] == idx1) {
                    pos[h][w] = idx2;
                } else if (pos[h][w] == idx2) {
                    pos[h][w] = idx1;
                }
            }
        }

        return pos;
    }

    /*
     * Display states
     */

    public static void displayState(State givenState) {
        // Display given state in the console
        int wDisp = givenState.getWidth();
        int hDisp = givenState.getHeight();
        int[][] pDisp = givenState.getPositions();

        System.out.println(wDisp + "," + hDisp + ",");
        for (int h = 0; h < hDisp; h++) {
            for (int w = 0; w < wDisp; w++) {
                System.out.print(pDisp[h][w] + ",");
            }
            System.out.print("\n");
        }
    }

    /*
     * Evaluate states
     */

    public static boolean comparePositions(int[][] pos1, int[][] pos2) {
        // Compare two position matrices, return true if identical, false if not

        for (int h = 0; h < pos1.length; h++) {
            for (int w = 0; w < pos1[h].length; w++) {
                if (pos1[h][w] != pos2[h][w]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkPuzzleComplete(State givenState) {
        // Check if Puzzle complete (contains no -1)
        // Return true if complete, false if not

        // Iterate through game state
        // Return false (and break) as soon as -1 encountered
        for (int[] row : givenState.getPositions()) {
            for (int value : row) {
                if (value == -1) {
                    return false;
                }
            }
        }

        // if we did not return false, must be complete
        return true;
    }

}