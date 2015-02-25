import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloPlayerYeutter.java: Othello player implementing the standard minimax algorithm
 */

public class OthelloPlayerYeutter extends OthelloPlayer {

    static int depthLimit;

    /*
     * Initialize player and set the depth limit
     */
    public OthelloPlayerYeutter(int maxDepth) {
        depthLimit = maxDepth;
    }

    /*
     * Get the best move as determined by minimax
     */
    @Override
    public OthelloMove getMove(OthelloState state) {
        // Player 0/O is maximizing, Player 1/X is minimizing

        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        System.out.println("possible:");
        for (OthelloMove m : moves) {
            System.out.println(m.toString());
        }

        // Perform goal test
        if (moves.isEmpty()) {
            return null;
        }

        if (state.nextPlayerToMove == 0) {
            // maximizing
            System.out.println("maximizing");
            return maxDecision(state, moves);
        } else {
            // minimizing
            System.out.println("minimizing");
            return minDecision(state, moves);
        }
    }

    /*
     * Minimax decision function if player is maximizing
     */
    private static OthelloMove maxDecision(OthelloState state, List<OthelloMove> moves) {
        // Get scores for all possible actions in current moves
        List<Double> scores = new ArrayList<Double>();
        for (OthelloMove m : moves) {
            scores.add(minValue(state.applyMoveCloning(m), 0));
        }

        // Last move
        if (scores.size() == 1 && scores.get(0) == null) {
            return moves.get(0);
        }

        // Find the score index with the arg max value
        Double argMaxVal = Double.NEGATIVE_INFINITY;
        for (Double s : scores) {
            System.out.println("Scores:");
            if (s == null) {
                continue;
            }
            System.out.println(s);
            argMaxVal = Math.max(argMaxVal, s);
        }
        System.out.println(argMaxVal);
        int indexVal = scores.indexOf(argMaxVal);

        // Return the move with the arg max value
        return moves.get(indexVal);
    }

    /*
     * Minimax decision function if player is minimizing
     */
    private static OthelloMove minDecision(OthelloState state, List<OthelloMove> moves) {
        // Get scores for all possible actions in current moves
        List<Double> scores = new ArrayList<Double>();
        for (OthelloMove m : moves) {
            scores.add(maxValue(state.applyMoveCloning(m), 0));
        }

        // Last move
        if (scores.size() == 1 && scores.get(0) == null) {
            return moves.get(0);
        }

        // Find the score index with the arg min value
        Double argMinVal = Double.POSITIVE_INFINITY;
        for (Double s : scores) {
            System.out.println("Scores:");
            if (s == null) {
                continue;
            }
            System.out.println(s);
            argMinVal = Math.min(argMinVal, s);
        }
        System.out.println(argMinVal);
        int indexVal = scores.indexOf(argMinVal);

        // Return the move with the arg max value
        return moves.get(indexVal);
    }

    /*
     * Determines the move with the max value for a given state
     */
    private static Double maxValue(OthelloState state, int depth) {
        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Perform goal test and depth limit test
        if (moves.isEmpty() || depth >= depthLimit) {
            return null;
        }

        // Find the max value and return it
        Double value = Double.NEGATIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double minVal = minValue(state.applyMoveCloning(m), depth + 1);
            if (minVal == null) {
                return value;
            }
            value = Math.max(value, minVal);
        }
        return value;
    }

    /*
     * Determines the move with the min value for a given state
     */
    private static Double minValue(OthelloState state, int depth) {
        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Perform goal test
        if (moves.isEmpty() || depth >= depthLimit) {
            return null;
        }

        // Find the min value and return it
        Double value = Double.POSITIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double maxVal = maxValue(state.applyMoveCloning(m), depth + 1);
            if (maxVal == null) {
                return value;
            }
            value = Math.min(value, maxVal);
        }
        return value;
    }
}
