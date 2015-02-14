Greg Yeutter
CS510 Artificial Intelligence
Winter 2015
HW3.2 Documentation
2/13/15

1. Running Instructions
From the run folder, one can perform an A* search with the following command:

java AStarSearch filePath

where filePath is the *.txt file with the initial state. The output should be
the solution path, followed by the solved puzzle.


2. Description
This week, A* graph search was implemented for the sliding block puzzle. The following
functions and classes were added:

AStarSearch: the class through which A* with a Manhattan distance heuristic can be run
SearchGeneration:
-manhattanDistance: the function for computing Manhattan distance
-computeManhattanHeuristicCost: the function to compute and set the f(n) and h(n) values
    for each node
-lowFPop: the function to remove the (first) lowest-value node from the frontier and set
    it as the current node
Node:
-int values storing the heuristic and total cost were added to the constructor

These classes can be examined in the src folder.

Additionally, the entire code base was cleaned up to (finally) fix state reference errors.
The legacy RandomWalks, BreadthFirstSearch, and DepthFirstSearch functions can be run as
follows from the run folder:
java RandomWalks filePath N // where N is the depth limit. If it is ommitted, the default
    value is 100
java BreadthFirstSearch filePath
java DepthFirstSearch filePath