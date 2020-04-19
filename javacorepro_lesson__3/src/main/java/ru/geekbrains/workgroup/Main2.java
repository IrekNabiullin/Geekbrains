package ru.geekbrains.workgroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2 {
    public static void main(String[] args){
        try(FileInputStream in =  new FileInputStream("1.txt")){
            int x;
            byte[] bytes = new byte[1024];
//            while((x=in.read()) != -1){ //-1 служебный байт, который указывает, что данных в потоке больше нет
//                System.out.print((char)x);
//            }
            String str;
            while(in.read(bytes) > 0){
                str = new String(bytes);
                System.out.println(str);
            }
        }catch(FileNotFoundException e){

        }catch(IOException e){

        }
        try(FileOutputStream out = new FileOutputStream("files/3.txt")){
            for(int i = 0; i < 10; i++){
                out.write(65);
            }
        }catch(FileNotFoundException e){

        }catch(IOException e){

        }

    }
}
