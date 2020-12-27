package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class KingPiece extends Piece {
    public KingPiece(PlayerColor color) {
        super(PieceType.KING, color);
    }

    @Override
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {
        return super.canMove(board, fromX, fromY, toX, toY) &&
                (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1);
    }


    @Override
    public String getPieceName() {
        return "King";
    }

}
