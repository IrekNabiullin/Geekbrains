package ru.geekbrains.exceptions;

import java.util.Scanner;

public class MakeArray {
    private int rows = 0; // количество рядов в двумерном массиве
    private int columns = 0; // количество колонок в двумерном массиве
    protected static Scanner sc = new Scanner(System.in);

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
