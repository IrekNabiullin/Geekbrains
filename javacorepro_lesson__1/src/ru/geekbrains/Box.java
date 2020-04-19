package ru.geekbrains;

import java.util.List;

public class Box {
    private List<Object> objects;
    private Object object;
    public Box(Object object){
        this.object = object;
    }
    public Box(List<Object> objects){
        this.objects = objects;
    }
    public List<Object> getObjects(){
        return objects;
    }
    public void setObjects(List<Object> objects){
        this.objects = objects;
    }
    public Object getObject(){
        return object;
    }
    public void setObject(Object object){
        this.object = object;
    }
}
