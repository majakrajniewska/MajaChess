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
    public List<Point> generateLegalMoves() {
        legalMoves.clear();
        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = getStartPoint().getX() + dx;
            int y = getStartPoint().getY() + dy;

            while (isValidSquare(x, y)) {

                if (isSquareEmpty(x, y)) {
                    legalMoves.add(new Point(x, y));
                } else if (isSquareOccupied(x, y, whitePiece())) {
                    legalMoves.add(new Point(x, y));  // Can capture opponent's piece.
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
    public List<Point> generateLegalMovesWithCheck() {
        legalMoves.clear();
        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = getStartPoint().getX() + dx;
            int y = getStartPoint().getY() + dy;

            Point tempPoint = new Point(x, y);

            while (isValidSquare(tempPoint)) {
                if (isSquareEmpty(tempPoint) && isValidMoveWithCheck(tempPoint)) {
                    legalMoves.add(tempPoint);
                } else if (isSquareOccupied(tempPoint, whitePiece()) && isValidMoveWithCheck(tempPoint)) {
                    legalMoves.add(tempPoint);  // Can capture opponent's piece.
                    break;  // No need to check further in this direction.
                }else if(isSquareEmpty(tempPoint) && !isValidMoveWithCheck()){} //if square is empty, but there is check - keep going
                else {
                    break;  // Friendly piece blocking the way.
                }

                x += dx;
                y += dy;
                tempPoint.setPoint(x, y);
            }
        }
        return legalMoves;
    }
}
