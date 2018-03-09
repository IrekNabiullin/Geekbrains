import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    int id;
    String name;
    String phone;


    HashMap<Integer, String> phonesBook = new HashMap<Integer, String>(16, 0.75f);

    void makeList() {
        phonesBook.put(1, "Ivanov");
        phonesBook.put(2, "Petrov");
        phonesBook.put(3, "Sidorov");
        phonesBook.put(4, "Petrov");
        phonesBook.put(5, "Kozlov");
        phonesBook.put(6, "Fedorov");
        phonesBook.put(7, "Semenov");
        phonesBook.put(8, "Potapov");
        phonesBook.put(9, "Ivanov");
        phonesBook.put(10, "Petrov");
        phonesBook.put(11, "Sidorov");
        System.out.println(phonesBook);
    }

    public String get(String s) {
        return s + ": " + phonesBook.get(s);
    }

    public void add(Integer Id, String phone) {
        phonesBook.put(Id, phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof YellowPages)) {
            return false;
        }
        return false;
        /*
        YellowPages phonesEntry = (YellowPages) obj;
        phones.get(s);
        return id == phones.id
                && (firstName == guest.firstName
                || (firstName != null &&firstName.equals(guest.getFirstName())))                && (lastName == guest.lastName
                || (lastName != null && lastName .equals(guest.getLastName())
        )); */
    }


    @Override
    public int hashCode() {
        final int prime = 37; // берем одно из простых чисисел
        int result = 1;
        result = prime * result + ((phonesBook.get(this) == null) ? 0 : phonesBook.get(this).hashCode());
//        result = prime * result + id;
        result = prime * result +
                ((phonesBook.get(this) == null) ? 0 : phonesBook.get(this).hashCode());
        return result;
    }


//        hCode = phones.hashCode();

/*        System.out.println(hCode);
        return hCode;*/

//        return result;
    //       }



/*      String s=null;
        result = (int) phones.get(s);
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id; result = prime * result +
                ((lastName == null) ? 0 : lastName.hashCode());
        return result;
        */




    void printBook() {
        System.out.println("All phone book:");
        Set<Map.Entry<Integer, String>> set = phonesBook.entrySet();
        for (Map.Entry<Integer, String> o : set) {
            System.out.print(o.getKey() + ": ");
            System.out.println(o.getValue());
        }

//        System.out.println(phones);
    }

}
