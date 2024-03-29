package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.utils.GenericMovement;

public class QueenPiece extends Piece {

    /**
     * @param color
     */
    public QueenPiece(PlayerColor color) {
        super(PieceType.QUEEN, color);

    }

    /**
     * @param board
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        return super.canMove(board, fromX, fromY, toX, toY) && (
                GenericMovement.crossMovement(board, fromX, fromY, toX, toY) ||
                        GenericMovement.diagonalMovement(board, fromX, fromY, toX, toY)
        );
    }

    /**
     * @return
     */
    @Override
    public String getPieceName() {
        return "Queen";
    }

}
