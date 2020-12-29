package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public abstract class Piece {
    private final PieceType type;
    private final PlayerColor color;
    private boolean hasMoved = false;

    /**
     *
     * @param type Le type de la pièce (Roi, Pion, etc)
     * @param color La couleur de la pièce
     */
    public Piece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     *
     * @return Le nom de la pièce
     */
    public String getPieceName() {
        return "Piece";
    }

    /**
     * getter du type de la pièce
     * @return Le type de la pièce
     */
    public PieceType type() {
        return type;
    }

    /**
     * getter de la couleur de la pièce
     * @return La couleur de la pièce
     */
    public PlayerColor color() {
        return color;
    }

    /**
     * Indique si un déplacement de la case (fromX, fromY) à la case (toX, toY) est possible
     * @param board L'échiquier de la partie
     * @param fromX coordonnée X de la case de départ
     * @param fromY coordonnée Y de la case de départ
     * @param toX coordonnée X de la case d'arrivée
     * @param toY coordonnée Y de la case d'arrivée
     * @return vrai si le déplacement est possible, faux dans la négative
     */
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        Piece arrivee = board.getPiece(toX, toY);
        if (fromX == toX && fromY == toY) return false;
        return (arrivee == null || arrivee.color != this.color);
    }

    /**
     * setter de hasMoved
     * @param value
     */
    public void setMoved(boolean value) {
        this.hasMoved = value;
    }

    /**
     * getter de hasMoved
     * @return valeur de hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * toString de la pièce
     * @return Un String contenant le nom de la pièce ainsi que sa couleur
     */
    @Override
    public String toString() {
        return getPieceName() + " " + color;
    }
}
