import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Test {
    float value() default 5.0f;
}

class Tests {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("1.BeforeSuite method executed...");
    }

    @Test (value = 8.0f)
    public void runTest8() {
        int priority = 8;
        System.out.println("Test with priority " + priority + " runs...");
    }

    @Test (value = 10.0f)
    public void runTest10() {
        int priority = 10;
        System.out.println("Test with priority " + priority + " runs...");
    }


    @Test
    public void runTest5() {
        int priority = 5;
        System.out.println("Test with priority " + priority + " runs...");
    }

    @Test (value = 3.0f)
    public void runTest3() {
        int priority = 3;
        System.out.println("Test with priority " + priority + " runs...");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("2.AfterSuite method executed...");
    }

}


