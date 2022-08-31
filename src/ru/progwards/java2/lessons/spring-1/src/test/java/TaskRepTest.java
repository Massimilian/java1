import TaskRepository.SimpleTaskRepository;
import TaskRepository.Task;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskRepTest {
Task task = new Task("-1", "Test task", "Autotest", "Task name", -1);

    @Test
    public void whenTryToAddThenTakeThenDeleteThenDoIt() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        SimpleTaskRepository str = context.getBean("str", SimpleTaskRepository.class);
        str.save(task);
        Task test = str.get("-1");
        Assert.assertTrue(test.getAuthor().equals("Autotest"));
        Assert.assertTrue(test.getName().equals("Task name"));
        Assert.assertTrue(test.getDescription().equals("Test task"));
        Assert.assertEquals(test.getStoryPoint(), -1);
        str.delete("-1");
        Assert.assertTrue(str.get("-1") == null);
    }
}
