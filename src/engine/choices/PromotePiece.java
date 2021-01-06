package engine.choices;

import chess.ChessView.UserChoice;
import engine.piece.Piece;

/**
 * Classe représentant une option de promotion
 */
public class PromotePiece implements UserChoice {

    private final Piece piece;

    /**
     * Constructeur
     *
     * @param piece l'option de promotion
     */
    public PromotePiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Retourne le nom de la pièce
     *
     * @return Un String représentant le nom de la pièce
     */
    @Override
    public String textValue() {
        return toString();
    }

    /**
     * toString
     *
     * @return Le nom de l'option de promotion
     */
    @Override
    public String toString() {
        return piece.getPieceName();
    }

    /**
     * getter de la pièce
     *
     * @return l'option de promotion
     */
    public Piece getPiece() {
        return piece;
    }
}