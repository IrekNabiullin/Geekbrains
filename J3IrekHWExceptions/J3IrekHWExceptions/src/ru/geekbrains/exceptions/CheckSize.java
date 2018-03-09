package ru.geekbrains.exceptions;

public class CheckSize {
    protected String[][] inputArray;

    protected CheckSize(String[][] inputArray) {
        this.inputArray = inputArray;
        checkSize(inputArray);
    }

    private boolean checkSize(String[][] inputArray) throws MyArraySizeException {

        if (inputArray.length != 3) {
            throw new MyArraySizeException(" Array rows != 3 ");
        } else {
            for (int i = 0; i < inputArray.length; i++) {
                if (inputArray[i].length != 3)
                    throw new MyArraySizeException(" Array columns != 3 ");
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Size is OK.";
    }
}