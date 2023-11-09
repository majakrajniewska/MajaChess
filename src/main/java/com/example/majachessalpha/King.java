package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class King extends Piece{
    private Image pieceImage;
    private ImageView pieceImageView;
    int color; //black 0 white 1
    char pieceChar;

    public King(int color, int gridSize, AnchorPane pane, char[][] board, ImageView[][] boardImg) {
        super(1000, gridSize, pane, board, boardImg); // value

        //set color of the image
        if(color == 1){
            pieceImage = loadImage("img/kingW.png");
            this.color = 1;
            this.pieceChar = 'K';
        } else{
            pieceImage = loadImage("img/kingB.png");
            this.color = 0;
            this.pieceChar = 'k';
        }

        //resize and make the image draggable
        pieceImageView = prepareImage(pieceImage);
    }

    public ImageView getPieceImage() {
        return pieceImageView;
    }

    int[][] movement = {
            {1, 0},
            {1, -1},
            {1, 1},
            {0, 1},
            {0, -1},
            {-1, -1},
            {-1, 0},
            {-1, 1}
    };
    //Validating the move
    @Override
    public boolean isValidMove(){
        int deltaX = Math.abs(startX - currentX);
        int deltaY = Math.abs(startY - currentY);
        return (deltaX + deltaY <= 1 || deltaX <= 1 && deltaY <= 1) && isSquareAvailable();
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
