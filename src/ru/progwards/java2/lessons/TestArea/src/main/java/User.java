public class User {
    Base base;
    private String login;
    private String password;

    public User(String login, String password, Base base) {
        this.base = base;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNew(String login, String password) {
        base.setUser(new User(login, password, this.base));
    }
}
