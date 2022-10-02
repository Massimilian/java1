package TaskRepository;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Task {
    private String id;
    private String description;
    private String author;
    private String name;
    private int storyPoint;

    public Task() {
    }

    public Task(boolean b) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your task's name");
        this.name = scanner.nextLine();
        System.out.println("Enter your decription:");
        this.description = scanner.nextLine();
        System.out.println("Enter your name:");
        this.author = scanner.nextLine();
        System.out.println("Enter your story point:");
        this.setStoryPoint();
    }

    public Task(String id) {
        this.id = id;
    }

    public Task(String id, String description, String author, String name, int storyPoint) {
        this.id = id;
        this.description = description;
        this.author = author;
        this.name = name;
        this.storyPoint = storyPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint() {
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();
        if (!Pattern.matches("[0-9]+", number)) {
            System.out.println("Enter correct story point (number):");
            setStoryPoint();
        } else {
            this.storyPoint = Integer.parseInt(number);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:").append(this.getId()).append(System.lineSeparator());
        sb.append("NAME:").append(this.getName()).append(System.lineSeparator());
        sb.append("DESCRIPTION:").append(this.getDescription()).append(System.lineSeparator());
        sb.append("AUTHOR:").append(this.getAuthor()).append(System.lineSeparator());
        sb.append("STORY POINT:").append(this.getStoryPoint()).append(System.lineSeparator());
        sb.append("*******").append(System.lineSeparator());
        return sb.toString();
    }
}