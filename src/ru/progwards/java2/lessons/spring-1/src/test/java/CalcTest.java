import Calculator.AdvancedCalculator;
import Calculator.ICalculator;
import Calculator.SimpleCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcTest {

    @Test
    public void whenTryToUseSimpleCalcThenWorksGood() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICalculator scalc = context.getBean("sc", SimpleCalculator.class);
        Assert.assertEquals(scalc.sum(2, 2), 4);
        Assert.assertEquals(scalc.diff(2, 2), 0);
        Assert.assertEquals(scalc.mult(2, 2), 4);
        Assert.assertEquals(scalc.div(2, 2), 1);
    }

    @Test
    public void whenTryToUseAdcamsedCalcThenWorksGood() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICalculator acalc = context.getBean("ac", AdvancedCalculator.class);
        Assert.assertEquals(acalc.sum(2, 2), 4);
        Assert.assertEquals(acalc.diff(2, 2), 0);
        Assert.assertEquals(acalc.mult(2, 2), 4);
        Assert.assertEquals(acalc.div(2, 2), 1);

    }

    @Test(expected = RuntimeException.class)
    public void whenTryToMakeZeroDivideExceptionThenDoIt() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICalculator acalc = context.getBean("ac", AdvancedCalculator.class);
        acalc.div(1, 0);
    }

    @Test(expected = RuntimeException.class)
    public void whenTryToMakeOverflowExceptionThenDoIt() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICalculator acalc = context.getBean("ac", AdvancedCalculator.class);
        acalc.mult(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
