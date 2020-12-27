package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.utils.GenericMovement;

public class RookPiece extends Piece {
    /**
     *
     * @param color
     */
    public RookPiece(PlayerColor color) {
        super(PieceType.ROOK, color);
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
    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        return super.canMove(board, fromX, fromY, toX, toY) &&
                GenericMovement.crossMovement(board, fromX, fromY, toX, toY);
    }

    /**
     *
     * @return
     */
    @Override
    public String getPieceName() {
        return "Rook";
    }
}
