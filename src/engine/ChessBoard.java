package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import engine.utils.Lol;
import engine.utils.Position;

import java.util.ArrayList;

public class ChessBoard {
    private final int boardSize = 8;
    private final Piece[][] board = new Piece[boardSize][boardSize];
    private final Position[] kingsPos = {
            new Position(),
            new Position(),
    };
    private PlayerColor winner = null;
    private final ArrayList<Position> updatedPositions = new ArrayList<>();

    /**
     *
     */
    public ChessBoard() {
        initPlayerBoard(PlayerColor.WHITE, 0, 1);
        initPlayerBoard(PlayerColor.BLACK, boardSize - 1, boardSize - 2);
    }

    public ArrayList<Position> getUpdatedPositions() {
        return updatedPositions;
    }

    public void clearUpdatedPositions() {
        updatedPositions.clear();
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

    /**
     * @param posX position x
     * @param posY position y
     * @return la piece à la position (x,y)
     */
    public Piece getPiece(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= boardSize || posY >= boardSize) return null; //TODO lol c'est moche
        return board[posY][posX];
    }

    /**
     * @param killer piece déplacée
     * @param killed piece supprimée
     */
    private void killed(Piece killer, Piece killed) {
        System.out.println(killed + " a été tué par " + killer);

        if (killed.type() == PieceType.PAWN) Lol.playSound("deathSoundRoblox.wav");
        else if (killed.type() == PieceType.BISHOP) Lol.playSound("deathSoundMurloc.wav");
        else if (killed.type() == PieceType.KING) Lol.playSound("deathSoundMGS.wav");
        else if (killed.type() == PieceType.ROOK) Lol.playSound("deathSoundYoda.wav");
        else Lol.playSound("deathSoundMinecraft.wav");

        if (killed.type() == PieceType.KING) {
            winner = killer.color();
        }
    }

    /**
     * @return
     */
    //TODO check match null;
    public boolean isGameOver() {
        return winner != null;
    }

    /**
     * @return
     */
    public PlayerColor getWinner() {
        return winner;
    }

    /**
     * @param fromX position x initiale de la pièce déplacée
     * @param fromY position y initiale de la pièce déplacée
     * @param toX   position x d'arrivée de la pièce déplacée
     * @param toY   position y d'arrivée de la pièce déplacée
     */
    public void setPiece(int fromX, int fromY, int toX, int toY) {
        setPiece(toX, toY, getPiece(fromX, fromY));
        setPiece(fromX, fromY, null);
    }

    /**
     * @param x     position x où placer une pièce
     * @param y     position y où placer une pièce
     * @param piece pièce à placer
     */
    public void setPiece(int x, int y, Piece piece) {
        if (piece != null) {
            if (piece.getClass() == KingPiece.class) {
                kingsPos[(piece.color() == PlayerColor.WHITE) ? 0 : 1].set(x, y);
            }
            piece.setMoved(true);
        }
        board[y][x] = piece;
        updatedPositions.add(new Position(x, y));
    }

    /**
     * vérifie si place est dangereuse
     *
     * @param fromX position x de départ de la pièce
     * @param fromY position y de départ de la pièce
     * @param toX   position x d'arrivée de la pièce
     * @param toY   position y d'arrivée de la pièce
     * @return vrai si la place est dangereuse
     */
    public boolean isADangerousPlace(int fromX, int fromY, int toX, int toY) {
        Piece from = getPiece(fromX, fromY);
        Piece to = getPiece(toX, toY);

        boolean isDangerous = false;

        board[fromY][fromX] = null;
        board[toY][toX] = from;

        for (int x = 0; x < boardSize; ++x) {
            for (int y = 0; y < boardSize; ++y) {
                if (x == toX && y == toY) continue;
                Piece piece = getPiece(x, y); //piece courante pour laquelle on vérifie que pas d'échec possible
                if (piece != null && piece.canMove(this, x, y, toX, toY)) {
                    isDangerous = true;
                    break;
                }
            }
        }
        board[toY][toX] = to;
        board[fromY][fromX] = from;
        return isDangerous;
    }

    /**
     * @param color couleur du roi
     * @return vrai si roi est en échec
     */
    public boolean checkKingInCheck(PlayerColor color) { //échec au roi
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        return false;
    }

