package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.choices.PromotePiece;
import engine.piece.*;
import engine.utils.Lol;
import engine.utils.Position;

public class Game implements ChessController {

    private ChessView cv;

    private ChessBoard board;

    private int rounds = 0;
    private boolean hasMoved = false;

    /**
     * @return
     */
    private boolean playerHasMoved() {
        return this.hasMoved;
    }

    /**
     * @param piece
     * @param x
     * @param y
     */
    private void afterMove(Piece piece, int x, int y) {
        if (board.isPawnPromotable(x, y)) {
            PromotePiece promoted = cv.askUser("Promotion", "Quelle promotion voulez-vous ?",
                    new PromotePiece(new QueenPiece(piece.color())),
                    new PromotePiece(new RookPiece(piece.color())),
                    new PromotePiece(new KnightPiece(piece.color())),
                    new PromotePiece(new BishopPiece(piece.color()))
            );
            Lol.playSound("powerUp.wav");
            board.setPiece(x, y, promoted.getPiece());
        }
    }

    /**
     *
     */
    private void nextRound() {
        hasMoved = false;
        ++rounds;

        if (board.isGameOver()) {
            cv.displayMessage("Le roi est mort, " + board.getWinner() + " est le gagnant!");
            return;
        }

        cv.displayMessage(getPlayerColor() == PlayerColor.WHITE ? "Aux Blancs" : "Aux Noirs");
        if (board.checkmate(getPlayerColor())) {
            cv.displayMessage("Check!");
        }
    }

    PlayerColor getPlayerColor() {
        return rounds % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    private void updateGUI() {
        for (Position pos : board.getUpdatedPositions()) {
            Piece p = board.getPiece(pos.x, pos.y);
            if (p == null) cv.removePiece(pos.x, pos.y);
            else cv.putPiece(p.type(), p.color(), pos.x, pos.y);
        }
        board.clearUpdatedPositions();
    }

    /**
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        cv = view;
        cv.startView();
        newGame();
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if (board.isGameOver()) return false;

        Piece piece = board.getPiece(fromX, fromY);
        if (piece == null) return false;

        if (piece.color() != getPlayerColor()) {
            cv.displayMessage("Ce n'est pas votre tour!");
            return false;
        }

        if (board.movePiece(fromX, fromY, toX, toY)) {
            afterMove(piece, toX, toY);
            nextRound();
            updateGUI();
            return true;
        }
        cv.displayMessage("Mouvement illégal!");
        return false;
    }

    /**
     *
     */
    @Override
    public void newGame() {
        board = new ChessBoard();
        Piece[][] pieces = board.board();
        rounds = 0;
        hasMoved = false;
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (pieces[x][y] != null) {
                    cv.putPiece(pieces[x][y].type(), pieces[x][y].color(), y, x);
                }
            }
        }
        cv.displayMessage("Aux Blancs");
    }
}
