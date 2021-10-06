package lessons.datetime;

import java.time.Instant;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private final String userName;
    private Instant lastAccess;

    public UserSession(Integer key, ShortUserSession value) {
        this.sessionHandle = key;
        this.userName = value.getUserName();
        this.lastAccess = Instant.now();
    }

    public UserSession(String userName) {
        this.userName = userName;
        this.sessionHandle = new Random().nextInt();
        this.lastAccess = Instant.now();
    }

    public int getSessionHandle() {
        return sessionHandle;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getLastAccess() {
        return lastAccess;
    }

    public void renovateSessionHandle() {
        this.sessionHandle = new Random().nextInt();
    }

    public void updateLastAccess() {
        this.lastAccess = Instant.now();
    }
}
