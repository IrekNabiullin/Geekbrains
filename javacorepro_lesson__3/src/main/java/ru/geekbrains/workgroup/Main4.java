package ru.geekbrains.workgroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Main4 {
    public static void main(String[] args) throws Exception{
        FileInputStream in1 = new FileInputStream(new File("1.txt"));
        FileInputStream in2 = new FileInputStream(new File("files/3.txt"));
        ArrayList<InputStream> arrayList = new ArrayList<>();
        arrayList.add(in1);
        arrayList.add(in2);
        Enumeration<InputStream> e = Collections.enumeration(arrayList);
//        SequenceInputStream sq = new SequenceInputStream(in1, in2);
        SequenceInputStream sq = new SequenceInputStream(e);
        int x;
        while((x = sq.read()) != -1 ) System.out.print((char) x);
        sq.close();
//        DataInputStream in3 = new DataInputStream(new FileInputStream("1.txt"));
//        System.out.println(in3.readUTF());
//        while((x = in3.readUTF()) != -1 ) System.out.print((char) x);
//        in3.close();
    }
}

