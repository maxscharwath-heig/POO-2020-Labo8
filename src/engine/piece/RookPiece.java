package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.utils.GenericMovement;

public class RookPiece extends Piece {
    public RookPiece(PlayerColor color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        return super.canMove(board, fromX, fromY, toX, toY) &&
                GenericMovement.crossMovement(board, fromX, fromY, toX, toY);
    }

    @Override
    public String getPieceName() {
        return "Rook";
    }
}
