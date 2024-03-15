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
            setPieceChar('K');
        } else{
            pieceImage = loadImage("img/kingB.png");
            setPieceChar('k');
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
        int deltaX = Math.abs(getStartPoint().getX() - getNewPoint().getX());
        int deltaY = Math.abs(getStartPoint().getY() - getNewPoint().getY());
        if ((deltaX + deltaY <= 1 || deltaX <= 1 && deltaY <= 1) && isSquareAvailable()){
            isFirstMove = false;
            return true;
        }
        if(isValidCastle(getNewPoint().getX())){
            isFirstMove = false;
            return true;
        }
        return false;
    }
    @Override
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
    @Override
    public List<int[]> generateLegalMovesWithCheck() {
        legalMoves.clear();

        for (int[] move : movement) {
            int x = getStartPoint().getX() - move[0];
            int y = getStartPoint().getY() - move[1];

            if (isValidSquare(x, y) && isValidMoveWithCheck(x, y) && (isSquareEmpty(x, y) || isSquareOccupied(x, y, whitePiece()))){
                legalMoves.add(new int[]{x, y});
            }
        }
        if(isValidCastle(getStartPoint().getX()-2))
            legalMoves.add(new int[]{getStartPoint().getX()-2, getStartPoint().getY()});
        if(isValidCastle(getStartPoint().getX()+2))
            legalMoves.add(new int[]{getStartPoint().getX()+2, getStartPoint().getY()});
        return legalMoves;
    }

    //targetX: getStartPoint().getX()-2 or getStartPoint().getX()+2
    public boolean isValidCastle(int targetX){
        if(isFirstMove){
            if(targetX == getStartPoint().getX()-2){
                //player black
                if(getStartPoint().getY() == 0){
                    for(Rook rook : getPlayerBlackRooks()){
                        if(rook.getStartPoint().getX() == getStartPoint().getX()-4) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(getStartPoint().getX() - 4) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
                //player white
                else if(getStartPoint().getY() == 7){
                    for(Rook rook : getPlayerWhiteRooks()){
                        if(rook.getStartPoint().getX() == getStartPoint().getX()-4) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(getStartPoint().getX() - 4) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
            }
            else if(targetX == getStartPoint().getX() + 2){
                //player black
                if(getStartPoint().getY() == 0){
                    for(Rook rook : getPlayerBlackRooks()){
                            return rook.isFirstMove() &&
                                !isPieceBetweenRookAndKing(getStartPoint().getX()+3) &&
                                !isCheckWhileCastle();
                    }
                }
                //player white
                else if(getStartPoint().getY() == 7){
                    for(Rook rook : getPlayerWhiteRooks()){
                        if(rook.getStartPoint().getX() == getStartPoint().getX()+3) {
                            return rook.isFirstMove() &&
                                    !isPieceBetweenRookAndKing(getStartPoint().getX() + 3) &&
                                    !isCheckWhileCastle();
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isPieceBetweenRookAndKing(int rookX){
        if(getStartPoint().getX() < rookX) {
            for (int i = getStartPoint().getX()+1; i < rookX; i++)
                if (charBoard[i][getStartPoint().getY()] != '.') return true;
        }
        else {
            for (int i = rookX+1; i < getStartPoint().getX(); i++)
                if (charBoard[i][getStartPoint().getY()] != '.') return true;
        }
        return false;
    }

    public boolean isCheckWhileCastle(){    
        if(whitePiece()){
            if(getStartPoint().getX() < getNewPoint().getX())
                return loopPiecesBlack(getStartPoint().getX(), getNewPoint().getX());
            else
                return loopPiecesBlack(getNewPoint().getX(), getStartPoint().getX());
        } else{
            if(getStartPoint().getX() < getNewPoint().getX())
                return loopPiecesWhite(getStartPoint().getX(), getNewPoint().getX());
            else
                return loopPiecesWhite(getNewPoint().getX(), getStartPoint().getX());
        }
    }
    public boolean loopPiecesWhite(int start, int end){
        for(int x = start; x <= end; x++){
            for(Piece p : getPlayerWhitePieces()) {
                for (int[] move : p.generateLegalMoves()) {
                    if (move[0] == x && move[1] == blackKingPoint.getY()) return true;
                }
            }
        }
        return false;
    }
    public boolean loopPiecesBlack(int start, int end){
        for(int x = start; x <= end; x++){
            for(Piece p : getPlayerBlackPieces()) {
                for (int[] move : p.generateLegalMoves()) {
                    if (move[0] == x && move[1] == whiteKingPoint.getY()) return true;
                }
            }
        }
        return false;
    }
}
