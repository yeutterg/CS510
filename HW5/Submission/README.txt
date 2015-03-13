Greg Yeutter
CS510 Winter 2015
HW5 - Othello Part 2
March 13, 2015

Table of Contents:
1. Contents of submission
2. Instructions for running
3. Task description
4. Performance evaluation
5. References

-----
1. Contents of submission

Source code (*.java files) is found in the src folder. Executable files (*.class files) are found in
the run folder. The following classes were added or modified:

    OthelloState: Improved evaluation function was added (OthelloState.evaluation())
    OthelloGYEPlayer: Minimax agent with updated evaluation function
    OthelloGYTPlayer: Modified tournament version of OthelloGYEPlayer, with specified time limit
    OthelloNode: Constructor for a node, used in tree search
    OthelloMCPlayer: Othello agent implementing iterations-limited Monte Carlo tree search
    OthelloMCTPlayer: Modified tournament version of OthelloMCPlayer, with specified time limit
    TestR_GYE: Run a random agent versus OthelloGYEPlayer
    TestR_MC: Run a random agent versus OthelloMCPlayer
    TestGYE_MC: Run OthelloGYEPlayer versus OthelloMCPlayer
    TestGYT_MCT: Run OthelloGYTPlayer versus OthelloMCTPlayer in a tournament
	
-----
2. Instructions for running

Executable files can be run from the run folder. Depending on the environment, one should be able to
run "java classname" on any of the test classes. Each class can be run with or without arguments. E.g.,
to run TestGYE_MC.class with no arguments, the input would be "java TestGYE_MC". To run 
TestGYE_MC.class and specify the board size, minimax depth limit, and Monte Carlo iteration limit, run
"java TestGYE_MC 8 3 1000", for example. 

The test classes in detail:

a. TestR_GYE: 
    Player 0: OthelloRandomPlayer, Player 1: OthelloGYEPlayer
    arguments:
        0: board size. e.g. 8 = 8x8 board
	1: depth limit for player 1
    If no arguments specified: 8x8 board, depth limit of 4 for Player 1
    "java TestR_GYE"
    "java TestR_GYE 8 5"
	
b. TestR_MC: 
    Player 0: OthelloRandomPlayer, Player 1: OthelloMCPlayer
    arguments:
	0: board size
	1: iteration limit for player 1
    If no arguments specified: 8x8 board, iteration limit of 10000 for Player 1
    "java TestR_MC"
    "java TestR_MC 8 20000"
	
c. TestGYE_MC:
    Player 0: OthelloGYEPlayer, Player 1: OthelloMCPlayer
    arguments:
	0: board size
	1: depth limit for player 0
	2: iteration limit for player 1
    If no arguments specified: 8x8 board, depth limit of 4 for Player 0, iteration limit of 20000 for
    Player 1
    "java TestGYE_MC"
    "java TestGYE_MC 8 3 10000"
	
d. TestGYT_MCT
    Player 0: OthelloGYTPlayer, Player 1: OthelloMCTPlayer
    arguments:
	0: board size
	1: time limit in ms for both players 
    If no arguments specified: 8x8 board, time limit of 2000 ms for both players
    "java TestGYT_MCT"
    "java TestGYT_MCT 8 1000"
	
-----
3. Task description

    Subcontents:
    a. Improved evaluation function
    b. Monte Carlo tree search    

