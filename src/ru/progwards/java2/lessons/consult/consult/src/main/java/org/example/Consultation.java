package org.example;

import java.util.Objects;

public class Consultation {
    public final String mentor;
    public final long start;
    public final long duration;
    public final String student;
    public final String comment;

    public Consultation(String mentor, long start, long duration, String student, String comment) {
        this.mentor = mentor;
        this.start = start;
        this.duration = duration;
        this.student = student;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultation that = (Consultation) o;
        return start == that.start && Objects.equals(mentor, that.mentor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mentor, start);
    }
}
