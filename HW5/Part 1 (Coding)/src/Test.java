/**
 *
 * @author santi
 */
public class Test {
    
    
    public static void main(String args[]) {
        // Create the game state with the initial position for an 8x8 board:
        long startTime = System.currentTimeMillis();
        OthelloState state = new OthelloState(8);
        OthelloPlayer players[] = {new OthelloMCTPlayer(500, 2000),
                                   new OthelloMCTPlayer(500, 4000)};
        
        do{
            // Display the current state in the console:
            System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
            System.out.print(state);
            
            // Get the move from the player:
            OthelloMove move = players[state.nextPlayerToMove].getMove(state);            
            System.out.println(move);
            state = state.applyMoveCloning(move);            
        }while(!state.gameOver());
        long endTime = System.currentTimeMillis();

        // Show the result of the game:
        System.out.println("\nRun time: " + (endTime - startTime) + " ms");
        System.out.println("Final state with score: " + state.score());
        System.out.println(state);
        System.out.println("AB Explored: " + OthelloABPlayer.explored);
        System.out.println("Minimax Explored: " + OthelloGYPlayer.explored);
        System.out.println("Minimax (eval) Explored: " + OthelloGYEPlayer.explored);
        System.out.println("Minimax (eval/tournament) Explored: " + OthelloGYTPlayer.explored);
        System.out.println("Iterative deepening explored: " + OthelloIDPlayer.explored);
        System.out.println("Monte Carlo explored nodes: " + OthelloMCPlayer.explored);
        System.out.println("Monte Carlo tournament explored nodes: " + OthelloMCTPlayer.explored);

    }    
    
}
