package com.example.majachessalpha;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    private GridHandler backgroundGridHandler;
    private PositionHandler positionHandler;
    @FXML
    private AnchorPane pane;
    private Player playerWhite;
    private Player playerBlack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerWhite = new Player(1);
        playerBlack = new Player(0);
        backgroundGridHandler = new GridHandler(pane);
        backgroundGridHandler.updateGrid();

        positionHandler = new PositionHandler(
                pane,
                playerWhite.getPlayersPieces(),
                playerBlack.getPlayersPieces());

        //pieceHandler = new Piece(gridSize, pane, backgroundGridHandler.getBoard(), backgroundGridHandler.getBoardImageViews());
        positionHandler.setPosition(FEN);



   }
}