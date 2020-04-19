package ru.geekbrains.workgroup;

public class Main2 {
    public static void main(String[] args){
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run(){
                int time = 0;
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    time++;
                    System.out.println("Time: " + time);
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
        System.out.println("Main - sleep");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Main End");
    }
}
