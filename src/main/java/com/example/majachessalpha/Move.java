package com.example.majachessalpha;

public class Move {
    private Piece piece;
    private Point startPoint;
    private Point newPoint;

    public Move(Piece piece, Point startPoint, Point newPoint) {
        this.piece = piece;
        this.startPoint = startPoint;
        this.newPoint = newPoint;
    }

    public Piece getPiece() {
        return piece;
    }
    public String toString(){
        return "PIECE: " + piece.getPieceChar() + "; START: " + startPoint.toString() + "; END: " + newPoint.toString();
    }
}
