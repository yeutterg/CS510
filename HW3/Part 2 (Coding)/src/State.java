import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * State.java: Constructor for a search state
 */

public class State {

    private int[][] positions;
    private int width;
    private int height;
    private List<Integer> allPieces;
    private List<Move> allPossibleMoves;

    public int[][] getPositions() {
        return positions;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Integer> getAllPieces() {
        return allPieces;
    }

    public void setAllPieces(List<Integer> allPieces) {
        this.allPieces = allPieces;
    }

    public List<Move> getAllPossibleMoves() {
        return allPossibleMoves;
    }

    public void setAllPossibleMoves(List<Move> allPossibleMoves) {
        this.allPossibleMoves = allPossibleMoves;
    }

    public State(int[][] positions, int width, int height, List<Integer> allPieces, List<Move> allPossibleMoves) {
        this.positions = positions;
        this.width = width;
        this.height = height;
        this.allPieces = allPieces;
        this.allPossibleMoves = allPossibleMoves;
    }

    public State(State oldState) {
        this.positions = StateGeneration.clonePositionArray(oldState.positions);
        this.width = oldState.width;
        this.height = oldState.height;
        this.allPieces = oldState.allPieces;
        this.allPossibleMoves = oldState.allPossibleMoves;
    }
}