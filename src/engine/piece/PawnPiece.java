package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;

public class PawnPiece extends Piece {
    public PawnPiece(PlayerColor color) {
        super(PieceType.PAWN, color);
    }

    @Override //TODO
    public boolean canMove(ChessBoard board, int fromX, int fromY, int toX, int toY) {

        if (!super.canMove(board, fromX, fromY, toX, toY)) return false;

        int dy = toY - fromY;
        int dx = toX - fromX;

        if (dy == 0 && dx != 0) return false;

        //Le pion bouge de plus de 2 cases
        if (Math.abs(dy) > 2 || Math.abs(dx) > 1) {
            return false;
        }

        boolean isWhite = this.color() == PlayerColor.WHITE;

        //Le pion recule
        if (!isWhite && dy < 0) return false;
        if (isWhite && dy > 0) return false;

        //Le pion a déjà bougé et bouge de 2 cases
        if (!isWhite && dy == 2 && (fromY != 1 || Math.abs(dx) == 1)) return false;
        if (isWhite && dy == -2 && (fromY != 6 || Math.abs(dx) == 1)) return false;

        //Le pion ne peut pas aller en diagonale si il n'y a pas une pièce ennemie sur sa destination
        int sens = !isWhite ? 1 : -1;
        if (dy == sens) {
            if (Math.abs(dx) == 1 && board.getPiece(toX, toY) == null) {
                return false;
            } else return Math.abs(dx) != 0 || board.getPiece(toX, toY) == null;
        }
        return true;
    }
}
