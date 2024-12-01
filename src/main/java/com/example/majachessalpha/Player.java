package com.example.majachessalpha;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private boolean color;
    //true - white; false - black
    private boolean turn;
    private List<Piece> playerPieces;
    private List<Rook> playerRooks; //for castle
    Player(boolean color, boolean turn){
        this.turn = turn;
        this.color = color;
        playerPieces = new ArrayList<>();
        playerRooks = new ArrayList<>();
    }

    List<Piece> getPlayerPieces(){
        return playerPieces;
    }
    List<Rook> getPlayerRooks(){
        return playerRooks;
    }
    boolean isPlayerTurn(){return turn;}
    void switchPlayersTurn(){
        turn = !turn;
    }
}
