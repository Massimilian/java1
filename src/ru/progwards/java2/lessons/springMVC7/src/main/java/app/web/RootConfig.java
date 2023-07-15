package app.web;

import app.services.TaskService;
import app.services.TesterBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public TesterBean getTesterBean() {
        return new TesterBean();
    }

    @Bean
    public TaskService getTaskService() {
        return new TaskService();
    }
}
