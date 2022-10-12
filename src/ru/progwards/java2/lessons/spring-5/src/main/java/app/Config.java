package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class Config {
    @Value("${spring.calculator.type}")
    public String type;

    @Bean
    public Calculator getCalculator() {
        if (type.equals("simple")) {
            return new SimpleCalculator();
        } else {
            return new AdvancedCalculator();
        }
    }
}
