/*
 *
 * Java Core Pro. Home work 4. Многопоточность.
 *
 * @author Irek Nabiullin
 * @version dated Apr 15, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 *  1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок
 *  – ABСABСABС). Используйте wait/notify/notifyAll.
 *  2. Написать небольшой метод, в котором 3 потока построчно пишут данные в файл (по 10
 *  записей с периодом в 20 мс).
 *  3. Написать класс МФУ, на котором возможно одновременно выполнять печать и сканирование
 *  документов, но нельзя одновременно печатать или сканировать два документа. При печати в
 *  консоль выводится сообщения «Отпечатано 1, 2, 3,... страницы», при сканировании –
 *  аналогично «Отсканировано...». Вывод в консоль с периодом в 50 мс.
 */

package ru.geekbrains;


public class Main {
    // Задание 1
    public static Object monitor = new Object();

    public static char currentChar = 'A';

    public static void main(String[] args) {
            new Thread(() -> {
                try{
                    for(int i = 0; i < 5; i++){
                        synchronized(monitor){
                            while(currentChar != 'A'){
                                monitor.wait();
                            }
                            System.out.println("A");
                            currentChar = 'B';
                            monitor.notifyAll();
                        }
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try{
                    for(int i = 0; i < 5; i++){
                        synchronized(monitor){
                            while(currentChar != 'B'){
                                monitor.wait();
                            }
                            System.out.println("B");
                            currentChar = 'C';
                            monitor.notifyAll();
                        }
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        new Thread(() -> {
            try{
                for(int i = 0; i < 5; i++){
                    synchronized(monitor){
                        while(currentChar != 'C'){
                            monitor.wait();
                        }
                        System.out.println("C");
                        currentChar = 'A';
                        monitor.notifyAll();
                    }
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();

    }
}
