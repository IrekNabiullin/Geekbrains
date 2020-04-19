package ru.geekbrains.workgroup;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        File file = new File("1.txt");
        File file2 = new File("2.txt");
        System.out.println(file.exists());
        System.out.println(file2.exists());
        File dir = new File("files");
        System.out.println(Arrays.toString(dir.list()));
        String[] filtered = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name){
                return name.startsWith("3");
            }
        });
        System.out.println(Arrays.toString(filtered));
        file.mkdir();
        File file3 = new File("files/5");
        file3.mkdir();
        File file4 = new File("files/10/17/25/7");
        file4.mkdirs();
    }
}
