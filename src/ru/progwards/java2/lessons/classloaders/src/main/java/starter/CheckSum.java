package starter;

public class CheckSum implements Task {
    private long modifiedTime;

    @Override
    public long getModifiedTime() {
        return modifiedTime;
    }

    @Override
    public void setModifiedTime(long time) {
        this.modifiedTime = time;
    }

    @Override
    public String process(byte[] data) {
        if (data.length == 0)
            return "Нет данных";

        byte checkSum= 0;
        for (int i = 0; i < data.length; i++) {
            checkSum += data[i];
        }

        return "Контрольная сумма: " + checkSum;

    }
}
