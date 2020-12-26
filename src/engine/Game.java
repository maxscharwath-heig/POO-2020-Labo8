package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.Piece;

public class Game implements ChessController {

    private ChessView cv;

    private ChessBoard board;

    private int rounds = 0;
    private boolean hasMoved = false;

    public boolean playerHasMoved() {
        return this.hasMoved;
    }

    public void nextRound() {
        hasMoved = false;
        ++rounds;
        cv.displayMessage(getPlayerColor() == PlayerColor.WHITE ? "Aux Blancs" : "Aux Noirs");
        if (board.checkmate(getPlayerColor())) {
            cv.displayMessage(getPlayerColor() + " est en echec et mat");
        }
    }

    PlayerColor getPlayerColor() {
        return rounds % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    @Override
    public void start(ChessView view) {
        cv = view;
        cv.startView();
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece piece = board.getPiece(fromX, fromY);
        if (piece == null) return false;

        if (piece.color() != getPlayerColor()) {
            cv.displayMessage("Ce n'est pas votre tour!");
            return false;
        }

        System.out.println(piece.getClass().getSimpleName() + " " + fromX + " " + fromY + " " + toX + " " + toY);
        if (board.movePiece(fromX, fromY, toX, toY)) {
            cv.removePiece(fromX, fromY);
            cv.putPiece(piece.type(), piece.color(), toX, toY);
            nextRound();
            return true;
        }
        cv.displayMessage("Mouvement illégal!");
        return false;
    }

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
