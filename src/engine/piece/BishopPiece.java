package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.utils.GenericMovement;

public class BishopPiece extends Piece {
    /**
     *
     * @param color
     */
    public BishopPiece(PlayerColor color) {
        super(PieceType.BISHOP, color);
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
                GenericMovement.diagonalMovement(board, fromX, fromY, toX, toY);

    }

    /**
     *
     * @return
     */
    @Override
    public String getPieceName() {
        return "Bishop";
    }
}
