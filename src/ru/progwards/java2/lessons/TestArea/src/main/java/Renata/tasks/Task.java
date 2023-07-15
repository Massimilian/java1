package Renata.tasks;

import java.time.LocalDate;

/**
 * Класс задачи
 */
public class Task {
    public int number;
    public LocalDate start;
    public LocalDate finish;
    public String name;
    public String description;

    public Task(LocalDate finish, String name, String description) {
        this.number = TaskKeeper.number++;
        this.start = LocalDate.now();
        this.finish = finish;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task №").append(this.number).append(":").append(System.lineSeparator());
        sb.append("'").append(this.name).append("'").append(System.lineSeparator());
        sb.append(this.description).append(System.lineSeparator());
        sb.append("Start date: ").append(start.toString()).append(System.lineSeparator()); // todo
        sb.append("Finish date: ").append(finish.toString()).append(System.lineSeparator());
        sb.append("*****************").append(System.lineSeparator()).append(System.lineSeparator());
        return sb.toString();
    }
}
