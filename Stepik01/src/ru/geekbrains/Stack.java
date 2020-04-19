package ru.geekbrains;

public class Stack {
    int leng;
    int tos;
    int stck[];

    public Stack(int leng){
        this.leng = leng;
        System.out.println ("leng = "+ leng);
        tos = -1;
        stck = new int [leng];
    }


    // push an element to the stack
    void push (int item) {
        if (tos == leng) {
            System.out.println ("Stack is full");
        }
        else {
            stck[++tos] = item;
            System.out.println("stck [tos] = " + tos);
            System.out.println ("item = " + item);
        }
    }

    // pop an element from the stack
    int pop () {
        if (tos <0) {
            System.out.println ("Stack is empty");
            return 0;
        }
        else {
            return stck [tos--];
        }
    }
}
