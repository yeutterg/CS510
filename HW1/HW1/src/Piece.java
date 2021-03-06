import java.util.ArrayList;
import java.util.Collections;
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

}