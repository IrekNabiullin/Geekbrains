/*
 *
 * Java Core Pro. Home work 5. Race (Гонки)
 *
 * @author Irek Nabiullin
 * @version dated Apr 19, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 *
 *  Организуем гонки:
 *  Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого их них уходит разное время.
 *  В тоннель не может заехать одновременно больше половины участников (условность).
 *  Попробуйте все это синхронизировать.
 *  Только после того, как все завершат гонку, нужно выдать объявление об окончании.
 *  Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
 *
 */

package ru.geekbrains;
import java.util.concurrent.*;

public class Main {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CountDownLatch cdownStart = new CountDownLatch(CARS_COUNT);
        CountDownLatch cdownFinish = new CountDownLatch(CARS_COUNT);
        CyclicBarrier cbarrier = new CyclicBarrier(CARS_COUNT+1);
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdownStart, cbarrier, cdownFinish);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try{
            cdownStart.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cbarrier.await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            cdownFinish.await();
            cbarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");


    }
}

