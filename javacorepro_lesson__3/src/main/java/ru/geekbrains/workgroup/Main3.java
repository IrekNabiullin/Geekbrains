package ru.geekbrains.workgroup;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main3 {
    public static void main(String[] args) throws Exception{
        InputStream in = new BufferedInputStream(new FileInputStream("1.txt"), 1024);
        int x;
        while((x = in.read()) != -1 ) System.out.print((char) x);
        in.close();
    }
}
