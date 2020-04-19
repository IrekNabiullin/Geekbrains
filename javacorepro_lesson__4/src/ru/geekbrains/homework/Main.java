package ru.geekbrains.homework;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        try{
            readFileToByteArray();
            mergeFiles();
            readFileByPage();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void readFileToByteArray() throws FileNotFoundException, IOException{
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("1.txt"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int x;
        while((x = in.read()) != -1) out.write(x);
        byte[] bytes = out.toByteArray();
        System.out.println(new String(bytes));
        in.close();
        out.close();
    }
    public static void mergeFiles() throws FileNotFoundException, IOException{
        ArrayList<InputStream> al = new ArrayList<>();
        al.add(new FileInputStream("2.txt"));
        al.add(new FileInputStream("3.txt"));
        al.add(new FileInputStream("4.txt"));
        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(al)));
        int x;
        while((x = in.read()) != -1) System.out.print((char) x);
        in.close();
    }
    public static void readFileByPage() throws IOException{
        final int PAGE_SIZE = 1800;
        RandomAccessFile randomAccessFile = new RandomAccessFile("5.txt", "rw");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter page: ");
        int page = scanner.nextInt() - 1;
        randomAccessFile.seek(page*PAGE_SIZE);
        byte[] bytes = new byte[PAGE_SIZE];
        randomAccessFile.read(bytes);
        System.out.println(new String(bytes));
        randomAccessFile.close();
    }

}
