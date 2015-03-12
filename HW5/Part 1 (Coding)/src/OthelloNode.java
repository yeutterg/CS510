import java.util.ArrayList;
import java.util.List;

/**
 * CS510 Winter 2015
 * Greg Yeutter
 * OthelloNode.java: Constructor for an Othello Node
 */

public class OthelloNode {

    public OthelloNode parent;
    public OthelloState state;
    public List<OthelloNode> children;
    public List<OthelloMove> actions;
    public int timesVisited;
    public List<Integer> scores;
    public Double avgScore;

    public OthelloNode getParent() {
        return parent;
    }

    public void setParent(OthelloNode parent) {
        this.parent = parent;
    }

    public OthelloState getState() {
        return state;
    }

    public void setState(OthelloState state) {
        this.state = state;
    }

    public List<OthelloNode> getChildren() {
        return children;
    }

    public void setChildren(List<OthelloNode> children) {
        this.children = children;
    }

    public List<OthelloMove> getActions() {
        return actions;
    }

    public void setActions(List<OthelloMove> actions) {
        this.actions = actions;
    }

    public int getTimesVisited() {
        return timesVisited;
    }

    public void setTimesVisited(int timesVisited) {
        this.timesVisited = timesVisited;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    /*
     * Initialize the score for this node
     */
    public void initScore() {
        int score = this.state.score();
        this.scores.add(score);
        this.avgScore = (double) score;
    }

    /*
     * Initialize the children for this node
     */
    public void initChildren() {
        List<OthelloMove> moves = this.state.generateMoves();
        for (OthelloMove m : moves) {
            OthelloState s = this.state.applyMoveCloning(m);
            List<OthelloMove> childMove = this.getActions();
            childMove.add(m);
            OthelloNode c = new OthelloNode(this, s, new ArrayList<OthelloNode>(),childMove, 0,
                    new ArrayList<Integer>(), 0.0);
            c.initScore();
            this.children.add(c);
        }
    }

    public OthelloNode(OthelloNode parent, OthelloState state, List<OthelloNode> children, List<OthelloMove> actions,
                       int timesVisited, List<Integer> scores, Double avgScore) {
        this.parent = parent;
        this.state = state;
        this.children = children;
        this.actions = actions;
        this.timesVisited = timesVisited;
        this.scores = scores;
        this.avgScore = avgScore;
    }

}
