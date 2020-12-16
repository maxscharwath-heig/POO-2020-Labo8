package engine;

import chess.PlayerColor;
import engine.piece.*;
import engine.utils.Position;

public class ChessBoard {
    private final Piece[][] board = new Piece[8][8];

    private Position[] kingsPos = {
            new Position(),
            new Position(),
    };


    public ChessBoard() {
        initPlayerBoard(PlayerColor.BLACK, 0, 1);
        initPlayerBoard(PlayerColor.WHITE, 7, 6);
    }

    /**
     *
     * @param color Couleur
     * @param y1 ligne des rois reines
     * @param y2 ligne des pions
     */
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

        kingsPos[(color == PlayerColor.WHITE) ? 0 : 1].set(3, y1);
    }

    public Piece getPiece(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= 8 || posY >= 8) return null; //TODO lol c'est moche
        return board[posY][posX];
    }

    private KingPiece getKing(PlayerColor color){
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        return (KingPiece) getPiece(kingPos.x, kingPos.y);
    }

    public boolean checkCastling(PlayerColor color){ //roque


        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];

        KingPiece king = getKing(color);

        if(!king.hasMoved() //todo roi pas bougé, tour pas bougé, et pas de pieces ennemie qui pourrait venir entre deux
        ){ //todo

        }else{
            //todo
        }
        return false;
    }

    public boolean movePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);

        if (!p.canMove(this, fromX, fromY, toX, toY)) return false;

        board[toY][toX] = board[fromY][fromX];
        board[fromY][fromX] = null;

        if (p.getClass() == KingPiece.class) {//TODO MOCHE

            kingsPos[(p.color() == PlayerColor.WHITE) ? 0 : 1].set(toX, toY);
            System.out.println(kingsPos[0].x + " " + kingsPos[0].y + "-" + kingsPos[1].x + " " + kingsPos[1].y);
        }
        p.setMoved(true);
        return true;
    }

    public Piece[][] board() {
        return board;
    }
}
