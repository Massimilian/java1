package app.services;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Task {
    private long id;
    private String name;
    private Date created;
    private PriorityType priority;
    private Type type;

    public Task(long id, String name, Date created, PriorityType priority, Type type) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.priority = priority;
        this.type = type;
    }

    public Task() {
    }
}
