import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloMCPlayer.java: Othello player implementing the Monte Carlo tree search algorithm,
 * with specified number of iterations
 */

public class OthelloMCPlayer extends OthelloPlayer {

    static int iterationsLimit;
    static int explored;

    /*
     * Initialize player and set the depth limit
     */
    public OthelloMCPlayer(int iterations) {
        if (iterations < 1) {
            System.out.println("Error: Iterations limit must be >= 1.");
            System.exit(-1);
        }
        iterationsLimit = iterations;
    }

    /*
     * Get the best move as determined by Monte Carlo Search
     */
    @Override
    public OthelloMove getMove(OthelloState state) {
        // Player 0/O is maximizing, Player 1/X is minimizing

        OthelloNode root = createNode(state);

        for (int i = 0; i < iterationsLimit; i++) {

        }

        return bestChild(root);

        // Generate possible moves for current state
        List<OthelloMove> moves = state.generateMoves();

        // Pass if no possible moves
        if (moves.isEmpty()) {
            return null;
        }


    }

    /*
     * Create a game tree node from the given state
     */
    private static OthelloNode createNode(OthelloState state) {

    }

    /*
     * Return the child with the max or min average score, depending on whether the player
     * is minimizing or maximizing
     */
    private static OthelloMove bestChild(OthelloNode node) {
        OthelloState state = node.getState();
        List<OthelloNode> children = node.getChildren();

        // Decide if player is maximizing or minimizing
        if (state.nextPlayerToMove == 0) {
            // return child with max score
            return maxDecision(state, moves);
        } else {
            // return child with min score
            return minDecision(state, moves);
        }
    }

    /*
     * Generate new node not in the tree and return it, return terminal nodes,
     * if all children already in tree, implement epsilon-greedy strategy
     */
    private static OthelloNode treePolicy(OthelloNode node) {

    }

    /*
     * Select random actions for each player and return final game state node
     */
    private static OthelloNode defaultPolicy(OthelloNode node) {
        OthelloState state = node.getState();
        Random r = new Random;

        // Generate moves
        List<OthelloMove> moves = state.generateMoves();

        // select one at random
        OthelloMove move;
        if (!moves.isEmpty()) move = moves.get(r.nextInt(moves.size()));
        else move = null;

        // apply move to generate new state, node
        OthelloState newState = state.applyMoveCloning(move);
        // TODO finish this

    }

    /*
     * Return the score of the game for the given node
     */
    private static int score(OthelloNode node) {
        OthelloState state = node.getState();
        return state.score();
    }

    /*
     * Increment the number of times the node has been visited and update node's average
     * score. Call backup on parent node, if it exists.
     */
    private static void backup(OthelloNode node, int score) {
        // Increment number of times node visited
        node.setTimesVisited(node.getTimesVisited() + 1);

        // Update node's average score
        List<Integer> scores = node.getScores();
        scores.add(score);
        int total = 0;
        for (int s : scores) total += s;
        int avg = total/scores.size();
        node.setScores(scores);
        node.setAvgScore(avg);

        // Call backup on parent node if it exists
        if (node.getParent() != null) backup(node.getParent(), score);
    }
}
