package classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.Store;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class InfoStore implements Store<Account> {
    private final String address = "src/main/resources/info.txt";

    @Override
    public void write(Account item) {
        String json = new Gson().toJson(item);
        try {
            Files.write(Path.of(address), (json + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> read() {
        List<Account> result = new ArrayList<>();
        try {
            String json = Files.readString(Path.of(address));
            String[] accounts = json.split(System.lineSeparator());
            Type type = new TypeToken<Account>() {}.getType();
            for (String account : accounts) {
                Account temp = new Gson().fromJson(account, type);
                result.add(temp);
            }
            Files.writeString(Path.of(address), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
