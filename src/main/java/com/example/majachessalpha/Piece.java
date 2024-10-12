package com.example.majachessalpha;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;
//UNPASSANT DZIALA TYLKO CZARNY W LEWO I NIE USUWA BIALEGO PIONKA
public abstract class Piece extends GridBase{
    //PIECE
    private boolean color;
    private int value;
    private char pieceChar;
    //PIECE HANDLER
    double mouseAnchorX;
    double mouseAnchorY;
    //coordinates from which piece starts its move
    private Point startPoint;
    //coordinates where piece ends its move (if its possible)
    private Point newPoint;

    //saving last removed piece
    Deque<Piece> stackRemovedPiece = new LinkedList<>();
    static Point whiteKingPoint;
    static Point blackKingPoint;

    List<Point> legalMoves = new ArrayList<>();

    static char[][] charBoard;
    static char[][] previousCharBoard = new char[BOARD_WIDTH][BOARD_LENGTH];
    private static List<Piece> playerWhitePieces;
    private static List<Piece> playerBlackPieces;
    //we need to have 50 last moves to check if it's a tie and if un passant is legal
    private static Vector<Move> historyOfMoves = new Vector<>();

    public Piece(AnchorPane pane){
        super(pane);
        if(charBoard == null) charBoard = getBoard();
        if(playerBlackPieces == null) playerBlackPieces = getPlayerBlackPieces();
        if(playerWhitePieces == null) playerWhitePieces = getPlayerWhitePieces();
        if(whiteKingPoint == null) whiteKingPoint = getWhiteKingPoint();
        if(blackKingPoint == null) blackKingPoint = getBlackKingPoint();

        startPoint = new Point();
        newPoint = new Point();
    }

