package app.services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// этот элемент будет Bean-ом
@Component
public class TaskService {
    // собственно хранилище
    private static List<Task> store = new ArrayList<>();

    // изначальные значения по умолчанию
    static {
        store.add(new Task(1L, "Создание обращения", new Date(), PriorityType.CRITICAL, Type.TASK));
        store.add(new Task(2L, "Исправить обращения", new Date(), PriorityType.BLOCKER, Type.BUG));
        store.add(new Task(3L, "Выполнить задание 3", new Date(), PriorityType.MINOR, Type.TASK));
        store.add(new Task(4L, "Выполнить задание 4", new Date(), PriorityType.MAJOR, Type.TASK));
    }

    public void add(Task task) {
        store.add(task);
    }

    public void delete(Task task) {
        store.remove(task);
    }

    public void delete(Long id) {
        this.delete(this.get(id));
    }

    public List<Task> getAll() {
        return store;
    }

    public Task get(long id) {
        return store.stream().filter(o -> o.getId() == id).findFirst().get();
    }

    public void update(Task task) {
        Task old = this.get(task.getId());
        this.delete(old);
        this.add(task);
    }

    public int getUnusedId() {
        boolean isUsed = true;
        int result = 1;
        while(isUsed) {
            isUsed = false;
            for (int i = 0; i < store.size(); i++) {
                if (store.get(i).getId() == result) {
                    isUsed = true;
                    result++;
                    break;
                }
            }
        }
        return result;
    }

}
