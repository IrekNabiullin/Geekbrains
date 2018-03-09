package ru.geekbrains.exceptions;

public class MyArrayDataException extends RuntimeException{

    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public MyArrayDataException(int row, int col) {
        super("Error in cell " + row + ":" + col);
        this.row = row;
        this.col = col;
    }
}

