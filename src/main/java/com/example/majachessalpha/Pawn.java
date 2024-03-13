package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Pawn extends Piece {
    private Image pieceImage;
    ImageView pieceImageView;

    public Pawn(boolean color, AnchorPane pane){
        super(color,pane);
        setValue(1);
        //set color of image
        if(color){
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
            if(isHorizontal() && Math.abs(startY - currentY)==2 && isNotBlocked()){
                return true;
            }
        }
        //allowing to go one square or capture another piece
        if(whitePiece()){
            if(currentY == startY-1 && isHorizontal() && isSquareEmpty(currentX, currentY)){
                return true;
            } else if (canDoUnPassant() && currentY == 2 && (currentX == startX-1 || currentX == startX+1)){
                return true;
            }
            else return ((currentX == startX - 1 && currentY == startY - 1) ||
                    (currentX == startX + 1 && currentY == startY - 1)) &&
                    (canCapture());
        } else {
            if(currentY == startY + 1 && isHorizontal() && isSquareEmpty(currentX, currentY)){
                return true;
            }else if (canDoUnPassant() && currentY == 5 && (currentX == startX-1 || currentX == startX+1)){
                return true;
            }
            else return (currentX == startX - 1 && currentY == startY + 1) ||
                    (currentX == startX + 1 && currentY == startY + 1) &&
                            (canCapture());
        }
    }
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();
        if(whitePiece()){
            if(isFirstMove() && isSquareEmpty(startX, startY-1) && isSquareEmpty(startX, startY-2))
                legalMoves.add(new int[]{startX, startY-2});
            if(isValidSquare(startX, startY-1) && isSquareEmpty(startX, startY-1))
                legalMoves.add(new int[]{startX, startY-1});
            if(isValidSquare(startX-1, startY-1) && isSquareOccupied(startX-1, startY-1, true))
                legalMoves.add(new int[]{startX-1, startY-1});
            if(isValidSquare(startX+1, startY-1) && isSquareOccupied(startX+1, startY-1, true))
                legalMoves.add(new int[]{startX+1, startY-1});
            if(canDoUnPassantLeft()) legalMoves.add(new int[]{startX-1, startY-1});
            if(canDoUnPassantRight()) legalMoves.add(new int[]{startX+1, startY-1});
        } else{
            if(isFirstMove() && isSquareEmpty(startX, startY+1) && isSquareEmpty(startX, startY+2))
                legalMoves.add(new int[]{startX, startY+2});
            if(isValidSquare(startX, startY+1) && isSquareEmpty(startX, startY+1))
                legalMoves.add(new int[]{startX, startY+1});
            if(isValidSquare(startX-1, startY+1) && isSquareOccupied(startX-1, startY+1, false))
                legalMoves.add(new int[]{startX-1, startY+1});
            if(isValidSquare(startX+1, startY+1) && isSquareOccupied(startX+1, startY+1, false))
                legalMoves.add(new int[]{startX+1, startY+1});
            if(canDoUnPassantLeft()) legalMoves.add(new int[]{startX-1, startY+1});
            if(canDoUnPassantRight()) legalMoves.add(new int[]{startX+1, startY+1});
        }

        return legalMoves;
    }

    //doesn't have un passant yet
    public List<int[]> generateLegalMovesWithCheck() {
        legalMoves.clear();
        if(whitePiece()){
            if(isFirstMove() && isSquareEmpty(startX, startY-1) && isSquareEmpty(startX, startY-2) && isValidMoveWithCheck(startX, startY-2))
                legalMoves.add(new int[]{startX, startY - 2});
            if(isValidSquare(startX, startY-1) && isSquareEmpty(startX, startY-1) && isValidMoveWithCheck(startX, startY-1))
                legalMoves.add(new int[]{startX, startY-1});
            if(isValidSquare(startX-1, startY-1) && isSquareOccupied(startX-1, startY-1, true) && isValidMoveWithCheck(startX-1, startY-1)) {
                legalMoves.add(new int[]{startX - 1, startY - 1});
            }
            if(isValidSquare(startX+1, startY-1) && isSquareOccupied(startX+1, startY-1, true) && isValidMoveWithCheck(startX+1, startY-1)) {
                legalMoves.add(new int[]{startX + 1, startY - 1});
            }
        } else{
            if(isFirstMove() && isSquareEmpty(startX, startY+1) && isSquareEmpty(startX, startY+2) && isValidMoveWithCheck(startX, startY+2))
                legalMoves.add(new int[]{startX, startY+2});
            if(isValidSquare(startX, startY+1) && isSquareEmpty(startX, startY+1) && isValidMoveWithCheck(startX, startY+1))
                legalMoves.add(new int[]{startX, startY+1});
            if(isValidSquare(startX-1, startY+1) && isSquareOccupied(startX-1, startY+1, false) && isValidMoveWithCheck(startX-1, startY+1)) {
                legalMoves.add(new int[]{startX - 1, startY + 1});
            }
            if(isValidSquare(startX+1, startY+1) && isSquareOccupied(startX+1, startY+1, false) && isValidMoveWithCheck(startX+1, startY+1)){
                legalMoves.add(new int[]{startX + 1, startY + 1});
            }
        }
        return legalMoves;
    }

    public boolean isFirstMove(){
        if(whitePiece())
            return startY == 6;
        else
            return startY == 1;
    }
    public boolean canDoUnPassant(){
        if(whitePiece() && startY==3){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return (lastMovedPiece.getPieceChar() == 'p') &&
                    (lastMovedPiece.currentY == 3) &&
                    (lastMovedPiece.currentX == this.currentX - 1 || lastMovedPiece.currentX == this.currentX + 1) &&
                    (lastMovedPiece.startY == 1);
        } else if(!whitePiece() && startY==4){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return (lastMovedPiece.getPieceChar() == 'P') &&
                    (lastMovedPiece.currentY == 4) &&
                    (lastMovedPiece.currentX == this.currentX - 1 || lastMovedPiece.currentX == this.currentX + 1) &&
                    (lastMovedPiece.startY == 6);
        }
        return false;
    }
    public boolean canDoUnPassantLeft(){
        if(canDoUnPassant()){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return lastMovedPiece.currentX == this.currentX - 1;
        }
        return false;
    }
    public boolean canDoUnPassantRight(){
        if(canDoUnPassant()){
            Move lastMove = getLastMove();
            Piece lastMovedPiece = lastMove.getPiece();
            return lastMovedPiece.currentX == this.currentX + 1;
        }
        return false;
    }
}
