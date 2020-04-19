package ru.geekbrains.workgroup;

public class Main4 {
    public static Object lock1 = new Object();
    public static void main(String[] args){
        new Thread(() -> method1()).start();
        new Thread(() -> method1()).start();
        new Thread(() -> method1()).start();
    }
    public static void method1(){
        try{
            System.out.println("Non synchronized Begin: " + Thread.currentThread().getName());
            for(int i = 0; i < 3; i++){
                System.out.println(" . ");
                Thread.sleep(100);
            }
            System.out.println("Non synchronized End " + Thread.currentThread().getName() );
            synchronized(lock1){
                System.out.println("Synchronized Begin " + Thread.currentThread().getName() );
                for(int i = 0; i < 5; i++){
                    System.out.println(" & ");
                    Thread.sleep(200);
                    System.out.println("Synchronized End " + Thread.currentThread().getName() );
                }
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
