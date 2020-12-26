package engine;

import chess.PlayerColor;
import engine.piece.*;
import engine.utils.Position;

import javax.swing.plaf.metal.MetalIconFactory;

public class ChessBoard {
    private final Piece[][] board = new Piece[8][8];

    private Position[] kingsPos = {
            new Position(),
            new Position(),
    };


    public ChessBoard() {
        initPlayerBoard(PlayerColor.WHITE, 0, 1);
        initPlayerBoard(PlayerColor.BLACK, 7, 6);
    }

    /**
     * @param color Couleur
     * @param y1    ligne des rois reines
     * @param y2    ligne des pions
     */
    private void initPlayerBoard(PlayerColor color, int y1, int y2) {
        Piece[] lineOne = {
                new RookPiece(color),
                new KnightPiece(color),
                new BishopPiece(color),
                new QueenPiece(color),
                new KingPiece(color),
                new BishopPiece(color),
                new KnightPiece(color),
                new RookPiece(color),
        };
        board[y1] = lineOne;
        for (int i = 0; i < board[y2].length; ++i) {
            board[y2][i] = new PawnPiece(color);
        }

        kingsPos[(color == PlayerColor.WHITE) ? 0 : 1].set(4, y1);
    }

    public Piece getPiece(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= 8 || posY >= 8) return null; //TODO lol c'est moche
        return board[posY][posX];
    }

    public void setPiece(int fromX, int fromY, int toX, int toY) {
        board[toY][toX] = board[fromY][fromX];
        board[fromY][fromX] = null;
    }

    private KingPiece getKing(PlayerColor color) {
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        return (KingPiece) getPiece(kingPos.x, kingPos.y);
    }

    public boolean isADangerousPlace(int fromX, int fromY, int toX, int toY) {
        Piece from = getPiece(fromX, fromY);
        Piece to = getPiece(toX, toY);
        boolean isDangerous = false;
        setPiece(fromX, fromY, toX, toY);
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (x == toX && y == toY) continue;
                Piece piece = getPiece(x, y);
                if (piece != null && piece.canMove(this, x, y, toX, toY)) {
                    System.out.println("Echec par " + piece + " " + x + " " + y + " " + toX + " " + toY);
                    System.out.println(getPiece(toX, toY));
                    isDangerous = true;
                    break;
                }
            }
        }
        board[toY][toX] = to;
        board[fromY][fromX] = from;
        return isDangerous;
    }

    public boolean checkKingInCheck(PlayerColor color) { //échec au roi
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        return false;
    }

    public boolean checkmate(PlayerColor color) { //échec et mat
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        int a = 0;
        int b = 0;
        Piece king = getPiece(kingPos.x, kingPos.y);
        for (int x = kingPos.x - 1; x <= kingPos.x + 1; ++x) {
            for (int y = kingPos.y - 1; y <= kingPos.y + 1; ++y) {
                if (x == kingPos.x && y == kingPos.y) continue;
                if (x < 0 || y < 0 || x >= 8 || y >= 8) continue;
                if (king.canMove(this, kingPos.x, kingPos.y, x, y)) {
                    a++;
                    if (isADangerousPlace(kingPos.x, kingPos.y, x, y)) {
                        b++;
                    }
                }
            }
        }
        System.out.println(king+" "+a+" "+b);
        return a > 0 && a == b;
        /**
         * 1) Vérifier que le roi n'as pas de mouvement valable
         * 2) Vérifier qu'il ne peut pas être sauvé par un allié
         */
    }

    public boolean checkCastling(PlayerColor color) { //roque
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        KingPiece king = getKing(color);
        if (!king.hasMoved() //todo roi pas bougé, tour pas bougé, et pas de pieces ennemie qui pourrait venir entre deux
        ) { //todo

        } else {
            //todo
        }
        return false;
    }

    public boolean movePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);

        if (!p.canMove(this, fromX, fromY, toX, toY)) return false;

        if (p.getClass() == KingPiece.class) {
            if (isADangerousPlace(fromX, fromY, toX, toY)) return false;
            kingsPos[(p.color() == PlayerColor.WHITE) ? 0 : 1].set(toX, toY);
            System.out.println(kingsPos[0].x + " " + kingsPos[0].y + "-" + kingsPos[1].x + " " + kingsPos[1].y);
        }
        setPiece(fromX, fromY, toX, toY);


        p.setMoved(true);
        return true;
    }

    public Piece[][] board() {
        return board;
    }
}
