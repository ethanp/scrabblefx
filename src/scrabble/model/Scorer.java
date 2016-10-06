package scrabble.model;

import java.util.List;

/**
 * 10/6/16 12:33 AM
 */
class Scorer {
    private final List<TileModel> pending;
    private final BoardModel boardModel;
    private final Direction direction;

    Scorer(List<TileModel> tilesPendingConfirmation, BoardModel boardModel) {
        this.pending = tilesPendingConfirmation;
        this.boardModel = boardModel;
        this.direction = getMainWordDirection();
    }

    private Direction getMainWordDirection() {
        return Direction.RIGHT;
    }

    int scorePendingTiles() {
        int mainWord = scoreMainWord();
        int otherWords = scoreIncidentalWords();
        return -1;
    }

    private int scoreMainWord() {
        TileModel topLeftPending = getTopLeftPendingLetter();
        TileModel topLeftLetter = topLeftWordLetter(direction, topLeftPending);
        return wordScore(direction, topLeftLetter);
    }

    private int scoreIncidentalWords() {
        Direction opposite = direction.opposite();
        int incidentalAcc = 0;
        for (TileModel tile : pending) {
            boolean extendsBack = before(opposite, tile).nonEmpty();
            boolean extendsForward = after(opposite, tile).nonEmpty();
            if (extendsBack || extendsForward) {
                incidentalAcc += wordScore(opposite, topLeftWordLetter(opposite, tile));
            }
        }
        return incidentalAcc;
    }

    private TileModel getTopLeftPendingLetter() {
        return null;
    }

    private TileModel topLeftWordLetter(Direction direction, TileModel topLeft) {
        while (before(direction, topLeft).nonEmpty()) {
            topLeft = before(direction, topLeft);
        }
        return topLeft;
    }

    /** add up the scores of all the tiles in the word */
    private int wordScore(Direction direction, TileModel currentTile) {
        int scoreAccumulator = currentTile.calculateScore();
        while (after(direction, currentTile).nonEmpty()) {
            currentTile = after(direction, currentTile);
            scoreAccumulator += currentTile.calculateScore();
        }
        return scoreAccumulator;
    }

    private TileModel before(Direction direction, TileModel original) {
        return direction == Direction.DOWN
               ? boardModel.upFrom(original)
               : boardModel.leftOf(original);
    }

    private TileModel after(Direction direction, TileModel original) {
        return direction == Direction.DOWN
               ? boardModel.downFrom(original)
               : boardModel.rightOf(original);
    }

    private enum Direction {
        DOWN, RIGHT;

        Direction opposite() {
            return this == DOWN ? RIGHT : DOWN;
        }
    }
}
