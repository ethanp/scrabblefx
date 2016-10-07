package scrabble.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 9/29/16 10:27 PM
 */
public class Player {

    private final ScrabbleGame game;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty score;
    private final LetterRack letterRack;

    Player(String name, ScrabbleGame game) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(0);
        this.game = game;
        letterRack = game.getLetterBag().drawInitialSet();
    }

    public void playLetterAtSquare(LetterModel letterModel, int row, int col) {
        if (!letterRack.contains(letterModel)) {
            System.err.println("you don't have the letter " + letterModel + ". cancelling");
            return;
        }
        letterRack.remove(letterModel);
        game.addLetterToBoard(letterModel, row, col);
    }

    public LetterRack getLetterRack() {
        return letterRack;
    }

    void addLetterToRack(LetterModel letter) {
        letterRack.addLetter(letter);
    }

    public void refillRackFromBag() {
        letterRack.refillFromBag();
    }

    /* intellij doesn't realize that these are used by the table*/

    @SuppressWarnings("unused") public SimpleStringProperty nameProperty() {
        return name;
    }

    @SuppressWarnings("unused") public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
