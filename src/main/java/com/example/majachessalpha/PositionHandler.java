package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class PositionHandler extends GridBase{
    private static char[][] charBoard;
    private static ImageView[][] boardImageView;
    private List<Piece> playerWhitePieces;
    private List<Piece> playerBlackPieces;

    public PositionHandler(AnchorPane pane, List<Piece> w, List<Piece> b) {
        super(pane);
        if(charBoard == null) charBoard = getBoard();
        if(boardImageView == null) boardImageView = getBoardImageViews();
        this.playerWhitePieces = w;
        this.playerBlackPieces = b;
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
                        Rook piece = new Rook(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'R' -> {
                        Rook piece = new Rook(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'n' -> {
                        Knight piece = new Knight(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'N' -> {
                        Knight piece = new Knight(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'b' -> {
                        Bishop piece = new Bishop(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'B' -> {
                        Bishop piece = new Bishop(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'q' -> {
                        Queen piece = new Queen(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'Q' -> {
                        Queen piece = new Queen(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'k' -> {
                        King piece = new King(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'K' -> {
                        King piece = new King(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                    case 'p' -> {
                        Pawn piece = new Pawn(0);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerBlackPieces.add(piece);
                    }
                    case 'P' -> {
                        Pawn piece = new Pawn(1);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                        playerWhitePieces.add(piece);
                    }
                }
            }
        }
    }

    public void setPieceGraphic(ImageView image, AnchorPane grid, int square, char charAtI){
        if(square < 64){
            charBoard[square%8][square/8] = charAtI;
            boardImageView[square%8][square/8] = image;

            image.setX((square%8)*getGridSize());
            image.setY((square/8)*getGridSize());
            grid.getChildren().add(image);
        }
    }
}
