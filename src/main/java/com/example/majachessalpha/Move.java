package com.example.majachessalpha;

import java.util.Vector;

public class Move {
    private Piece piece;
    private Point startPoint;
    private Point newPoint;

    public Move(Piece piece, Point startPoint, Point newPoint) {
        this.piece = piece;
        this.startPoint = startPoint;
        this.newPoint = newPoint;
    }

//    public void printMove(){
//        System.out.println(piece.getPieceChar() + " move from X: " + getStartPoint().getX() + ", Y: " + getStartPoint().getY() + ", to X: " + getNewPoint().getX() + ", Y: " + currentY);
//    }
    public Piece getPiece() {
        return piece;
    }
}
