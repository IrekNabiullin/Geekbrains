package ru.geekbrains;

/* Stepik Task 1-1
/ Вы разрабатываете текстовый редактор для
программистов и хотите реализовать проверку
корректности расстановки скобок. В коде могут
встречаться скобки []{}(). Из них скобки [,{
и ( считаются открывающими, а соответству-
ющими им закрывающими скобками являются
],} и ).
В случае, если скобки расставлены неправильно, редактор должен
также сообщить пользователю первое место, где обнаружена ошибка.
В первую очередь необходимо найти закрывающую скобку, для кото-
рой либо нет соответствующей открывающей (например, скобка ] в
строке “]()”), либо же она закрывает не соответствующую ей откры-
вающую скобку (пример: “()[}”). Если таких ошибок нет, необходи-
мо найти первую открывающую скобку, для которой нет соответству-
ющей закрывающей (пример: скобка ( в строке “{}([]”).
Помимо скобок, исходный код может содержать символы латин-
ского алфавита, цифры и знаки препинания.
Формат входа. Строка s[1 . . . n], состоящая из заглавных и пропис-
ных букв латинского алфавита, цифр, знаков препинания и ско-
бок из множества []{}().
Формат выхода. Если скобки в s расставлены правильно, выведите
строку “Success". В противном случае выведите индекс (исполь-
зуя индексацию с единицы) первой закрывающей скобки, для
которой нет соответствующей открывающей. Если такой нет,
выведите индекс первой открывающей скобки, для которой нет
соответствующей закрывающей.
*/


// Program coded by Irek Nabiullin
// with love to developers' world.

// ***** Hit a like if you enjoy it *****


/*
 * This is example to cut input string
 * into little tokens splitted by regex
 * simbols.
 * You may use it for correct recognition of bithday, etc.
 *
 * @author Irek Nabiullin
 * @version dated May 21, 2018
 * @link on my github:
 * https://github.com/IrekNabiullin
 */

import java.util.Scanner;
import java.io.*;
import java.util.regex.*;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class Main {

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();

//close scaner on exit
            try {
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
// System.exit(0);
            int leng = str.length();
            System.out.println(str);

            System.out.println("length = " + leng);


            Stack mystack1 = new Stack(leng);
            Stack mystack2 = new Stack(leng);


// push elements to stacks
// equals to 40,41, 91, 93, 123, 125

            for (int i=0; i<leng; i++) {
                System.out.println ("i = " + i);
                if (str.charAt(i) == 123){
                    System.out.println ("Elements of stack1:");
                    mystack1.push(str.charAt(i));
                    System.out.println ("Elements of stack2:");
                    mystack2.push(i);
                    System.out.println(str.charAt(i));
                }
                if (str.charAt(i) == 123){
                    System.out.println (mystack1.pop());
                    mystack2.pop();
                }
            }

        }

    }










