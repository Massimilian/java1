package ru.progwards.java2.lessons.patterns.proxy;

/**
 * Class of GPS with empty verification
 */
public class GPS {
    public double lat; // широта
    public double lon; // долгота
    public long time; // время в мс

    public GPS(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.time = System.currentTimeMillis();
    }

    public boolean verification(double lat, double lon) {
        return true;
    }
}
