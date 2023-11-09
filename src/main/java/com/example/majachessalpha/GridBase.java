package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class GridBase {

    //visualization
    private double boardWidth; //640
    private int sqaresAmount = 8; //8 - constant
    private int allSqares = sqaresAmount*sqaresAmount;  //64 - constant
    private int gridSize;  //80
    private AnchorPane anchorPane;

    //char board
    private char[][] board;
    //board with images
    private ImageView[][] boardImageViews;

    public GridBase(int gridSize, AnchorPane anchorPane) {
        this.boardWidth = gridSize*sqaresAmount;
        this.gridSize = gridSize;
        this.anchorPane = anchorPane;
        this.board = new char[getSqaresAmount()][getSqaresAmount()];
        this.boardImageViews = new ImageView[getSqaresAmount()][getSqaresAmount()];
    }

    public int getAllSqares() {
        return allSqares;
    }

    public int getSqaresAmount() {
        return sqaresAmount;
    }

    public int getGridSize() {
        return gridSize;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public char[][] getBoard() {
        return board;
    }

    public void printBoard(char[][] charBoard){
        for(int i = 0; i < charBoard.length; i++){
            for(int j = 0; j < charBoard.length; j++){
                System.out.print(charBoard[j][i] + " ");
            }
            System.out.println();
        }
    }

    public ImageView[][] getBoardImageViews(){
        return boardImageViews;
    }
}

