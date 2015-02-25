import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloABPlayer.java: Othello player implementing the alpha-beta pruning, with specified depth limit
 */
public class OthelloABPlayer extends OthelloPlayer{

    static int depthLimit;

    /*
     * Initialize player and set the depth limit
     */
    public OthelloABPlayer(int maxDepth) {
        if (maxDepth < 1) {
            System.out.println("Error: Depth limit must be >= 1.");
            System.exit(-1);
        }
        depthLimit = maxDepth;
    }

    /*
     * Get the best move as determined by alpha-beta pruning
     */
    @Override
    public OthelloMove getMove(OthelloState state) {
        // Player 0/O is maximizing, Player 1/X is minimizing

        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Perform goal test
        if (moves.isEmpty()) {
            return null;
        }

    }
}
