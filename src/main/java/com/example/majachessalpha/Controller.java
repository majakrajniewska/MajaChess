package com.example.majachessalpha;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private int gridSize = 80;
    private String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    //test endgame position
    //private String FEN = "8/5k2/8/8/8/8/8/5K1R";

    //private char[][] charBoard = new char[8][8];
    @FXML
    private AnchorPane pane;

    //private GridBase gridBase;
    private GridHandler backgroundGridHandler;
    private PositionHandler positionHandler;
    private Piece pieceHandler;

    private Player playerWhite;
    private Player playerBlack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerWhite = new Player(1);
        playerBlack = new Player(0);
        backgroundGridHandler = new GridHandler(gridSize, pane);
        backgroundGridHandler.updateGrid();

        positionHandler = new PositionHandler(
                gridSize,
                pane,
                playerWhite.getPlayersPieces(),
                playerBlack.getPlayersPieces());

        //pieceHandler = new Piece(gridSize, pane, backgroundGridHandler.getBoard(), backgroundGridHandler.getBoardImageViews());
        positionHandler.setPosition(FEN);



   }
}