import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class States {
	
	// Variables 
	static int[][] gameState = null;
	static int width = 0;
	static int height = 0;
	
	// Part 2.A: State Representation
	
	public static void loadGameState(String fileName) {
		// Load predefined game state from a text file
		
		// Initialize scanner and input array, input file
		ArrayList<Integer> rawInput = new ArrayList<Integer>(); 
		try {
			// Scan in file
			// sc = scanner for lines within file, sc2 = scanner for values within line
			Scanner sc = new Scanner(new File(fileName)); 
			while (sc.hasNext()) {
				// Scan in line to obtain individual values
				Scanner sc2 = new Scanner(sc.next()); 
				// use comma delimiter
				sc2.useDelimiter(","); 
				while (sc2.hasNextInt()) {
					// add value to rawInput
					rawInput.add(sc2.nextInt()); 
				}
				sc2.close(); // close scanner
			}
			sc.close(); // close scanner
		} catch (FileNotFoundException e) {
			System.out.println("Error: File Not Found.");
			e.printStackTrace();
		}

		
		// Parse input and apply it to gameState	
		parseInputToGameState(rawInput); // pass ArrayList to be parsed
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
	
	public static int[][] cloneGameState() {
		// Clone game state
		return gameState.clone();
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
	
	// Part 2.B: Puzzle Complete Check
	
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
	
	// Part 2.D: State Comparison
	
	public static boolean compareStates(int[][] state1, int[][] state2) {
		// Compare two states, return true if identical, false if not
		// Using the java "equals" function as it is simpler in this case 
		
		if (state1.equals(state2)) {
			return true;
		} else {
			return false;
		}
	}
	
	// Part 2.E: Normalization
	
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
