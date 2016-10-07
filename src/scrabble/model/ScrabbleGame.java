package scrabble.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import scrabble.model.scoring.Scorer;

import java.util.ArrayList;
import java.util.List;

/**
 * 9/29/16 9:21 PM
 */
public class ScrabbleGame {
    private final ObservableList<Player> players = FXCollections.observableArrayList();
    private final LetterBag letterBag = new LetterBag();
    private final BoardModel boardModel = new BoardModel(this);
    /**
     * These are the tiles that have been placed on the board, but haven't
     * been confirmed as the player's move yet.
     */
    private final List<TileModel> tilesPendingConfirmation = new ArrayList<>();
    private ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>();

    public ScrabbleGame() {
        addPlayer("Player 1");
        addPlayer("Player 2");
    }

    public void addPlayer(String name) {
        Player player = new Player(name, this);
        players.add(player);
        if (currentPlayer.get() == null) {
            currentPlayer.set(player);
        }
    }

    public void setPendingConfirmation(TileModel tileModel) {
        tilesPendingConfirmation.add(tileModel);
    }

    LetterBag getLetterBag() {
        return letterBag;
    }

    void addLetterToBoard(LetterModel letterModel, int row, int col) {
        boardModel.placeLetter(letterModel, row, col);
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public Player getCurrentPlayer() {
        return currentPlayer.get();
    }

    public void resetPendingTiles() {
        for (TileModel tile : tilesPendingConfirmation) {
            currentPlayer.get().addLetterToRack(tile.getLetter());
            tile.removeLetter();
        }
        tilesPendingConfirmation.clear();
    }

    public void confirmPendingTiles() {
        tilesPendingConfirmation.clear();
    }

    public void nextPlayersTurn() {
        int curIdx = players.indexOf(currentPlayer.get());
        int nextIdx = (curIdx + 1)%players.size();
        currentPlayer.set(players.get(nextIdx));
    }

    public void addPlayerChangeListener(ChangeListener<Player> listener) {
        currentPlayer.addListener(listener);
    }

    public void addPlayerListChangeListener(ListChangeListener<Player> listener) {
        players.addListener(listener);
    }

    public int scorePendingTiles() {
        return new Scorer(tilesPendingConfirmation, boardModel).scorePendingTiles();
    }

    public ObservableList<Player> getPlayers() {
        return players;
    }
}
