package org.example.servlets.testTask.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class InfoKeeper {
    public static String logPass;
    public static ArrayList<User> users = new ArrayList<>();
    public static String placePass;
    public static ArrayList<Place> places = new ArrayList<>();
    public static Place current;


    public static boolean hasUserName(String newUser) {
        boolean result = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(newUser)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void destroyUsersAndPlaces(ArrayList<?> objects, boolean areUsers) {
        String json = new Gson().toJson(objects);
        Path path;
        if (areUsers) {
            path = Path.of(logPass);
        } else {
            path = Path.of(placePass);
        }
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (areUsers) {
            System.out.println("Users has been written.");
        } else {
            System.out.println("Places has been written.");
        }
    }

    public static void initUsersAndPlaces(String name, boolean areUsers) {
        Path path = Path.of(name);
        if (areUsers) {
            InfoKeeper.logPass = String.valueOf(path.toAbsolutePath());
        } else {
            InfoKeeper.placePass = String.valueOf(path.toAbsolutePath());
        }
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                if (areUsers) {
                    User user = new User("admin", "password", "https://img5.goodfon.ru/wallpaper/nbig/7/3f/akita-inu-sobaka-morda-vzgliad-fon-portret.jpg");
                    InfoKeeper.users.add(user);
                    String json = new Gson().toJson(InfoKeeper.users);
                    Files.writeString(path, json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String json = null;
            try {
                json = areUsers ? Files.readString(Path.of(InfoKeeper.logPass)) : Files.readString(Path.of(InfoKeeper.placePass));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type type = areUsers ? new TypeToken<ArrayList<User>>() {}.getType() : new TypeToken<ArrayList<Place>>() {}.getType();
            if (areUsers) {
                InfoKeeper.users = new Gson().fromJson(json, type);
            } else {
                InfoKeeper.places = new Gson().fromJson(json, type);
                if (InfoKeeper.places == null) {
                    InfoKeeper.places = new ArrayList<>();
                }
            }
        }
    }

    public static Place getPlace(HttpServletRequest req) {
        Place temp = null;
        for (int i = 0; i < places.size(); i++) {
            temp = places.get(i);
            if (req.getParameter(temp.getHeader()) != null && req.getParameter(temp.getHeader()).equals(temp.getHeader())) {
                break;
            }
        }
        return temp;
    }

    public static String voter(Place current, String good, String bad, Object userName) {
        String result;
        if (current.getTheyHaveSaid().contains((String)userName)) {
            result = "Вы уже голосовали за этот проект.";
        } else {
            if (good != null) {
                current.setLikes(InfoKeeper.current.getLikes() + 1);
            }
            if (bad != null) {
                current.setLikes(InfoKeeper.current.getLikes() - 1);
            }
            current.getTheyHaveSaid().add((String)userName);
            result = "Спасибо, Ваш голос учтён!";
        }
        return result;
    }

    public static String getPictureByAuthor(String author) {
        String result = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(author)) {
                result = users.get(i).getPicture();
            }
        }
        return result;
    }
}
