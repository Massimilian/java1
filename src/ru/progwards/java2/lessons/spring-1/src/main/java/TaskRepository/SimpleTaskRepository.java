package TaskRepository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleTaskRepository implements TaskRepository {

    long maxId;
    String address = "src/main/resources/tasks.txt";
    ArrayList<Task> tasks = new ArrayList<>();

    public void init() throws IOException {
        String json = Files.readString(Path.of(address));
        if (!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Task>>() {
            }.getType();
            this.tasks = new Gson().fromJson(json, type);
        }
        findMaxId();
    }

    private void findMaxId() {
        long id = -1;
        for (int i = 0; i < tasks.size(); i++) {
            long temp = Long.valueOf(tasks.get(i).getId());
            if (id <= temp) {
                id = temp;
            }
        }
        this.maxId = id;
    }

    public void destroy() throws IOException {
        String json = new Gson().toJson(this.tasks);
        Files.writeString(Path.of(address), json);
    }

    @Override
    public void save(Task task) {
        if (task.getId().isEmpty()) {
            task.setId(String.valueOf(++maxId));
        }
        tasks.add(task);
    }

    @Override
    public void update(Task task) {
        String id = task.getId();
        task = this.get(id);
        if (task != null) {
            renovate(task, id);
        }
    }

    private void renovate(Task task, String id) {
        delete(id);
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter new task's name. Current name is '%s'.", task.getName());
        String value = scanner.nextLine();
        task.setName(value.equals("") ? task.getName() : value);
        System.out.printf("Enter new decription. Current description is '%s'.", task.getDescription());
        value = scanner.nextLine();
        task.setDescription(value.equals("") ? task.getDescription() : value);
        System.out.println("Enter your name:");
        value = scanner.nextLine();
        task.setAuthor(value.equals("") ? task.getAuthor() : task.getAuthor().contains(value) ? task.getAuthor() : task.getAuthor() + ", " + value);
        this.save(task);
    }

    @Override
    public void delete(String id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                tasks.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Task> get() {
        return tasks;
    }

    @Override
    public Task get(String id) {
        Task result = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                result = tasks.get(i);
                break;
            }
        }
        return result;
    }
}
