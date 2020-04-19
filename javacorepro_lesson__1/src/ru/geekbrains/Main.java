package ru.geekbrains;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Box intBox1 = new Box(50);
        Box intBox2 = new Box(20);
        //500 lines of code
//        intBox1.setObject("Java");

        int result = 0;
        if(intBox1.getObject() instanceof Integer && intBox2.getObject() instanceof Integer)
        result = (Integer) intBox1.getObject() + (Integer) intBox2.getObject();
        System.out.println(result);

        BoxGen<Integer> intBox3 = new BoxGen<>(3);
        BoxGen<Integer> intBox4 = new BoxGen<>(5);
        BoxGen<String> stringBox = new BoxGen<>("Java");
        BoxGen box = new BoxGen();
        int resultGen = intBox3.getObj() + intBox4.getObj();
//        intBox3.setObj("Java");
        NumberBox<Integer> integerNumberBox = new NumberBox<>(new Integer[]{1,2,3,4,5});
        NumberBox<Double> doubleNumberBox = new NumberBox<>(new Double[]{1.,2., 3., 4., 5.});
        System.out.println("Int Avg: " + integerNumberBox.average());
        System.out.println("Double Avg: " + doubleNumberBox.average());
        NumberBox<Number> numberNumberBox = new NumberBox<>();
        System.out.println(integerNumberBox.compare(doubleNumberBox));

        //ArrayList<int> -- в дженериках запрещено использованеи примитивных типов\
        Integer integer1 = 1;
        Integer integer2 = 5;
        String string = "Java";
        System.out.println(compare(integer1, integer2));
        int[] array = {1,2,3,4};
        ArrayList<Integer> arrayList;

    }
//    public static <T extends Comparable<T>, V extends  Number> int compare(T t1, V t2){
//        return t1.compareTo(t2);
//    }
        public static <T extends Comparable<T>> int compare(T t1, T t2){
            return t1.compareTo(t2);
        }

}
