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

    public OthelloNode(OthelloNode parent, OthelloState state, int timesVisited, List<OthelloMove> actions) {
        this.parent = parent;
        this.state = state;
        this.timesVisited = timesVisited;
        this.actions = actions;
    }
}
