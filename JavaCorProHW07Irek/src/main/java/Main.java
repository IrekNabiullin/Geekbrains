import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Tests test = new Tests();
        try{
            start (test);
        } catch (InvocationTargetException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static void start (Tests test) throws InvocationTargetException, IllegalAccessException {
        Class c = test.getClass ();
        Method[] methods = c.getDeclaredMethods();
        ArrayList<Method> list = new ArrayList<Method>();

        Method before = null;
        Method after = null;

        for(Method m : methods) {
            if(m.isAnnotationPresent(BeforeSuite.class)) {
                if(before != null) throw new RuntimeException("BeforeSuite");
                before = m;
            }
            else if (m.isAnnotationPresent(AfterSuite.class)) {
                if(after != null) throw new RuntimeException("AfterSuite");
                after = m;
            }
        }

    list.sort((o1, o2) -> o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Tests.class).priority());

        if (before != null) {
            before.invoke(test);
        }

        for (Method m: list) {
            try {
                m.invoke(test);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (after != null) {
            after.invoke(test);
        }

    }

}
