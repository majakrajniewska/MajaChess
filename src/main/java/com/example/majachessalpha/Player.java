package com.example.majachessalpha;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int color;
    List<Piece> playerPieces;
    Player(int color){
        this.color = color;
        playerPieces = new ArrayList<>();
    }

    List<Piece> getPlayerPieces(){
        return playerPieces;
    }
    void addPieceToPlayerePieces(Piece piece){
        playerPieces.add(piece);
    }
}
