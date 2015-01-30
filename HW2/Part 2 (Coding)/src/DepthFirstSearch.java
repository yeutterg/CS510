/**
 * CS510 Winter 2015
 * Greg Yeutter
 * gwy23@drexel.edu
 * DepthFirstSearch.java: Apply breadth first search algorithm to find solution of legal moves
 */

public class DepthFirstSearch {

    public static void main(String fileName) {
        // Load in a state
        State newState = StateGeneration.loadState(fileName);

        // Execute breadth-first search (HW2 2.2)
        depthFirstProblem(newState);
    }

    public static void depthFirstProblem(State givenState) {

    }
}
