package com.example.majachessalpha;

import java.util.List;

public class GameHandler {

    //Constants
    public static final boolean WHITE_COLOR = true;
    public static final boolean BLACK_COLOR = false;
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_LENGTH = 8;

    static Player playerWhite;
    static Player playerBlack;
    static Point whiteKingPoint = new Point();
    static Point blackKingPoint = new Point();
    GameHandler(){
        if(playerWhite == null) playerWhite = new Player(WHITE_COLOR, true);
        if(playerBlack == null) playerBlack = new Player(BLACK_COLOR, false);
    }

    public List<Piece> getPlayerWhitePieces() {
        return playerWhite.getPlayerPieces();
    }
    public List<Piece> getPlayerBlackPieces() {
        return playerBlack.getPlayerPieces();
    }

    //for castle
    public List<Rook> getPlayerWhiteRooks() {
        return playerWhite.getPlayerRooks();
    }
    public List<Rook> getPlayerBlackRooks() {
        return playerBlack.getPlayerRooks();
    }

    public static Point getWhiteKingPoint() {
        return whiteKingPoint;
    }
    public static Point getBlackKingPoint() {
        return blackKingPoint;
    }

    public void nextPlayersTurn(){
        playerBlack.switchPlayersTurn();
        playerWhite.switchPlayersTurn();
    }
}
