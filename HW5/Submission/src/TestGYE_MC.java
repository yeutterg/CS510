/**
 * Greg Yeutter - CS510 Winter 2015
 * Test Minimax with improved evaluation vs Monte Carlo
 */
public class TestGYE_MC {
    
    
    public static void main(String args[]) {
        // Create the game state with the initial position for a nxn board:
		int argsi[] = new int[3];
		if (args.length == 0) {
		    argsi[0] = 8;
			argsi[1] = 4;
			argsi[2] = 20000;
		} else {
		    argsi[0] = Integer.parseInt(args[0]);
			argsi[1] = Integer.parseInt(args[1]);
			argsi[2] = Integer.parseInt(args[2]);
		}
        OthelloState state = new OthelloState(argsi[0]);
        OthelloPlayer players[] = {new OthelloGYEPlayer(argsi[1]),
                                   new OthelloMCPlayer(argsi[2])};
        
        do{
            // Display the current state in the console:
            System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
            System.out.print(state);
            
            // Get the move from the player:
            OthelloMove move = players[state.nextPlayerToMove].getMove(state);            
            System.out.println(move);
            state = state.applyMoveCloning(move);            
        }while(!state.gameOver());

        // Show the result of the game:
        System.out.println("\nFinal state with score: " + state.score());
        System.out.println(state);
    }    
    
}
