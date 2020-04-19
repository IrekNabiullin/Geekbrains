import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Test1and4 {
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {new int[]{1,3,4,4}, false},
                {new int[]{1,1,1,4,4,4}, true},
                {new int[]{1,1,1,1}, false},
                {new int[]{4,4,4,4}, false}
        });
    }
    private int[] array;
    private boolean result;
    public Test1and4(int[] array, boolean result){
        this.array = array;
        this.result = result;
    }
    @Test
    public void test(){
        Assert.assertEquals(Processor.only1and4(array), result);
    }
}
