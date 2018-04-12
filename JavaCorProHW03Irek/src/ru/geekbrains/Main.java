/*
 *
 * Java Core Pro. Home work 3. Средства ввода-вывода.
 *
 * @author Irek Nabiullin
 * @version dated Apr 11, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 *  1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
 *  2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт). Может пригодиться
 *     следующая конструкция:
 *     ArrayList<InputStream> al = ​new​ ArrayList<>();
 *     ...
 *    Enumeration<InputStream> e = Collections.enumeration(al);
 *    3. Написать консольное приложение, которое умеет постранично читать текстовые файлы
 *    (размером > 10 mb). Вводим страницу (за страницу можно принять 1800 символов), программа
 *    выводит ее в консоль. Контролируем время выполнения: программа не должна загружаться
 *    дольше 10 секунд, а чтение – занимать свыше 5 секунд.
 *    Чтобы не было проблем с кодировкой, используйте латинские буквы
 */

package ru.geekbrains;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try {
            task1(); //выполняем задание 1
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            task2(); //выполняем задание 2
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            task3(); //выполняем задание 3

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void task1() throws FileNotFoundException, IOException {
        try {
            File myFile1 = new File("files/test.txt");
            if (myFile1.exists()) {                         // проверим, существует ли файл в папке files
                try {
                    try (FileInputStream in = new FileInputStream("files/test.txt")) {
                        byte[] bytes = new byte[1024];      // используем массив байтов
                        String str;
                        System.out.println("File " + myFile1.getName() + " is " + myFile1.length() + " bytes length and has data:");
                        while (in.read(bytes) > 0) {
                            str = new String(bytes);
                            System.out.println(str);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } else {
                System.out.println("File " + myFile1.getName() + " not found.");
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

        // не хотел вручную  создавать 5 файлов и высчитывать размеры по 100 Бт, поэтому немного нагрузил нашу программу
        // лишними операциями, чтобы в цикле создать 5 требуемых файлов:
        private static void task2() throws Exception {
            File[] files = new File[5];  // создаем массив для 5 файлов
            FileInputStream[] in = new FileInputStream[5]; //заранее создадим потоки для чтения
            ArrayList<InputStream> a1 = new ArrayList<>(); // используем ArrayList

            for (int i = 0; i < files.length; i++) {
                files[i] = new File("files/test" + i + ".txt"); //в циле создадим 5 файлов

                try (FileOutputStream out = new FileOutputStream("files/test" + i + ".txt")) { //
                    for (int j = 0; j < 100; j++) {
                        out.write(65 + i); //запишем в файл 100 байт символов
                    }
                } catch (FileNotFoundException e4) {
                    e4.printStackTrace();
                }
                if (files[i].exists()) { // проверим успешность создания файла и проверим размер
                    System.out.print("File " + files[i].getName() + " is " + files[i].length() + " bytes length");
                    in[i] = new FileInputStream(files[i]);
                    a1.add(in[i]);
                }
                System.out.println(" and has data:"); // выведем для проверки содержание каждого отдельного файла

                //теперь прочитаем содержимое всех 5 файлов, сошьем их в один поток и
                // сразу закинем наш поток в файл, что требуется по условию задания
                    try{
                        InputStream inNext = new BufferedInputStream(new FileInputStream("files/test" + i + ".txt"), 1024);
                    int x;
                    while((x = inNext.read()) != -1 ) System.out.print((char) x);
                    inNext.close();
                        System.out.println();
                } catch (FileNotFoundException e4){
                e4.printStackTrace();
                 }
            }
                Enumeration<InputStream> e = Collections.enumeration(a1);
                SequenceInputStream sq = new SequenceInputStream(e);
                int x;
                try {
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("files/test0_4.txt"));
                    while ((x = sq.read()) != -1) {
                        out.write(x);
                        System.out.print((char) x);
                    }
                    sq.close();
                    out.close();
                    System.out.println();
                }catch (FileNotFoundException e5){
                    e5.printStackTrace();
                }
            }


    private static void task3() throws Exception {

            File hugeFile = new File("files/hugeTest.txt");   // Прежде всего создадим большой файл
            if (!hugeFile.exists()) {                                   // проверим, не существует ли файл в папке files
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("files/hugeTest.txt")); // создадим поток для записи
                    byte[] bytes = new byte[1024];                      // используем массив байтов

                    for ( int i = 0; i<100000; i++) {
                        for (int k = 0; k < bytes.length; k++) {
                            bytes [k] = (byte)i;
                        }
                        out.write(bytes);                               //запишем в файл из массива символов
                    }

            } else if (hugeFile.exists()) { // проверим успешность создания файла и проверим размер
                System.out.println("File " + hugeFile.getName() + " is " + hugeFile.length() + " bytes length");
            }
            //теперь зададим для каждой страницы фиксированную длину
            int PageSize = 1800;
            RandomAccessFile raf = new RandomAccessFile("files/hugeTest.txt", "rw");
            int pages = (int) (hugeFile.length()/PageSize);
        System.out.println(" There are " + pages + " in our hugeText.txt");

            // просим пользователя ввести номер страницы
            Scanner sc = new Scanner(System.in);

                int x;
                do {
                    System.out.println("Enter page number, please:");
                    x = sc.nextInt();
                } while (x <= 0 || x > pages);

                    int p = x - 1; // страница. Минус один, т.к. внутренняя нумерация начинается с нуля

                    long t = System.currentTimeMillis(); //фиксируем текущее время
                    try {
                        raf.seek(p * PageSize); // ищем нужную страницу
                        byte[] readArray = new byte[PageSize];
                        int n = raf.read(readArray);
                        System.out.println(new String(readArray, 0, n));
                        raf.close();
                    } catch (StringIndexOutOfBoundsException exc) {
                        exc.printStackTrace();
                    }
                    System.out.println("Time elapsed: " + (System.currentTimeMillis() - t));

    }
}






