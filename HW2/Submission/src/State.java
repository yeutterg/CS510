import java.util.ArrayList;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * State.java: Constructor for a search state
 */

public class State {

    public int[][] positions;
    public int width;
    public int height;
    public Piece[] allPieces;
    public ArrayList<Move> allPossibleMoves;

    public State(int[][] positions, int width, int height, Piece[] allPieces) {
        this.positions = positions;
        this.width = width;
        this.height = height;
        this.allPieces = allPieces;
    }

    public State(int[][] positions, int width, int height, Piece[] allPieces, ArrayList<Move> allPossibleMoves) {
        this.positions = positions;
        this.width = width;
        this.height = height;
        this.allPieces = allPieces;
        this.allPossibleMoves = allPossibleMoves;
    }


    public State(State givenState) {
        this.positions = givenState.getPositions();
        this.width = givenState.getWidth();
        this.height = givenState.getHeight();
        this.allPieces = givenState.getAllPieces();
        this.allPossibleMoves = givenState.getAllPossibleMoves();
    }

    public int[][] getPositions() {
        return positions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Piece[] getAllPieces() {
        return allPieces;
    }

    public ArrayList<Move> getAllPossibleMoves() {
        return allPossibleMoves;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public void setAllPieces(Piece[] allPieces) {
        this.allPieces = allPieces;
    }

    public void setSinglePosition(int h, int w, int value) {
        int[][] newPositions = this.getPositions();
        newPositions[h][w] = value;
        this.positions = newPositions;
    }

    public void setPossibleMoves(ArrayList<Move> allPossibleMoves) {
        this.allPossibleMoves = allPossibleMoves;
    }
}
