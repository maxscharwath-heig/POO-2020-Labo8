package engine;

import chess.PlayerColor;
import engine.piece.*;

public class ChessBoard {
    private final Piece[][] board = new Piece[8][8];

    private int[][] KingsPos = new int[2][2];

    private int rounds = 0;
    private boolean hasMoved = false;

    public boolean playerHasMoved(){
        return this.hasMoved;
    }

    public void nextRound() {
        //todo moche je réflechis;
        hasMoved = false;
        ++rounds;
    }

    PlayerColor getPlayerColor(){
        return rounds%2==0?PlayerColor.WHITE:PlayerColor.BLACK;
    }

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

    public boolean movePiece(int fromX, int fromY, int toX, int toY) {
        Piece p = getPiece(fromX, fromY);

        if(p.color() != getPlayerColor()){
            return false;
        }

        boolean bouger = p.canMove(this, fromX, fromY, toX, toY);
        if (bouger) {
            board[toY][toX] = board[fromY][fromX];
            board[fromY][fromX] = null;
        }
        if(bouger){
            if(p.getClass() == KingPiece.class){
                KingsPos[(p.color() == PlayerColor.WHITE)?0:1] = new int[]{toX, toY};
                System.out.println(KingsPos[0][0]+" "+KingsPos[0][1]+"-"+KingsPos[1][0]+" "+KingsPos[1][1]);
            }
            //todo peut etre moche
            hasMoved = true;
            nextRound();
        }
        return bouger;
    }

    public Piece[][] board() {
        return board;
    }
}
