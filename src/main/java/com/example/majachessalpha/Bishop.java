package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Bishop extends Piece{
    private Image pieceImage;
    private ImageView pieceImageView;
    int color; //black 0 white 1
    int value;
    char pieceChar;
    public Bishop(int color, int gridSize, AnchorPane pane){
        super(gridSize, pane);
        this.value = 3;
        //set color of the image
        if(color == 1){
            pieceImage = loadImage("img/bishopW.png");
            this.color = 1;
            this.pieceChar = 'B';
        } else{
            pieceImage = loadImage("img/bishopB.png");
            this.color = 0;
            this.pieceChar = 'b';
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
        return isDiagonal() && isNotBlocked();
    }
    @Override
    public char getPieceChar(){
        return pieceChar;
    }
    @Override
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();

        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = startX + dx;
            int y = startY + dy;

            while (isValidSquare(x, y)) {
                if (isSquareEmpty(x, y)) {
                    legalMoves.add(new int[]{x, y});
                } else if (isSquareOccupied(x, y, color)) {
                    legalMoves.add(new int[]{x, y});  // Can capture opponent's piece.
                    break;  // No need to check further in this direction.
                } else {
                    break;  // Friendly piece blocking the way.
                }

                x += dx;
                y += dy;
            }
        }

        return legalMoves;
    }
}
