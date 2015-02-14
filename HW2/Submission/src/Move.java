/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * Move.java: Constructor for a move
 */

public class Move {
    public int pieceNum;
    public char moveId;

    public Move(int pieceNum, char moveId) {
        this.pieceNum = pieceNum;
        this.moveId = moveId;
    }

    public int getPieceNum() {
        return pieceNum;
    }

    public char getMoveId() {
        return moveId;
    }
}
