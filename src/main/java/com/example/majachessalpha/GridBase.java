package com.example.majachessalpha;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class GridBase extends GameHandler{

    //visualization
    public static final int SQUARES_AMOUNT = BOARD_LENGTH*BOARD_WIDTH; //64 - constant
    public static final int GRID_SIZE = 80;

    AnchorPane pane;
    //char board
    private static char[][] board = new char[BOARD_WIDTH][BOARD_LENGTH];
    //board with images
    private static ImageView[][] boardImageViews = new ImageView[BOARD_WIDTH][BOARD_LENGTH];

    public GridBase(){}
    public GridBase(AnchorPane pane){
        this.pane = pane;
    }

    public int getGridSize() {
        return GRID_SIZE;
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

