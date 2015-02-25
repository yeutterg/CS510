/**
 *
 * @author santi
 */
public class Test {
    
    
    public static void main(String args[]) {
        // Create the game state with the initial position for an 8x8 board:
        OthelloState state = new OthelloState(8);
        OthelloPlayer players[] = {new OthelloRandomPlayer(),
                                   new OthelloIDPlayer(50)};
        
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
        //System.out.println("AB Explored: " + OthelloABPlayer.explored);
        //System.out.println("Minimax Explored: " + OthelloGYPlayer.explored);
        System.out.println(state);
    }    
    
}
