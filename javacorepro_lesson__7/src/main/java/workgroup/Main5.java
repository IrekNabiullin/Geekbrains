package workgroup;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;

public class Main5 {
    public static void main(String[] args) throws Exception{
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("Russia", "Moscow");
        hm.put("Germany", "Berlin");
        hm.put("France", "Paris");

        HashMap<Cat, String> hm2 = new HashMap<>();
        hm2.put(new Cat("Murzik"), "Number1");
        hm2.put(new Cat("Pushistik"), "Number2");
        hm2.put(new Cat("Kotik"), "Number3");
        hm2.put(new Cat("Barsik"), "Number4");

        Class chm = HashMap.class;
        Field field = chm.getDeclaredField("table");
        field.setAccessible(true);
        Object[] array = (Object[]) field.get(hm2);
        System.out.println(array.length);
        for(Object o : array){
            if(o != null) System.out.println(o.getClass());
            if(o instanceof LinkedList){
                LinkedList list = (LinkedList) o;
                for(Object o1 : list){
                    System.out.println(o1 + ", ");
                }
            }
            System.out.print(o + ", ");
        }
    }
}
