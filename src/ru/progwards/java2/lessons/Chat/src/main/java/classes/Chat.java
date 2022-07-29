package classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Chat {
    private static String placeKeeper = "chat.txt";
    ArrayList<User> users = new ArrayList<>();
    Text text = new Text();

    private Chat() {
    }

    public String addUser(String name, String password, String repeat, String color, boolean fromRenovate) {
        String result = "Новый пользователь добавлен!";
        if (!password.equals(repeat)) {
            result = "Вы не смогли повторить пароль";
        } else {
            if (name.equals("") || password.equals("")) {
                result = "Вы не указали все сведения";
            } else {
                User user = new User(name, password, color);
                if (users.contains(user) && !fromRenovate) {
                    result = "Такой пользователь уже существует";
                } else {
                    if (fromRenovate) {
                        users.remove(user);
                    }
                    users.add(user);
                }
            }
        }
        return result;
    }

    public User getUser(String name) {
        User result = null;
        for (int i = 0; i < users.size(); i++) {
            User temp = users.get(i);
            if (temp.getName().equals(name)) {
                result = temp;
                break;
            }
        }
        return result;
    }

    public static Chat getInstance() {
        Chat chat = null;
        Path path = Paths.get(placeKeeper);
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                Type type = new TypeToken<Chat>() {
                }.getType();
                chat = new Gson().fromJson(json, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            chat = new Chat();
        }
        return chat;
    }

    public static void writeAll(Chat chat) {
        synchronized (chat) {
            String json = new Gson().toJson(chat);
            Path path = Paths.get(placeKeeper);
            if (!Files.exists(path)) {
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.writeString(path, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String renovateUser(String name, String password, String repeat) {
        String result;
        User temp = this.getUser(name);
        if (temp == null) {
            result = "Пользователя с таким именем не существует!";
        } else if (temp.getPassword().equals(password)) {
            result = "Этот пароль себя скомпроментировал, введите новый.";
        } else {
            result = this.addUser(name, password, repeat, temp.getColor(), true);
        }
        if (result.equals("Новый пользователь добавлен!")) {
            result = "Данные успешно обновлены!";
        }
        return result;
    }

    public String checkUser(String name, String password) {
        String result = null;
        if (name.equals("")) {
            result = "Вы не ввели имя.";
        } else if (password.equals("")) {
            result = "Вы не ввели пароль.";
        } else {
            User temp = this.getUser(name);
            if (!users.contains(temp)) {
                result = "Такого пользователя не существует";
            } else if (!temp.getPassword().equals(password)) {
                result = "Неверный пароль!";
            }
        }
        return result;
    }
}
