package ru.geekbrains.workgroup;

public class Student extends Human {
    String name;
    int score;
    transient Book book;
    public Student(String name, int score, Book book){
        System.out.println("Student Constructor Called!");
        this.name = name;
        this.score = score;
        this.book = book;
    }
    public void info(){
        System.out.println("Name: " + this.name + " Score: " + this.score);
    }
}
