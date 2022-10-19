package pack;

import app.Calculator;
import app.Config;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class); // конфигурационный класс подтягивается из нашей зависимости
        System.out.println(context.getBean(Calculator.class).getClass()); // в зависимости от application.properties выведет либо simpleCalculator, либо advancedCalculator.
        System.out.println(context.getBean(Calculator.class).sum(5, 5));
    }
}
