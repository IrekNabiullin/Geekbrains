package ru.geekbrains.workgroup;

import java.io.RandomAccessFile;

public class Main5 {
    public static void main(String[] args) throws Exception{
        RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");
        raf.seek(1000);
    }
}
