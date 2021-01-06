package engine.rules;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.piece.Piece;
import engine.utils.Position;

public class EnPassantRule extends Rule {
    public EnPassantRule(ChessBoard board) {
        super(board);
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    @Override
    public RuleResult execute(int fromX, int fromY, int toX, int toY) {
        Piece p = board.getPiece(fromX, fromY);
        if (p == null || p.type() != PieceType.PAWN) return RuleResult.IGNORE;

        int sens = p.color() == PlayerColor.WHITE ? 1 : -1;
        //pas fait un mouvement en diagonale
        if (toY != fromY + sens || (toX != fromX + 1 && toX != fromX - 1)) return RuleResult.IGNORE;

        Piece lastMoved = board.getLastMovedPiece();
        Position lastPosLastMoved = board.getLastMovedPiecePos();

        if (lastPosLastMoved == null || lastMoved == null) return RuleResult.IGNORE;
        if (lastMoved.type() != PieceType.PAWN ||
                lastMoved.color() == p.color() ||
                Math.abs(lastPosLastMoved.y - fromY) != 2) return RuleResult.IGNORE;

        //vérifier si pièce peut être prise en passant
        //à gauche de fromX
        if (lastMoved == board.getPiece(fromX - 1, fromY)) {
            board.setPieceTo(fromX, fromY, toX, toY); // bouge pion attaquant
            board.setPiece(fromX - 1, fromY, null); // détruit pion attaqué
            return RuleResult.ACCEPT_MOVEMENT;
        } // à droite de fromX
        else if (lastMoved == board.getPiece(fromX + 1, fromY)) {
            board.setPieceTo(fromX, fromY, toX, toY); // bouge pion attaquant
            board.setPiece(fromX + 1, fromY, null); // détruit pion attaqué
            return RuleResult.ACCEPT_MOVEMENT;
        }

        return RuleResult.IGNORE;
    }
}
