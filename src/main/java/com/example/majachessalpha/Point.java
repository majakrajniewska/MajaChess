package com.example.majachessalpha;

public class Point {
    private int x;
    private int y;
    Point(){}
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void copyPoint(Point point){
        this.x = point.getX();
        this.y = point.getY();
    }
}
