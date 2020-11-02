import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalcTest {
    Calculator calculator;
    @Before
    public void init(){
        System.out.println("INIT");
        calculator = new Calculator();
    }
    @After
    public void disconnect(){
        System.out.println("DISC");
    }
    @Test
    public void assetTest(){
        String string = "Aa";
        Assert.assertThat(string, new Matcher<String>() {
            public boolean matches(Object o){
                return ((String)o).length() == 1;
            }
            public void describeMismatch(Object o, Description description){
                System.out.println(((String)o).length());
            }
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_(){
            }
            public void describeTo(Description description){
            }
        });
    }


    @Test
    public void addTest(){
//        calculator = new Calculator();
        Assert.assertEquals(8, calculator.add(4,4));
        //так делать не нужно, потому что первый fail в Assert прерывает работу метода
//        Assert.assertEquals(12, calculator.add(5,5));
//        Assert.assertEquals(12, calculator.add(6,6));
//        Assert.assertEquals(14, calculator.add(7,7));
//        Assert.assertEquals(19, calculator.add(8,8));
    }
    @Test
    public void subTest(){
//        calculator = new Calculator();
        Assert.assertEquals(8, calculator.sub(12,4));
    }
    @Test
    public void mulTest(){
//        calculator = new Calculator();
        Assert.assertEquals(16, calculator.mul(8,2));
    }
    @Test
    public void divTest(){
//        calculator = new Calculator();
        Assert.assertEquals(1, calculator.div(8,8));
    }
    @Test(expected = ArithmeticException.class)
    public void divTestBy0(){
        calculator.div(4,0);
    }

    @Test
    public void powTest(){
//        calculator = new Calculator();
        Assert.assertEquals(8, calculator.pow(2,3));
    }
}
