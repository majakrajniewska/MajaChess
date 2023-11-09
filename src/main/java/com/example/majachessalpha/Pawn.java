package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Pawn extends Piece {

    private Image pieceImage;
    ImageView pieceImageView;
    int color; //black 0 white 1
    char pieceChar;
    boolean isFirstMove;


    public Pawn(int color, int gridSize, AnchorPane pane, char [][] board, ImageView[][] boardImg) {
        super(1, gridSize, pane, board, boardImg); // value
        isFirstMove = true;
        //set color of image
        if(color == 1){
            pieceImage = loadImage("img/pawnW.png");
            this.color = 1;
            this.pieceChar = 'P';
        } else{
            pieceImage = loadImage("img/pawnB.png");
            this.color = 0;
            this.pieceChar = 'p';
        }

        //resize and make the image draggable
        pieceImageView = prepareImage(pieceImage);
        }

    public ImageView getPieceImage() {
        return pieceImageView;
    }

    //Validating the move
    @Override
    public boolean isValidMove(){
        //allowing to go 2 squares in the first move
        if(isFirstMove){
            if(isHorizontal() && Math.abs(startY - currentY)==2 && isNotBlocked()){
                isFirstMove = false;
                return true;
            }
        }
        //allowing to go one square or capture another piece
        if(color == 1){
            if(currentY == startY-1 && isHorizontal() && isSquareEmpty(currentX, currentY)){
                isFirstMove = false;
                return true;
            }
            else if(((currentX == startX - 1 && currentY == startY - 1) ||
                    (currentX == startX + 1 && currentY == startY - 1)) &&
                    (canCapture())){
                isFirstMove = false;
                return true;
            } else return false;
        } else {
            if(currentY == startY+1 && isHorizontal() && isSquareEmpty(currentX, currentY)){
                isFirstMove = false;
                return true;
            }
            else if((currentX == startX - 1 && currentY == startY + 1) ||
                    (currentX == startX + 1 && currentY == startY + 1) &&
                    (canCapture())){
                isFirstMove = false;
                return true;
            } else return false;
        }
    }
    @Override
    public char getPieceChar(){
        return pieceChar;
    }
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();
        if(color == 1){
            if(isFirstMove && isSquareEmpty(startX, startY-1) && isSquareEmpty(startX, startY-2))
                legalMoves.add(new int[]{startX, startY-2});
            if(isValidSquare(startX, startY-1) && isSquareEmpty(startX, startY-1))
                legalMoves.add(new int[]{startX, startY-1});
            if(isValidSquare(startX-1, startY-1) && isSquareOccupied(startX, startY-1, 1))
                legalMoves.add(new int[]{startX-1, startY-1});
            if(isValidSquare(startX+1, startY-1) && isSquareOccupied(startX, startY-1, 1))
                legalMoves.add(new int[]{startX-1, startY-1});
        } else{
            if(isFirstMove && isSquareEmpty(startX, startY+1) && isSquareEmpty(startX, startY+2))
                legalMoves.add(new int[]{startX, startY+2});
            if(isValidSquare(startX, startY+1) && isSquareEmpty(startX, startY+1))
                legalMoves.add(new int[]{startX, startY+1});
            if(isValidSquare(startX-1, startY+1) && isSquareOccupied(startX, startY+1, 0))
                legalMoves.add(new int[]{startX-1, startY+1});
            if(isValidSquare(startX+1, startY+1) && isSquareOccupied(startX, startY+1, 0))
                legalMoves.add(new int[]{startX-1, startY+1});
        }

        return legalMoves;
    }

}
