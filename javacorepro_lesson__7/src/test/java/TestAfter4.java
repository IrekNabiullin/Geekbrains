import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestAfter4 {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {new int[]{1,4,0}, new int[] {0}},
                {new int[]{1,4,4}, new int[0]},
                {new int[]{1,3,4,5,6,4,7,8,9}, new int[]{7,8,9}}
        });
    }

    private int[] arrayIn;
    private int[] arrayOut;
    public TestAfter4(int[] arrayIn, int[] arrayOut){
        this.arrayIn = arrayIn;
        this.arrayOut = arrayOut;
    }
    @Test
    public void test(){
        Assert.assertArrayEquals(arrayOut, Processor.arrayAfter4(arrayIn));
    }
}
