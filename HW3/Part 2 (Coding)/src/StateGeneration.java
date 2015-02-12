import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * StateGeneration.java: Represent states in the sliding block puzzle problem
 */

public class StateGeneration {

    // Variables
    public static State gameState = null;

    /*
     * Load, display, and clone game state
     */

    public static void loadGameState(String fileName) {
        // Load predefined game state from a text file
        gameState = loadState(fileName);
    }

    public static State loadState(String fileName) {
        // Load predefined state from a text file and return it

        // Initialize scanner and input array, input file
        ArrayList<Integer> rawInput = new ArrayList<Integer>();
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

        // Parse input and apply it to gameState
        State newState = parseInputToState(rawInput); // pass ArrayList to be parsed

        return newState;
    }

    public static void parseInputToGameState(ArrayList<Integer> input) {
        // Parse ArrayList input to the integer matrix gameState
        gameState = parseInputToState(input);
    }

    public static State parseInputToState(ArrayList<Integer> input) {
        // Parse ArrayList input to the integer matrix gameState

        // Populate gameState with values
        int w = 0;
        int h = 0;
        int width = input.get(0);
        int height = input.get(1);
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

        // Generate all pieces in state
        // find all pieces in current state (>= 2)
        ArrayList<Integer> currentPieces = new ArrayList<Integer>();
        for (int[] row : positionArray) {
            for (int value : row) {
                if (!currentPieces.contains(value) && (value >= 2)) {
                    currentPieces.add(value);
                }
            }
        }

        // Load all pieces and associated moves
        Piece[] newPieces = new Piece[currentPieces.size()];
        int i = 0;
        for (int thisPieceNum : currentPieces) {
            newPieces[i] = new Piece(thisPieceNum, MoveGeneration.possiblePieceMoves(positionArray, thisPieceNum));
            i++;
        }

        State.Builder newStateBuilder = new State.Builder(positionArray);
        State newState = newStateBuilder.width(width).height(height).allPieces(newPieces).build();

        // Get all possible moves for state
        newState.setPossibleMoves(MoveGeneration.allPossiblePieceMoves(newState));

        return newState;
    }

    public static void displayGameState() {
        // Display current game state in the console
        displayState(gameState);
    }

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

    public static State cloneGameState() {
        // Clone the current game state and return it
        return cloneState(gameState);
    }

    public static State cloneState(State givenState) {
        // Clone the given state and return it
        State.Builder newStateBuilder = new State.Builder(givenState);
        return new State(givenState);
    }

    /*
     * Puzzle completion check
     */

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

    /*
     * State comparison
     */

    public static boolean compareStates(int[][] state1, int[][] state2) {
        // Compare two states, return true if identical, false if not
        // Using the java "equals" function as it is simpler in this case

        if (state1.equals(state2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareStates(State state1, State state2) {
        // Compare two states, return true if identical, false if not
        // Using the java "equals" function as it is simpler in this case

        if (state1.getPositions().equals(state2.getPositions())) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * State normalization: make sure states are equivalent even if int values different
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
}
