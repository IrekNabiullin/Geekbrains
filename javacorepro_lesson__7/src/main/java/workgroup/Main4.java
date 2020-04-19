package workgroup;

import java.lang.reflect.Constructor;

public class Main4 {
    public static void main(String[] args) throws Exception{
        Class c = Cat.class;
        Cat cat = new Cat();
        Constructor cn = c.getConstructor();
        Cat cat1 = (Cat) cn.newInstance();
    }
}
