package starter;

public interface Task {
    // методы для получения и установки времени создания файлов
    public long getModifiedTime();
    public void setModifiedTime(long time);

    // метод для обработки данных
    // и возвращения результата в виде строки
    public String process(byte[] data);
}
