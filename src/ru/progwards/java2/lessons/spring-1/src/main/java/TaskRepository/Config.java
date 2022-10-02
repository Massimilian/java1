package TaskRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean("tra")
    public TaskRepositoryAdmin getTaskRepositoryAdmin() {
       return new TaskRepositoryAdmin();
    }

    @Bean ("str")
    public SimpleTaskRepository getSimpleTaskRepository() {
        return new SimpleTaskRepository();
    }

    @Bean("task")
    public Task getTask() {
        return new Task();
    }
}
