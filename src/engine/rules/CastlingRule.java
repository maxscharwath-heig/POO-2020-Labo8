package engine.rules;

import chess.PieceType;
import engine.ChessBoard;
import engine.piece.Piece;

public class CastlingRule extends Rule {


    public CastlingRule(ChessBoard board) {
        super(board);
    }

    /**
     * Effectue un petit ou un grand roque
     *
     * @param fromX position x de départ du roi qui veut faire le roque
     * @param fromY position y de départ du roi qui veut faire le roque
     * @param toX   position x de déplacement pour faire le roque
     * @param toY   position y de déplacement pour faire le roque
     * @return vrai si on a effectué le roque
     */
    private boolean doCastling(int fromX, int fromY, int toX, int toY) { //roque
        if (board.isADangerousPlace(fromX, fromY)) return false;
        Piece king = board.getPiece(fromX, fromY);
        if (king == null || king.type() != PieceType.KING || king.hasMoved()) return false;
        if (toY != fromY) return false;
        if (Math.abs(toX - fromX) != 2) return false;
        int sens = (toX - fromX) > 0 ? -1 : 1;

        if (
                board.isADangerousPlace(fromX, fromY, toX, toY) ||
                        board.isADangerousPlace(fromX, fromY, toX + sens, toY) ||
                        board.getPiece(toX, toY) != null ||
                        board.getPiece(toX + sens, toY) != null)
            return false;

        int rookX = (toX - fromX) > 0 ? ChessBoard.boardSize - 1 : 0;
        Piece rook = board.getPiece(rookX, fromY);
        if (rook == null || rook.type() != PieceType.ROOK || rook.hasMoved()) return false;

        board.setPieceTo(rookX, fromY, toX + sens, fromY);
        board.setPieceTo(fromX, fromY, toX, toY);
        return true;
    }

    @Override
    public RuleResult execute(int fromX, int fromY, int toX, int toY) {
        Piece p = board.getPiece(fromX, fromY);
        if (p != null && p.type() == PieceType.KING) {
            if (doCastling(fromX, fromY, toX, toY)) {
                return RuleResult.ACCEPT_MOVEMENT;
            }

        }
        return RuleResult.IGNORE;
    }
}
