import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Movement {
	
	// Part 2.C: Move Generation
	
	public static Piece possiblePieceMoves(int[][] state, int pieceNum) {
		// return all possible moves for a piece given a game state
				
		// initialize possible moves matrix
		Set<Character> possibleMoves = new HashSet<Character>();
		
		// locate piece in game state
		ArrayList<int[]> pieceLocations = currentPieceLocations(state, pieceNum);
		System.out.println("Current piece = " + pieceNum);
		System.out.println("Current piece: ");
		for (int[] location : pieceLocations) {
			System.out.println(location[1] + "," + location[0]);
		}
		
		// look for all zeros (empty) in game state
		ArrayList<int[]> zeroLocations = currentPieceLocations(state, 0);
		possibleMoves.addAll(compareMoves(pieceLocations, zeroLocations));
		System.out.println("Zeros: ");
		for (int[] location : zeroLocations) {
			System.out.println(location[1] + "," + location[0]);
		}
		
		// if piece = master (2), look for -1 values as well
		if (pieceNum == 2) {
			ArrayList<int[]> negOneLocations = currentPieceLocations(state, -1);
			possibleMoves.addAll(compareMoves(pieceLocations, negOneLocations));
			System.out.println("negOnes: ");
			for (int[] location : negOneLocations) {
				System.out.println(location[1] + "," + location[0]);
			}
		}
		
		System.out.println("Possible: " + pieceNum + "," + possibleMoves);
		
		return new Piece(pieceNum, possibleMoves);
	}
	
	public static ArrayList<Move> allPossibleMoves(int[][] state) {
		// return all possible moves for all pieces given a game state
		
		// find all pieces in current game state (>= 2)
		ArrayList<Integer> currentPieces = new ArrayList<Integer>();
		for (int[] row : state) {
			for (int value : row) {
				if (!currentPieces.contains(value) && (value >= 2)) {
					currentPieces.add(value);
				}
			}
		}
		
		// run possiblePieceMoves for each value in currentPieces
		Piece[] allPieces = new Piece[currentPieces.size()];
		int i = 0;
		for (int piece : currentPieces) {
			allPieces[i] = possiblePieceMoves(state, piece);
			i++;
		}
		
		// Parse allMoves array into array of individual moves and return
		ArrayList<Move> individualMoves = new ArrayList<Move>();
		for (Piece currentPiece : allPieces) {
			Set<Character> possibleMoves = currentPiece.getPossibleMoves();
			for (char currentMoveId : possibleMoves) {
				individualMoves.add(new Move(currentPiece.getPieceNum(), currentMoveId));
			}
		}
		
		for (Move thisMove : individualMoves) {
			System.out.println("Possible" + thisMove.getPieceNum() + "," + thisMove.getMove());
		}
		
		return individualMoves;
	}
	
	public static void applyMove(int[][] state, int pieceNum, char requestedMove) {
		// move specified piece and apply to gameState
		
		// move piece over cell(s), fill old location with zeros
		ArrayList<int[]> originalLocations = currentPieceLocations(state, pieceNum);
		Piece possibleMoves = possiblePieceMoves(state, pieceNum);
		for (int[] location : originalLocations) {
			if (possibleMoves.isMovePossible(requestedMove)) {
				int w = location[1];
				int h = location[0];
				if (requestedMove == 'u') {
					// Move up
					state[h-1][w] = pieceNum;					
				} else if (requestedMove == 'd') {
					// Move down
					state[h+1][w] = pieceNum;
				} else if (requestedMove == 'l') {
					// Move left
					state[h][w-1] = pieceNum;
				} else if (requestedMove == 'r') {
					// Move right
					state[h][w+1] = pieceNum;
				}
				state[h][w] = 0; // Make moved from cell empty
			}
		}
		States.gameState = state;
	}
	
	public static int[][] applyMoveCloning(int[][] state, int pieceNum, char requestedMove) {
		// move specified piece and apply to a cloned state
		
		// move piece over cell(s), fill old location with zeros
		ArrayList<int[]> originalLocations = currentPieceLocations(state, pieceNum);
		Piece possibleMoves = possiblePieceMoves(state, pieceNum);
		for (int[] location : originalLocations) {
			if (possibleMoves.isMovePossible(requestedMove)) {
				int w = location[1];
				int h = location[0];
				if (requestedMove == 'u') {
					// Move up
					state[h-1][w] = pieceNum;					
				} else if (requestedMove == 'd') {
					// Move down
					state[h+1][w] = pieceNum;
				} else if (requestedMove == 'l') {
					// Move left
					state[h][w-1] = pieceNum;
				} else if (requestedMove == 'r') {
					// Move right
					state[h][w+1] = pieceNum;
				}
				state[h][w] = 0; // Make moved from cell empty
			}
		}
		return state;
	}
	
	public static ArrayList<int[]> currentPieceLocations(int[][] state, int pieceNum) {
		// Find and return coordinates of specific value in specified game state
		
		ArrayList<int[]> locations = new ArrayList<int[]>();
		int w = 0;
		int h = 0;
		for (int[] row : state) {
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
	
	public static ArrayList<Character> compareMoves(ArrayList<int[]> pieceState, 
			ArrayList<int[]> targetState) {
		// Compare two states and output possible moves
		
		ArrayList<Character> possibleMoves = new ArrayList<Character>();
		
		for (int[] cellPiece : pieceState) {
			int wPiece = cellPiece[1];
			int hPiece = cellPiece[0];
			for (int[] cellTarget : targetState) {
				int wTarget = cellTarget[1];
				int hTarget = cellTarget[0];
				
				if ((wTarget == (wPiece + 1)) && (hTarget == hPiece)) {
					// Possible to move right
					possibleMoves.add('r');
				} else if ((wTarget == (wPiece - 1)) && (hTarget == hPiece)) {
					// Possible to move left
					possibleMoves.add('l');
				} else if ((hTarget == (hPiece + 1)) && (wTarget == wPiece)) {
					// Possible to move up
					possibleMoves.add('u');
				} else if ((hTarget == (hPiece - 1)) && (wTarget == wPiece)) {
					// Possible to move down
					possibleMoves.add('d');
				}	
			}
			
		}
		
		return possibleMoves;
	}
	

}
