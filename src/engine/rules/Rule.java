package engine.rules;

import engine.ChessBoard;

abstract public class Rule {

    protected final ChessBoard board;

    public Rule(ChessBoard board) {
        this.board = board;
    }

    /**
     * Execute le code de la régle du jeu
     *
     * @param fromX Coordonnée x de départ
     * @param fromY Coordonnée y de départ
     * @param toX   Coordonnée x d'arrivée
     * @param toY   Coordonnée y d'arrivée
     * @return return une RuleResult utilisé dans ChessBoard
     */
    public abstract RuleResult execute(int fromX, int fromY, int toX, int toY);

}
