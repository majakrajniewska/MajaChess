package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Queen extends Piece{
    private Image pieceImage;
    private ImageView pieceImageView;
    public Queen(boolean color, AnchorPane pane){
        super(color, pane);
        setValue(9);
        //set color of the image
        if(color == WHITE_COLOR){
            pieceImage = loadImage("img/queenW.png");
            setPieceChar('Q');
        } else{
            pieceImage = loadImage("img/queenB.png");
            setPieceChar('q');
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
        return (isDiagonal() || isHorizontal() || isVertical()) && isNotBlocked();
    }
    @Override
    public List<Point> generateLegalMoves() {
        legalMoves.clear();

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Horizontal and vertical
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
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // Horizontal and vertical
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonals
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = getStartPoint().getX() + dx;
            int y = getStartPoint().getY() + dy;

            while (isValidSquare(x, y)) {
                if (isSquareEmpty(x, y) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new Point(x, y));
                } else if (isSquareOccupied(x, y, whitePiece()) && isValidMoveWithCheck(x, y)) {
                    legalMoves.add(new Point(x, y));  // Can capture opponent's piece.
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
