package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class Task {
    private long id;
    private String info;
    private String startDate;
    private String finishDate;

    public Task(String info, LocalDateTime finishDate) {
        this.id = KeeperCreator.TASK_MAX_ID + 1;
        this.info = info;
        this.finishDate = finishDate.toString();
        this.startDate = LocalDateTime.now().toString();

    }


}
