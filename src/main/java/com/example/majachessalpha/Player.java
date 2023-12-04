package com.example.majachessalpha;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int color;
    //1 - this player's turn; 0 - opponent's turn
    private boolean turn;
    private List<Piece> playerPieces;
    Player(int color){
        this.color = color;
        playerPieces = new ArrayList<>();
    }
    Player(int color, boolean turn){
        this.turn = turn;
        this.color = color;
        playerPieces = new ArrayList<>();
    }

    List<Piece> getPlayerPieces(){
        return playerPieces;
    }
    boolean isPlayerTurn(){return turn;}
    void switchPlayersTurn(){
        if(turn) turn = false;
        else turn = true;
    }
}
