import java.util.ArrayList;
import java.util.List;

public class Base {
    List<User> users = new ArrayList<>();

    public Base() {
        users.add(new User("Admin", "admin", this));
    }

    public void setUser(User user) {
        users.add(user);
    }

    public static void main(String[] args) {
        Base base = new Base();
        User user = new SafeUser("Hacker", "hacker", base); // Создаём User-хакера, который пытается добавить пользователя, сам не будучи частью листа users
        user.setNew("Mistake", "mistake"); // добавление невозможно - будет выведено "Impossible operation!"
    }
}
