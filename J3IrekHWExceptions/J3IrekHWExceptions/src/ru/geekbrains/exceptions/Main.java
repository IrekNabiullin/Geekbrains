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


