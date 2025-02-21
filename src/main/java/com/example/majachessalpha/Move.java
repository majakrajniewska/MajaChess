package com.example.majachessalpha;

public class Move {
    private final Piece piece;
    private final Point startPoint;
    private final Point newPoint;

    public Move(Piece piece, Point startPoint, Point newPoint) {
        this.piece = piece;
        this.startPoint = startPoint;
        this.newPoint = newPoint;
    }

    public Piece getPiece() {
        return piece;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getNewPoint() {
        return newPoint;
    }

    public String toString(){
        return "PIECE: " + piece.getPieceChar() + "; START: " + startPoint.toString() + "; END: " + newPoint.toString();
    }
}
