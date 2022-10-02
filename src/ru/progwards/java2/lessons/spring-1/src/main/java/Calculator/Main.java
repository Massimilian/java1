package Calculator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ICalculator scalc = context.getBean("sc", SimpleCalculator.class);
        ICalculator acalc = context.getBean("ac", AdvancedCalculator.class);
        UserWorker uw = new UserWorker(acalc);
        boolean cont;
        do {
            cont = uw.work();
        } while (cont);
    }
}
