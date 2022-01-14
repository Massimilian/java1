public class SafeUser extends User{
    public SafeUser(String login, String password, Base base) {
        super(login, password, base);
    }

    @Override
    public void setNew(String login, String password) {
        boolean canAdd = false;
        for (int i = 0; i < this.base.users.size(); i++) {
            if (this.getLogin().equals(login)) {
                canAdd = true;
                super.setNew(login, password);
            }
        }
        if (!canAdd) {
            System.out.println("Impossible operation!");
        }
    }
}
