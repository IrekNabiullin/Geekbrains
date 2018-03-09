/**
 * Java Core. Home work 3. Collections
 *
 * @author Irek Nabiullin
 * @version dated March 05, 2018
 * @link https://github.com/IrekNabiullin/Geekbrains
 * Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и
 * вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать, сколько раз встречается каждое слово.
 * 2 Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и
 * телефонных номеров. В этот телефонный справочник с помощью метода add() можно
 * добавлять записи, а с помощью метода get() искать номер телефона по фамилии. Следует
 * учесть, что под одной фамилией может быть несколько телефонов (в случае
 * однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
 * Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в телефонную
 * запись добавлять еще дополнительные поля (имя, отчество, адрес), делать взаимодействие с
 * пользователем через консоль и т.д. Консоль желательно не использовать (в том числе Scanner),
 * тестировать просто из метода main(), прописывая add() и get(). *
 *
 */

import java.util.*;

public class J3ArraysIrekHW03 {

    public static void main(String[] args) {
        //Task#1
        String[] colorArray = {"Red", "Blue", "Green", "Silver", "Orange", "Black", "White", "Green", "Gold", "Olive", "Gold", "Silver", "Brown", "Black", "Gold"}; //задаем массив
        HashSet<String> colorSet = new HashSet<String>();       // для того чтобы в коллекции остались только уникальные значения
        Collections.addAll(colorSet, colorArray);               // преобразуем массив в Set
        System.out.println("Уникальные значения: " + colorSet); // выводим уникальные значения

        int k = 0;                                              // задаем переменную для подсчета повторяемости слов
        Object[] colorObjArr = colorSet.toArray();              // преобразуем Set в массив объектов для сравнения

        for (int i = 0; i < colorObjArr.length; i++) {          // пробегаем циклом по каждому значению
            for (int j = 0; j < colorArray.length; j++) {
                if (colorObjArr[i].equals(colorArray[j])) {
                    k++;
                }
            }
            System.out.print(colorObjArr[i]);
            System.out.println(" встречается " + k + " раз(а)");   // выводим на консоль количество сколько раз встречается каждое слово
            k = 0;
        }

        // Task#2

        YellowPages pages = new YellowPages();
        pages.makeList();
        pages.add("Smirnov", "8(999)333-00-11");
        pages.add("Petrov", "8(999)333-00-22");
        pages.get("Petrov");
        pages.get("Sergeev");
        pages.get("Ivanov");
    }
}

