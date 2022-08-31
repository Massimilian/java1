import classes.AccService;
import exceptions.NotEnoughMoneyException;
import exceptions.UnknownAccountException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class AccServiceTest {
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    AccService as = context.getBean(AccService.class);

    @Test
    public void whenTryToStartUsingThenPutTenCardHolders() {
        Assert.assertEquals(as.getAccounts().size(), 10);
    }

    @Test
    public void whenTryToWithdrawThenDoit() throws NotEnoughMoneyException, UnknownAccountException {
        as.withdrow(1, 1000);
        as.balance(1);
    }

    @Test
    public void whenTryToAskImpossibleAmountThenThrowException() {
        try {
            as.withdrow(1, 10000);
        } catch (NotEnoughMoneyException e) {
            String info = e.toString();
            assertEquals(info, "exceptions.NotEnoughMoneyException");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenTryToAskImpossibleIdThenThrowException() {
        try {
            as.balance(100);
        } catch (UnknownAccountException e) {
            String info = e.toString();
            assertEquals(info, "exceptions.UnknownAccountException");
        }
    }
}
