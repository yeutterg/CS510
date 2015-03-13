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
    static List<OthelloNode> tree;

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
        if (state.generateMoves().isEmpty()) return null; // no possible moves

        OthelloNode root = createNode(state);
        tree = new ArrayList<OthelloNode>();
        tree.add(root);

        for (int i = 0; i < iterationsLimit; i++) {
            OthelloNode node = treePolicy(root);
            if (node != null) {
                OthelloNode node2 = defaultPolicy(node);
                int node2Score = score(node2);
                backup(node, node2Score);
            }
        }

        return bestChild(root).getActions().get(0);
    }

    /*
     * Create a game tree node from the given state
     */
    private static OthelloNode createNode(OthelloState state) {
        OthelloNode node = new OthelloNode(null, state, new ArrayList<OthelloNode>(),
                new ArrayList<OthelloMove>(), 1, new ArrayList<Integer>(), 0.0);
        node.initScore();
        node.initChildren();
        explored++;
        return node;
    }

    /*
     * Return the child with the max or min average score, depending on whether the player
     * is minimizing or maximizing
     */
    private static OthelloNode bestChild(OthelloNode node) {
        OthelloState state = node.getState();
        List<OthelloNode> children = node.getChildren();
        if (children.isEmpty()) return node;
        int index = 0;

        // Decide if player is maximizing or minimizing
        if (state.nextPlayerToMove == 0) {
            // find child with max score
            Double maxScore = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < children.size(); i++) {
                OthelloNode n = children.get(i);
                if (n.getAvgScore() > maxScore) {
                    maxScore = n.getAvgScore();
                    index = i;
                }
            }
        } else {
            // find child with min score
            Double minScore = Double.POSITIVE_INFINITY;
            for (int i = 0; i < children.size(); i++) {
                OthelloNode n = children.get(i);
                if (n.getAvgScore() < minScore) {
                    minScore = n.getAvgScore();
                    index = i;
                }
            }
        }
        return children.get(index);
    }

    /*
     * Generate new node not in the tree and return it, return terminal nodes,
     * if all children already in tree, implement epsilon-greedy strategy
     */
    private static OthelloNode treePolicy(OthelloNode node) {
        List<OthelloNode> children = node.getChildren();

        // If node is terminal (no children), return it
        if (children.isEmpty()) return node;

        // Check if any children not in the tree, add the first one encountered and return it
        for (OthelloNode n : children)
            if (!tree.contains(n)) {
                tree.add(n);
                explored++;
                return n;
            }

        // If node not terminal but all children in tree, implement epsilon-greedy strategy
        // 10% of the time return a random child, 90% of the time return the best child
        OthelloNode nodeTmp;
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance <= 0.10f) nodeTmp = children.get(r.nextInt(children.size()));
        else nodeTmp = bestChild(node);
        explored++;
        return treePolicy(nodeTmp);
    }

    /*
     * Select random actions for each player and return final game state node
     */
    private static OthelloNode defaultPolicy(OthelloNode node) {
        OthelloState state = node.getState();
        Random r = new Random();

        while (!node.getChildren().isEmpty()) {
            // Generate moves
            List<OthelloMove> moves = state.generateMoves();

            // select one at random
            OthelloMove move;
            if (!moves.isEmpty()) move = moves.get(r.nextInt(moves.size()));
            else move = null;

            // apply move to generate new state, node
            List<OthelloMove> newMoves = node.getActions();
            newMoves.add(move);
            OthelloState newState = state.applyMoveCloning(move);
            node = new OthelloNode(node, newState, new ArrayList<OthelloNode>(), newMoves, 1,
                    new ArrayList<Integer>(), 0.0);
            node.initScore();
            explored++;
            tree.add(node);
        }

        return node;
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
        Double total = 0.0;
        for (int s : scores) total += s;
        Double avg = total/scores.size();
        node.setScores(scores);
        node.setAvgScore(avg);

        // Call backup on parent node if it exists
        if (node.getParent() != null) backup(node.getParent(), score);
    }
}
