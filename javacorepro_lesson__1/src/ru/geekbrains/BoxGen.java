package ru.geekbrains;

//T – тип данных (любой)
//E – элемент коллекции
//K – ключ
//V - значение
//N – числовые значения


public class BoxGen<T> {
    private T obj;

    public BoxGen(){
    }
    public BoxGen(T obj){
        this.obj = obj;
    }
    public T getObj(){
        return obj;
    }
    public void setObj(T obj){
        this.obj = obj;
    }
}
