import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloIDPlayer.java: Othello player implementing iterative deepening, with specified time limit. This depth limit
 * is applied to an agent that uses alpha-beta pruning (e.g. OthelloABPlayer)
 */

public class OthelloIDPlayer extends OthelloPlayer{

    static long timeLimit;
    static int currentDepthLimit;
    static int explored;

    /*
     * Initialize player and set the time limit
     */
    public OthelloIDPlayer(int maxTime) {
        if (maxTime < 1) {
            System.out.println("Error: Time limit must be >= 1 ms.");
            System.exit(-1);
        }
        timeLimit = maxTime;
    }

    /*
     * Get the best move as determined by iterative deepening search
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
        currentDepthLimit = 1;
        System.out.println("current time " + System.currentTimeMillis());
        System.out.println("deadline " + deadline);
        OthelloMove resultingMove = null; // store the latest result

        while (System.currentTimeMillis() < deadline) {

            // Decide if player is maximizing or minimizing
            List<Double> scores = new ArrayList<Double>();
            Double value;
            if (state.nextPlayerToMove == 0) {
                // maximizing
                for (OthelloMove m : moves) {
                    scores.add(minValue(state.applyMoveCloning(m), 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
                }

                // Find the score index with the arg max value
                Double argMaxVal = Double.NEGATIVE_INFINITY;
                for (Double s : scores) {
                    argMaxVal = Math.max(argMaxVal, s);
                }
                int indexVal = scores.indexOf(argMaxVal);

                // Store the move with the arg max value
                resultingMove = moves.get(indexVal);
            } else {
                // minimizing
                for (OthelloMove m : moves) {
                    scores.add(maxValue(state.applyMoveCloning(m), 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
                }

                // Find the score index with the arg min value
                Double argMinVal = Double.POSITIVE_INFINITY;
                for (Double s : scores) {
                    argMinVal = Math.min(argMinVal, s);
                }
                int indexVal = scores.indexOf(argMinVal);

                // Store the move with the arg min value
                resultingMove = moves.get(indexVal);
            }

            // Increment the depth limit
            currentDepthLimit++;
        }

        // Return the last computed move before the deadline
        return resultingMove;
    }

    /*
    * Determines the move with the max value for a given state
    */
    private static Double maxValue(OthelloState state, int depth, Double alpha, Double beta) {
        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Perform goal test and depth limit test
        if (moves.isEmpty() || depth >= currentDepthLimit) {
            return (double) state.score();
        }

        // Find the max value and return it
        Double value = Double.NEGATIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double minVal = minValue(state.applyMoveCloning(m), depth + 1, alpha, beta);
            value = Math.max(value, minVal);
            if (value >= beta) {
                return value;
            }
            explored++;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    /*
     * Determines the move with the min value for a given state
     */
    private static Double minValue(OthelloState state, int depth, Double alpha, Double beta) {
        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Perform goal test
        if (moves.isEmpty() || depth >= currentDepthLimit) {
            return (double) state.score();
        }

        // Find the min value and return it
        Double value = Double.POSITIVE_INFINITY;
        for (OthelloMove m : moves) {
            Double maxVal = maxValue(state.applyMoveCloning(m), depth + 1, alpha, beta);
            value = Math.min(value, maxVal);
            if (value <= alpha) {
                return value;
            }
            explored++;
            beta = Math.min(beta, value);
        }
        return value;
    }

}
