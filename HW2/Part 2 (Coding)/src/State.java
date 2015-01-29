/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * State.java: Constructor for a search state
 */

public class State {

    public int[][] positions;
    public int width;
    public int height;

    public State(int[][] positions, int width, int height) {
        this.positions = positions;
        this.width = width;
        this.height = height;
    }

    public State(State givenState) {
        this.positions = givenState.getPositions();
        this.width = givenState.getWidth();
        this.height = givenState.getHeight();
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
}
