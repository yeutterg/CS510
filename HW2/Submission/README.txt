CS510 Winter 2015
Greg Yeutter
HW1 Code Submission

1. To run:
a. Make sure all five java classes are in the same folder.
b. Edit the main method in GamePlay.java:
	i. Load the game state in line 7: States.loadGameState(...)
	ii. Specify the value of N in randomWalks(state, N)
c. Run using a suitable Java compiler.
		
2. Program Description:
This program uses two constructors, one for a move (e.g. piece 2, 
left) and one for a piece (e.g. piece 2, can move left and down in 
this state). They are found in the classes Move.java and Piece.java,
respectively. Three additional classes handle the state of the game,
the movement of pieces, and game play. The class GamePlay.java
contains the main method, as well as the random walks function.
The class States.java stores the game state and size, and handles
state cloning, loading and parsing a state from a file, displaying 
the state, goal checking, state comparison, and normalization. The
class Movement.java computes possible moves for each piece and all
pieces, applies moves and move cloning, and finds the locations of
requested pieces.

Admittedly, this code is a bit messy, and there does seem to be an
error where I am getting references instead of values in the Piece
arrays, both of which need to be resolved for submission 2.
