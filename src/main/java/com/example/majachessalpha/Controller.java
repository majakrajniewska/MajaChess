package com.example.majachessalpha;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private String FEN = "r2qk2r/pppppppp/8/8/8/8/PPPPPPPP/R2QK2R";

    private GridHandler backgroundGridHandler;
    private PositionHandler positionHandler;
    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundGridHandler = new GridHandler(pane);
        backgroundGridHandler.updateGrid();

        positionHandler = new PositionHandler(pane);
        positionHandler.setPosition(FEN);

   }
}