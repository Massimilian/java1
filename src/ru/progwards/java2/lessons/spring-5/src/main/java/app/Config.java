package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.calculator", name = "type")
public class Config {
    @Value("${spring.user.type}")
    String type;

    @Bean
    public Calculator getCalculator() {
        if (type.equals("advanced")) {
            return new AdvancedCalculator();
        } else {
            return new SimpleCalculator();
        }
    }
}
