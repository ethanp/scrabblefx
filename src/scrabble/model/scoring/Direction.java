package scrabble.model.scoring;

import scrabble.model.BoardModel;
import scrabble.model.TileModel;

/**
 * 10/6/16 2:10 AM
 */
class Direction {

    private final BoardModel boardModel;
    private final boolean isVertical;

    Direction(BoardModel boardModel, boolean isVertical) {
        this.boardModel = boardModel;
        this.isVertical = isVertical;
    }

    Direction opposite() {
        return isVertical ? new Direction(boardModel, false) : new Direction(boardModel, true);
    }

    int scoreWordFromPending(TileModel tile) {
        return wordScore(topLeftWordLetter(tile));
    }

    /** add up the scores of all the tiles in the word */
    private int wordScore(TileModel currentTile) {
        int scoreAccumulator = currentTile.calculateScore();
        while (after(currentTile) != null && after(currentTile).nonEmpty()) {
            currentTile = after(currentTile);
            scoreAccumulator += currentTile.calculateScore();
        }
        return scoreAccumulator;
    }

    private TileModel topLeftWordLetter(TileModel topLeft) {
        while (before(topLeft) != null && before(topLeft).nonEmpty()) {
            topLeft = before(topLeft);
        }
        return topLeft;
    }

    TileModel after(TileModel original) {
        return isVertical ? boardModel.downFrom(original) : boardModel.rightOf(original);
    }

    TileModel before(TileModel original) {
        return isVertical ? boardModel.upFrom(original) : boardModel.leftOf(original);
    }
}
