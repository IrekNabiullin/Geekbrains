package ru.geekbrains.fruits;

import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args){
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Integer> integers1 = new ArrayList<>();
        ArrayList<Float> floats = new ArrayList<>();
        ArrayList<Number> numbers = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Objects> objects = new ArrayList<>();
        copyElements(integers, numbers);
//        copyElements(strings, numbers);
//        copyElements(numbers, objects);
        copyElements(integers, integers1);
//        copyElements(floats, integers1);
        copyElements(floats, numbers);

    }
    //super - класс должен быть не ниже по дереву наследования (такой же или выше)
    //extends - класс должен быть ниже по девеву наследования (такой же или ниже)
    public static <T> void copyElements(ArrayList<? extends T> src, ArrayList<? super T> dst){
        for(int i = 0; i < src.size(); i++){
            dst.add(src.get(i));
        }
        src.clear();
    }
}
