package engine.rules;

import chess.PieceType;
import engine.ChessBoard;
import engine.piece.Piece;

/**
 * Règle qui interdit le déplacement d'un roi en échec
 */
public class KingCheckRule extends Rule {
    public KingCheckRule(ChessBoard board) {
        super(board);
    }

    @Override
    public RuleResult execute(int fromX, int fromY, int toX, int toY) {
        Piece p = board.getPiece(fromX, fromY);

        if (p != null && p.type() == PieceType.KING &&
                board.isADangerousPlace(fromX, fromY, toX, toY)) {
            return RuleResult.REJECT_MOVEMENT;
        }
        return RuleResult.IGNORE;
    }
}
