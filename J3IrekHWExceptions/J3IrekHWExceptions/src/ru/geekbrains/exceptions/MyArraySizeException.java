
package ru.geekbrains.exceptions;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException {

    public MyArraySizeException(String errorMessage) {
        super("Error:" + errorMessage);
            }
}

