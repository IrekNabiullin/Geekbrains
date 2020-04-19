package ru.geekbrains.workgroup;

public class Main {
    public static void main(String[] args){
        Thread t1 = new ThreadClass();
        Thread t2 = new Thread(new RunnableInt());
//        t1.run(); //это не многопоточность
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Main end");
    }
}
