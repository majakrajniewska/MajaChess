package com.example.majachessalpha;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class GridBase extends GameHandler{

    //visualization
    private int sqaresAmount = 8; //8 - constant
    private int allSqares = sqaresAmount*sqaresAmount;  //64 - constant
    private static int gridSize = 80;

    AnchorPane pane;
    //char board
    private static char[][] board = new char[8][8];
    //board with images
    private static ImageView[][] boardImageViews = new ImageView[8][8];

    public GridBase(){}
    public GridBase(AnchorPane pane){
        this.pane = pane;
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
        return pane;
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

