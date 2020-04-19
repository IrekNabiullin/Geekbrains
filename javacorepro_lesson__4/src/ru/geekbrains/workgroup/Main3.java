package ru.geekbrains.workgroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main3 {
    public static void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(5);
        ExecutorService service1 = Executors.newFixedThreadPool(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r){
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setPriority(10);
                return t;
            }
        });
        for(int i = 0; i < 10; i++){
            final  int w = i;
            service.execute(new Runnable() {
                @Override
                public void run(){
                    System.out.println(w + " --begin");
                    try{
                        Thread.sleep(100 + (int)(3000*Math.random()));
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(w + " -- end");
                }
            });
        }
//        service.shutdown();
        service.shutdownNow();
    }
}
