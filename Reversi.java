public class Reversi {
    private int[][] board;
    private int currentPlayer;

    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public Reversi() {
        board = new int[8][8];
        // Set initial positions
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;

        // Decide who goes first
        currentPlayer = BLACK;
    }

    private static final int[][] DIRECTIONS = {
            { -1, 0 }, // Up
            { 1, 0 }, // Down
            { 0, -1 }, // Left
            { 0, 1 }, // Right
            { -1, -1 }, // Up-Left
            { -1, 1 }, // Up-Right
            { 1, -1 }, // Down-Left
            { 1, 1 } // Down-Right
    };

    private boolean isValidMove(int row, int col, int player) {
        // 1. The cell must be empty.
        if (board[row][col] != EMPTY) {
            return false;
        }

        int opponent = (player == BLACK) ? WHITE : BLACK;
        boolean validInAtLeastOneDirection = false;

        // 2. Check all directions
        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];
            boolean hasOpponentPieces = false;

            // Move in this direction as long as we see opponent pieces
            while (isOnBoard(r, c) && board[r][c] == opponent) {
                r += dir[0];
                c += dir[1];
                hasOpponentPieces = true;
            }

            // Now we either went off-board or hit a cell that is not the opponent's.
            // For a valid move, we need:
            // - We stayed on the board
            // - The last piece we see is the current player's piece
            // - We saw at least one opponent piece on the way
            if (isOnBoard(r, c) && board[r][c] == player && hasOpponentPieces) {
                validInAtLeastOneDirection = true;
                break; // we can stop checking if we only need to know if it's valid once
            }
        }

        return validInAtLeastOneDirection;
    }

    private boolean isOnBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private void placePiece(int row, int col, int player) {
        board[row][col] = player; // Place the piece

        int opponent = (player == BLACK) ? WHITE : BLACK;

        // Check all directions for flippable pieces
        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];

            // Move forward as long as we see the opponent
            while (isOnBoard(r, c) && board[r][c] == opponent) {
                r += dir[0];
                c += dir[1];
            }

            // If we eventually find a 'player' piece, we flip in the backward direction
            if (isOnBoard(r, c) && board[r][c] == player) {
                // Move backwards until we reach the (row, col) again
                int flipRow = r - dir[0];
                int flipCol = c - dir[1];

                while (flipRow != row || flipCol != col) {
                    board[flipRow][flipCol] = player;
                    flipRow -= dir[0];
                    flipCol -= dir[1];
                }
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (!isValidMove(row, col, currentPlayer)) {
            return false;
        }
        placePiece(row, col, currentPlayer);
        // Switch player
        currentPlayer = (currentPlayer == BLACK) ? WHITE : BLACK;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Reversi!");
    }
}
