package engine.choices;

import chess.ChessView.*;
import engine.piece.Piece;

public class PromotePiece implements UserChoice {

    private final Piece piece;

    public PromotePiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String textValue() {
        System.out.println("coucou : " + piece.getPieceName());
        return piece.getPieceName();
    }

    @Override
    public String toString() {
        return textValue();
    }

    public Piece getPiece() {
        return piece;
    }
}
