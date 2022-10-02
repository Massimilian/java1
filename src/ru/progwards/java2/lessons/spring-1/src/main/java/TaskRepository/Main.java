package TaskRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        TaskRepositoryAdmin tra = context.getBean("tra", TaskRepositoryAdmin.class);
        boolean toCont;
        do {
            toCont = tra.work();
        } while(toCont);
        ((ConfigurableApplicationContext)context).registerShutdownHook();
        System.out.println("Thank you for using the program!");
    }
}
