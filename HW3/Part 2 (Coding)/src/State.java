import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * State.java: Builder for a search state
 */

public class State {

    private final int[][] positions;
    private final int width;
    private final int height;
    private final List<Integer> allPieces;
    private final List<Move> allPossibleMoves;

    public static class Builder {
        // Required parameter
        private final int[][] positions;

        // Optional parameters with default values
        private int width = 1;
        private int height = 1;
        private List<Integer> allPieces = null;
        private List<Move> allPossibleMoves = new ArrayList<Move>(0);

        public Builder(int[][] positions) {
            this.positions = positions;
        }

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder allPieces(List<Integer> input) {
            allPieces = input;
            return this;
        }

        public Builder allPossibleMoves(List<Move> moves) {
            allPossibleMoves = moves;
            return this;
        }

        protected int[][] getPositions() {
            return positions;
        }

        protected int getWidth() {
            return width;
        }

        protected int getHeight() {
            return height;
        }

        protected List<Integer> getAllPieces() {
            return allPieces;
        }

        protected List<Move> getAllPossibleMoves() {
            return allPossibleMoves;
        }

        public State build() {
            return new State(this);
        }
    }

    private State(Builder builder) {
        positions = builder.positions;
        width = builder.width;
        height = builder.height;
        allPieces = builder.allPieces;
        allPossibleMoves = builder.allPossibleMoves;
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

    public List<Integer> getAllPieces() {
        return allPieces;
    }

    public List<Move> getAllPossibleMoves() {
        return allPossibleMoves;
    }
}