    public Piece(boolean color, AnchorPane pane){
        super(pane);
        if(charBoard == null) charBoard = getBoard();
        if(playerBlackPieces == null) playerBlackPieces = getPlayerBlackPieces();
        if(playerWhitePieces == null) playerWhitePieces = getPlayerWhitePieces();
        if(whiteKingPoint == null) whiteKingPoint = getWhiteKingPoint();
        if(blackKingPoint == null) blackKingPoint = getBlackKingPoint();
        this.color = color;

        startPoint = new Point();
        newPoint = new Point();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getNewPoint() {
        return newPoint;
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
        pieceImageView.setFitWidth(GRID_SIZE);
        pieceImageView.setFitHeight(GRID_SIZE);

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
            startPoint.setX((int) ((mouseAnchorX/getGridSize()) % BOARD_WIDTH));
            startPoint.setY((int) ((mouseAnchorY/getGridSize()) % BOARD_LENGTH));
        });
        //set new coordinates
        pieceImageView.setOnMouseDragged(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();

            //board[x/80][y/80]
            int x = (int) ((mouseAnchorX/getGridSize()) % BOARD_WIDTH);
            int y = (int) ((mouseAnchorY/getGridSize()) % BOARD_LENGTH);

            newPoint.setPoint(x, y);
            setImageSquare(pieceImageView, newPoint);
        });
        //validate new coordinates
        pieceImageView.setOnMouseReleased(mouseEvent -> {
            //if move is valid - make move
            if(isPlayerTurn()){
                if(canCastle()){
                    Move move = new Move(getCurrentPiece(getPieceChar()), startPoint, newPoint);
                    historyOfMoves.add(move);
                    //Console visualisation
                    printBoard(charBoard);
                    printHistoryOfMoves();
                    setImageSquare(pieceImageView, newPoint);
                    castle();
                    if(isMate()) mate();
                    nextPlayersTurn();
                }
                else if(isValidMoveWithCheck()){
                    Move move = new Move(getCurrentPiece(getPieceChar()), startPoint, newPoint);
                    historyOfMoves.add(move);
                    //Console visualisation
                    printBoard(charBoard);
                    printHistoryOfMoves();
                    if(!stackRemovedPiece.isEmpty()){
                        getAnchorPane().getChildren().remove(stackRemovedPiece.pop().getPieceImage());
                    }
                    startPoint.copyPoint(newPoint);
                    if(isMate()) mate();
                    nextPlayersTurn();
                }else {
                    setImageSquare(pieceImageView, startPoint);
                }
            }
            else {
                setImageSquare(pieceImageView, startPoint);
            }
        });
    }
    boolean isPlayerTurn(){
        if(whitePiece() && playerWhite.isPlayerTurn())
            return true;
        else return !whitePiece() && playerBlack.isPlayerTurn();
    }

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
                switchKingPoint(startPoint);
                //if illegal capture has been made - add captured piece back
                addDeletedPieceBack();
                return false;
            }
            return true;
        }
        return false;
    }
    //use only while generating moves, not when making them
    public boolean isValidMoveWithCheck(Point point){
        copyCharBoard(charBoard, previousCharBoard);
        if(canCapture(point))
            stackRemovedPiece.push(capture(point));
        makeMove(point);

        //save current king coordinate and change them if needed
        Point tempKingPoint = switchAndSaveKingCoordinates(point);

        if(isCheck(previousCharBoard)) {
            addDeletedPieceBack();
            switchKingPoint(tempKingPoint);
            copyCharBoard(previousCharBoard, charBoard);
            return false;
        }
        addDeletedPieceBack();
        switchKingPoint(tempKingPoint);
        copyCharBoard(previousCharBoard, charBoard);
        return true;
    }
    public boolean isValidMoveWithCheck(int x, int y){
        Point point = new Point(x, y);
        copyCharBoard(charBoard, previousCharBoard);
        if(canCapture(point))
            stackRemovedPiece.push(capture(point));
        makeMove(point);

        //save current king coordinate and change them if needed
        Point tempKingPoint = switchAndSaveKingCoordinates(point);

        if(isCheck(previousCharBoard)) {
            addDeletedPieceBack();
            switchKingPoint(tempKingPoint);
            copyCharBoard(previousCharBoard, charBoard);
            return false;
        }
        addDeletedPieceBack();
        switchKingPoint(tempKingPoint);
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

    //MOVE TO MOVE
    public void makeMove(){
        //updating char board
        charBoard[startPoint.getX()][startPoint.getY()] = '.';
        charBoard[newPoint.getX()][newPoint.getY()] = getPieceChar();
        //if king has been moved - change its coordinates
        switchKingPoint(newPoint);
    }
    public void makeMove(Point point){
        //updating char board
        charBoard[startPoint.getX()][startPoint.getY()] = '.';
        charBoard[point.getX()][point.getY()] = getPieceChar();
    };

    public Point switchAndSaveKingCoordinates(Point point){
        Point startPoint = new Point();
        //if king has been moved - change its coordinates
        if(getPieceChar()=='K'){
            startPoint.copyPoint(whiteKingPoint);
            whiteKingPoint.setPoint(point.getX(), point.getY());
        } else if(getPieceChar()=='k'){
            startPoint.copyPoint(blackKingPoint);
            blackKingPoint.setPoint(point.getX(), point.getY());
        }
        return startPoint;
    }
    public void switchKingPoint(Point point){
        //if king has been moved - change its coordinates
        if(getPieceChar()=='K'){
            whiteKingPoint.setPoint(point.getX(), point.getY());
        } else if(getPieceChar()=='k'){
            blackKingPoint.setPoint(point.getX(), point.getY());
        }
    }

    public char getPieceChar(){return pieceChar;};
    public boolean whitePiece(){return color;};
    public void setPieceChar(char pieceChar) {
        this.pieceChar = pieceChar;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public void setImageSquare(ImageView img, Point point){
        img.setLayoutX(point.getX() * getGridSize() - img.getX());
        img.setLayoutY(point.getY() * getGridSize() - img.getY());
    }
    public abstract ImageView getPieceImage();
    public Piece getCurrentPiece(char pieceChar){
        if(whitePiece()){
            for(Piece piece : playerWhitePieces){
                if(piece.getPieceChar() == pieceChar)
                    return piece;
            }
        } else{
            for(Piece piece : playerBlackPieces){
                if(piece.getPieceChar() == pieceChar)
                    return piece;
            }
        }
        return null;
    }

    //remove instance of Piece from Player
    public Piece capture(){ //start, current
        if(Character.isUpperCase(previousCharBoard[startPoint.getX()][startPoint.getY()]) &&
            Character.isLowerCase(previousCharBoard[newPoint.getX()][newPoint.getY()])){
            for(Piece piece : playerBlackPieces){
                if(piece.startPoint.getX() == newPoint.getX() && piece.startPoint.getY() == newPoint.getY()){
                    playerBlackPieces.remove(piece);
                    return piece;
                }
            }
        }
        else if(Character.isLowerCase(previousCharBoard[startPoint.getX()][startPoint.getY()]) &&
            Character.isUpperCase(previousCharBoard[newPoint.getX()][newPoint.getY()])){
            for(Piece piece : playerWhitePieces){
                if(piece.startPoint.getX() == newPoint.getX() && piece.startPoint.getY() == newPoint.getY()){
                    playerWhitePieces.remove(piece);
                    return piece;
                }
            }
        }
        return null;
    }
    public Piece capture(Point point){
        if(Character.isUpperCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
            Character.isLowerCase(charBoard[point.getX()][point.getY()])){
            for(Piece piece : playerBlackPieces){
                if(piece.startPoint.getX() == point.getX() && piece.startPoint.getY() == point.getY()){
                    playerBlackPieces.remove(piece);
                    return piece;
                }
            }
        }
        else if(Character.isLowerCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isUpperCase(charBoard[point.getX()][point.getY()])){
            for(Piece piece : playerWhitePieces){
                if(piece.startPoint.getX() == point.getX() && piece.startPoint.getY() == point.getY()){
                    playerWhitePieces.remove(piece);
                    return piece;
                }
            }
        }
        return null;
    }

    //MOVEMENT VALIDATION - MOVE TO MOVE
    public boolean isHorizontal(){
        return startPoint.getY() != newPoint.getY() && startPoint.getX() == newPoint.getX();
    }
    public boolean isVertical(){
        return startPoint.getY() == newPoint.getY() && startPoint.getX() != newPoint.getX();
    }
    public boolean isDiagonal(){
        return (startPoint.getX() - startPoint.getY() == newPoint.getX() - newPoint.getY() ||
                startPoint.getY() + startPoint.getX() == newPoint.getX() + newPoint.getY());
    }
    public boolean isNotBlocked(){
        //vertical
        if(startPoint.getY() == newPoint.getY()){
            if(startPoint.getX() < newPoint.getX()) {
                for (int i = startPoint.getX() + 1; i < newPoint.getX(); i++)
                    if (charBoard[i][startPoint.getY()] != '.') return false;
            }
            else {
                for (int j = newPoint.getX() + 1; j < startPoint.getX(); j++)
                    if (charBoard[j][startPoint.getY()] != '.') return false;
            }
        } //horizontal
        else if(startPoint.getX() == newPoint.getX()){
            if(startPoint.getY() < newPoint.getY()) {
                for (int i = startPoint.getX() + 1; i < newPoint.getY(); i++)
                    if (charBoard[startPoint.getX()][i] != '.') return false;
            }
            else {
                for (int j = newPoint.getY() + 1; j < startPoint.getY(); j++)
                    if (charBoard[startPoint.getX()][j] != '.') return false;
            }
        } // diagonal
        else if(startPoint.getX() - startPoint.getY() == newPoint.getX() - newPoint.getY()){
            if(startPoint.getY() < newPoint.getY()) {
                int tempX = startPoint.getX() + 1, tempY = startPoint.getY() + 1;
                while (tempX!= newPoint.getX()){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX++;
                    tempY++;
                }
            }
            else {
                int tempX = startPoint.getX() - 1, tempY = startPoint.getY() - 1;
                while (tempX!= newPoint.getX()){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX--;
                    tempY--;
                }
            }
        } //second diagonal
        else if(startPoint.getX() + startPoint.getY() == newPoint.getX() + newPoint.getY()){
            if(startPoint.getY() < newPoint.getY()) {
                int tempX = startPoint.getX()-1, tempY = startPoint.getY()+1;
                while (tempX!= newPoint.getX()){
                    if (charBoard[tempX][tempY] != '.') return false;
                    tempX--;
                    tempY++;
                }
            }
            else {
                int tempX = startPoint.getX()+1, tempY = startPoint.getY()-1;
                while (tempX!= newPoint.getX()){
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
        return !((Character.isUpperCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isUpperCase(charBoard[newPoint.getX()][newPoint.getY()])) ||
                (Character.isLowerCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isLowerCase(charBoard[newPoint.getX()][newPoint.getY()])));
    }
    public boolean canCapture(){
        return ((Character.isUpperCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isLowerCase(charBoard[newPoint.getX()][newPoint.getY()])) ||
                (Character.isLowerCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isUpperCase(charBoard[newPoint.getX()][newPoint.getY()])));
    }
    public boolean canCapture(Point point){
        return ((Character.isUpperCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isLowerCase(charBoard[point.getX()][point.getY()])) ||
                (Character.isLowerCase(charBoard[startPoint.getX()][startPoint.getY()]) &&
                Character.isUpperCase(charBoard[point.getX()][point.getY()])));
    }


    //GENERATING LEGAL MOVES
    public boolean isValidSquare(Point point){
        return point.getX() >= 0 && point.getX() <= 7 && point.getY() >= 0 && point.getY() <= 7;
    }
    public boolean isValidSquare(int x, int y){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    public boolean isSquareEmpty(Point point){
        return charBoard[point.getX()][point.getY()] == '.';
    }
    public boolean isSquareEmpty(int x, int y){
        return charBoard[x][y] == '.';
    }
    public boolean isSquareOccupied(Point point, boolean playerColor){
        if(playerColor){
            return Character.isLowerCase(charBoard[point.getX()][point.getY()]);
        } else{
            return Character.isUpperCase(charBoard[point.getX()][point.getY()]);
        }
    }
    public boolean isSquareOccupied(int x, int y, boolean playerColor){
        if(playerColor){
            return Character.isLowerCase(charBoard[x][y]);
        } else{
            return Character.isUpperCase(charBoard[x][y]);
        }
    }
    public void printLegalMoves(){
        for(Point move : legalMoves){
            System.out.println("x: " + move.getX() + "; y: " + move.getY());
        }
        System.out.println();
    }
    public abstract List<Point> generateLegalMoves();
    public abstract List<Point> generateLegalMovesWithCheck();

    //CHECK AND MATE
    public boolean isCheck(char[][] board){
        if(Character.isUpperCase(board[startPoint.getX()][startPoint.getY()])){
            for(Piece p : playerBlackPieces){
                for(Point move : p.generateLegalMoves())
                    if(move.getX() == whiteKingPoint.getX() && move.getY() == whiteKingPoint.getY()) return true;

            }}
        else{
            for(Piece p : playerWhitePieces){
                for(Point move : p.generateLegalMoves())
                    if(move.getX() == blackKingPoint.getX() && move.getY() == blackKingPoint.getY()) return true;

            }
        }
        return false;
    }
    public boolean isMate(){
        if(whitePiece()){
            for(Piece p : playerBlackPieces){
                if(!(p.generateLegalMovesWithCheck().isEmpty())){
                    return false;
                }
            }
        } else {
            for(Piece p : playerWhitePieces){
                if(!(p.generateLegalMovesWithCheck().isEmpty())){
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
        if(whitePiece()){
            alert.setContentText("Białe wygrały ez");
            System.out.println("White won");
        } else{
            alert.setContentText("Czarne wygrały ez");
            System.out.println("Black won");
        }
        alert.showAndWait();
    }

    //CASTLE FUNCTIONALITY
    public boolean canCastle(){
        return isValidMove() &&
                (getPieceChar() == 'k' || getPieceChar() == 'K') &&
                (newPoint.getX() == startPoint.getX() - 2 || newPoint.getX() == startPoint.getX() + 2);
    }
    public void castle(){
        //switch king and rook (charboard & GUI)
        if(!whitePiece()){
            if(newPoint.getX() < startPoint.getX()){
                switchKingAndRookBlack(startPoint.getX() - 4, startPoint.getX() - 1);
            } else{
                switchKingAndRookBlack(startPoint.getX() + 3, startPoint.getX() + 1);
            }
        } else{
            if(newPoint.getX() < startPoint.getX()){
                switchKingAndRookWhite(startPoint.getX() - 4, startPoint.getX() - 1);
            } else{
                switchKingAndRookWhite(startPoint.getX() + 3, startPoint.getX() + 1);
            }
        }
    }
    public void switchKingAndRookWhite(int startRookX, int newRookX){
        for(Rook rook:getPlayerWhiteRooks()){
            if(rook.getStartPoint().getX() == startRookX){
                charBoard[rook.getStartPoint().getX()][rook.getStartPoint().getY()] = '.';
                charBoard[newRookX][rook.getStartPoint().getY()] = 'R';
                charBoard[startPoint.getX()][startPoint.getY()] = '.';
                charBoard[newPoint.getX()][newPoint.getY()] = 'K';
                rook.getStartPoint().setX(newRookX);
                switchKingPoint(newPoint);
                setImageSquare(rook.getPieceImage(), new Point(newRookX, newPoint.getY()));
                break;
            }
        }
    }
    public void switchKingAndRookBlack(int startRookX, int newRookX){
        for(Rook rook:getPlayerBlackRooks()){
            if(rook.getStartPoint().getX() == startRookX){
                charBoard[rook.getStartPoint().getX()][rook.getStartPoint().getY()] = '.';
                charBoard[newRookX][rook.getStartPoint().getY()] = 'r';
                charBoard[startPoint.getX()][startPoint.getY()] = '.';
                charBoard[newPoint.getX()][newPoint.getY()] = 'k';
                rook.getStartPoint().setX(newRookX);
                switchKingPoint(newPoint);
                setImageSquare(rook.getPieceImage(), new Point(newRookX, newPoint.getY()));
                break;
            }
        }
    }

    //BOARD FUNCTIONALITY
    void copyCharBoard(char[][] boardFrom, char[][] boardTo){
        for(int i = 0; i<boardFrom.length; i++)
            System.arraycopy(boardFrom[i], 0, boardTo[i], 0, boardFrom[0].length);
    }

    //MOVES FUNCTIONALITY
    public static Vector<Move> getHistoryOfMoves() {
        return historyOfMoves;
    }
    public static Move getLastMove(){
        Move lastMove = null;
        if(!historyOfMoves.isEmpty()){
            lastMove = historyOfMoves.lastElement();
            historyOfMoves.remove(historyOfMoves.size()-1);
        }
        return lastMove;
    }
    public static void printHistoryOfMoves(){
        for(Move move : historyOfMoves){
            System.out.println(move.toString());
        }
    }
}
