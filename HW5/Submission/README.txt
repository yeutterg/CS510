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


-----
2. Instructions for running


-----
3. Task description

a. Evaluation function
An improved Othello evaluation function was created, based on several well-known strategies
that increase the probability of winning. This was based on the original OthelloState.score()
function, which tallies the total quantity of each player. The following strategies were then
implemented to form the OthelloState.evaluation() function:

    i. Corner bonus
	ii. C/X penalty
	iii. Frontier minimization bonus
	iv. Edge creep bonus
	
An example 8x8 Othello Board is given to explain positions:

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
player occupies a corner, he receives a bonus, in this case 10 points. E.g.: for every corner
player 1 occupies, the total score increases by 10 points. Similarly, for every corner player
2 occupies, the total score decreases by 10 points.

ii. C/X penalty: C-squares (a2, a7, b1, b8, g1, g8, h2, h7) and x-squares (b2, b7, g2, g7) should
be avoided in most cases because they make it easy for the opponent to occupy a highly-desirable
corner. Thus, for the northwest corner, if a player occupies b1, a2, or b2, but not a1, his 
opponent may be able to capture that corner. However, if a single player occupies both the corner
and a c- or x-square, his chances of winning are even stronger than just occupying a corner, since
edge pieces have a lower probability of being flipped than pieces not along the edge. In this 
implementation, for each corner, if a player occupies a c- or x-square but not the corner, it gets 
a 10-point penalty, but if the player also occupies the corner, it gets a 10-point bonus instead.

iii. Frontier minimization bonus: Strategic players like to minimize the number of pieces on the
frontier (pieces with adjacent empty cells), because the opponent may be able to capture those 
pieces. Here, for each player, the number of pieces with at least one adjacent empty cell is
computed. Then, the player with the smallest number of pieces on the frontier is given a bonus. If
both players have an equal number of pieces on the frontier, no bonus is awarded.

iv. Edge creep bonus: Edge creeping is a strategy in which positions on the edges of the board are
chosen, while leaving the frontier to the opponent. The opponent may quickly run out of moves, 
although the strategy could backfire for the creeper. Here, for each edge, a bonus is given to the
player that has at least half the board width in occupied positions along that edge (in an 8x8 game, 
at least 4 positions). If both players have 4 occupied positions, no bonus is awarded. 

With these measures, several key strategies can be considered when searching through possible moves.
While the bonus value is arbitrary, and multiple strategies are excluded from this evaluation, it is 
likely that this function performs better than the score() function in many game scenarios. 
Performance is evaluated in section 4.

References for this problem are [1][2].


b. Monte carlo tree search


-----
4. Performance evaluation


-----
5. References
[1] http://en.wikipedia.org/wiki/Reversi
[2] http://radagast.se/othello/Help/strategy.html
