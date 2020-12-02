package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class RookPiece extends Piece {
    public RookPiece(PlayerColor color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        if (!super.canMove(board, fromX, fromY, toX, toY)) return false;
        if (fromX == toX) {//VERTICAL MOVE
            int delta = toY - fromY > 0 ? 1 : -1;
            for (int i = fromY + delta; i != toY; i += delta) {
                if (board.getPiece(fromX, i) != null) return false;
            }
        } else if (fromY == toY) {//HORIZOINATALD MOVE
            int delta = toX - fromX > 0 ? 1 : -1;
            for (int i = fromX + delta; i != toX; i += delta) {
                if (board.getPiece(i, fromY) != null) return false;
            }
        } else return false;
        return true;
    }
}
