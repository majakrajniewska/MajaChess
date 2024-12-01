package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.Console;
import java.util.List;

public class Pawn extends Piece {
    private Image pieceImage;
    ImageView pieceImageView;

    public Pawn(boolean color, AnchorPane pane){
        super(color,pane);
        setValue(1);
        //set color of image
        if(color == WHITE_COLOR){
            pieceImage = loadImage("img/pawnW.png");
            setPieceChar('P');
        } else{
            pieceImage = loadImage("img/pawnB.png");
            setPieceChar('p');
        }

        //resize and make the image draggable
        pieceImageView = prepareImage(pieceImage);
        }

    @Override
    public ImageView getPieceImage() {
        return pieceImageView;
    }

    //Validating the move
    @Override
    public boolean isValidMove(){
        //allowing to go 2 squares in the first move
        if(isFirstMove()){
            if(isHorizontal() && Math.abs(getStartPoint().getY() - getNewPoint().getY()) == 2 && isNotBlocked()){
                return true;
            }
        }
        //allowing to go one square or capture another piece
        if(whitePiece()){
            //Normal move
            if(getNewPoint().getY() == getStartPoint().getY()-1 && isHorizontal() && isSquareEmpty(getNewPoint().getX(), getNewPoint().getY())){
                return true;
            }

            //Capturing
            else if (((getNewPoint().getX() == getStartPoint().getX() - 1 && getNewPoint().getY() == getStartPoint().getY() - 1) ||
                    (getNewPoint().getX() == getStartPoint().getX() + 1 && getNewPoint().getY() == getStartPoint().getY() - 1)) &&
                    (canCapture())){
                return true;
            }
            //en passant
            else return(canDoUnPassant() && getNewPoint().getY() == 2 && (getNewPoint().getX() == getStartPoint().getX()-1 || getNewPoint().getX() == getStartPoint().getX()+1));

        } else {
            //Normal move
            if(getNewPoint().getY() == getStartPoint().getY() + 1 && isHorizontal() && isSquareEmpty(getNewPoint())){
                return true;
            }

            //Capturing
            else if ((getNewPoint().getX() == getStartPoint().getX() - 1 && getNewPoint().getY() == getStartPoint().getY() + 1) ||
                    (getNewPoint().getX() == getStartPoint().getX() + 1 && getNewPoint().getY() == getStartPoint().getY() + 1) &&
                    (canCapture())){
                return true;
            }
            //en passant
            else return (canDoUnPassant() && getNewPoint().getY() == 5 && (getNewPoint().getX() == getStartPoint().getX()-1 || getNewPoint().getX() == getStartPoint().getX()+1));
        }
    }
    public List<Point> generateLegalMoves() {
        legalMoves.clear();
        if(whitePiece()){
            if(isFirstMove() && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-2))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()-2));
            if(isValidSquare(getStartPoint().getX(), getStartPoint().getY()-1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-1))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()-1));
            if(isValidSquare(getStartPoint().getX()-1, getStartPoint().getY()-1) && isSquareOccupied(getStartPoint().getX()-1, getStartPoint().getY()-1, true))
                legalMoves.add(new Point(getStartPoint().getX()-1, getStartPoint().getY()-1));
            if(isValidSquare(getStartPoint().getX()+1, getStartPoint().getY()-1) && isSquareOccupied(getStartPoint().getX()+1, getStartPoint().getY()-1, true))
                legalMoves.add(new Point(getStartPoint().getX()+1, getStartPoint().getY()-1));
            //Un passant
            if(canDoUnPassantLeft()) legalMoves.add(new Point(getStartPoint().getX()-1, getStartPoint().getY()-1));
            if(canDoUnPassantRight()) legalMoves.add(new Point(getStartPoint().getX()+1, getStartPoint().getY()-1));
        } else{
            if(isFirstMove() && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+2))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()+2));
            if(isValidSquare(getStartPoint().getX(), getStartPoint().getY()+1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+1))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()+1));
            if(isValidSquare(getStartPoint().getX()-1, getStartPoint().getY()+1) && isSquareOccupied(getStartPoint().getX()-1, getStartPoint().getY()+1, false))
                legalMoves.add(new Point(getStartPoint().getX()-1, getStartPoint().getY()+1));
            if(isValidSquare(getStartPoint().getX()+1, getStartPoint().getY()+1) && isSquareOccupied(getStartPoint().getX()+1, getStartPoint().getY()+1, false))
                legalMoves.add(new Point(getStartPoint().getX()+1, getStartPoint().getY()+1));
            //Un passant
            if(canDoUnPassantLeft()) legalMoves.add(new Point(getStartPoint().getX()-1, getStartPoint().getY()+1));
            if(canDoUnPassantRight()) legalMoves.add(new Point(getStartPoint().getX()+1, getStartPoint().getY()+1));
        }

        return legalMoves;
    }

    //doesn't have un passant yet
    public List<Point> generateLegalMovesWithCheck() {
        legalMoves.clear();
        if(whitePiece()){
            if(isFirstMove() && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-2) && isValidMoveWithCheck(getStartPoint().getX(), getStartPoint().getY()-2))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY() - 2));
            if(isValidSquare(getStartPoint().getX(), getStartPoint().getY()-1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()-1) && isValidMoveWithCheck(getStartPoint().getX(), getStartPoint().getY()-1))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()-1));
            if(isValidSquare(getStartPoint().getX()-1, getStartPoint().getY()-1) && isSquareOccupied(getStartPoint().getX()-1, getStartPoint().getY()-1, true) && isValidMoveWithCheck(getStartPoint().getX()-1, getStartPoint().getY()-1)) {
                legalMoves.add(new Point(getStartPoint().getX() - 1, getStartPoint().getY() - 1));
            }
            if(isValidSquare(getStartPoint().getX()+1, getStartPoint().getY()-1) && isSquareOccupied(getStartPoint().getX()+1, getStartPoint().getY()-1, true) && isValidMoveWithCheck(getStartPoint().getX()+1, getStartPoint().getY()-1)) {
                legalMoves.add(new Point(getStartPoint().getX() + 1, getStartPoint().getY() - 1));
            }
        } else{
            if(isFirstMove() && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+2) && isValidMoveWithCheck(getStartPoint().getX(), getStartPoint().getY()+2))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()+2));
            if(isValidSquare(getStartPoint().getX(), getStartPoint().getY()+1) && isSquareEmpty(getStartPoint().getX(), getStartPoint().getY()+1) && isValidMoveWithCheck(getStartPoint().getX(), getStartPoint().getY()+1))
                legalMoves.add(new Point(getStartPoint().getX(), getStartPoint().getY()+1));
            if(isValidSquare(getStartPoint().getX()-1, getStartPoint().getY()+1) && isSquareOccupied(getStartPoint().getX()-1, getStartPoint().getY()+1, false) && isValidMoveWithCheck(getStartPoint().getX()-1, getStartPoint().getY()+1)) {
                legalMoves.add(new Point(getStartPoint().getX() - 1, getStartPoint().getY() + 1));
            }
            if(isValidSquare(getStartPoint().getX()+1, getStartPoint().getY()+1) && isSquareOccupied(getStartPoint().getX()+1, getStartPoint().getY()+1, false) && isValidMoveWithCheck(getStartPoint().getX()+1, getStartPoint().getY()+1)){
                legalMoves.add(new Point(getStartPoint().getX() + 1, getStartPoint().getY() + 1));
            }
        }
        return legalMoves;
    }

    public boolean isFirstMove(){
        if(whitePiece())
            return getStartPoint().getY() == 6;
        else
            return getStartPoint().getY() == 1;
    }
    public boolean canDoUnPassant(){
        if(whitePiece() && getStartPoint().getY()==3){
            Move lastMove = getLastMove();
            return ((lastMove.getPiece().getPieceChar() == 'p') &&
                (lastMove.getNewPoint().getY() == 3) &&
                (lastMove.getNewPoint().getX() == this.getStartPoint().getX() - 1 || lastMove.getNewPoint().getX() == this.getStartPoint().getX() + 1) &&
                (lastMove.getStartPoint().getY() == 1));
        } else if(!whitePiece() && getStartPoint().getY()==4){
            Move lastMove = getLastMove();

            return (lastMove.getPiece().getPieceChar() == 'P') &&
                    (lastMove.getNewPoint().getY() == 4) &&
                    (lastMove.getNewPoint().getX() == this.getStartPoint().getX() - 1 || lastMove.getNewPoint().getX() == this.getStartPoint().getX() + 1) &&
                    (lastMove.getStartPoint().getY() == 6);
        }
        return false;
    }
    public boolean canDoUnPassantLeft(){
        if(canDoUnPassant()){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return lastMovedPiece.getNewPoint().getX() == this.getNewPoint().getX() - 1;
        }
        return false;
    }
    public boolean canDoUnPassantRight(){
        if(canDoUnPassant()){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return lastMovedPiece.getNewPoint().getX() == this.getNewPoint().getX() + 1;
        }
        return false;
    }
}
