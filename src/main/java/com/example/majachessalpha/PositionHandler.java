package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PositionHandler extends GridBase{
    private char[][] charBoard;
    private ImageView[][] boardImageViews;
    public PositionHandler(int gridSize, AnchorPane anchorPane, char[][] board, ImageView[][] boardImg) {
        super(gridSize, anchorPane);
        this.charBoard = board;
        this.boardImageViews = boardImg;
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
                        Rook piece = new Rook(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'R' -> {
                        Rook piece = new Rook(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'n' -> {
                        Knight piece = new Knight(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'N' -> {
                        Knight piece = new Knight(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'b' -> {
                        Bishop piece = new Bishop(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'B' -> {
                        Bishop piece = new Bishop(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'q' -> {
                        Queen piece = new Queen(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'Q' -> {
                        Queen piece = new Queen(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'k' -> {
                        King piece = new King(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'K' -> {
                        King piece = new King(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'p' -> {
                        Pawn piece = new Pawn(0, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                    case 'P' -> {
                        Pawn piece = new Pawn(1, getGridSize(), getAnchorPane(), charBoard, boardImageViews);
                        setPieceGraphic(piece.getPieceImage(), getAnchorPane(), square, fen.charAt(i));
                        square+=1;
                    }
                }
            }
        }
    }

    public void setPieceGraphic(ImageView image, AnchorPane grid, int square, char charAtI){
        if(square < 64){
            charBoard[square%8][square/8] = charAtI;
            boardImageViews[square%8][square/8] = image;

            image.setX((square%8)*getGridSize());
            image.setY((square/8)*getGridSize());
            grid.getChildren().add(image);
        }
    }
}
