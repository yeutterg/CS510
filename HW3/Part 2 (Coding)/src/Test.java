/**
 * Created by Greg on 2/12/2015.
 */
public class Test {
    public static void main(String[] args) {
        String[] inFile = new String[1];
        inFile[0] = "D:/Academic/Winter 2015/CS 510 Artificial Intelligence/Homework/HW3/HW3/SBP-level0.txt";
        //BreadthFirstSearch.main(inFile);
        //DepthFirstSearch.main(inFile);

        String[] input = new String[2];
        input[0] = inFile[0];
        input[1] = "1000";
        RandomWalks.main(input);
    }

}
