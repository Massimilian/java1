package ru.progwards.java1.lessons;

public class Test implements ITest{
    public static void main(String[] args) {
        Test test = new Test();
    }

    public enum DayOfWeek {
        SUNDAY ("Воскресенье"),
        MONDAY ("Понедельник"),
        TUESDAY ("Вторник"),
        WEDNESDAY ("Среда"),
        THURSDAY ("Четверг"),
        FRIDAY ("Пятница"),
        SATURDAY ("Суббота");

        private String title;

        DayOfWeek(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "DayOfWeek{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }
}
