/**
 * Java 1. Home work 4.
 *
 * @author Irek Nabiullin
 * @version dated Feb 09, 2018
 */


import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class IrekXOXgame {
    public static int SIZE = 3;
    public static int DOTS_TO_WIN = 3;
    public static final char DOT_EMPTY = '-';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char [][] map;
    public static int [][] mapWin; // дубликат игрового поля для усовершенстованной проверки выигрыша и для тестирования программы
    public static Scanner sc = new Scanner (System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {

        SIZE = getNumberFromScanner("Input map size from 3 till 9", 3, 9); // "Введите размер поля от 3 до 9"
        System.out.println("SIZE = " + SIZE);
        DOTS_TO_WIN = getNumberFromScanner("Input row length to win (3-5)", 3, 5); //"Введите длину ряда для достижения победы от 3 до 5"

        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Yoy win!"); //"Вы победили"
                break;
            }
            if (isMapFull()) {
                System.out.println("Dead heat");//"Ничья"
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Computer wins");//"Компьютер победил"
                break;
            }
            if (isMapFull()) {
                System.out.println("Dead heat");//"Ничья"
                break;
            }
        }
        System.out.println("Game over");//"Игра закончена"
    }

    public static int getNumberFromScanner (String message, int minSize, int maxSize) {
        int x;
        do {
            System.out.println(message);
            x = sc.nextInt();
        } while (x < minSize || x > maxSize);
        return x;
    }

    /* Пишем метод, подставляющий 1 в ячейки, заполненные проверяемым символом, и проверяющий сумму  в соседних ячейках
     * путем перебора всех ячеек в цикле, сначала по колонкам, затем по рядам, затем по нисходящим диагоналям,
     * затем по восходящим диагоналям. Метод работает в зависимости от заданного размера поля и заданной длины выигрышной
     * последовательности символов и возвращает true в случае совпадения суммы длине выигрышной последовательности.
     */
    public static boolean checkWin(char symb) {

        int checkSumDots = 0; // Переменная для подсчета суммы в последовательности соседних ячеек.

        /* используем дополнительный массив,
         * при каждой проверке сначала заполняем его нулями,
         * затем заполняем его единицами в ячейках, содержащих проверяемый  символ
         */
        mapWin = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++ ) {
           for (int j = 0; j < SIZE; j++) {
                mapWin[i][j] = 0;
           }
        }
        /* Кстати, этот дополнительный массив можно использовать для быстрого тестирования работоспособности нашей программы
         * заполняя разные варианты mapWin мы увидим как работает проверка выигрыша chekWin и алгоритм искусственного интеллекта
         *
         */


        for (int i = 0; i < SIZE; i++ ) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == symb) { // если ячейка содержит проверяемый символ
                    mapWin[i][j] = 1;    // присваиваем ей единицу
                }
            }
        }

        /*
         * перебираем по строкам поля:
         */
        for (int i = 0; i <SIZE; i++ ) {
            for (int k = 0; k < SIZE; k++) { // создаем цикл для отсчета начала проверяемой последовательности ячеек
                checkSumDots = 0;            // сбрасываем счетчик суммы перед каждым проходом
                try {                        // применяем try для отсечки выхода за границу массива
                    for (int j = k; j < k + DOTS_TO_WIN; j++) { // "отодвигаем" первый элемент проверяемой последовательности на 1 шаг
                        checkSumDots += mapWin[i][j];           // если в ячейке единица, то она увеличит сумму проверяемой последовательности
//                        System.out.println("checkSumDots [" + i + "][" + j + "] = " + checkSumDots); // раскомментировать для отладки
                        if (checkSumDots == DOTS_TO_WIN || checkSumDots >= DOTS_TO_WIN) // при достижении суммы необходимой длины в проверяемой последовательности
                            return true;                                                // возвращаем true
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {   // в случае выхода за границу массива
                    continue;                                   // начинаем следующий шаг цикла
                }
            }
        }

        /*
         * перебираем по строкам поля:
         */
        for (int j = 0; j <SIZE; j++ ) {
            for (int k = 0; k < SIZE; k++) {
                checkSumDots = 0;
                try {
                    for (int i = k; i < k + DOTS_TO_WIN; i++) {
                        checkSumDots += mapWin[i][j];
//                        System.out.println("checkSumDots [" + i + "][" + j + "] = " + checkSumDots); // раскомментировать для отладки
                        if (checkSumDots == DOTS_TO_WIN || checkSumDots >= DOTS_TO_WIN)
                            return true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }

        /*
         * перебираем по нисходящим диагоналям вправо:
         */
        for (int m = 0; m <SIZE; m++ ) {     // вводим дополнительный счетчик для сдвига по диагонали
            for (int k = m; k < SIZE; k++) {
                checkSumDots = 0;
                try {
                    for (int i = k, j = m; i < k + DOTS_TO_WIN; i++, j++) { // "отодвигаем" первый элемент проверяемой последовательности на 1 шаг вправо и перебираем диагональ
                        checkSumDots += mapWin[i][j];
//                        System.out.println("checkSumDots [" + i + "][" + j + "] = " + checkSumDots); // раскомментировать для отладки
                        if (checkSumDots == DOTS_TO_WIN || checkSumDots >= DOTS_TO_WIN)
                            return true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }

        /*
         * перебираем по нисходящим диагоналям вниз:
         */
        for (int m = 0; m <SIZE; m++ ) {
            for (int k = m; k < SIZE; k++) {
                checkSumDots = 0;
                try {
                    for (int i = m, j = k; i < m + DOTS_TO_WIN; i++, j++) { // меняем местами координаты "отодвигаемого" элемента
                        checkSumDots += mapWin[i][j];
//                        System.out.println("checkSumDots [" + i + "][" + j + "] = " + checkSumDots); // раскомментировать для отладки
                        if (checkSumDots == DOTS_TO_WIN || checkSumDots >= DOTS_TO_WIN)
                            return true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }

        /*
         * перебираем по восходящим диагоналям вверх:
         */
        int size = SIZE; // вводим переменную для обратного отсчета размера поля
        for (int m = 0; m <SIZE; m++ ) {
            for (int k = size; k > 0; k--) {
                checkSumDots = 0;
                try {
                    for (int i = m, j = k; i < m + DOTS_TO_WIN; i++, j--) { // вводим отрицательный счетчик для восходящей диагонали
                        checkSumDots += mapWin[i][j];
//                        System.out.println("checkSumDots [" + i + "][" + j + "] = " + checkSumDots); // раскомментировать для отладки
                        if (checkSumDots == DOTS_TO_WIN || checkSumDots >= DOTS_TO_WIN)
                            return true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }
//        if (symb == 'X') System.out.println("Test winMap = " + Arrays.deepToString(mapWin));// раскомментировать для получения готового массива для тестирования


        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY)
                    return false;
            }
        }
        return true;
    }

    /*
     * доработаем искусственный интеллект и будем ставить 'O' в ячеках с координатами, которые имеют максимум
     * среди последовательности ячеек из метода checkWin. Для этого рассчитываем выигрышный вариант со стороны человека
     * и на пересечении ячеек с максимальным значением checkSumDots ставим 'O'
     */
    public static void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Computer pass point " + (x + 1) + " " + (y + 1)); //"Компьютер проходил точку "
        map[y][x] = DOT_O;
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Input point coordinates in format X Y"); //"Введите координаты в формате X Y"
            while (!sc.hasNextInt()) {
                System.out.println("Input integer for X:"); //"Введите целое число для координаты X:"
                sc.next();
            }
            x = sc.nextInt() - 1;
            while (!sc.hasNextInt()) {
                System.out.println("Input integer for Y:"); //"Введите целое число для координаты Y:"
                sc.next();
            }
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y)); //while (isCellValid(x,y) = false
        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int x, int y) {
		if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return false;		 
		if (map[y][x] == DOT_EMPTY)
            return true;
		return false;
	}
    

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }



    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Методика тестирования программы.
     * Для быстрого тестирования можно проверить несколько  вариантов mapWin, заранее заполненные нулями и единицами,
     * содержащими выигрышные последовательности и позиции, в котрых чаще всего могут возникать ошибки.
     * Массив mapWin делаем размером не более SIZE и с разными DOTS_TO_WIN - длиной выигрышной последовательности.
     * Больше всего ошибок при разработке алгоритма игры мною было сделано при проверке диагональных последовательностей,
     * поэтому в тестовом массиве задаем разные варианты диагоналей, включая "квадраты" с площадью DOTS_TO_WIN.
     * Например на поле размером 5X5 и длиной отрезка 3, задаем выигрышные координаты
     * [4][1] [5][1].
     * [4][2] [5][2]
     *
     *
     * Также задаем диагональные последовательности с началом диагонали менее , чем длина DOTS_TO_WIN до "края" поля,
     * например на поле размером 5X5 и длиной отрезка 3, задаем координаты [4][1] [5][2].
     *
     * Также проверяем, не выдаст ли программа ошибочного выигрыша, если есть "пробелы в нашем отрезке.
     *
     * Проверим на разную последовательность DOTS_TO_WIN. Например, что будет, если задать DOTS_TO_WIN=4
     *
     * Ниже представлены несколько вариантов mapWin
     * winMap = [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 1, 1], [0, 0, 0, 1, 1]]
     * winMap = [[0, 0, 0, 0, 1, 0], [0, 0, 0, 1, 0, 0], [0, 0, 1, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]]
     * winMap = [[0, 0, 0, 1, 0, 0], [0, 0, 0, 1, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 1, 0, 0], [0, 0, 0, 1, 0, 0], [0, 0, 0, 0, 0, 0]]
     *
     * Автоматически создать массивы для тестирования можно раскомментировав строку 197 программного кода
     */
}
