/**
 * Java Core. Home work 2. Array exceptions
 *
 * @author Irek Nabiullin
 * @version dated Feb 28, 2018
 * @link https://github.com/IrekNabiullin
 *
 *
 *  Задание:
 * Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4. При
 * подаче массива другого размера необходимо бросить исключение MyArraySizeException.
 * 2 Далее метод должен пройтись по всем элементам массива, преобразовать в int и
 * просуммировать. Если в каком-то элементе массива преобразование не удалось (например, в
 * ячейке лежит символ или текст вместо числа), должно быть брошено исключение
 * MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
 * 3 В методе main() вызвать полученный метод, обработать возможные исключения
 * MySizeArrayException и MyArrayDataException и вывести результат расчета.
 *
 */

package ru.geekbrains.exceptions;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MakeArray aa = new MakeArray(); // создаем массив
        String myStringArr[][] = aa.fillArray(aa.makeArray());   // заполняем массив значениями

        try {
            System.out.println("Checking new array...");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Wait a bit...");
        }

        try {
            CheckSize cz = new CheckSize(myStringArr);
            System.out.println(cz);
        } catch (Exception eSize) {
            System.out.println("Error. Array size is not correct!");
        }
        try {
            SumCells ss = new SumCells(myStringArr);
            System.out.println(ss);
        } catch (Exception eData) {
            System.out.println("Error. Some data in array is not integer figure!");
            System.out.println(eData);
        }
    }
}
public class MakeArray {
    int rows = 0; // количество рядов в двумерном массиве
    int columns = 0; // количество колонок в двумерном массиве
    public static Scanner sc = new Scanner(System.in);

    static int[] makeArray() {
        int rows = 0; // количество рядов в двумерном массиве
        int columns = 0; // количество колонок в двумерном массиве
        int tempArr[] = new int[3];

        // Будем просить пользователя вручную ввести количество рядов и колонок.
        // Для отладки будем пробовать два вида входного массива: 1 - только числа, 2 - числа и символы или строки
        int option = 0;
        Scanner sc = new Scanner(System.in);
        rows = getInputFromScanner("Input number of rows in range of 0 till 5: ", 0, 5);
        System.out.println("Your input: " + rows);
        columns = getInputFromScanner("Input number of columns in range of 0 till 5: ", 0, 5);
        System.out.println("Your input: " + columns);
        option = getInputFromScanner("Choose option to fill our array by: \n" +
                "1 - integers only \n" +
                "2 - random simbols and figures ", 1, 2);
        System.out.println("Your input: " + option);
        tempArr[0] = rows;
        tempArr[1] = columns;
        tempArr[2] = option;
		sc.close();
        return tempArr;
    }

    // используем метод для проверки ввода пользователя на соответствие заданным границам диапазона
    protected static int getInputFromScanner(String message, int min, int max) {
        int x;
        do {
            System.out.println(message);
            x = sc.nextInt();
        } while (x < min || x > max);
        return x;
    }

    protected String[][] stringArr = new String[rows][columns]; // строковый массив
    protected int[][] intArr = new int[4][4]; // int-массив для суммирования значений

    // пишем метод для заполнения нашего строкового массива цифрами или случайными символами, включая числа:
    String[][] fillArray(int[] tempArr) {
        int rows = tempArr[0];
        int columns = tempArr[1];
        int option = tempArr[2];
        String stringArr[][] = new String[rows][columns];
        String randomArr [] = {"0", "1", "2", "3", "o", "l", "З"}; //отсюда будем заполнять наш двумерный массив
                            //                      ^это буквы^

        switch (option) {
            case 1:
                for (int i = 0; i < rows; i++) {
                    System.out.print("[");
                    for (int j = 0; j < columns; j++) {
                        stringArr[i][j] = String.valueOf(i) + String.valueOf(j);
                        System.out.print(stringArr[i][j] + " ");
                    }
                    System.out.println("]");
                }
                break;
            case 2:
                for (int i = 0; i < rows; i++) {
                    System.out.print("[");
                    for (int j = 0; j < columns; j++) {
                        stringArr[i][j] = randomArr[(int) (Math.random() * 6)];
                        System.out.print(stringArr[i][j] + " ");
                    }
                    System.out.println("]");
                }
                break;
            default:
                System.out.println("Error");
        }
        return stringArr;
    }
}

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

public class MyArraySizeException extends ArrayIndexOutOfBoundsException {

    public MyArraySizeException(String errorMessage) {
        super("Error:" + errorMessage);
            }
}

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
