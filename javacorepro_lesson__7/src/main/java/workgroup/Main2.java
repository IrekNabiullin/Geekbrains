package workgroup;

import java.lang.reflect.Field;
/**
 * example
 */
public class Main2 {
    public static void main(String[] args) throws Exception{
        Class c = Cat.class;
        Cat cat = new Cat();
        Field f = c.getField("age");
        Field f2 = c.getDeclaredField("color");
        f2.setAccessible(true);
        f2.set(cat, "black");
        System.out.println(f.get(cat));
        System.out.println(f2.get(cat));

        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            System.out.println(field.getType().getSimpleName() + " " + field.getName());
        }

    }
}
