package ru.geekbrains.homework;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Box<Orange> orangeBox1 = new Box<>(new Orange(), new Orange());
        Box<Orange> orangeBox2 = new Box<>(new Orange(), new Orange(), new Orange());
        Box<Apple> appleBox = new Box<>(new Apple(), new Apple());
        System.out.println(orangeBox1.weightCompare(orangeBox2));
        System.out.println(orangeBox2.weightCompare(appleBox));
//        orangeBox1.transferFruits(appleBox);
        orangeBox1.transferFruits(orangeBox2);
        Box<Fruit> fruitBox = new Box<>(new Apple(), new Orange());
        appleBox.transferFruits(fruitBox);

    }
    public static <T> void swapElements(T[] arr, int index1, int index2){
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    public static <T>ArrayList<T> arrayList(T[] arr){
        return new ArrayList<>(Arrays.asList(arr));
    }

}
