public class MyException extends RuntimeException {
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public MyException(int row, int col) {
        super("Error in " + row + ":" + col);
        this.row = row;
        this.col = col;
    }
}
