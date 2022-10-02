package Calculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean("sc")
    public SimpleCalculator getSimpleCalculator() {
        return new SimpleCalculator();
    }

    @Bean("ac")
    public AdvancedCalculator getAdvancedCalculator() {
        return new AdvancedCalculator();
    }

}
