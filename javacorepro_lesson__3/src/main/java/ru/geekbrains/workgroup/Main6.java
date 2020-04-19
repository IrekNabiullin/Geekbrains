package ru.geekbrains.workgroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main6 {
    public static void main(String[] args) throws Exception{
        Book book = new Book(1);
        Student s = new Student("Morty", 10, book);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"));
        oos.writeObject(s);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"));
        Student s2 = (Student) ois.readObject();
        ois.close();
        s.info();
        s2.info();
        System.out.println(s.toString());
        System.out.println(s2.toString());
    }
}
