package com.example.majachessalpha;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int color;
    List<Piece> playersPieces;
    Player(int color){
        this.color = color;
        playersPieces = new ArrayList<>();
    }

    List<Piece> getPlayersPieces(){
        return playersPieces;
    }
    void addPieceToPlayeresPieces(Piece piece){
        playersPieces.add(piece);
    }
}
