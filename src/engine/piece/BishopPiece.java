package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class BishopPiece extends Piece {
    public BishopPiece(PlayerColor color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {

        if (!super.canMove(board, fromX, fromY, toX, toY)) return false;
        int dx = toX - fromX;
        int dy = toY - fromY;
        if (dy == 0) return false;
        int p = dx / dy;
        if (Math.abs(p) != 1) return false;
        int d = dx > 0 ? 1 : -1;

        for (int i = 1; i < dx * d; ++i) {
            if (board.getPiece(fromX + i * d, fromY + i * d * p) != null) return false;
        }

        return true;

    }
}