a. Improved evaluation function

    An improved Othello evaluation function was created, based on several well-known strategies
    that increase the probability of winning. This was based on the original OthelloState.score()
    function, which tallies the total quantity of each player. The following strategies were then
    added to form the OthelloState.evaluation() function:

        i. Corner bonus
        ii. C/X penalty
        iii. Frontier minimization bonus
        iv. Edge creep bonus
	
    An example 8x8 Othello Board is shown to explain positions:

           a  b  c  d  e  f  g  h
        1  -  -  -  -  -  -  -  -
	2  -  -  -  -  -  -  -  -
	3  -  -  -  -  -  -  -  -
	4  -  -  -  -  -  -  -  -
	5  -  -  -  -  -  -  -  -
	6  -  -  -  -  -  -  -  -
	7  -  -  -  -  -  -  -  -
	8  -  -  -  -  -  -  -  -
	
    This board is also explained in terms of compass directions, e.g. the top row is the north edge
    and the bottom-left corner is the southwest corner.
	
    i. Corner bonus: The corner positions (a1, h1, a8, h8) are good squares to occupy because they
    are stable, meaning the discs in these positions can never be flipped once placed. So, if a 
    player occupies a corner, he receives a bonus, in this case 5 points. E.g.: for every corner
    player 1 occupies, the total score increases by 5 points. Similarly, for every corner player
    2 occupies, the total score decreases by 5 points.

    ii. C/X penalty: C-squares (a2, a7, b1, b8, g1, g8, h2, h7) and x-squares (b2, b7, g2, g7) should
    be avoided in most cases because they make it easy for the opponent to occupy a highly-desirable
    corner. Thus, for the northwest corner, if a player occupies b1, a2, or b2, but not a1, his 
    opponent may be able to capture that corner. However, if a single player occupies both the corner
    and a c- or x-square, his chances of winning are even stronger than just occupying a corner, since
    edge pieces have a lower probability of being flipped than pieces not along the edge. In this 
    implementation, for each corner, if a player occupies a c- or x-square but not the corner, it gets 
    a 5-point penalty, but if the player also occupies the corner, it gets a 5-point bonus instead.

    iii. Frontier minimization bonus: Strategic players like to minimize the number of pieces on the
    frontier (pieces with adjacent empty cells), because the opponent may be able to capture those 
    pieces. Here, for each player, the number of pieces with at least one adjacent empty cell is
    computed. Then, the player with the smallest number of pieces on the frontier is given a bonus. If
    both players have an equal number of pieces on the frontier, no bonus is awarded.

    iv. Edge creep bonus: Edge creeping is a strategy in which positions on the edges of the board are
    chosen, while leaving the frontier open to the opponent. The opponent may quickly run out of moves, 
    although the strategy could backfire for the creeper. Here, for each edge, a bonus is given to the
    player that has at least half the board width in occupied positions along that edge (in an 8x8 game, 
    at least 4 positions). If both players have 4 occupied positions, no bonus is awarded. 

    With these measures, several key strategies can be considered and weighted when searching through 
    possible moves. While the bonus value is arbitrary, and multiple strategies are excluded from this 
    evaluation, it is likely that this function performs better than the score() function in many game 
    scenarios. 

    The new evaluation function is applied in both OthelloGYEPlayer (depth-limited minimax) and 
    OthelloGYTPlayer (time-limited minimax). Performance is evaluated in section 4.

    References for this problem are [1][2].

b. Monte Carlo tree search

    The Monte Carlo Tree Search algorithm was implemented in both OthelloMCPlayer (with specified
    iterations limit) and OthelloMCTPlayer (with specified time limit). In addition, a new constructor,
    OthelloNode, was created. Each OthelloNode holds the state of the current node, the parent node,
    all child nodes, the list of actions required to get to that node, the number of times the node has
    been visited, a list of all assigned scores, and the most-recently computed average score. The node
    tree was then specified as a list of OthelloNode objects within the player class. 

    When OthelloMCTPlayer is called, either the time limit (in ms) or both the time limit and iteration 
    increment can be specified. For example, OthelloMCTPlayer(2000) sets the time limit to 2000 ms and 
    the initial iteration limit to the default of 1000. If time remains to raise the iterations limit,
    it raises by 1000 until the deadline. If OthelloMCTPlayer(2000, 3000) is specified, the time limit
    is set to 2000 ms, the initial iteration limit is set to 3000, and the iteration limit always 
    increases by 3000. 

    Performance for iteration-limited and time-limited Monte Carlo search is evaluated in section 4.

-----
4. Performance evaluation

    Subcontents:
    a. OthelloRandomPlayer() vs. OthelloGYEPlayer(4)
    b. OthelloGYEPlayer(4) vs. OthelloGYPlayer(4)
    c. OthelloGYPlayer(5) vs. OthelloGYEPlayer(3)
    d. OthelloGYTPlayer(500) vs. OthelloIDPlayer(500)*
    e. OthelloRandomPlayer() vs. OthelloMCPlayer(10000)
    f. OthelloMCPlayer(10000) vs. OthelloGYEPlayer(3)
    g. OthelloIDPlayer(500)* vs. OthelloMCTPlayer(500)
    h. OthelloMCTPlayer(1000) vs. OthelloGYTPlayer(1000) 
    * - OthelloIDPlayer is the iterative deepening alpha-beta pruning tournament agent from HW4

