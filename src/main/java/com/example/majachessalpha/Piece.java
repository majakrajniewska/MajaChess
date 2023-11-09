package com.example.majachessalpha;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece extends GridBase{
    double mouseAnchorX;
    double mouseAnchorY;
    int startX, startY;
    int currentX, currentY;

    List<int[]> legalMoves = new ArrayList<>();

    static char[][] charBoard;
    static ImageView[][] boardImageView;
    private List<Piece> playerWhitePieces;
    private List<Piece> playerBlackPieces;
    public Piece(int gridSize, AnchorPane anchorPane){
        super(gridSize, anchorPane);
        if(charBoard == null) charBoard = getBoard();
        if(boardImageView == null) boardImageView = getBoardImageViews();
    }

    public Image loadImage(String path){
        Image pieceImage;
        // Load the image
        URL imageURL = Piece.class.getResource(path);
        pieceImage = new Image(imageURL.toExternalForm());

        return pieceImage;
    }
    public ImageView prepareImage(Image img){
        ImageView pieceImageView = new ImageView(img);

        // Resize (set width and height)
        pieceImageView.setFitWidth(80);
        pieceImageView.setFitHeight(80);

        //make draggable
        makeDraggable(pieceImageView);

        return pieceImageView;
    }

    public void makeDraggable(ImageView pieceImageView){
        //get starting coordinates
        pieceImageView.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();

            //board[x/80][y/80]
            startX = (int) ((mouseAnchorX/getGridSize()) % getSqaresAmount());
            startY = (int) ((mouseAnchorY/getGridSize()) % getSqaresAmount());

            legalMoves = generateLegalMoves();
            printLegalMoves();

        });
        //set new coordinates
        pieceImageView.setOnMouseDragged(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();

            //board[x/80][y/80]
            int x = (int) ((mouseAnchorX/getGridSize()) % getSqaresAmount());
            int y = (int) ((mouseAnchorY/getGridSize()) % getSqaresAmount());

            setCurrentCoordinates(x, y);
            setImageSquare(pieceImageView, this.currentX, this.currentY);
        });
        //validate new coordinates
        pieceImageView.setOnMouseReleased(mouseEvent -> {
            //if move is valid - make move
            if(isValidMove()){
                //if possible capture
                capture();
                makeMove(pieceImageView);
                //legalMoves = generateLegalMoves();

            } else {
                setImageSquare(pieceImageView, this.startX, this.startY);
            }
            printBoard(charBoard);
            System.out.println();
        });
    }

    //abstract validation function
    public abstract boolean isValidMove();
    public void makeMove(ImageView img){
        //updating char board and imageView board
        charBoard[startX][startY] = '.';
        charBoard[currentX][currentY] = getPieceChar();

        boardImageView[startX][startY] = null;
        boardImageView[currentX][currentY] = img;

        //set start coordinates to current coordinates
        setStartCoordinates(currentX, currentY);
    };
    public abstract char getPieceChar();
    public void setImageSquare(ImageView img, int x, int y){
        img.setLayoutX(x * getGridSize() - img.getX());
        img.setLayoutY(y * getGridSize() - img.getY());
    }

    //set and get for start and current coordinates of the piece
    public void setCurrentCoordinates(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }
    public void setStartCoordinates(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

//    public int getCurrentX(){
//        return currentX;
//    }
//    public int getCurrentY(){
//        return currentY;
//    }
//    public int getStartX(){
//        return startX;
//    }
//    public int getStartY(){
//        return startY;
//    }

    public void capture(){
        if((Character.isUpperCase(charBoard[startX][startY]) &&
            Character.isLowerCase(charBoard[currentX][currentY])) ||
            (Character.isLowerCase(charBoard[startX][startY]) &&
            Character.isUpperCase(charBoard[currentX][currentY]))){
            getAnchorPane().getChildren().remove(boardImageView[currentX][currentY]);
        }
    }

    //MOVEMENT VALIDATION
    //check if move...
    public boolean isHorizontal(){
        return startY != currentY && startX == currentX;
    }
    public boolean isVertical(){
        return startY == currentY && startX != currentX;
    }
    public boolean isDiagonal(){
        return startX - startY == currentX - currentY || startY + startX == currentX + currentY;
    }
    //check if there is a figure between start point and current point - horizontal and vertical
    public boolean isNotBlocked(){
        //vertical
        if(startY == currentY){
            if(startX < currentX) {
                for (int i = startX + 1; i < currentX; i++)
                    if (charBoard[i][startY] != '.') return false;
            }
            else {
                for (int j = currentX + 1; j < startX; j++)
                    if (charBoard[j][startY] != '.') return false;
            }
        } //horizontal
        else if(startX == currentX){
            if(startY < currentY) {
                for (int i = startY + 1; i < currentY; i++)
                    if (charBoard[startX][i] != '.') return false;
            }
            else {
                for (int j = currentY + 1; j < startY; j++)
                    if (charBoard[startX][j] != '.') return false;
            }
        } // diagonal
        else if(startX - startY == currentX - currentY){
            if(startY < currentY) {
                int tempX = startX+1, tempY = startY+1;
                while (tempX!= currentX){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX++;
                    tempY++;
                }
            }
            else {
                int tempX = startX-1, tempY = startY-1;
                while (tempX!= currentX){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX--;
                    tempY--;
                }
            }
        } //second diagonal
        else if(startX + startY == currentX + currentY){
            if(startY < currentY) {
                int tempX = startX-1, tempY = startY+1;
                while (tempX!= currentX){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX--;
                    tempY++;
                }
            }
            else {
                int tempX = startX+1, tempY = startY-1;
                while (tempX!= currentX){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX++;
                    tempY--;
                }
            }
        }
        //check if piece is trying to stand on the square with piece with the same color
        return isSquareAvailable();
    }
    public boolean isSquareAvailable(){
        return !((Character.isUpperCase(charBoard[startX][startY]) &&
                Character.isUpperCase(charBoard[currentX][currentY])) ||
                (Character.isLowerCase(charBoard[startX][startY]) &&
                Character.isLowerCase(charBoard[currentX][currentY])));
    }
    public boolean canCapture(){
        return ((Character.isUpperCase(charBoard[startX][startY]) &&
                Character.isLowerCase(charBoard[currentX][currentY])) ||
                (Character.isLowerCase(charBoard[startX][startY]) &&
                Character.isUpperCase(charBoard[currentX][currentY])));
    }


    //GENERATING LEGAL MOVES
    public boolean isValidSquare(int x, int y){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    public boolean isSquareEmpty(int x, int y){
        return charBoard[x][y] == '.';
    }
    public boolean isSquareOccupied(int x, int y, int playerColor){
        if(playerColor==1){
            return Character.isLowerCase(charBoard[x][y]);
        } else{
            return Character.isUpperCase(charBoard[x][y]);
        }
    }
    public void printLegalMoves(){
        for(int[] move : legalMoves){
            System.out.println("x: " + move[0] + "; y: " + move[1]);
        }
        System.out.println();
    }
    public abstract List<int[]> generateLegalMoves();


    //SET LIST OF PLAYERS PIECES
    public void setPlayerWhitePieces(List<Piece> playerWhitePieces) {
        this.playerWhitePieces = playerWhitePieces;
    }
    public void setPlayerBlackPieces(List<Piece> playerBlackPieces) {
        this.playerBlackPieces = playerBlackPieces;
    }
}
