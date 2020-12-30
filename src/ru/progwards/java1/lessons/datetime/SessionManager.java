package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class SessionManager {
    private final HashMap<Integer, ShortUserSession> sessions = new HashMap<>();
    private final int sessionValid;

    public SessionManager(int sessionValid) {
        this.sessionValid = sessionValid;
    }

    /**
     * Method to add UserSession, if it number isn't in Map
     *
     * @param userSession for add
     */
    public void add(UserSession userSession) {
        while (sessions.containsKey(userSession.getSessionHandle())) {
            userSession.renovateSessionHandle();
        }
        sessions.put(userSession.getSessionHandle(), new ShortUserSession(userSession));
    }

    /**
     * Method for find UserSession by name
     *
     * @param userName for find
     * @return UserSession with that name
     */
    public UserSession find(String userName) {
        UserSession userSession = null;
        for (Map.Entry<Integer, ShortUserSession> entry : sessions.entrySet()) {
            if (entry.getValue().equals(new ShortUserSession(userName)) && isValid(entry.getValue())) {
                userSession = new UserSession(entry.getKey(), entry.getValue());
                userSession.updateLastAccess();
                break;
            }
        }
        return userSession;
    }

    /**
     * Method for find UserSession by handle
     *
     * @param sessionHandle for session
     * @return found session
     */
    public UserSession get(int sessionHandle) {
        UserSession userSession = null;
        if (sessions.get(sessionHandle) != null && isValid(sessions.get(sessionHandle))) {
            userSession = new UserSession(sessionHandle, sessions.get(sessionHandle));
            userSession.updateLastAccess();
        }
        return userSession;
    }

    /**
     * Method for delete session
     *
     * @param sessionHandle for delete
     */
    public void delete(int sessionHandle) {
        sessions.remove(sessionHandle);
    }

    /**
     * Method for delete all unvalid sessions
     */
    public void deleteExpired() {
        Iterator<Map.Entry<Integer, ShortUserSession>> iterator = sessions.entrySet().iterator();
        ArrayList<Integer> nums = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ShortUserSession> entry = iterator.next();
            if (!isValid(entry.getValue())) {
                nums.add(entry.getKey());
            }
        }
        for (Integer num : nums) {
            delete(num);
        }
    }

    /**
     * Method for check validation of current session
     *
     * @param value is session for check
     * @return session is Valid
     */
    private boolean isValid(ShortUserSession value) {
        return value.getLastAccess().plusSeconds(sessionValid).isAfter(Instant.now());
    }

    public static void main(String[] args) throws InterruptedException {
        // №1
        SessionManager sm = new SessionManager(1);
        String name = "User";
        UserSession us = new UserSession(name);
        assert sm.find(name) == null;
        int handle = us.getSessionHandle();
        sm.add(us);
        // №2
        assert sm.get(handle) != null;
        assert sm.get(handle).getSessionHandle() == us.getSessionHandle();
        assert sm.get(handle).getUserName().equals(us.getUserName());
        // №3
        Thread.sleep(1000);
        // №4
        assert sm.get(handle) == null;
        // №5
        us = new UserSession(name);
        sm.add(us);
        // №6
        Thread.sleep(500);
        // №7
        UserSession usOne = new UserSession(name + "-1");
        handle = usOne.getSessionHandle();
        sm.add(usOne);
        // №8
        Thread.sleep(500);
        // №9
        sm.deleteExpired();
        // №10
        assert sm.find(name) == null;
        assert sm.find(name + "-1").getUserName().equals(name + "-1");
        // №11
        sm.delete(handle);
        // №12
        assert sm.find(name + "-1") == null;
    }
}
