package ru.geekbrains.workgroup;

public class Main6 {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args){
        DeadThreadOne threadOne = new DeadThreadOne();
        DeadThreadTwo threadTwo = new DeadThreadTwo();
        threadOne.start();
        threadTwo.start();
    }
    private static class DeadThreadOne extends Thread{
        @Override
        public void run(){
            synchronized(lock1){
                System.out.println("DeadThreadOne is holding lock1");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("DeadThreadOne is waiting for lock2");
            }
            synchronized(lock2){
                System.out.println("DeathThreadOne is holding lock1 and lock2");
            }
        }
    }
    private static class DeadThreadTwo extends Thread{
        @Override
        public void run(){
            synchronized(lock2){
                System.out.println("DeadThreadTwo is holding lock2");
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("DeadThreadTwo is waiting for lock1");
            }
            synchronized(lock1){
                System.out.println("DeathThreadTwo is holding lock1 and lock2");
            }
        }
    }
}
