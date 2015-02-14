/**
 * Created by Greg on 2/12/2015.
 */
public class Test {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String[] inFile = new String[1];
        inFile[0] = "D:/Academic/Winter 2015/CS 510 Artificial Intelligence/Homework/HW3/HW3/SBP-level3.txt";
        //BreadthFirstSearch.main(inFile);
//        DepthFirstSearch.main(inFile);
        AStarSearch.main(inFile);



        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("time " + totalTime);
        System.out.println("nodes explored " + SearchGeneration.explored.size());
        System.out.println("solution len " + SearchGeneration.childNode.getAction().size());

//        String[] input = new String[2];
//        input[0] = inFile[0];
//        input[1] = "1000";
//        RandomWalks.main(input);
    }

}
