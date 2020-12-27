package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class KnightPiece extends Piece {
    public KnightPiece(PlayerColor color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        return super.canMove(board, fromX, fromY, toX, toY) &&
                ((Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1) ||
                        (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2));
    }


    @Override
    public String getPieceName() {
        return "Knight";
    }
}
