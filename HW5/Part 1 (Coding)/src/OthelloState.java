import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author santi
 */
public class OthelloState {
    
    
    public static final int NOTHING = -1;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;
    
    public static final String PLAYER_NAMES[] = {"O","X"};
    
    
    int boardSize = 8;
    int board[][] = null;
    int nextPlayerToMove = PLAYER1;
    
    
    /*
     * Constructor of the game state, it creates a board with the initial state for the game of Othello    
     */
    public OthelloState(int a_boardSize) {
        boardSize = a_boardSize;
        if (boardSize<2) boardSize = 2;
        board = new int[boardSize][boardSize];
        for(int i = 0;i<boardSize;i++)
            for(int j = 0;j<boardSize;j++)
                board[i][j] = NOTHING;
        // initial position:
        board[boardSize/2-1][boardSize/2-1] = PLAYER1;
        board[boardSize/2][boardSize/2] = PLAYER1;
        board[boardSize/2-1][boardSize/2] = PLAYER2;
        board[boardSize/2][boardSize/2-1] = PLAYER2;
    }
    
    
    /*
     * Converts a game board to a string, for displaying it via the console    
     */
    public String toString() {
        StringBuffer output = new StringBuffer();
        for(int j = 0;j<boardSize;j++) {
            for(int i = 0;i<boardSize;i++) {
                if (board[i][j]==PLAYER1) output.append(PLAYER_NAMES[PLAYER1]);
                else if (board[i][j]==PLAYER2) output.append(PLAYER_NAMES[PLAYER2]);
                else output.append(".");
            }
            output.append("\n");
        }
        return output.toString();
    }
    
    
    /*
     * Makes a copy of a game state
     */
    public OthelloState clone() {
        OthelloState newState = new OthelloState(boardSize);
        for(int i = 0;i<boardSize;i++)
            for(int j = 0;j<boardSize;j++)
                newState.board[i][j] = board[i][j];
        newState.nextPlayerToMove = nextPlayerToMove;
        return newState;
    }
    
    
    /*
     * Determines whether the game is over or not
     */
    public boolean gameOver() {
        if (generateMoves(PLAYER1).isEmpty() &&
            generateMoves(PLAYER2).isEmpty()) return true;
        return false;
    }
    
    
    /*
     * Returns the final score, once a game is over
     */
    public int score() {
        int score = 0;
        for(int i = 0;i<boardSize;i++)
            for(int j = 0;j<boardSize;j++) {
                if (board[i][j]==PLAYER1) score++;
                if (board[i][j]==PLAYER2) score--;
            }
        return score;
    }

