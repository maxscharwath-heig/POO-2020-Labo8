package engine.choices;

import chess.ChessView.UserChoice;
import engine.piece.Piece;

public class PromotePiece implements UserChoice {

    private final Piece piece;

    /**
     *
     * @param piece
     */
    public PromotePiece(Piece piece) {
        this.piece = piece;
    }

    /**
     *
     * @return
     */
    @Override
    public String textValue() {
        return piece.getPieceName();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return textValue();
    }

    /**
     *
     * @return
     */
    public Piece getPiece() {
        return piece;
    }
}