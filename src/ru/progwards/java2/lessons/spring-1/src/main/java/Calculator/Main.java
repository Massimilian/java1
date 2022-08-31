package Calculator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICalculator scalc = context.getBean("sc", SimpleCalculator.class);
        ICalculator acalc = context.getBean("ac", AdvancedCalculator.class);
        UserWorker uw = new UserWorker(acalc);
        boolean cont = false;
        do {
            cont = uw.work();
        } while (cont);
    }
}
