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
    static int[][] gameState = null;
    static int width = 0;
    static int height = 0;

    /*
     * Load, display, and clone game state
     */

    public static void loadGameState(String fileName) {
        // Load predefined game state from a text file

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
        parseInputToGameState(rawInput); // pass ArrayList to be parsed

        // Generate all pieces in gameState
        MoveGeneration.loadAllPieces();
    }

    public static void parseInputToGameState(ArrayList<Integer> input) {
        // Parse ArrayList input to the integer matrix gameState

        // Set up w,h of gameState
        width = input.get(0);
        height = input.get(1);
        gameState = new int[height][width]; // set up gameState matrix with specified w,h

        // Populate gameState with values
        int w = 0;
        int h = 0;
        for (int i = 2; i < input.size(); i++) {
            if (w < width) {
                // fill row
                gameState[h][w] = input.get(i);
                w++;
            } else {
                // next row
                w = 0;
                h++;
                gameState[h][w] = input.get(i);
                w++;
            }
        }
    }

    public static void displayGameState() {
        // Display current game state in the console

        System.out.println(width + "," + height + ",");
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                System.out.print(gameState[h][w] + ",");
            }
            System.out.print("\n");
        }
    }

    public static void displayState(State givenState) {
        // Display given state in the console

        System.out.println(givenState.width + "," + givenState.height + ",");
        for (int h = 0; h < givenState.height; h++) {
            for (int w = 0; w < givenState.width; w++) {
                System.out.print(givenState.positions[h][w] + ",");
            }
            System.out.print("\n");
        }
    }

    public static int[][] cloneGameState() {
        // Clone the current game state and return it
        return gameState.clone();
    }

    public static State cloneState(State givenState) {
        // Clone the given state and return it
        return new State(givenState);
    }

    /*
     * Puzzle completion check
     */

    public static boolean checkPuzzleComplete() {
        // Check if Puzzle complete (contains no -1)
        // Return true if complete, false if not

        // Iterate through game state
        // Return false (and break) as soon as -1 encountered
        for (int[] row : gameState) {
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

    /*
     * State normalization: make sure states are equivalent even if int values different
     */

    public static int[][] normalizeState(int[][] state) {
        // Normalize state

        int nextIdx = 3;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (state[h][w] == nextIdx) {
                    nextIdx++;
                } else if (state[h][w] > nextIdx) {
                    swapIdx(state, nextIdx, state[h][w]);
                    nextIdx++;
                }
            }
        }
        return state;
    }

    public static int[][] swapIdx(int[][] state, int idx1, int idx2) {
        // Swap values to aid in normalizing state

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (state[h][w] == idx1) {
                    state[h][w] = idx2;
                } else if (state[h][w] == idx2) {
                    state[h][w] = idx1;
                }
            }
        }
        return state;
    }
}
