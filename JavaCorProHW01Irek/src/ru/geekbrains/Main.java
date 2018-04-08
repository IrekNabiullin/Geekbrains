/*
 *
 * Java Core Pro. Home work 1. Generic (Обобщения)
 *
 * @author Irek Nabiullin
 * @version dated Apr 04, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 * 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
 * 2. Написать метод, который преобразует массив в ArrayList;
 * 3. Задача:
 * a. Даны классы Fruit -> Apple, Orange;
 * b. Класс Box, в который можно складывать фрукты. Коробки условно сортируются по
 *    типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 * c. Для хранения фруктов внутри коробки можно использовать ArrayList;
 * d. Сделать метод getWeight(), который высчитывает вес коробки. Задать вес одного
 * фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не
 * важны);
 * e. Внутри класса Box сделать метод Compare, который позволяет сравнить текущую
 * коробку с той, которую подадут в Compare в качестве параметра. True – если их массы
 * равны, False в противоположном случае. Можно сравнивать коробки с яблоками и
 * апельсинами;
 * f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую.
 * Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами.
 * Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются
 * объекты, которые были в первой;
 * g. Не забываем про метод добавления фрукта в коробку.
 */

package ru.geekbrains;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        // Задание 1.
        // Создаем массив ссылочного типа, соержащий 10 цифр от 0 до 9 и выводим его на консоль
        ArrayList<Integer> myArray = new ArrayList<Integer>();
        System.out.println("Task 1.");
        System.out.print("myArray = {");    // Печатаем наименование массива и открывающую фигурную скобку
        for (int i = 0; i<10; i++) {        // в цикле:
            myArray.add(i);                 //добавляем очередной элемент в массив
            System.out.print(i + ", ");     // печатаем элементы массива, разделяя их запятыми
        }
        System.out.println("\b\b}");       //убираем последнюю запятую и печатаем закрывающую фигурную скобку

        // Просим пользователя ввести две цифры для замены
        Integer firstNumber = 0; // первая цифра для замены
        Integer secondNumber = 0; // вторая цифра для замены

        System.out.println("Let's change two figures in myArray!");
        firstNumber = getInputFromScanner("Input first number in range of 0 till 9: ", 0, 9);
        System.out.println("Your input: " + firstNumber);
        do {
            secondNumber = getInputFromScanner("Input second number in range of 0 till 9: ", 0, 9);
            System.out.println("Your input: " + secondNumber);
            if (firstNumber == secondNumber)
                System.out.println("Please input other number.");
        } while (firstNumber == secondNumber);
        System.out.println();
        System.out.println("Thank you! Let's check the array: ");

        // меняем местами элементы, указанные пользователем
        int firstIndex = myArray.indexOf(firstNumber);  // определяем индекс первого элемента к замене
        int secondIndex = myArray.indexOf(secondNumber);// определяем индекс второго элемента к замене
        myArray.set(firstIndex, secondNumber); // подставляем второй элемент на место первого
        myArray.set(secondIndex, firstNumber); // // подставляем первый элемент на место второго
        System.out.println(myArray);            // выводим результат на консоль
        System.out.println();


        // Задание 2.
        // Написать метод, который преобразует массив в ArrayList
        Integer [] arrInt = {1,2,3,4,5}; // берем произвольный массив
        ArrayList<Integer> arr = new ArrayList<>(); // создаем пустой список
        Collections.addAll(arr, arrInt); // записываем массив в список
        System.out.println(" Task2. Arr = " + arr); // выводим результат на консоль
        System.out.println();

        //Задача 3.

        System.out.println("Task 3.");
        // Создаем коробки двух видов - для яблок и для апельсинов
        BoxOfFruit<Apple> apples1 = new BoxOfFruit<>(new Apple());
        BoxOfFruit<Apple> apples2 = new BoxOfFruit<>(new Apple(), new Apple());
        BoxOfFruit<Apple> apples3 = new BoxOfFruit<>(new Apple(), new Apple(), new Apple());
        BoxOfFruit<Orange> oranges1 = new BoxOfFruit<>(new Orange());
        BoxOfFruit<Orange> oranges2 = new BoxOfFruit<>(new Orange(), new Orange());
        BoxOfFruit<Orange> oranges3 = new BoxOfFruit<>(new Orange(), new Orange(), new Orange());

        // Положим в первую коробку 30 яблок
        //    BoxOfFruit.addFruit(apples);

        // Пололжим во вторую коробку 20 апельсинов
         //   oranges.addFruit(20, 1.5f);


        //сравниваем вес коробки с апельсинами и корбоки с яблоками
        System.out.println("Compare = " + apples1.compare(oranges1));
        System.out.println("Compare = " + apples1.compare(oranges2));
        System.out.println("Compare = " + apples1.compare(oranges3));
        System.out.println("Compare = " + apples2.compare(oranges1));
        System.out.println("Compare = " + apples2.compare(oranges2));
        System.out.println("Compare = " + apples2.compare(oranges3));
        System.out.println("Compare = " + apples3.compare(oranges1));
        System.out.println("Compare = " + apples3.compare(oranges2));
        System.out.println("Compare = " + apples3.compare(oranges3));




    }

    // используем метод для проверки ввода пользователя на соответствие заданным границам диапазона из методички java-1
    protected static int getInputFromScanner(String message, int min, int max) {

           Scanner sc = new Scanner(System.in);
            int x;
            do {
                System.out.println(message);
                x = sc.nextInt();
            } while (x < min || x > max);
        /*
        try {
            sc.close();  // странно, если закрыть сканер, то вылетает ошибка try-catch-finally не помогает
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        return x;
    }

/*
 * Так как моя java-8 не смогла скомпилировать код с лекции, написанный в java-9, пришлось ввести код заново,
 * поэтому некоторые наименования классов отличаются по названиям от лекции.
 *
 */



//        МАТЕРИАЛЫ ЛЕКЦИИ-1
//        Box intBox1 = new Box(50);
//        Box intBox2 = new Box(20);
        //500 lines of code
//        intBox1.setObject("Java");

//        int result = 0;
//        if(intBox1.getObject() instanceof Integer && intBox2.getObject() instanceof Integer)
//            result = (Integer) intBox1.getObject() + (Integer) intBox2.getObject();
//        System.out.println(result);

//        BoxGen<Integer> intBox3 = new BoxGen<>(3);
//        BoxGen<Integer> intBox4 = new BoxGen<>(5);
//        BoxGen<String> stringBox = new BoxGen<>("Java");
//        BoxGen box = new BoxGen();
//        int resultGen = intBox3.getObj() + intBox4.getObj();
//        intBox3.setObj("Java");
//        NumberBox<Integer> integerNumberBox = new NumberBox<>(new Integer[]{1,2,3,4,5});
//        NumberBox<Double> doubleNumberBox = new NumberBox<>(new Double[]{1.,2., 3., 4., 5.});
//        System.out.println("Int Avg: " + integerNumberBox.average());
//        System.out.println("Double Avg: " + doubleNumberBox.average());
//        NumberBox<Number> numberNumberBox = new NumberBox<>();
//        System.out.println(integerNumberBox.compare(doubleNumberBox));

        //ArrayList<int> -- в дженериках запрещено использованеи примитивных типов\
//        Integer integer1 = 1;
//        Integer integer2 = 5;
//        String string = "Java";
//        System.out.println(compare(integer1, integer2));
//        int[] array = {1,2,3,4};
//        ArrayList<Integer> arrayList;


    //    public static <T extends Comparable<T>, V extends  Number> int compare(T t1, V t2){
//        return t1.compareTo(t2);
//    }
 //   public static <T extends Comparable<T>> int compare(T t1, T t2){
 //       return t1.compareTo(t2);
    }

