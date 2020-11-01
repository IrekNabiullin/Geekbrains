import org.junit.Test;

/**
 * test example
 */
public class TestRE {
    @Test(expected = RuntimeException.class)
    public void reTest(){
        Processor.arrayAfter4(new int[]{1,3,2});
    }
}
