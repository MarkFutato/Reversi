public class ReversiGUI extends Application {
    private Reversi game;

    @Override
    public void start(Stage primaryStage) {
        game = new ReversiGame();
        GridPane grid = new GridPane();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button cellBtn = new Button();
                cellBtn.setPrefSize(50, 50);

                // We'll store the row/col in the button's user data or create a closure
                final int r = row;
                final int c = col;

                cellBtn.setOnAction(e -> handleCellClick(r, c));

                grid.add(cellBtn, col, row);
            }
        }

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateBoardUI(grid); // draw the initial state
    }

    private void handleCellClick(int row, int col) {
        boolean success = game.makeMove(row, col);
        if (success) {
            updateBoardUI(/* the grid or board UI elements */);
        } else {
            // Maybe show a message "Invalid Move!"
        }
    }

    private void updateBoardUI(GridPane grid) {
        // Loop over 8x8, check game.getBoard()[row][col]
        // and set the button color/text accordingly
    }

    public static void main(String[] args) {
        launch(args);
    }
}
