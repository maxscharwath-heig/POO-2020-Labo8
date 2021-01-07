package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import engine.rules.CastlingRule;
import engine.rules.EnPassantRule;
import engine.rules.KingCheckRule;
import engine.rules.Rule;
import engine.utils.Position;

import java.util.ArrayList;

/**
 * Classe représentant l'échiquier du jeu
 */
public class ChessBoard {
    static public final int boardSize = 8;
    private final Rule[] rules = {
            new KingCheckRule(this),
            new CastlingRule(this),
            new EnPassantRule(this),
    };
    private final Position[] kingsPos = {
            new Position(),
            new Position(),
    };
    private final ArrayList<Position> updatedPositions = new ArrayList<>();
    private PlayerColor winner = null;
    private Piece[][] board = new Piece[boardSize][boardSize];
    private Piece lastMovedPiece = null;
    private Position lastMovedPiecePos = null;

    /**
     * constructeur utilisé pour les tests
     *
     * @param board tableau de pieces
     */
    public ChessBoard(Piece[][] board) {
        this.board = board;
    }

    /**
     * construction de l'échiquier :
     */
    public ChessBoard() {
        initPlayerBoard(PlayerColor.WHITE, 0, 1);
        initPlayerBoard(PlayerColor.BLACK, boardSize - 1, boardSize - 2);
    }

    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    public Position getLastMovedPiecePos() {
        return lastMovedPiecePos;
    }

    /**
     * getter du tableau updatedPositions
     *
     * @return le tableau updatedPositions
     */
    public ArrayList<Position> getUpdatedPositions() {
        return updatedPositions;
    }

    /**
     * Vide le tableau updatedPositions
     */
    public void clearUpdatedPositions() {
        updatedPositions.clear();
    }

    /**
     * Renvoie la position de la roi de la couleur donnée
     *
     * @param color la couleur du roi désiré
     * @return La position du roi
     */
    private Position getKingPos(PlayerColor color) {
        return kingsPos[(color == PlayerColor.WHITE) ? 0 : 1];
    }

    /**
     * Initialise la position de tous les pions d'une couleur dans l'échiquier
     *
     * @param color Couleur des pions à intialiser
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

        getKingPos(color).set(4, y1);
    }

    /**
     * Détermine un gagnant si la pièce supprimée est un Roi
     *
     * @param killer piece déplacée
     * @param killed piece supprimée
     */
    private void killed(Piece killer, Piece killed) {
        if (killed.type() == PieceType.KING) {
            winner = killer.color();
        }
    }

    /**
     * Indique si la partie est terminée
     *
     * @return vrai si winner n'est pas NULL
     */
    public boolean isGameOver() {
        return winner != null;
    }

    /**
     * getter de l'attribut winner
     *
     * @return l'attribut winner
     */
    public PlayerColor getWinner() {
        return winner;
    }

    /**
     * Retourne la pièce à la position (x,y)
     *
     * @param posX position x
     * @param posY position y
     * @return la pièce à la position (x,y)
     */
    public Piece getPiece(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= boardSize || posY >= boardSize)
            return null;
        return board[posY][posX];
    }

    /**
     * Déplace une pièce dans l'échiquier
     *
     * @param fromX position x initiale de la pièce déplacée
     * @param fromY position y initiale de la pièce déplacée
     * @param toX   position x d'arrivée de la pièce déplacée
     * @param toY   position y d'arrivée de la pièce déplacée
     */
    public void setPieceTo(int fromX, int fromY, int toX, int toY) {
        lastMovedPiece = getPiece(fromX, fromY);
        lastMovedPiecePos = new Position(fromX, fromY);
        setPiece(toX, toY, getPiece(fromX, fromY));
        setPiece(fromX, fromY, null);
    }

    /**
     * Place une pièce dans l'échiquier
     *
     * @param x     position x où placer une pièce
     * @param y     position y où placer une pièce
     * @param piece pièce à placer
     */
    public void setPiece(int x, int y, Piece piece) {
        if (piece != null) {
            if (piece.type() == PieceType.KING) {
                kingsPos[(piece.color() == PlayerColor.WHITE) ? 0 : 1].set(x, y);
            }
            piece.setMoved(true);
        }
        board[y][x] = piece;
        updatedPositions.add(new Position(x, y));
    }

    /**
     * Indique si la case d'une pièce est dangereuse (attaquée par l'ennemi)
     *
     * @param x position x d'une pièce
     * @param y position y d'une pièce
     * @return vrai la place où se trouve la pièce est dangereuse pour la pièce
     */
    public boolean isADangerousPlace(int x, int y) {
        return isADangerousPlace(x, y, x, y);
    }

    /**
     * vérifie si le déplacement de la pièce est dangereux (atterit sur une
     * case attaquée par l'ennemi
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
                //piece courante pour laquelle on vérifie que pas d'échec possible
                Piece piece = getPiece(x, y);
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
     * Indique si le roi de la couleur choisie est en échec
     *
     * @param color couleur du roi
     * @return vrai si roi est en échec
     */
    public boolean checkKingInCheck(PlayerColor color) { //échec au roi
        Position kingPos = getKingPos(color);
        return isADangerousPlace(kingPos.x, kingPos.y);
    }

    /**
     * Bouge le pièce de la position (fromX, fromY) à la position (toX, toY)
     *
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX   position x d'arrivée
     * @param toY   position y d'arrivée
     * @return vrai si on peut bouger la pièce
     */
    public boolean movePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);
        if (p == null) return false;
        if (p.type() == PieceType.KING) {
            if (isADangerousPlace(fromX, fromY, toX, toY)) return false;
        }

        //rules
        for (Rule rule : rules) {
            switch (rule.execute(fromX, fromY, toX, toY)) {
                case ACCEPT_MOVEMENT:
                    return true;
                case REJECT_MOVEMENT:
                    return false;
                default:
                    break;
            }
        }

        if (!p.canMove(this, fromX, fromY, toX, toY)) return false;
        if (getPiece(toX, toY) != null)
            killed(p, getPiece(toX, toY));
        setPieceTo(fromX, fromY, toX, toY);
        return true;
    }

    /**
     * Détermine si la pièce à la position (x,y) est promotable (sur la
     * dernière ligne du terrain adverse)
     *
     * @param x coordonnée X de la pièce
     * @param y coordonnée Y de la pièce
     * @return vrai si pièce promouvable
     */
    public boolean isPromotable(int x, int y) {
        Piece p = getPiece(x, y);
        if (p.type() != PieceType.PAWN) return false;
        return (p.color() == PlayerColor.WHITE && y == boardSize - 1 ||
                p.color() == PlayerColor.BLACK && y == 0);
    }

}
