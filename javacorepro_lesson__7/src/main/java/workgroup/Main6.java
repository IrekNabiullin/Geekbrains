package workgroup;

import java.lang.reflect.Method;

/**
 * example
 */
public class Main6 {
    public static void main(String[] args) throws Exception{
        Class c = Cat.class;
        Cat cat = new Cat();
        Method[] methods = c.getDeclaredMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(MyAnno.class)){
                System.out.println("#prio " + method.getAnnotation(MyAnno.class).prio());
                method.invoke(cat);
            }
        }
    }
}
