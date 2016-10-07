package scrabble.model.scoring;

import scrabble.model.BoardModel;
import scrabble.model.TileModel;

import java.util.List;

/**
 * 10/6/16 12:33 AM
 */
public class Scorer {
    private final List<TileModel> pendingTiles;
    private final Direction direction;

    public Scorer(List<TileModel> tilesPendingConfirmation, BoardModel boardModel) {
        this.pendingTiles = tilesPendingConfirmation;
        this.direction = new Direction(boardModel, mainWordIsVertical());
    }

    private boolean mainWordIsVertical() {
        return pendingTiles.size() <= 1 || pendingTiles.get(0).col == pendingTiles.get(1).col;
    }

    public int scorePendingTiles() {
        return scoreMainWord() + scoreIncidentalWords();
    }

    private int scoreMainWord() {
        return this.direction.scoreWordFromPending(getTopLeftPendingLetter());
    }

    private int scoreIncidentalWords() {
        Direction opp = direction.opposite();
        int incidentalAcc = 0;
        for (TileModel pending : pendingTiles) {
            boolean extendsBack = opp.before(pending) != null && opp.before(pending).nonEmpty();
            boolean extendsForward = opp.after(pending) != null && opp.after(pending).nonEmpty();
            if (extendsBack || extendsForward) {
                incidentalAcc += opp.scoreWordFromPending(pending);
            }
        }
        return incidentalAcc;
    }

    private TileModel getTopLeftPendingLetter() {
        TileModel best = pendingTiles.get(0);
        for (TileModel tileModel : pendingTiles) {
            if (tileModel.row < best.row || tileModel.col < best.col) {
                best = tileModel;
            }
        }
        return best;
    }

}
