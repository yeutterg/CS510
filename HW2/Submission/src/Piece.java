import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * Piece.java: Constructor for a piece and all moves it can make
 */

public class Piece {
    public int pieceNum;
    public ArrayList<Character> possibleMoves;

    public Piece(int pieceNum, ArrayList<Character> possibleMoves) {
        this.pieceNum = pieceNum;
        this.possibleMoves = possibleMoves;
    }

    public int getPieceNum() {
        return pieceNum;
    }

    public ArrayList<Character> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Character> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