a. OthelloRandomPlayer() vs. OthelloGYEPlayer(4)

    Run time: 6697 ms
    Final state with score: -40
    XXXXXXXX
    XOOOOXXX
    XXXXXXXX
    XOOOXXXX
    XXXXXXXX
    XXXXXXXX
    XOOOOXXO
    XXXXXXXX
    Total minimax (evaluation()) explored states: 1231946

    Remark: minimax with improved evaluation function performs better than random move selection

b. OthelloGYEPlayer(4) vs. OthelloGYPlayer(4) 

    Run time: 12986 ms
    Final state with score: 24
    OOOOXOOO
    XOOXOXXO
    XOXOOOOO
    XXOOXOOO
    XXXOOXOO
    OXXOOXXO
    OOOOOOXX
    OOOOOOOO
    Total minimax (evaluation()) explored states: 1779781
    Total minimax (score()) explored states: 1234035

    Remark: evaluation() performs better than score() for minimax at the same depth limit

c. OthelloGYPlayer(5) vs. OthelloGYEPlayer(3)

    Run time: 21431 ms
    Final state with score: -42
    XXXXXXXX
    XXXXXOOX
    XXXXOXOX
    XOXXXXXX
    XXOXXXXX
    XOXOXXXX
    XOOXXXXX
    XOXXXXXX
    Total minimax (score()) explored states: 5930824
    Total minimax (evaluation()) explored states: 96736

    Remark: even at a lower depth limit, evaluation() outperforms score() for minimax

d. OthelloGYTPlayer(500) vs. OthelloIDPlayer(500)

    Run time: 64138 ms
    Final state with score: -38
    XXXXXXXX
    XXOOOXXX
    XXXOXOXX
    XXXXOXXX
    XXXXXOXX
    XXXXOXXO
    XXXOOXXO
    XXXXXXXO
    Total minimax (tournament/evaluation()) explored states: 9161859
    Total iterative deepening explored states: 6262227

    Remark: iterative deepening alpha-beta pruning (using score()) outperforms minimax, even if 
    minimax implements evaluation()

e. OthelloRandomPlayer() vs. OthelloMCPlayer(10000)

    Run time: 23229 ms
    Final state with score: -30
    XXXXXXXX
    XOOOXXXX
    OOOOOOXX
    XOOOXXXX
    XXOOOXXX
    XXXOXXXX
    XXXXXXXX
    XXXXXXXO
    Total Monte Carlo explored nodes: 320032

    Remark: Monte carlo outperforms random move selection

f. f. OthelloMCPlayer(10000) vs. OthelloGYEPlayer(3)

    Run time: 23076 ms
    Final state with score: -58
    XXXXXXXX
    XXXXXXXX
    XXXXXXXX
    XXXXXXXX
    XXOXXXXX
    XXXXOXXX
    XXXXXOXX
    XXXXXXXX
    Total minimax (evaluation()) explored states: 121410
    Total Monte Carlo explored nodes: 300030

    Remark: At this depth/iteration limit, minimax with evalutaion() outperforms Monte Carlo

g. OthelloIDPlayer(500) vs. OthelloMCTPlayer(500)
    
    Run time: 51687 ms
    Final state with score: 16
    OOOOOOOX
    OOOOXOOX
    OOOXXXOX
    OXXOOXOX
    OXOXOXXX
    OOXXOXXX
    OOOOOOXX
    OOOOOOOX
    Total iterative deepening explored states: 7487136
    Total Monte Carlo (tournament) explored nodes: 310031

    Remark: at this time limit, iterative deepening (alpha-beta pruning) slightly outperforms
    Monte Carlo. However, Monte Carlo is much more compact in terms of the number of explored
    states/nodes

h. OthelloMCTPlayer(1000) vs. OthelloGYTPlayer(1000) 

    Run time: 141537 ms
    Final state with score: -36
    XXXXXXXX    
    XXOXXXXX
    XXXXXXXX    
    XXXXXOXX
    XXOOOXOO
    XXXOXXXX
    OOOOXXXX
    XXXXXOOX
    Total Monte Carlo (tournament) explored nodes: 445030
    Total minimax (tournament/evaluation()) explored states: 24974978
 
    Remark: Minimax with evaluation() outperforms Monte Carlo with score() at this time limit.
    However, the number of explored nodes/states by Monte Carlo is again much smaller

-----
5. References

[1] http://en.wikipedia.org/wiki/Reversi
[2] http://radagast.se/othello/Help/strategy.html
