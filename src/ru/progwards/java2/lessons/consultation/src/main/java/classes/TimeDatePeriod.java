package classes;

public class TimeDatePeriod {
    private TimePeriod time;
    private int numOfDay;

    public TimeDatePeriod(int nameOfDay, TimePeriod time) {
        this.time = time;
        this.numOfDay = nameOfDay;
    }

    public TimePeriod getTime() {
        return this.time;
    }


    public int getNumOfDay() {
        return this.numOfDay;
    }

    public String getNameOfDay() {
        String result = "";
        switch (this.numOfDay) {
            case 1:
                result = "Понедельник";
                break;
            case 2:
                result = "Вторник";
                break;
            case 3:
                result = "Среда";
                break;
            case 4:
                result = "Четверг";
                break;
            case 5:
                result = "Пятница";
                break;
            case 6:
                result = "Суббота";
                break;
            default:
                result = "Воскресенье";
        }
        return result;
    }

}
