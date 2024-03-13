package com.example.majachessalpha;

import java.util.List;

public class GameHandler {

    //Constants
    public static final boolean WHITE_COLOR = true;
    public static final boolean BLACK_COLOR = false;
    public static final int NUM_OF_PLAYERS = 2;
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_LENGTH = 8;

    static Player playerWhite;
    static Player playerBlack;
    static int[] whiteKingCoordinates = new int[NUM_OF_PLAYERS];
    static int[] blackKingCoordinates = new int[NUM_OF_PLAYERS];
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

    public static int[] getWhiteKingCoordinates() {
        return whiteKingCoordinates;
    }
    public static int[] getBlackKingCoordinates() {
        return blackKingCoordinates;
    }

    public void nextPlayersTurn(){
        playerBlack.switchPlayersTurn();
        playerWhite.switchPlayersTurn();
    }
}
