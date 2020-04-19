import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParamTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {0,1,0},
                {1,1,2},
                {2,3,4},
                {3,3,6},
                {5,5,10},
                {6,7,12},
        });
    }
    private int a;
    private int b;
    private int c;
    Calculator calculator;
    public ParamTest(int a, int b, int c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Before
    public void init(){
        calculator = new Calculator();
    }
    @Test
    public void massTestAdd(){
        Assert.assertEquals(c, calculator.add(a,b));
    }
}
