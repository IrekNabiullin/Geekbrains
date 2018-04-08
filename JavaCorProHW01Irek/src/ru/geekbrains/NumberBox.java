package ru.geekbrains;

public class NumberBox<N extends Number> { //только ключевое слово extends
    private N[] numbers;
    private N obj;
    public N[] getNumbers(){
        return numbers;
    }
    public void setNumbers(N[] numbers){
        this.numbers = numbers;
    }
    public N getObj(){
        return obj;
    }
    public void setObj(N obj){
        this.obj = obj;
    }
    public NumberBox(){
    }
    public NumberBox(N[] numbers){
        this.numbers = numbers;
    }
    public double average(){
        double d = 0.;
        for(N number : numbers){
            d+= number.doubleValue();
        }
        d /= this.numbers.length;
        return d;
    }
    // ? - маска/метасимвольный аргумент
    public boolean compare(NumberBox<?> anotherBox){
        return Math.abs(this.average() - anotherBox.average()) < 0.0001f;
    }
}

