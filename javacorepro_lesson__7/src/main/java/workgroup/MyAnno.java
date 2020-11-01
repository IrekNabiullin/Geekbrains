package workgroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * example
 * Аннотация @Retention позволяет указать, в какой момент жизни программного кода будет доступна аннотация:
 * только в исходном коде, в скомпилированном классе или во время выполнения программы.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnno {
    int prio() default 5;

}
