package com.example.majachessalpha;

import java.util.List;

public class GameHandler {
    static Player playerWhite;
    static Player playerBlack;

    static int[] whiteKingCoordinates = new int[2];
    static int[] blackKingCoordinates = new int[2];
    GameHandler(){
        if(playerWhite == null) playerWhite = new Player(1);
        if(playerBlack == null) playerBlack = new Player(0);
    }

    public List<Piece> getPlayerWhitePieces() {
        return playerWhite.getPlayerPieces();
    }

    public List<Piece> getPlayerBlackPieces() {
        return playerBlack.getPlayerPieces();
    }

    public static int[] getWhiteKingCoordinates() {
        return whiteKingCoordinates;
    }

    public static int[] getBlackKingCoordinates() {
        return blackKingCoordinates;
    }
}
