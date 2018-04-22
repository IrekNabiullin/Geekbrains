package ru.geekbrains;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CountDownLatch cdown;
    private CyclicBarrier cbarrier;
    private CountDownLatch cdownFinish;
    private static Object winMonitor = new Object();
    private static boolean winner;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
    

    public Car(Race race, int speed, CountDownLatch cdownStart, CyclicBarrier cbarrier, CountDownLatch cdownFinish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cdown = cdownStart;
        this.cbarrier = cbarrier;
        this.cdownFinish = cdownFinish;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cdown.countDown();
            cbarrier.await();

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        synchronized (winMonitor) {
            if(!winner) {
                System.out.println(name + " win!");
                winner = true;
            }
        }


            cdownFinish.countDown();
            cbarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
