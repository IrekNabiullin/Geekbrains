import java.util.*;

public class YellowPages {

    // создаем двумерный массив с фамилиями и номерами телефонов
    private String[][] phoneBook = {
            {"Ivanov", "8(999)111-11-00)"},
            {"Petrov", "8(999)111-11-11)"},
            {"Sidorov", "8(999)111-11-22)"},
            {"Petrov", "8(999)111-11-33)"},
            {"Kozlov", "8(999)111-11-44)"},
            {"Fedorov", "8(999)111-11-55)"},
            {"Semenov", "8(999)111-11-66)"},
            {"Potapov", "8(999)111-11-77)"},
            {"Ivanov", "8(999)111-11-88)"},
            {"Petrov", "8(999)111-11-99)"},
            {"Petrov", "8(999)111-22-00)"}};

    // Используем HashMap. Фамилии будем хранить в качестве ключа, а телефоны в виде списка
    HashMap<String, ArrayList> names = new HashMap<String, ArrayList>(16, 0.75f);
    // создадим списки для каждого значения HashMap:
    ArrayList[] arrayLists = new ArrayList[phoneBook.length];

    void makeList() {

        for (int i = 0; i < phoneBook.length; i++) { // в цикле пробегаемся по всему массиву номеров
            arrayLists[i] = new ArrayList();          // создаем списки номеров для каждой фамилии
            arrayLists[i].add(phoneBook[i][1]);       // добавляем номера телефонов в список

            if (!names.containsKey(phoneBook[i][0])) { // если фамилии нет в HashMap
                names.put(phoneBook[i][0], arrayLists[i]); // присваиваем ее в качестве ключа и давляем телефон в список
            } else {                                     // если фамилия уже используется в качестве ключа
                names.get(phoneBook[i][0]).add(arrayLists[i]); // добавляем в список еще один телефон
//                    System.out.println(names.get(phoneBook[i][0] + "::" + arrayLists [i]));
            }
        }

/*        System.out.println("All phone book:"); // для проверки можем распечатать все фамилии. Работает.
        Set<Map.Entry<String, ArrayList>> set = names.entrySet();
        for (Map.Entry<String, ArrayList> o : set) {
            System.out.print(o.getKey() + ": ");
            System.out.println(o.getValue());
        }*/
    }


    public void add(String name, String phone) {
        ArrayList arrayList  = new ArrayList();
            if(names.containsKey(name)) {
                names.get(name).add(phone);
            } else {
                names.put(name, arrayList);
                arrayList.add(phone);
            }
    }

    public void get(String getName) {
        ArrayList arrayListNotFound  = new ArrayList();
        arrayListNotFound.add("Name not found");
        System.out.println(getName + ": "+ names.getOrDefault(getName, arrayListNotFound));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof YellowPages)) {
            return false;
        }

        YellowPages bookEntry = (YellowPages) obj;
        return (names.get(this) != null &&names.get(this).equals(bookEntry));
    }

    @Override
    public int hashCode() {
        final int prime = 37; // берем одно из простых чисел
        int result = 1;
        result = prime * result + ((names.get(this) == null) ? 0 : names.get(this).hashCode());
        result = prime * result +
                ((names.get(this) == null) ? 0 : names.get(this).hashCode());
        return result;
    }
}

