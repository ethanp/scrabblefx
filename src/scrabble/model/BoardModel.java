package scrabble.model;

/**
 * 9/29/16 10:24 PM
 */
public class BoardModel {
    public static final int NUM_COLS = 15;
    public static final int NUM_ROWS = 15;
    TileModel[][] tileModels = new TileModel[NUM_ROWS][NUM_COLS];
    private ScrabbleGame game;

    BoardModel(ScrabbleGame game) {
        this.game = game;
        SpecialSquareInputs specialSquareInputs = SpecialSquareInputs.readFromConfig();
        assert specialSquareInputs != null;
        RowsAndColumns.each((row, col) -> tileModels[row][col] =
              new TileModel(this, specialSquareInputs.get(row, col), row, col));
    }

    public ScrabbleGame getGame() {
        return game;
    }

    public void placeLetter(LetterModel letterModel, int row, int col) {
        tileModels[row][col].placeLetter(letterModel);
    }

    public TileModel getSquare(int row, int col) {
        return tileModels[row][col];
    }

    public TileModel leftOf(TileModel orig) {
        return null;
    }

    public TileModel upFrom(TileModel orig) {
        return null;
    }

    public TileModel downFrom(TileModel orig) {
        return null;
    }

    public TileModel rightOf(TileModel orig) {
        return null;
    }
}
