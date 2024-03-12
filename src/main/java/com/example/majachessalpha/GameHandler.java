package com.example.majachessalpha;

import java.util.List;

public class GameHandler {
    static Player playerWhite;
    static Player playerBlack;
    static int[] whiteKingCoordinates = new int[2];
    static int[] blackKingCoordinates = new int[2];
    GameHandler(){
        if(playerWhite == null) playerWhite = new Player(1, true);
        if(playerBlack == null) playerBlack = new Player(0, false);
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
