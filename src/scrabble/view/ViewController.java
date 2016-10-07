package scrabble.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import scrabble.model.Player;
import scrabble.model.ScrabbleGame;

public class ViewController {

    @FXML public Pane midPane;
    @FXML public TableView scoreTable;
    private ScrabbleScene scrabbleScene;

    public void setScrabbleScene(ScrabbleScene scrabbleScene) {
        this.scrabbleScene = scrabbleScene;
        initializeScoreTable(scrabbleScene);
    }

    @SuppressWarnings("unchecked") private void initializeScoreTable(ScrabbleScene scrabbleScene) {
        TableColumn nameCol = new TableColumn("Player");
        TableColumn scoreCol = new TableColumn("Score");
        nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        nameCol.setMinWidth(200);
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        scoreTable.setItems(scrabbleScene.getScrabbleGame().getPlayers());
        scoreTable.getColumns().addAll(nameCol, scoreCol);
    }

    @FXML void makeMovePressed(ActionEvent event) {
        System.out.println("make move pressed " + event);
        ScrabbleGame game = scrabbleScene.getScrabbleGame();
        int score = game.scorePendingTiles();
        System.out.println("score was " + score);
        game.confirmPendingTiles();
        game.getCurrentPlayer().refillRackFromBag();
        game.nextPlayersTurn();
    }

    @FXML void resetLettersPressed(ActionEvent event) {
        System.out.println("reset letters pressed " + event);
        scrabbleScene.getScrabbleGame().resetPendingTiles();
    }
}
