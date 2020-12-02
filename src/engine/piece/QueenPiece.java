package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class QueenPiece extends Piece {

    public QueenPiece(PlayerColor color) {
        super(PieceType.QUEEN, color);

    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        if (!super.canMove(board, fromX, fromY, toX, toY)) return false;

        int dx = toX - fromX;
        int dy = toY - fromY;
        if (dx == 0) {
            int delta = dy > 0 ? 1 : -1;
            for (int i = fromY + delta; i != toY; i += delta) {
                if (board.getPiece(fromX, i) != null) return false;
            }
        } else if (dy == 0) {
            int delta = dx > 0 ? 1 : -1;
            for (int i = fromX + delta; i != toX; i += delta) {
                if (board.getPiece(i, fromY) != null) return false;
            }
        } else {
            int pente = (dx) / (dy);
            if (Math.abs(pente) == 1) {
                int d = dx > 0 ? 1 : -1;
                for (int i = 1; i < dx * d; ++i) {
                    if (board.getPiece(fromX + i * d, fromY + i * d * pente) != null) return false;
                }
            } else return false;
        }

        return true;
    }
}
