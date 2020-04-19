/**
 * Java Core. Home work 5. Threads
 *
 * @author Irek Nabiullin
 * @version dated March 14, 2018
 * @link https://github.com/IrekNabiullin
 *
 *
 *  Задание:
1. Необходимо написать два метода, которые делают следующее:
1) Создают одномерный длинный массив
2) Заполняют этот массив единицами;
3) Засекают время выполнения: long a = System.currentTimeMillis();
4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
5) Проверяется время окончания метода System.currentTimeMillis();
6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
Отличие первого метода от второго:
Первый просто бежит по массиву и вычисляет значения.
Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один. *
 */

public class HW05IrekThreads {

    static final int size = 10000000;
    static final int halfSize = size/2;

    public static void main(String[] args) {

        timeCount1();
        timeCount2 ();

    }
    // создаем первый метод
    private static void timeCount1() {

        // заполняем массив единицами
        float [] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        // засекаем время
        long time1 = System.currentTimeMillis();

        //обсчитываем по формуле
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i/5) * (Math.cos(0.4f + i/2)));
        }
        // выводим результат на консоль
        System.out.println ("Method #1 result = " + (System.currentTimeMillis() - time1));
    }

    // создаем второй метод
    private static void timeCount2() {

        // заполняем массив единицами
        float [] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        // засекаем время
        long time2 = System.currentTimeMillis();

        //создаем два массива для половинок
        float [] a1 = new float[halfSize];
        float [] a2 = new float[halfSize];
        float [] a11;
        float [] a22;
        // режем массив на 2 половины
        System.arraycopy (arr,0, a1,0, halfSize); //копируем массив с позиции 0 в массив а1, начиная с позиции 0 до длины halfSize
        System.arraycopy (arr,halfSize, a2,0, halfSize );//копируем массив с позиции halfSize в массив а2, начиная с позиции 0 до длины halfSize

        //запускаем два потока с половинками массива на входе
        a11 = NewThread.arrayCalc(a1);
        a22 = NewThread.arrayCalc(a2);

        // склеиваем две половины в один массив
        System.arraycopy (a11,0, arr,0, halfSize); //копируем массив с позиции 0 в массив а1, начиная с позиции 0 до длины halfSize
        System.arraycopy (a22, 0, arr, halfSize, halfSize );//копируем массив с позиции halfSize в массив а2, начиная с позиции 0 до длины halfSize

        // выводим результат на консоль
        System.out.println ("Method #2 result = " + (System.currentTimeMillis() - time2));
    }
}

public class NewThread implements Runnable {

    Thread t; // поток
    float [] halfArray; // массив на входе

    NewThread (float[] halfArray) {
// создать новый поток
        t = new Thread(this, "New thread");
        this.halfArray  = halfArray ;
        t.start(); // запустить поток исполнения
    }

    // точка входа в поток
    public void run() {
        arrayCalc(halfArray);
    }
    public static float[] arrayCalc(float[] halfArray) {
        for (int i = 0; i < halfArray.length; i++) {
            halfArray[i] = (float) (halfArray[i] * Math.sin(0.2f + i / 5) * (Math.cos(0.4f + i / 2)));
        }

        return halfArray;
    }
}