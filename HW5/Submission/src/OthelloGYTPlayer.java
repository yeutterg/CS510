import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloGYEPlayer.java: Othello player implementing the standard minimax algorithm,
 * with specified time limit and the updated evaluation (OthelloState.evaluation) function
 */

public class OthelloGYTPlayer extends OthelloPlayer {

    static int depthLimit;
    static int timeLimit;
    static int explored;

    /*
     * Initialize player and set the time limit
     */
    public OthelloGYTPlayer(int maxTime) {
        if (maxTime < 1) {
            System.out.println("Error: Time limit must be >= 1 ms.");
            System.exit(-1);
        }
        timeLimit = maxTime;
    }

    /*
     * Get the best move as determined by minimax
     */
    @Override
    public OthelloMove getMove(OthelloState state) {
        // Player 0/O is maximizing, Player 1/X is minimizing

        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Pass if no possible moves
        if (moves.isEmpty()) {
            return null;
        }

        // Iterative deepening
        long deadline = System.currentTimeMillis() + timeLimit;
        depthLimit = 1;
        OthelloMove resultingMove = null; // store the latest result

        while (System.currentTimeMillis() < deadline) {
            // Decide if player is maximizing or minimizing
            if (state.nextPlayerToMove == 0) {
                // maximizing
                resultingMove = maxDecision(state, moves);
            } else {
                // minimizing
                resultingMove = minDecision(state, moves);
            }
            depthLimit++; // Increment the depth limit
        }
        return resultingMove; // Return the most recently computed move before the deadline
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

        // Find the score index with the arg max value
        Double argMaxVal = Double.NEGATIVE_INFINITY;
        for (Double s : scores) {
            argMaxVal = Math.max(argMaxVal, s);
        }
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

        // Find the score index with the arg min value
        Double argMinVal = Double.POSITIVE_INFINITY;
        for (Double s : scores) {
            argMinVal = Math.min(argMinVal, s);
        }
        int indexVal = scores.indexOf(argMinVal);

        // Return the move with the arg min value
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
            return (double) state.evaluation();
        }

        // Find the max value and return it
        Double value = Double.NEGATIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double minVal = minValue(state.applyMoveCloning(m), depth + 1);
            value = Math.max(value, minVal);
            explored++;
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
            return (double) state.evaluation();
        }

        // Find the min value and return it
        Double value = Double.POSITIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double maxVal = maxValue(state.applyMoveCloning(m), depth + 1);
            value = Math.min(value, maxVal);
            explored++;
        }
        return value;
    }
}
