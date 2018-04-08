package ru.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;

public class BoxOfFruit<T extends Fruits> {
    //для создания ArrayList c фруктами используем правильное обобщение
    //не забываем про метод add (добавить фрукт в коробку) - -яблоко положить к апельсинам нельзя {
    private ArrayList<T> container;
    public ArrayList<T> getContainer() {
        return container;
    }

    public BoxOfFruit(T ... fruits) {
        this.container = new ArrayList<>(Arrays.asList(fruits));
    }


    // метод добавления фрукта в коробку
    public void addFruit(T fruit) {
        this.container.add(fruit);
        }

    // метод удаления фрукта из коробки
    public void removeFruit(T fruit) {
        container.remove(fruit);
        }

    // метод определения веса коробки
    public float getWeight() {
        float weightOfBox = 0.0f;
        for (T t: container) {
            weightOfBox += t.getWeight();
        }
        System.out.println("Weight of " + container + " = " + weightOfBox);
        return weightOfBox;
    }

    // метод сравннеия веса коробки с другой коробкой того же вида
    public boolean compare(BoxOfFruit<?> anotherBox) {
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.0001f;
    }

    public void transferFruits (BoxOfFruit<? super T> anotherBox){
        anotherBox.container.addAll(this.container);
        this.container.clear();
    }

   @Override
    public String toString() {
//       return getClass().getName();
       return getClass().getSimpleName();

//       String fullClassName = this.getClass().getName();
//       String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
//       return simpleClassName;
    }


}
