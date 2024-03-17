package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Knight extends Piece {
    private Image pieceImage;
    private ImageView pieceImageView;
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
    public Knight(boolean color, AnchorPane pane){
        super(color,pane);
        setValue(3);
        //set color of the image
        if(color == WHITE_COLOR){
            pieceImage = loadImage("img/knightW.png");
            setPieceChar('N');
        } else{
            pieceImage = loadImage("img/knightB.png");
            setPieceChar('n');
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
            if(getStartPoint().getX() - getNewPoint().getX() == move[0] &&
                    getStartPoint().getY() - getNewPoint().getY() == move[1] &&
                    isSquareAvailable()){
                return true;
            }
        }
        return false;
    }
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = getStartPoint().getX() - move[0];
            int y = getStartPoint().getY() - move[1];

            if (isValidSquare(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, whitePiece()))){
                legalMoves.add(new int[]{x, y});
            }
        }
        return legalMoves;
    }
    public List<int[]> generateLegalMovesWithCheck() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = getStartPoint().getX() - move[0];
            int y = getStartPoint().getY() - move[1];

            if(isValidSquare(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, whitePiece())) && isValidMoveWithCheck(x, y))
                legalMoves.add(new int[] {x, y});
        }
        return legalMoves;
    }
}
