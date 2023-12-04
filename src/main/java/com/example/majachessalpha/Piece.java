package com.example.majachessalpha;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public abstract class Piece extends GridBase{
    double mouseAnchorX;
    double mouseAnchorY;
    int startX, startY;
    int currentX, currentY;

    //saving last removed piece
    Deque<Piece> stackRemovedPiece = new LinkedList<>();
    static int[] whiteKingCoordinates;
    static int[] blackKingCoordinates;

    List<int[]> legalMoves = new ArrayList<>();

    static char[][] charBoard;
    static char[][] previousCharBoard = new char[8][8];
    private static List<Piece> playerWhitePieces;
    private static List<Piece> playerBlackPieces;
    public Piece(AnchorPane pane){
        super(pane);
        if(charBoard == null) charBoard = getBoard();
        if(playerBlackPieces == null) playerBlackPieces = getPlayerBlackPieces();
        if(playerWhitePieces == null) playerWhitePieces = getPlayerWhitePieces();
        if(whiteKingCoordinates == null) whiteKingCoordinates = getWhiteKingCoordinates();
        if(blackKingCoordinates == null) blackKingCoordinates = getBlackKingCoordinates();
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

            legalMoves = generateLegalMovesWithCheck();
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
            if(isValidMoveWithCheck()){
                if(!stackRemovedPiece.isEmpty()){
                    getAnchorPane().getChildren().remove(stackRemovedPiece.pop().getPieceImage());
                }
                setStartCoordinates(currentX, currentY);
                if(isMate()) mate();
            } else {
                setImageSquare(pieceImageView, this.startX, this.startY);
            }
        });
    }

    //abstract validation function
    public abstract boolean isValidMove();
    public boolean isValidMoveWithCheck(){
        if(isValidMove()){
            copyCharBoard(charBoard, previousCharBoard);
            if(canCapture())
                stackRemovedPiece.push(capture());
            makeMove();

            if(isCheck(previousCharBoard)) {
                copyCharBoard(previousCharBoard, charBoard);

                //if king did illegal move - set coordinates to previous ones
                switchKingCoordinates(startX, startY);

                //if illegal capture has been made - add captured piece back
                addDeletedPieceBack();
                return false;
            }
            return true;
        }
        return false;
    }

    //use only while generating moves, not when making them
    public boolean isValidMoveWithCheck(int x, int y){
        copyCharBoard(charBoard, previousCharBoard);
        if(canCapture(x, y))
            stackRemovedPiece.push(capture(x, y));
        makeMove(x, y);

        //save current king coordinate and change them if needed
        int[] tempKingCoordinates = switchAndSaveKingCoordinates(x, y);

        if(isCheck(previousCharBoard)) {
            addDeletedPieceBack();
            switchKingCoordinates(tempKingCoordinates[0], tempKingCoordinates[1]);
            copyCharBoard(previousCharBoard, charBoard);
            return false;
        }
        addDeletedPieceBack();
        switchKingCoordinates(tempKingCoordinates[0], tempKingCoordinates[1]);
        copyCharBoard(previousCharBoard, charBoard);
        return true;
    }

    private void addDeletedPieceBack() {
        if (!stackRemovedPiece.isEmpty()) {
            Piece lastRemoved = stackRemovedPiece.pop();
            if (Character.isUpperCase(lastRemoved.getPieceChar()))
                playerWhitePieces.add(lastRemoved);
            else if (Character.isLowerCase(lastRemoved.getPieceChar()))
                playerBlackPieces.add(lastRemoved);
        }
    }

    public void makeMove(){
        //updating char board
        charBoard[startX][startY] = '.';
        charBoard[currentX][currentY] = getPieceChar();
        //if king has been moved - change its coordinates
        switchKingCoordinates(currentX, currentY);
    }
    public void makeMove(int x, int y){
        //updating char board
        charBoard[startX][startY] = '.';
        charBoard[x][y] = getPieceChar();
    };

    public int[] switchAndSaveKingCoordinates(int x, int y){
        int[] startCoordinates = new int[2];
        //if king has been moved - change its coordinates
        if(getPieceChar()=='K'){
            startCoordinates = whiteKingCoordinates.clone();
            whiteKingCoordinates[0] = x;
            whiteKingCoordinates[1] = y;
        } else if(getPieceChar()=='k'){
            startCoordinates = blackKingCoordinates.clone();
            blackKingCoordinates[0] = x;
            blackKingCoordinates[1] = y;
        }
        return startCoordinates;
    }
    public void switchKingCoordinates(int x, int y){
        //if king has been moved - change its coordinates
        if(getPieceChar()=='K'){
            whiteKingCoordinates[0] = x;
            whiteKingCoordinates[1] = y;
        } else if(getPieceChar()=='k'){
            blackKingCoordinates[0] = x;
            blackKingCoordinates[1] = y;
        }
    }

    public abstract char getPieceChar();
    public abstract int getPieceColor();
    public void setImageSquare(ImageView img, int x, int y){
        img.setLayoutX(x * getGridSize() - img.getX());
        img.setLayoutY(y * getGridSize() - img.getY());
    }
    public abstract ImageView getPieceImage();

    //set and get for start and current coordinates of the piece
    public void setCurrentCoordinates(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }
    public void setStartCoordinates(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    //remove instance of Piece from Player
    public Piece capture(){
        if(Character.isUpperCase(previousCharBoard[startX][startY]) &&
            Character.isLowerCase(previousCharBoard[currentX][currentY])){
            for(Piece piece : playerBlackPieces){
                if(piece.startX == currentX && piece.startY == currentY){
                    playerBlackPieces.remove(piece);
                    return piece;
                }
            }
        }
        else if(Character.isLowerCase(previousCharBoard[startX][startY]) &&
            Character.isUpperCase(previousCharBoard[currentX][currentY])){
            for(Piece piece : playerWhitePieces){
                if(piece.startX == currentX && piece.startY == currentY){
                    playerWhitePieces.remove(piece);
                    return piece;
                }
            }
        }
        return null;
    }
    public Piece capture(int x, int y){
        if(Character.isUpperCase(charBoard[startX][startY]) &&
            Character.isLowerCase(charBoard[x][y])){
            for(Piece piece : playerBlackPieces){
                if(piece.startX == x && piece.startY == y){
                    playerBlackPieces.remove(piece);
                    return piece;
                }
            }
        }
        else if(Character.isLowerCase(charBoard[startX][startY]) &&
                Character.isUpperCase(charBoard[x][y])){
            for(Piece piece : playerWhitePieces){
                if(piece.startX == x && piece.startY == y){
                    playerWhitePieces.remove(piece);
                    return piece;
                }
            }
        }
        return null;
    }

    //MOVEMENT VALIDATION
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
    public boolean canCapture(int x, int y){
        return ((Character.isUpperCase(charBoard[startX][startY]) &&
                Character.isLowerCase(charBoard[x][y])) ||
                (Character.isLowerCase(charBoard[startX][startY]) &&
                Character.isUpperCase(charBoard[x][y])));
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
    public abstract List<int[]> generateLegalMovesWithCheck();

    //CHECK AND MATE
    public boolean isCheck(char[][] board){
        if(Character.isUpperCase(board[startX][startY])){
            for(Piece p : playerBlackPieces){
                for(int[] move : p.generateLegalMoves())
                    if(move[0] == whiteKingCoordinates[0] && move[1] == whiteKingCoordinates[1]) return true;

            }}
        else{
            for(Piece p : playerWhitePieces){
                for(int[] move : p.generateLegalMoves())
                    if(move[0] == blackKingCoordinates[0] && move[1] == blackKingCoordinates[1]) return true;

            }
        }
        return false;
    }
    public boolean isMate(){
        if(getPieceColor() == 1){
            for(Piece p : playerBlackPieces){
                if(!(p.generateLegalMovesWithCheck().isEmpty())){
                    System.out.println(p.getPieceChar());
                    p.printLegalMoves();
                    return false;
                }
            }
        } else {
            for(Piece p : playerWhitePieces){
                if(!(p.generateLegalMovesWithCheck().isEmpty())){
                    System.out.println(p.getPieceChar());
                    return false;
                }
            }
        }
        System.out.println("MATE");
        return true;
    }
    public void mate(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MAT");
        alert.setHeaderText(null); // Set to null to remove header text
        if(getPieceColor() == 1){
            alert.setContentText("Białe wygrały ez");
            System.out.println("White won");
        } else{
            alert.setContentText("Czarne wygrały ez");
            System.out.println("Black won");
        }
        alert.showAndWait();
    }

    //BOARD IMPLEMENTATION
    void copyCharBoard(char[][] boardFrom, char[][] boardTo){
        for(int i = 0; i<boardFrom.length; i++)
            for(int j = 0; j<boardFrom[0].length; j++){
                boardTo[i][j] = boardFrom[i][j];
            }
    }

}
