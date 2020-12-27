package engine.utils;

import engine.ChessBoard;

public class GenericMovement {
    public static boolean crossMovement(ChessBoard board, int fromX, int fromY, int toX, int toY){
        if (fromX == toX) {//VERTICAL MOVE
            int delta = toY - fromY > 0 ? 1 : -1;
            for (int i = fromY + delta; i != toY; i += delta) {
                if (board.getPiece(fromX, i) != null) return false;
            }
        } else if (fromY == toY) {//HORIZONTAL MOVE
            int delta = toX - fromX > 0 ? 1 : -1;
            for (int i = fromX + delta; i != toX; i += delta) {
                if (board.getPiece(i, fromY) != null) return false;
            }
        } else return false;
        return true;
    }

    public static boolean diagonalMovement(ChessBoard board, int fromX, int fromY, int toX, int toY){
        int dx = toX - fromX;
        int dy = toY - fromY;
        if (dx == 0 || dy == 0) return false;
        if (Math.abs(dx) != Math.abs(dy)) return false;
        int p = dx / dy;
        int d = dx > 0 ? 1 : -1;
        for (int i = 1; i < dx * d; ++i) {
            if (board.getPiece(fromX + i * d, fromY + i * d * p) != null) return false;
        }
        return true;
    }

}