    /**
     * @param color couleur du joueur
     * @return vrai si échec et mat
     */
    public boolean checkmate(PlayerColor color) { //échec et mat
        Position kingPos = kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
        int a = 0;
        int b = 0;
        Piece king = getPiece(kingPos.x, kingPos.y);
        for (int x = kingPos.x - 1; x <= kingPos.x + 1; ++x) {
            for (int y = kingPos.y - 1; y <= kingPos.y + 1; ++y) {
                if (x == kingPos.x && y == kingPos.y) continue;
                if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) continue;
                if (king.canMove(this, kingPos.x, kingPos.y, x, y)) {
                    a++;
                    if (isADangerousPlace(kingPos.x, kingPos.y, x, y)) {
                        b++;
                    }
                }
            }
        }
        return a > 0 && a == b;
        /**
         * 1) Vérifier que le roi n'as pas de mouvement valable
         * 2) Vérifier qu'il ne peut pas être sauvé par un allié
         */
    }

    /**
     * @param fromX position x de départ du roi qui veut faire le roque
     * @param fromY position y de départ du roi qui veut faire le roque
     * @param toX   position x de déplacement pour faire le roque
     * @param toY   position y de déplacement pour faire le roque
     * @return vrai si on a effectué le roque
     */
    private boolean doCastling(int fromX, int fromY, int toX, int toY) { //roque
        KingPiece king = (KingPiece) getPiece(fromX, fromY);
        if (king.hasMoved()) return false;
        if (toY != fromY) return false;
        if (toX - fromX == 2) { //petit roque
            //todo : vérifier que rien (qui peut arriver) sur chemin du roi
            if ((isADangerousPlace(fromX, fromY, toX, toY) ||               // arrivée pas dangereuse
                    isADangerousPlace(fromX, fromY, toX - 1, toY)) ||  // chemin pas dangereux
                    getPiece(toX, toY) != null ||                           // pas de pièce à l'arrivée
                    getPiece(toX - 1, toY) != null)                   // pas de pièce avant l'arrivée
                return false;
            Piece rook = getPiece(boardSize - 1, fromY);
            if (rook != null && rook.type() == PieceType.ROOK && !rook.hasMoved()) {
                setPiece(boardSize - 1, fromY, toX - 1, fromY);
            } else {
                return false;
            }
        } else if (toX - fromX == -2) { //grand roque
            //todo : vérifier que rien (qui peut arriver) sur chemin du roi
            if (isADangerousPlace(fromX, fromY, toX, toY) ||
                    isADangerousPlace(fromX, fromY, toX + 1, toY) ||
                    getPiece(toX, toY) != null ||
                    getPiece(toX + 1, toY) != null)
                return false;
            Piece rook = getPiece(0, fromY);
            if (rook != null && rook.type() == PieceType.ROOK && !rook.hasMoved()) {
                setPiece(0, fromY, toX + 1, fromY);
            } else {
                return false;
            }
        } else {
            return false;
        }
        setPiece(fromX, fromY, toX, toY);
        return true;
    }

    /**
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX   position x d'arrivée
     * @param toY   position y d'arrivée
     * @return vrai si on peut bouger la pièce
     */
    public boolean movePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);

        if (p.getClass() == KingPiece.class) {
            if (isADangerousPlace(fromX, fromY, toX, toY)) return false;
            if (!isADangerousPlace(fromX, fromY, fromX, fromY) && doCastling(fromX, fromY, toX, toY)) {
                return true;
            }
        }

        if (!p.canMove(this, fromX, fromY, toX, toY)) return false;
        if (getPiece(toX, toY) != null)
            killed(p, getPiece(toX, toY));
        setPiece(fromX, fromY, toX, toY);
        return true;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean isPawnPromotable(int x, int y) {
        Piece p = getPiece(x, y);
        if (p.type() != PieceType.PAWN) return false;
        return (p.color() == PlayerColor.WHITE && y == boardSize - 1 ||
                p.color() == PlayerColor.BLACK && y == 0);
    }

    /**
     * @return
     */
    public Piece[][] board() {
        return board;
    }
}
