package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.choices.PromotePiece;
import engine.piece.*;
import engine.utils.Position;

public class GameController implements ChessController {

    private ChessView cv;

    private ChessBoard board;

    public ChessBoard getBoard() {
        return board;
    }

    private int rounds = 0;

    /**
     * Contient les actions à faire après le mouvement d'une pièce (par exemple affichage du menu de promotion)
     *
     * @param piece la pièce à regarder
     * @param x     coordonnée x de la pièce
     * @param y     coordonnée y de la pièce
     */
    private void afterMove(Piece piece, int x, int y) {
        if (board.isPromotable(x, y)) {
            PromotePiece promoted = cv.askUser("Promotion", "Pick a promotion ?",
                    new PromotePiece(new QueenPiece(piece.color())),
                    new PromotePiece(new RookPiece(piece.color())),
                    new PromotePiece(new KnightPiece(piece.color())),
                    new PromotePiece(new BishopPiece(piece.color()))
            );
            board.setPiece(x, y, promoted.getPiece());
        }
    }

    /**
     * Passage au round suivant + affichage d'une victoire et d'un échec au roi si c'est le cas
     */
    private void nextRound() {
        ++rounds;

        if (board.isGameOver()) {
            cv.displayMessage("The king is dead, " + board.getWinner() + " is the winner!");
            return;
        }

        cv.displayMessage(getCurrentPlayerColor() + " turn");
        if (board.checkKingInCheck(getCurrentPlayerColor())) {
            cv.displayMessage("Check!");
        }
    }

    /**
     * Retourne la couleur du joueur actif (Le blanc joue aux rounds pairs et le noir aux rounds impairs)
     * @return la couleur du joueur actif
     */
    private PlayerColor getCurrentPlayerColor() {
        return rounds % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    /**
     * Rafraichît l'interface en effectuant tous les mouvements du tour
     */
    private void updateGUI() {
        for (Position pos : board.getUpdatedPositions()) {
            Piece p = board.getPiece(pos.x, pos.y);
            if (p == null) cv.removePiece(pos.x, pos.y);
            else cv.putPiece(p.type(), p.color(), pos.x, pos.y);
        }
        board.clearUpdatedPositions();
    }

    /**
     * Démarre la vue sélectionnée
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        cv = view;
        cv.startView();
        newGame();
    }

    /**
     * Vérifie si le mouvement choisi est légal (affiche un messsage d'erreur si ce n'est pas le cas)
     * @param fromX coordonnée X de la case de départ
     * @param fromY coordonnée Y de la case de départ
     * @param toX coordonnée X de la case d'arrivée
     * @param toY coordonnée Y de la case d'arrivée
     * @return vrai si le mouvement est légal, faux dans la négative
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if (board.isGameOver()) return false;

        Piece piece = board.getPiece(fromX, fromY);
        if (piece == null) return false;

        if (piece.color() != getCurrentPlayerColor()) {
            cv.displayMessage("It's not your turn");
            return false;
        }


        if (board.movePiece(fromX, fromY, toX, toY)) {
            afterMove(piece, toX, toY);
            nextRound();
            updateGUI();
            return true;
        }
        cv.displayMessage("Illegal movement!");
        return false;
    }

    /**
     * Commence une nouvelle partie en créant un nouvel échiquier
     */
    @Override
    public void newGame() {
        board = new ChessBoard();
        rounds = 0;
        for (int x = 0; x < ChessBoard.boardSize; ++x) {
            for (int y = 0; y < ChessBoard.boardSize; ++y) {
                Piece piece = board.getPiece(x,y);
                if (piece != null) {
                    cv.putPiece(piece.type(), piece.color(), x, y);
                }
            }
        }
    }
}
