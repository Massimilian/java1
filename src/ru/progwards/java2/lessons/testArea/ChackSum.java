package ru.progwards.java2.lessons.testArea;

public class ChackSum implements Task {
    @Override
    public String process(byte[] data) {
        if (data.length == 0) {
            return "Нет данных";
        } else {
            byte checlSum = 0;
            for (int i = 0; i < data.length ; i++) {
                checlSum += data[i];
            }
            return "Контрольная сумма: " + checlSum;
        }
    }
}
