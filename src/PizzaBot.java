import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * GeekBrains. Robot exersices. Task 1.
 *
 * @author Irek Nabiullin
 * @version dated Feb 18, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Миссия: Провести вычисления
 *  Условия: Респондент №1 съедает объект "Пицца" за 60 минут. Респондент №2 – за 30 минут, Респондент №3 – за 5 минут.
 *  Вопрос: За сколько минут респонденты целиком поглотят один целый объект на %корпоративе
 */

public class PizzaBot {

    public static void main(String[] args) {

        int robotEatTime [] = {60,30,5}; //задаем массив с входными значениями времён, за которые роботы съедают пиццу
        // разрежем пиццу на части, чтобы каждый робот мог съесть целое количество кусочков за единицу времени
        // для этого используем алгоритм евклидова наибольшего общего делителя, переписанного для массива цифр
        int allPieces = 1;
        int nod = NOD.nod(robotEatTime);
        System.out.println("NOD = " + nod);
        for (int i = 0; i < robotEatTime.length; i++) {
            allPieces = allPieces * robotEatTime[i];
        }
        allPieces = allPieces / nod;
        System.out.println("Total pieces: " + allPieces); // выведем на консоль общее количество кусочков
//        System.out.println("NOD = " + NOD.nod(robotEatTime));
        Pizza pizza = new Pizza(allPieces); //Pizza состоит из кусочков

        Robot robotsArray [] = new Robot [robotEatTime.length]; // создаем массив роботов (может быть и более трех роботов)
        for (int i = 0; i < robotsArray.length; i++) {
            robotsArray[i] = new Robot (robotEatTime[i], allPieces);        // инициализируем роботов
        }

        int timeSpent = 0; //время, затраченное на полное уничтожение пиццы

        int minute = 1;
        do {
            System.out.println("Minute: " + minute);
            for (int i = 0; i < robotsArray.length; i++) {
                robotsArray[i].eat(pizza);
                System.out.print(robotsArray[i] + " ");
                System.out.println(pizza);
            }
            timeSpent = minute++;

        } while (pizza.pieces > 0);

        System.out.println("Pizza is eaten in " + timeSpent + " minute(s).");
    }
}

public class Robot {
    private int eatTime; // за сколько минут робот может съесть пиццу
    private int appetite; // аппетит - сколько кусочков пиццы робот может съесть за 1 минуту

    public Robot (int eatTime, int totalPieces) {
        this.eatTime = eatTime;
        try{
            this.appetite = totalPieces / eatTime;
        } catch (ArithmeticException e) {
            System.out.println("This robot is not hungry");
        }
    }

    public int eat (Pizza pizza ) {
        if (pizza.pieces >= appetite) {
            pizza.decreasePizza(appetite);
            return appetite;
        } else {
            appetite = pizza.pieces;
            pizza.pieces =0;
            return appetite;
        }
    }

    @Override
    public String toString(){
        return getClass().getName() + " eats " + appetite + " pieces of pizza.";
    }
}

public class Pizza {
    public int pieces;

    public Pizza (int pieces) {
        this.pieces = pieces;
    }
    void decreasePizza (int pieces) {
        if (pieces<=this.pieces)
            this.pieces -= pieces;
        else
            this.pieces = 0;

    }

    @Override
    public String toString(){
        return getClass().getName() + ": " + pieces + " pieces left.";
    }
}




public class NOD { // найдем максимальный общий делитель для 3х целых цифр

 public int [] arr; //массив для сравнения

    public NOD (int arr[]){
        this.arr = arr;
    }

    protected static int nod (int arr[]) {
        int a=1;
        int b=1;
        int c =1;
        int tempArr[] = new int[arr.length];
        Set<Integer> set = new HashSet<Integer>();

        int noda = arr[0]; //наибольший общий делитель

        for (int i = 0; i < arr.length; i++) {
            if (noda < arr[i]) {
                noda = arr[i];
            }
//            System.out.println("noda = " + noda);
            a = arr[i];
//            System.out.print("a = " + a + " ");
            for (int j = i + 1; j < arr.length; j++) {
                try {
                    b = arr[j];
//                    System.out.print("b = " + b + " ");
                } catch (IndexOutOfBoundsException ex) {
                    break;
                }
                while (b != 0) {
                    int tmp = a % b;
                    a = b;
                    b = tmp;
                }

                set.add(a);
//                System.out.println("c = " + a + " ");
                a = arr[i];
            }
        }

        for (Integer item: set) {
 //           System.out.println("item = " + item);
            if (item < noda) {
                noda = item;
            }
//            System.out.println("noda = " + noda);
        }

  /*      for (int i = 0; i < arr.length-1; i++) {
            System.out.println("c[" + i + "]= " + tempArr[i]);
            if (nod > tempArr[i]){
                nod = tempArr[i];
            }
            System.out.println(" nod = " + nod);
        }*/
        return noda;
    }

/* вариант 2 для 2-х целых чисел:
int a;
int b;
public NOD(int a, int b) {
    this.a = a;
    this.b = b;
    nod(a,b);
}

    public static int nod(int a, int b) {
        while (b !=0) {
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }
    */

}
