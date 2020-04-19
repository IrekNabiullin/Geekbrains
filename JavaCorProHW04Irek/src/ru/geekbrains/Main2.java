package ru.geekbrains;

import java.io.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main2 {
    //задание 2

        public static void main (String[] args ) {
            class WriteToFile implements Runnable {
                private PrintWriter pw;
                private String str;

                public WriteToFile (PrintWriter pw, String str) {
                    this.pw = pw;
                    this.str = str;
                }

                @Override
                public void run(){
                    for (int i=0; i<10; i++) {
                        try{
                            pw.println(str);
                            System.out.println("Writing to file...");
                            pw.flush();
                            Thread.sleep(20);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            PrintWriter pw = null;
            try {
                try{
                pw = new PrintWriter("files/test1.txt", "UTF-8");
                Thread t1 = new Thread(new WriteToFile(pw, "Thread1"));
                Thread t2 = new Thread(new WriteToFile(pw, "Thread2"));
                Thread t3 = new Thread(new WriteToFile(pw, "Thread3"));
                try{
                    t1.start();
                    t2.start();
                    t3.start();
                    t1.join();
                    t2.join();
                    t3.join();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            } finally {
                pw.close();
            }
        }
    }
