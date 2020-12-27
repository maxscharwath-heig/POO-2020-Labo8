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
     * @param type
     * @param color
     */
    public Piece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     *
     * @return
     */
    public String getPieceName() {
        return "Piece";
    }

    /**
     *
     * @return
     */
    public PieceType type() {
        return type;
    }

    /**
     *
     * @return
     */
    public PlayerColor color() {
        return color;
    }

    /**
     *
     * @param board
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        Piece arrivee = board.getPiece(toX, toY);
        if (fromX == toX && fromY == toY) return false;
        return (arrivee == null || arrivee.color != this.color);
    }

    /**
     *
     * @param value
     */
    public void setMoved(boolean value) {
        this.hasMoved = value;
    }

    /**
     *
     * @return
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getPieceName() + " " + color;
    }
}
