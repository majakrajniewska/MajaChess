package com.example.majachessalpha;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int color;
    //1 - this player's turn; 0 - opponent's turn
    private boolean turn;
    private List<Piece> playerPieces;
    private List<Rook> playerRooks; //for castle
    Player(int color, boolean turn){
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
