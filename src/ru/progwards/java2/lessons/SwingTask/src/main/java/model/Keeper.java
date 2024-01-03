package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class Keeper {
    private ArrayList<Task> tasks;

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String json = null;
        try {
            json = Files.readString(Path.of("src/main/resources/tasks.json"));
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            tasks = new Gson().fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public long getLastId() {
        long result = 0;
        Task nullable = new Task(null, LocalDateTime.now());
        nullable.setId(-1);
        if (!tasks.isEmpty()) {
            result = tasks.stream().reduce(nullable, (x, a) -> a.getId() < x.getId() ? x : a).getId();
        }
        return result;
    }

    private void save() {
        String json = new Gson().toJson(tasks);
        try {
            Files.writeString(Path.of("src/main/resources/tasks.json"), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean create(String info, LocalDateTime date) {
        boolean created = false;
        if (date.isAfter(LocalDateTime.now())) {
            Task task = new Task(info, date);
            tasks.add(task);
            this.save();
            created = true;
        }
        return created;
    }

    public Task getById(long id) {
        return tasks.stream().filter(i -> id == i.getId()).findFirst().get();
    }

    public boolean deleteById(long id) {
        return tasks.remove(this.getById(id));
    }
}
