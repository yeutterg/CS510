Greg Yeutter
CS510 Winter 2015
HW4 - Othello

Contents:
1. Overview
2. Instructions for running
3. Performance

---------------------------
1. Overview:
Source files are found in the folder src, while runnable class files are found in the run
folder. For this assignment, three new Othello players were created:

OthelloGYPlayer: implements the Minimax algorithm
OthelloABPlayer: implements the Alpha-Beta Pruning algorithm
OthelloIDPlayer: applied Iterative Deepening to the Alpha-Beta Pruning algorithm

When OthelloGYPlayer and OthelloABPlayer are instantiated, their depth limits are set with
an integer. For example:

new OthelloGYPlayer(4)

sets the depth limit for this player to 4. Similarly, the time limit, in ms, for a move 
performed by OthelloIDPlayer is set upon instantiation. For example:

new OthelloIDPlayer(100)

sets the time limit for iterative deepening to 100 ms.

Three test classes were created:

TestGY: Player 0/O chooses random possible moves, Player 1/X implements Minimax
TestAB: Player 0/O chooses random possible moves, Player 1/X implements Alpha-Beta Pruning
TestID: Player 0/O chooses random possible moves, Player 1/X implements Iterative Deepening

By default, each implements a board size of 8. TestGY and TestAB have a default depth limit
of 4 and TestID has a default time limit of 100 ms. TestID keeps expanding the search depth
until the time limit, then returns the move that was computed immediately prior to the time
limit.

---------------------------
2. Instructions for running:
The three test classes can be run with their default parameters:

java TestGY
java TestAB
java TestID

or the board size and the depth/time limit for Player 1/X can be specified. For example:

java TestGY 10 5
java TestAB 10 5

set the board size to 10 and the depth limit for Player 1/X to 5. Similarly,

java TestID 12 200

sets the board size to 12 and the time limit to 200 ms. Note that either none or both of
the parameters must be specified, so "java TestID 200" would be invalid.

---------------------------
3. Performance:
The following test cases were considered, each with a board size of 8:

a. Player 0/O: Random, Player 1/X: Minimax with a depth limit of 4
b. Player 0/O: Random, Player 1/X: Alpha-Beta Pruning with a depth limit of 4
c. Player 0/O: Random, Player 1/X: Iterative Deepening with a time limit of 200 ms
d. Player 0/O: Alpha-Beta Pruning with a depth limit of 4, 
    Player 1/X: Minimax with a depth limit of 4
e. Player 0/O: Iterative Deepening with a time limit of 100 ms, 
    Player 1/X: Alpha-Beta Pruning with a depth limit of 4
	
The results are as follows:

a. Player 0/O: Random, Player 1/X: Minimax with a depth limit of 4
Final state with score: -32
XXXXXXXO
XXXXXXXX
XXOOOOXX
XXXOOOXX
XXXOOXXX
XXXOXXXX
XXXXXXXX
XOOOXXOO
Run time: 8339 ms
Total minimax explored states: 1817262

b. Player 0/O: Random, Player 1/X: Alpha-Beta Pruning with a depth limit of 4
Final state with score: -40
XXXOXXXX
XXOOXXXX
XXOXXXXX
XXXXXXXX
XXOXXXXX
XXXOXOXX
XOOOOXXX
OXXXXXXX
Run time: 1046 ms
Total alpha-beta explored states: 79654

c. Player 0/O: Random, Player 1/X: Iterative Deepening with a time limit of 200 ms
Final state with score: -52
.XXXXXXX
XXXXOXXX
XXXXXOXX
XXXXXXOX
XXXXXXOX
XXXXXXOX
XXXXXXXX
XXXXXXX.
Run time: 10015 ms
Total iterative deepening explored states: 2458697

d. Player 0/O: Alpha-Beta Pruning with a depth limit of 4, 
    Player 1/X: Minimax with a depth limit of 4
Final state with score: -20
XXXXXXXX
XXOOOOXX
XOXXXOXX
XOXXOOXX
XXOXXOXO
XXXXXXXO
XXXXXXXO
OOOOOOOO
Run time: 6550 ms
Total alpha-beta explored states: 87653
Total minimax explored states: 1132006

e. Player 0/O: Iterative Deepening with a time limit of 100 ms, 
    Player 1/X: Alpha-Beta Pruning with a depth limit of 4
Final state with score: 8
XXXXXXXO
XXXXXXOO
OOOOOXOO
OOXOOXXO
OXOOOOXO
OXOXXOOO
XXXXXXOO
OOOOOOOO
Run time: 7550 ms
Total iterative deepening explored states: 1319899
Total alpha-beta explored states: 170221


