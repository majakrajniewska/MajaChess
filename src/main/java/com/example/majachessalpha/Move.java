package com.example.majachessalpha;

import java.util.Vector;

public class Move {
    private Piece piece;
    private int startX;
    private int startY;
    private int currentX;
    private int currentY;

    public Move(Piece piece, int startX, int startY, int curX, int curY) {
        this.piece = piece;
        this.startX = startX;
        this.startY = startY;
        this.currentX = curX;
        this.currentY = curY;
    }

    public void printMove(){
        System.out.println(piece.getPieceChar() + " move from X: " + startX + ", Y: " + startY + ", to X: " + currentX + ", Y: " + currentY);
    }
    public Piece getPiece() {
        return piece;
    }
    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public int getCurrentX() {
        return currentX;
    }
    public int getCurrentY() {
        return currentY;
    }
}
