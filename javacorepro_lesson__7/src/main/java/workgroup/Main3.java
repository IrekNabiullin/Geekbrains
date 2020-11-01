package workgroup;

import java.lang.reflect.Method;

/**
 * example
 */
public class Main3 {
    public static void main(String[] args) throws Exception{
        Class c = Cat.class;
        Cat cat = new Cat();
        Method m = c.getDeclaredMethod("meow");
        m.invoke(null);
        Method m2 = c.getDeclaredMethod("info");
        m2.invoke(cat);
    }

}
