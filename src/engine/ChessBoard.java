package engine;

import chess.PlayerColor;
import engine.piece.*;

public class ChessBoard {
    private Piece[][] board = new Piece[8][8];

    public ChessBoard() {
        initPlayerBoard(PlayerColor.BLACK, 0, 1);
        initPlayerBoard(PlayerColor.WHITE, 7, 6);
    }

    private void initPlayerBoard(PlayerColor color, int y1, int y2) {
        Piece[] lineOne = {
                new RookPiece(color),
                new KnightPiece(color),
                new BishopPiece(color),
                new KingPiece(color),
                new QueenPiece(color),
                new BishopPiece(color),
                new KnightPiece(color),
                new RookPiece(color),
        };
        board[y1] = lineOne;
        for (int i = 0; i < board[y2].length; ++i) {
            board[y2][i] = new PawnPiece(color);
        }
    }

    public Piece getPiece(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= 8 || posY >= 8) return null; //TODO lol c'est moche
        return board[posY][posX];
    }

    public boolean changePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);
        boolean bouger = p.canMove(this, fromX, fromY, toX, toY);
        if (bouger) {
            board[toY][toX] = board[fromY][fromX];
            board[fromY][fromX] = null;
        }
        return bouger;
    }


    public Piece[][] board() {
        return board;
    }
}
