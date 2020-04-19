package ru.geekbrains.homework;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private ArrayList<T> container;
    public ArrayList<T> getContainer(){
        return container;
    }
    public Box(T... fruits){
        this.container = new ArrayList<>(Arrays.asList(fruits));
    }
    public void addFruit(T fruit){
        this.container.add(fruit);
    }
    public float getWeight(){
        float w = 0.f;
        for(T t : container){
            w+=t.getWeight();
        }
        return w;
    }
    public boolean weightCompare(Box<?> anotherBox){
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.001f;
    }
    public void transferFruits(Box<? super T> anotherBox){
        anotherBox.container.addAll(this.container);
        this.container.clear();
    }
}