    /*
     * Updated evaluation function (HW5)
     */
    public int evaluation() {
        // Set bonus value
        int bonus = 10;

        // Count number of pieces for each player for baseline score
        int score = score();

        // Corner bonus - Bonus points for player occupying stable corners
        if (board[0][0] == PLAYER1) score += bonus; // nw
        if (board[0][boardSize-1] == PLAYER1) score += bonus; // sw
        if (board[boardSize-1][0] == PLAYER1) score += bonus; // ne
        if (board[boardSize-1][boardSize-1] == PLAYER1) score += bonus; // se
        if (board[0][0] == PLAYER2) score -= bonus; // nw
        if (board[0][boardSize-1] == PLAYER2) score -= bonus; // sw
        if (board[boardSize-1][0] == PLAYER2) score -= bonus; // ne
        if (board[boardSize-1][boardSize-1] == PLAYER2) score -= bonus; // se

        // C/X Penalty - Negative points for occupying unstable squares adjacent to corners
        // (c- and x-squares), unless stable corner also occupied (then add additional bonus points)
        // Northwest
        if (board[0][1]==PLAYER1 || board[1][0]==PLAYER1 || board[1][1]==PLAYER1) {
            if (board[0][0]!= PLAYER1) score -= bonus;
            if (board[0][0]==PLAYER1) score += bonus;
        }
        if (board[0][1]==PLAYER2 || board[1][0]==PLAYER2 || board[1][1]==PLAYER2) {
            if (board[0][0]!= PLAYER2) score += bonus;
            if (board[0][0]==PLAYER2) score -= bonus;
        }
        // Southwest
        if (board[0][boardSize-2]==PLAYER1 || board[1][boardSize-1]==PLAYER1 || board[1][boardSize-2]==PLAYER1) {
            if (board[0][boardSize-1]!= PLAYER1) score -= bonus;
            if (board[0][boardSize-1]==PLAYER1) score += bonus;
        }
        if (board[0][boardSize-2]==PLAYER2 || board[1][boardSize-1]==PLAYER2 || board[1][boardSize-2]==PLAYER2) {
            if (board[0][boardSize-1]!= PLAYER2) score += bonus;
            if (board[0][boardSize-1]==PLAYER2) score -= bonus;
        }
        // Northeast
        if (board[boardSize-2][0]==PLAYER1 || board[boardSize-1][1]==PLAYER1 || board[boardSize-2][1]==PLAYER1) {
            if (board[boardSize-1][0]!= PLAYER1) score -= bonus;
            if (board[boardSize-1][0]==PLAYER1) score += bonus;
        }
        if (board[boardSize-2][0]==PLAYER2 || board[boardSize-1][1]==PLAYER2 || board[boardSize-2][1]==PLAYER2) {
            if (board[boardSize-1][0]!= PLAYER2) score += bonus;
            if (board[boardSize-1][0]==PLAYER2) score -= bonus;
        }
        // Southeast
        if (board[boardSize-2][boardSize-2]==PLAYER1 || board[boardSize-1][boardSize-2]==PLAYER1 ||
                board[boardSize-2][boardSize-1]==PLAYER1) {
            if (board[boardSize-1][boardSize-1]!= PLAYER1) score -= bonus;
            if (board[boardSize-1][boardSize-1]==PLAYER1) score += bonus;
        }
        if (board[boardSize-2][boardSize-2]==PLAYER2 || board[boardSize-1][boardSize-2]==PLAYER2 ||
                board[boardSize-2][boardSize-1]==PLAYER2) {
            if (board[boardSize-1][boardSize-1]!= PLAYER2) score += bonus;
            if (board[boardSize-1][boardSize-1]==PLAYER2) score -= bonus;
        }

        // Frontier minimization bonus - Player with least # of positions along frontier
        // (>0 adjacent cells empty) gets bonus
        int posFrontier = 0;
        int negFrontier = 0;
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == PLAYER1) {
                    if (board[i - 1][j - 1] == NOTHING ||
                            board[i][j - 1] == NOTHING ||
                            board[i + 1][j - 1] == NOTHING ||
                            board[i - 1][j] == NOTHING ||
                            board[i + 1][j] == NOTHING ||
                            board[i - 1][j + 1] == NOTHING ||
                            board[i][j + 1] == NOTHING ||
                            board[i + 1][j + 1] == NOTHING)
                        posFrontier++;
                }
                if (board[i][j] == PLAYER2)
                    if (board[i - 1][j - 1] == NOTHING ||
                            board[i][j - 1] == NOTHING ||
                            board[i + 1][j - 1] == NOTHING ||
                            board[i - 1][j] == NOTHING ||
                            board[i + 1][j] == NOTHING ||
                            board[i - 1][j + 1] == NOTHING ||
                            board[i][j + 1] == NOTHING ||
                            board[i + 1][j + 1] == NOTHING)
                        negFrontier++;
            }
        }
        if (posFrontier > negFrontier) score -= bonus;
        else if (negFrontier > posFrontier) score += bonus;


        // Edge creep bonus - for each edge a player controls (>= half board size),
        // it gets a bonus. Do not award if both players occupy equal # of positions.
        // North edge
        int pos = 0;
        int neg = 0;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][0] == PLAYER1) pos++;
            if (board[i][0] == PLAYER2) neg++;
        }
        if (pos != neg) {
            if (pos >= boardSize/2) score += bonus;
            if (neg >= boardSize/2) score -= bonus;
        }
        // South edge
        pos = 0; neg = 0;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][boardSize-1] == PLAYER1) pos++;
            if (board[i][boardSize-1] == PLAYER2) neg++;
        }
        if (pos != neg) {
            if (pos >= boardSize/2) score += bonus;
            if (neg >= boardSize/2) score -= bonus;
        }
        // West edge
        pos = 0; neg = 0;
        for (int j = 0; j < boardSize; j++) {
            if (board[0][j] == PLAYER1) pos++;
            if (board[0][j] == PLAYER2) neg++;
        }
        if (pos != neg) {
            if (pos >= boardSize/2) score += bonus;
            if (neg >= boardSize/2) score -= bonus;
        }
        // East edge
        pos = 0; neg = 0;
        for (int j = 0; j < boardSize; j++) {
            if (board[boardSize-1][j] == PLAYER1) pos++;
            if (board[boardSize-1][j] == PLAYER2) neg++;
        }
        if (pos != neg) {
            if (pos >= boardSize/2) score += bonus;
            if (neg >= boardSize/2) score -= bonus;
        }

        return score;
    }
    
    
    /*
     * Returns the list of possible moves for the next player to move
     */
    public List<OthelloMove> generateMoves() {
        return generateMoves(nextPlayerToMove);
    }
    
    
    /*
     * Returns the list of possible moves for player 'player'
     */
    public List<OthelloMove> generateMoves(int player) {
        List<OthelloMove> moves = new LinkedList<OthelloMove>();

        // these two arrays encode the 8 posible directions in which a player can capture pieces:
        int offs_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
        int offs_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};

        for(int i = 0;i<boardSize;i++) {
            for(int j = 0;j<boardSize;j++) {
                if (board[i][j]==NOTHING) {
                    boolean moveFound = false;
                    for(int k = 0;k<offs_x.length && !moveFound;k++) {
                        int current_x = i + offs_x[k];
                        int current_y = j + offs_y[k];
                        while(current_x+offs_x[k]>=0 && current_x+offs_x[k]<boardSize &&
                              current_y+offs_y[k]>=0 && current_y+offs_y[k]<boardSize &&
                              board[current_x][current_y] == otherPlayer(player)) {
                            current_x += offs_x[k];
                            current_y += offs_y[k];
                            if (board[current_x][current_y] == player) {
                                // Legal move:
                                moveFound = true;
                                moves.add(new OthelloMove(player, i, j));
                                break;
                            }
                        }
                    }
                }
            }
        }
                
        return moves;
    }
    
    
    /*
     * Modifies the game state as for applying the given 'move'
     * Notice that move can be "null", which means that the player passes.
     * "passing" is only allowed if a player has no other moves available.
     */
    public void applyMove(OthelloMove move) {
        nextPlayerToMove = otherPlayer(nextPlayerToMove);
        
        if (move==null) return; // player passes
        
        // set the piece:
        board[move.x][move.y] = move.player;
        
        // these two arrays encode the 8 posible directions in which a player can capture pieces:
        int offs_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
        int offs_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};
        
        // see if any pieces are captured:
        for(int i = 0;i<offs_x.length;i++) {
            int current_x = move.x + offs_x[i];
            int current_y = move.y + offs_y[i];
            while(current_x+offs_x[i]>=0 && current_x+offs_x[i]<boardSize &&
                  current_y+offs_y[i]>=0 && current_y+offs_y[i]<boardSize &&
                  board[current_x][current_y] == otherPlayer(move.player)) {
                current_x += offs_x[i];
                current_y += offs_y[i];
                if (board[current_x][current_y] == move.player) {
                    // pieces captured!:
                    int reversed_x = move.x + offs_x[i];
                    int reversed_y = move.y + offs_y[i];
                    while(reversed_x!=current_x || reversed_y!=current_y) {
                        board[reversed_x][reversed_y] = move.player;
                        reversed_x += offs_x[i];
                        reversed_y += offs_y[i];
                    }
                    break;
                }
            }
        }
    } 
    
    
    /*
     * Creates a new game state that has the result of applying move 'move'
     */
    public OthelloState applyMoveCloning(OthelloMove move) {
        OthelloState newState = clone();
        newState.applyMove(move);
        return newState;
    }
    
    public int otherPlayer(int player) {
        if (player==PLAYER1) return PLAYER2;
        return PLAYER1;
    }
}
