package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.Piece;

public class Controller implements ChessController {

    private ChessView cv;

    private ChessBoard board = new ChessBoard();

    @Override
    public void start(ChessView view) {
        System.out.println("Start!");
        cv = view;
        cv.startView();
        cv.displayMessage("Aux blancs");
        Piece[][] pieces = board.board();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] != null) {
                    cv.putPiece(pieces[i][j].type(), pieces[i][j].color(), j, i);
                }
            }
        }
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece piece = board.getPiece(fromX, fromY);
        if (piece == null) return false;
        System.out.println(piece.getClass().getSimpleName() + " " + fromX + " " + fromY + " " + toX + " " + toY);
        if (piece.canMove(board, fromX, fromY, toX, toY)) {
            if (board.movePiece(fromX, fromY, toX, toY)) {
                cv.removePiece(fromX, fromY);
                cv.putPiece(piece.type(), piece.color(), toX, toY);
            }

            cv.displayMessage(board.getPlayerColor()== PlayerColor.WHITE?"Aux Blancs":"Aux Noirs");
            return true;
        }
        cv.displayMessage(board.getPlayerColor()== PlayerColor.WHITE?"Aux Blancs":"Aux Noirs");
        return false;
    }

    @Override
    public void newGame() {
        board = new ChessBoard();
        Piece[][] pieces = board.board();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (pieces[i][j] != null) {
                    cv.putPiece(pieces[i][j].type(), pieces[i][j].color(), j, i);
                }
            }
        }
        System.out.println("New Game!");
    }
}
