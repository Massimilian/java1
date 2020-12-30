package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.Objects;

public class ShortUserSession {
    private final String userName;
    private Instant lastAccess;

    public ShortUserSession(UserSession us) {
        this.userName = us.getUserName();
        this.lastAccess = us.getLastAccess();
    }

    public ShortUserSession(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getLastAccess() {
        return lastAccess;
    }

    public void updateLastAccess() {
        this.lastAccess = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortUserSession that = (ShortUserSession) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
