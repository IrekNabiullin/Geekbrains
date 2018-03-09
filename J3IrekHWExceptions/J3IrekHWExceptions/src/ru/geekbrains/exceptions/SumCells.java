package ru.geekbrains.exceptions;

public class SumCells {
    private String[][] inputArray2;
    protected int sum = 0;
    protected SumCells(String[][] inputArray2) {
        this.inputArray2 = inputArray2;
        sumCells(inputArray2);
    }
    private int sumCells (String[][]inputArray2) throws MyArrayDataException {

        int y = 0;
        for (int i = 0; i < inputArray2.length; i++) {
            for (int j = 0; j < inputArray2[i].length; j++) {
                try {
                    y = Integer.parseInt(inputArray2[i][j]);
                    sum = sum + y;
                } catch (Exception e) {
                    throw new MyArrayDataException(i, j);
                }
            }

        }
        return sum;
    }

    @Override
    public String toString() {
        return "Sum of cells = " + sum;
    }
}