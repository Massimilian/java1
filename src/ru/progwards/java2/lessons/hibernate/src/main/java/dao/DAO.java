package dao;

public interface DAO<T> {
    public T get(long id);
    public T getByTwoParameters(String first, String second);
    public void add();
    public void delete();
}
