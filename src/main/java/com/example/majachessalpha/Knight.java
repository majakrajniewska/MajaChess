package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Knight extends Piece {
    private Image pieceImage;
    private ImageView pieceImageView;
    int color; //black 0 white 1
    int value;
    char pieceChar;
    //knight possible movement
    int[][] movement = {
            {1, -2},
            {1, 2},
            {2, -1},
            {2, 1},
            {-1, 2},
            {-1, -2},
            {-2, 1},
            {-2, -1}
    };
    public Knight(int color){
        this.value = 3;
        //set color of the image
        if(color == 1){
            pieceImage = loadImage("img/knightW.png");
            this.color = 1;
            this.pieceChar = 'N';
        } else{
            pieceImage = loadImage("img/knightB.png");
            this.color = 0;
            this.pieceChar = 'n';
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
        for(int[] move : movement){
            if(startX - currentX == move[0] && startY - currentY == move[1] && isSquareAvailable()){
                return true;
            }
        }
        return false;
    }
    @Override
    public char getPieceChar(){
        return pieceChar;
    }

    @Override
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = startX - move[0];
            int y = startY - move[1];

            if (isValidSquare(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, color))){
                    legalMoves.add(new int[]{x, y});
            }
        }
        return legalMoves;
    }
}
