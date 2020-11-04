// Задание 2

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Array2Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        System.out.println("Parametrized test");
        return Arrays.asList(new Object[][]{
                {new int []{1, 1, 4}, true},
                {new int []{1, 1, 4, 4},true},
                {new int []{1, 2, 3, 4, 5}, false},
                {new int []{1, 1, 1}, false},
                {new int []{4, 4, 4}, false},
        });
    }

    private int[] arr;
    private boolean result;

    public Array2Test(int[] arr, boolean result) {
        this.arr = arr;
        this.result = result;
    }

    Array2 arr2 = new Array2();

    @Test
    public void task2Test() {
        Assert.assertEquals(result, arr2.task2(arr));
    }
}

