package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class PositionHandler extends GridBase{
    private static char[][] charBoard;
    private static ImageView[][] boardImageView;
    private static List<Piece> playerWhitePieces;
    private static List<Piece> playerBlackPieces;
    //for castle
    private static List<Rook> playerWhiteRooks;
    private static List<Rook> playerBlackRooks;
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

        //for castle
        if(playerBlackRooks == null) playerBlackRooks = getPlayerBlackRooks();
        if(playerWhiteRooks == null) playerWhiteRooks = getPlayerWhiteRooks();
    }

    public void setPosition(String fen){
        int square = 0;
        for(int i = 0; i < fen.length(); i++){
            if(fen.charAt(i) >= '1' && fen.charAt(i) <= '8') {
                //set dots on empty squares in char board
                for(int j = 0; j < (fen.charAt(i) - '0'); j++){
                    charBoard[(square+j)%BOARD_WIDTH][(square+j)/BOARD_LENGTH] = '.';
                }
                square += (fen.charAt(i) - '0');
            } else {
                switch (fen.charAt(i)){
                    case 'r' -> {
                        Rook piece = new Rook(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                        playerBlackRooks.add(piece);
                    }
                    case 'R' -> {
                        Rook piece = new Rook(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                        playerWhiteRooks.add(piece);
                    }
                    case 'n' -> {
                        Knight piece = new Knight(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'N' -> {
                        Knight piece = new Knight(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'b' -> {
                        Bishop piece = new Bishop(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'B' -> {
                        Bishop piece = new Bishop(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'q' -> {
                        Queen piece = new Queen(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'Q' -> {
                        Queen piece = new Queen(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'k' -> {
                        King piece = new King(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        blackKingCoordinates[0] = square%8;
                        blackKingCoordinates[1] = square/8;
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'K' -> {
                        King piece = new King(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        whiteKingCoordinates[0] = square%8;
                        whiteKingCoordinates[1] = square/8;
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'p' -> {
                        Pawn piece = new Pawn(BLACK_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'P' -> {
                        Pawn piece = new Pawn(WHITE_COLOR, pane);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i), piece);
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                }
            }
        }
    }

    public void setPieceGraphic(ImageView image, AnchorPane grid, int square, char charAtI, Piece piece){
        if(square < SQUARES_AMOUNT){
            charBoard[square%BOARD_WIDTH][square/BOARD_LENGTH] = charAtI;
            boardImageView[square%BOARD_WIDTH][square/BOARD_LENGTH] = image;
            piece.startX = square%BOARD_WIDTH;
            piece.startY = square/BOARD_LENGTH;

            image.setX((square%BOARD_WIDTH)*getGridSize());
            image.setY((square/BOARD_LENGTH)*getGridSize());
            grid.getChildren().add(image);
        }
    }
}
