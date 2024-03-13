package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class King extends Piece{
    private Image pieceImage;
    private ImageView pieceImageView;
    boolean isFirstMove;

    public King(boolean color, AnchorPane pane){
        super(color, pane);
        //set color of the image
        if(color == WHITE_COLOR){
            pieceImage = loadImage("img/kingW.png");
            this.color = WHITE_COLOR;
            this.pieceChar = 'K';
        } else{
            pieceImage = loadImage("img/kingB.png");
            this.color = BLACK_COLOR;
            this.pieceChar = 'k';
        }
        isFirstMove = true;
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
        if ((deltaX + deltaY <= 1 || deltaX <= 1 && deltaY <= 1) && isSquareAvailable()){
            isFirstMove = false;
            return true;
        }
        if(isValidCastle(currentX)){
            isFirstMove = false;
            return true;
        }
        return false;
    }
    @Override
    public List<int[]> generateLegalMoves() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = startX - move[0];
            int y = startY - move[1];

            if (isValidSquare(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, whitePiece()))){
                legalMoves.add(new int[]{x, y});
            }

        }
        return legalMoves;
    }
    @Override
    public List<int[]> generateLegalMovesWithCheck() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = startX - move[0];
            int y = startY - move[1];

            if (isValidSquare(x, y) && isValidMoveWithCheck(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, whitePiece()))){
                legalMoves.add(new int[]{x, y});
            }
        }
        if(isValidCastle(startX-2)) legalMoves.add(new int[]{startX-2, startY});
        if(isValidCastle(startX+2)) legalMoves.add(new int[]{startX+2, startY});
        return legalMoves;
    }

    //targetX: startX-2 or startX+2
    public boolean isValidCastle(int targetX){
        if(isFirstMove){
            if(targetX == startX-2){
                //player black
                if(startY == 0){
                    for(Rook rook : getPlayerBlackRooks()){
                        if(rook.startX == startX-4) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(startX - 4) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
                //player white
                else if(startY == 7){
                    for(Rook rook : getPlayerWhiteRooks()){
                        if(rook.startX == startX-4) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(startX - 4) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
            }
            else if(targetX == startX + 2){
                //player black
                if(startY == 0){
                    for(Rook rook : getPlayerBlackRooks()){
                            return rook.isFirstMove() &&
                                !isPieceBetweenRookAndKing(startX+3) &&
                                !isCheckWhileCastle();
                    }
                }
                //player white
                else if(startY == 7){
                    for(Rook rook : getPlayerWhiteRooks()){
                        if(rook.startX == startX+3) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(startX + 3) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isPieceBetweenRookAndKing(int rookX){
        if(startX < rookX) {
            for (int i = startX+1; i < rookX; i++)
                if (charBoard[i][startY] != '.') return true;
        }
        else {
            for (int i = rookX+1; i < startX; i++)
                if (charBoard[i][startY] != '.') return true;
        }
        return false;
    }

    public boolean isCheckWhileCastle(){    
        if(whitePiece()){
            if(startX < currentX)
                return loopPiecesBlack(startX, currentX);
            else
                return loopPiecesBlack(currentX, startX);
        } else{
            if(startX < currentX)
                return loopPiecesWhite(startX, currentX);
            else
                return loopPiecesWhite(currentX, startX);
        }
    }
    public boolean loopPiecesWhite(int start, int end){
        for(int x = start; x <= end; x++){
            for(Piece p : getPlayerWhitePieces()) {
                for (int[] move : p.generateLegalMoves()) {
                    if (move[0] == x && move[1] == blackKingCoordinates[1]) return true;
                }
            }
        }
        return false;
    }
    public boolean loopPiecesBlack(int start, int end){
        for(int x = start; x <= end; x++){
            for(Piece p : getPlayerBlackPieces()) {
                for (int[] move : p.generateLegalMoves()) {
                    if (move[0] == x && move[1] == whiteKingCoordinates[1]) return true;
                }
            }
        }
        return false;
    }
}
