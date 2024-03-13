package com.example.majachessalpha;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class GridHandler extends GridBase {

    public GridHandler(AnchorPane pane) {
        super(pane);
    }

    public void updateGrid() {
        for(int i = 0; i < SQUARES_AMOUNT; i++){
            int x = (i % BOARD_WIDTH);
            int y = (i / BOARD_LENGTH);

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

