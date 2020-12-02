package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public abstract class Piece {
    private PieceType type;
    private PlayerColor color;

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
}