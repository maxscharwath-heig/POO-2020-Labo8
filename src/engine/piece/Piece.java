package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public abstract class Piece {
    private PieceType type;
    private PlayerColor color;
    private boolean hasMoved = false;

    public Piece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }


    public PieceType type() {
        return type;
    }

    public PlayerColor color() {
        return color;
    }

    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        Piece depart = board.getPiece(fromX, fromY);
        Piece arrivee = board.getPiece(toX, toY);

        if (fromX == toX && fromY == toY) return false;
        return (arrivee == null || arrivee.color != this.color);
    }

    public void setMoved(boolean value) {
        this.hasMoved = value;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + color;
    }
}
