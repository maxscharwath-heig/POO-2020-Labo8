package tests;

import chess.PlayerColor;
import engine.ChessBoard;
import engine.piece.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void movePawn1() {
        ChessBoard board = new ChessBoard();
        assertTrue(board.movePiece(0, 1, 0, 2),
                "pion blanc avec mouvement de une case vers le haut");
        assertTrue(board.movePiece(0, 6, 0, 5),
                "pion noir avec mouvement de une case vers le bas");
    }

    @Test
    void movePawn2() {
        ChessBoard board = new ChessBoard();
        assertTrue(board.movePiece(1, 1, 1, 3),
                "pion blanc avec mouvement de deux cases vers le haut");
        assertTrue(board.movePiece(1, 6, 1, 4),
                "pion noir avec mouvement de deux cases vers le haut");
    }

    @Test
    void movePawnInvalid() {
        ChessBoard board = new ChessBoard();
        board.movePiece(1, 1, 1, 2);
        assertFalse(board.movePiece(1, 2, 1, 4),
                "mouvement de deux cases alors que pion déjà été déplacé");
    }

    @Test
    void moveQueenValid() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[0][3] = new QueenPiece(PlayerColor.WHITE);

        ChessBoard board = new ChessBoard(tab);
        assertTrue(board.movePiece(3, 0, 3, 7),
                "mouvement vertical");
        assertTrue(board.movePiece(3, 7, 7, 7),
                "mouvement horizontal");
        assertTrue(board.movePiece(7, 7, 0, 0),
                "mouvement diagonal");
    }

    @Test
    void moveQueenInvalid() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[0][3] = new QueenPiece(PlayerColor.WHITE);

        ChessBoard board = new ChessBoard(tab);
        assertFalse(board.movePiece(3, 0, 3, 0),
                "mouvement sur place");
        assertFalse(board.movePiece(3, 0, 5, 4),
                "mouvement ni diagonal ni vertical ni horizontal");
    }

    //ich bin müde.

    @Test
    void priseEnPassantGauche() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[6][3] = new PawnPiece(PlayerColor.BLACK);
        tab[4][4] = new PawnPiece(PlayerColor.WHITE);

        ChessBoard board = new ChessBoard(tab);
        board.movePiece(3, 6, 3, 4);
        assertTrue(board.movePiece(4, 4, 3, 5),
                "prise en passant vers la gauche");
    }

    @Test
    void priseEnPassantDroite() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[6][5] = new PawnPiece(PlayerColor.BLACK);
        tab[4][4] = new PawnPiece(PlayerColor.WHITE);

        ChessBoard board = new ChessBoard(tab);
        board.movePiece(5, 6, 5, 4);
        assertTrue(board.movePiece(4, 4, 5, 5),
                "prise en passant vers la droite");
    }

    @Test
    void CastlingInvalid() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[0][4] = new KingPiece(PlayerColor.WHITE);
        tab[4][1] = new QueenPiece(PlayerColor.WHITE);
        tab[7][4] = new KingPiece(PlayerColor.BLACK);
        tab[7][0] = new RookPiece(PlayerColor.BLACK);
        tab[7][7] = new RookPiece(PlayerColor.BLACK);
        ChessBoard board = new ChessBoard(tab);

        assertFalse(board.movePiece(4, 7, 6, 7), "Petit roque");
        assertFalse(board.movePiece(4, 7, 2, 7), "Grand roque");
    }

//help me

    @Test
    void CastlingValidKingSide() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[0][4] = new KingPiece(PlayerColor.WHITE);
        Piece King = tab[7][4] = new KingPiece(PlayerColor.BLACK);
        Piece Rook1 = tab[7][0] = new RookPiece(PlayerColor.BLACK);
        Piece Rook2 = tab[7][7] = new RookPiece(PlayerColor.BLACK);
        ChessBoard board = new ChessBoard(tab);

        assertTrue(board.movePiece(4, 7, 6, 7), "Petit roque");
        assertFalse(board.movePiece(4, 7, 2, 7), "Grand roque");
        assertEquals(board.getPiece(5, 7), Rook2, "Déplacement tour");
        assertEquals(board.getPiece(6, 7), King, "Déplacement roi");
    }

    @Test
    void CastlingValidQueenSide() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[0][4] = new KingPiece(PlayerColor.WHITE);

        Piece King = tab[7][4] = new KingPiece(PlayerColor.BLACK);
        Piece Rook1 = tab[7][0] = new RookPiece(PlayerColor.BLACK);
        Piece Rook2 = tab[7][7] = new RookPiece(PlayerColor.BLACK);
        ChessBoard board = new ChessBoard(tab);

        assertTrue(board.movePiece(4, 7, 2, 7), "Grand roque");
        assertFalse(board.movePiece(4, 7, 6, 7), "Petit roque");
        assertEquals(board.getPiece(3, 7), Rook1, "Déplacement tour");
        assertEquals(board.getPiece(2, 7), King, "Déplacement roi");
    }

    @Test
    void PawnPromotable() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        tab[7][3] = new KingPiece(PlayerColor.WHITE);
        tab[7][4] = new PawnPiece(PlayerColor.WHITE);
        tab[7][5] = new PawnPiece(PlayerColor.BLACK);

        tab[0][5] = new PawnPiece(PlayerColor.WHITE);
        tab[0][6] = new PawnPiece(PlayerColor.BLACK);
        tab[0][7] = new KingPiece(PlayerColor.BLACK);

        tab[2][5] = new PawnPiece(PlayerColor.BLACK);
        ChessBoard board = new ChessBoard(tab);

        assertFalse(board.isPromotable(3, 7), "Roi ne peut etre promu");
        assertTrue(board.isPromotable(4, 7), "Blanc promu");
        assertFalse(board.isPromotable(5, 7), "Noir pas promu (Chez lui)");
        assertFalse(board.isPromotable(5, 0), "Blanc pas promu (Chez lui)");
        assertTrue(board.isPromotable(6, 0), "Noir promu");
        assertFalse(board.isPromotable(7, 0), "Roi ne peut etre promu");
        assertFalse(board.isPromotable(5, 2), "Pion au milieu du plateau");

    }

    @Test
    void CheckKing() {
        Piece[][] tab = new Piece[ChessBoard.boardSize][ChessBoard.boardSize];
        ChessBoard board = new ChessBoard(tab);
        tab[7][3] = new KingPiece(PlayerColor.WHITE);
        tab[6][2] = new QueenPiece(PlayerColor.BLACK);
        assertFalse(board.movePiece(3, 7, 3, 6), "Roi se déplace en échec");
        assertTrue(board.movePiece(3, 7, 4, 7), "Roi ne se déplace pas en échec");

    }
}