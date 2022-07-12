package org.example.servlets.lesson20.requestDispatcher;

import java.util.HashMap;

public class LoginPassword {
    static HashMap<String, String> users = new HashMap<>();

    static void addNew(String nickname, String password) {
        users.put(nickname, password);
    }

}
