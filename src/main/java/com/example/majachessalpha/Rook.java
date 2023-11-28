package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Rook extends Piece {
    private Image pieceImage;
    private ImageView pieceImageView;
    int color; //black 0 white 1
    int value;
    char pieceChar;

    public Rook(int color, AnchorPane pane){
        super(pane);
        this.value = 5;
        //set color of the image
        if(color == 1){
            pieceImage = loadImage("img/rookW.png");
            this.color = 1;
            pieceChar = 'R';
        } else{
            pieceImage = loadImage("img/rookB.png");
            this.color = 0;
            pieceChar = 'r';
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
        return (isHorizontal() || isVertical()) && isNotBlocked();
    }
    @Override
    public char getPieceChar(){
        return pieceChar;
    }
    public int getPieceColor() {
        return color;
    }

    @Override
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Horizontal and vertical
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

    @Override
    public List<int[]> generateLegalMovesWithCheck() {
        legalMoves.clear();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Horizontal and vertical
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = startX + dx;
            int y = startY + dy;

            while (isValidSquare(x, y)) {
                if (isSquareEmpty(x, y) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new int[]{x, y});
                } else if (isSquareOccupied(x, y, color) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new int[]{x, y});  // Can capture opponent's piece.
                    break;  // No need to check further in this direction.
                } else if(isSquareEmpty(x, y) && !isValidMoveWithCheck()){} //if square is empty, but there is check - keep going
                else {
                    break;  // Friendly piece blocking the way.
                }

                x += dx;
                y += dy;

            }
        }
        return legalMoves;
    }
}
