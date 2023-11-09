package com.example.majachessalpha;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class GridHandler extends GridBase {

    public GridHandler(int gridSize, AnchorPane anchorPane) {
        super(gridSize, anchorPane);
    }

    public void updateGrid() {
        for(int i = 0; i < getAllSqares(); i++){
            int x = (i % getSqaresAmount());
            int y = (i / getSqaresAmount());

            Rectangle rectangle = new Rectangle(x * getGridSize(),y * getGridSize(),getGridSize(),getGridSize());

            if((x + y) % 2 == 0){
                rectangle.getStyleClass().add("square-white");
            } else {
                rectangle.getStyleClass().add("square-black");
            }
            getAnchorPane().getChildren().add(rectangle);
        }
    }
}

