package settings;

import beans.TesterBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public TesterBean getTesterBean() {
        return new TesterBean();
    }
}
