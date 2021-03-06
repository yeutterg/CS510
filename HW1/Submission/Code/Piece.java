import java.util.ArrayList;
import java.util.Set;

// Constructor for moves each piece can make 

public class Piece {
	public static int pieceNum; 
	public static Set<Character> possibleMoves;
	
	public Piece(int currentPieceNum, Set<Character> currentMoves) {
		pieceNum = currentPieceNum;
		possibleMoves = currentMoves;
	}
	
	public int getPieceNum() {
		return pieceNum;
	}
	
	public Set<Character> getPossibleMoves() {
		return possibleMoves;
	}
	
	public boolean isMovePossible(char requestedMove) {
		if (possibleMoves.contains(requestedMove)) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Move> generateIndividualMoves() {
		ArrayList<Move> allMoves = new ArrayList<Move>();
		
		for (Character thisMove : possibleMoves) {
			allMoves.add(new Move(pieceNum, thisMove));
		}
		
		return allMoves;
	}

}