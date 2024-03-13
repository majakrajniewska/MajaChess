package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Bishop extends Piece{
    private Image pieceImage;
    private ImageView pieceImageView;
    public Bishop(boolean color, AnchorPane pane){
        super(color,pane);
        setValue(3);
        //set color of the image
        if(color == WHITE_COLOR){
            pieceImage = loadImage("img/bishopW.png");
            setPieceChar('B');
        } else{
            pieceImage = loadImage("img/bishopB.png");
            setPieceChar('b');
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
                } else if (isSquareOccupied(x, y, whitePiece())) {
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
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = startX + dx;
            int y = startY + dy;

            while (isValidSquare(x, y)) {
                if (isSquareEmpty(x, y) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new int[]{x, y});
                } else if (isSquareOccupied(x, y, whitePiece()) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new int[]{x, y});  // Can capture opponent's piece.
                    break;  // No need to check further in this direction.
                }else if(isSquareEmpty(x, y) && !isValidMoveWithCheck()){} //if square is empty, but there is check - keep going
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
