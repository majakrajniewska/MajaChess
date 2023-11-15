package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class PositionHandler extends GridBase{
    private static char[][] charBoard;
    private static ImageView[][] boardImageView;
    private static List<Piece> playerWhitePieces;
    private static List<Piece> playerBlackPieces;
    private static int[] whiteKingCoordinates;
    private static int[] blackKingCoordinates;

    public PositionHandler(AnchorPane pane) {
        super(pane);
        if(charBoard == null) charBoard = getBoard();
        if(boardImageView == null) boardImageView = getBoardImageViews();
        if(playerBlackPieces == null) playerBlackPieces = getPlayerBlackPieces();
        if(playerWhitePieces == null) playerWhitePieces = getPlayerWhitePieces();
        if(whiteKingCoordinates == null) whiteKingCoordinates = getWhiteKingCoordinates();
        if(blackKingCoordinates == null) blackKingCoordinates = getBlackKingCoordinates();
    }

    public void setPosition(String fen){
        int square = 0;
        for(int i = 0; i < fen.length(); i++){
            if(fen.charAt(i) >= '1' && fen.charAt(i) <= '8') {
                //set dots on empty squares in char board
                for(int j = 0; j < (fen.charAt(i) - '0'); j++){
                    charBoard[(square+j)%8][(square+j)/8] = '.';
                }
                square += (fen.charAt(i) - '0');
            } else {
                switch (fen.charAt(i)){
                    case 'r' -> {
                        Rook piece = new Rook(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'R' -> {
                        Rook piece = new Rook(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'n' -> {
                        Knight piece = new Knight(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'N' -> {
                        Knight piece = new Knight(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'b' -> {
                        Bishop piece = new Bishop(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'B' -> {
                        Bishop piece = new Bishop(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'q' -> {
                        Queen piece = new Queen(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'Q' -> {
                        Queen piece = new Queen(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'k' -> {
                        King piece = new King(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        blackKingCoordinates[0] = square%8;
                        blackKingCoordinates[1] = square/8;
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'K' -> {
                        King piece = new King(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        whiteKingCoordinates[0] = square%8;
                        whiteKingCoordinates[1] = square/8;
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'p' -> {
                        Pawn piece = new Pawn(0, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'P' -> {
                        Pawn piece = new Pawn(1, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                }
            }
        }
    }

    public void setPieceGraphic(ImageView image, AnchorPane grid, int square, char charAtI, Piece piece){
        if(square < 64){
            charBoard[square%8][square/8] = charAtI;
            boardImageView[square%8][square/8] = image;
            piece.startX = square%8;
            piece.startY = square/8;

            image.setX((square%8)*getGridSize());
            image.setY((square/8)*getGridSize());
            grid.getChildren().add(image);
        }
    }
}
