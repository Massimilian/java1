package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserList {
    static HashMap<String, String> users = new HashMap<>();

    public static void addNew(String name, String password) {
        users.put(name, password);
    }

    public static String getList() {
        String result = "*****" + System.lineSeparator();
        for (Map.Entry<String, String> entry : users.entrySet()) {
            result += entry.getKey() + " - " + entry.getValue() + System.lineSeparator();
        }
        result += "*****";
        return result;
    }

    public static boolean checkUser(String name, String password) {
        return !name.isEmpty() && !password.isEmpty() && users.containsKey(name) && users.get(name).equals(password);
    }
}
