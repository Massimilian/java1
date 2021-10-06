package lessons.datetime;

import java.time.Instant;
import java.util.Objects;

public class StatisticInfo {
    public String sectionName;
    public int fullTime;
    public int selfTime;
    public int count;
    public Instant start;
    public boolean open;

    public StatisticInfo(String sectionName) {
        this.sectionName = sectionName;
        this.start = Instant.now();
        this.selfTime = 0;
        this.open = true;
        this.count = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticInfo that = (StatisticInfo) o;
        return Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionName);
    }
}
