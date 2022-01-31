package ru.progwards.java2.lessons.patterns.proxy;

import java.util.ArrayDeque;

public class PointKeeper {
    private final int size = 50;
    private double latMatExpectation;
    private double lonMatExpectation;
    ArrayDeque<GPS> keeper = new ArrayDeque<>(size);

    public void add(GPS gps) {
        if (gps.verification(latMatExpectation, lonMatExpectation)) {
            if (keeper.size() == size) {
                keeper.pollFirst();
            }
            keeper.addLast(gps);
            ArrayDeque<GPS> cloned = keeper.clone();
            setLatLonMatExpectation(cloned, 0, 0);
        }
    }

    /**
     * Method for renew values of all expectations
     * @param keeper is a deque for check
     * @param lat is sum of GPS lats
     * @param lon is sum of GPS lons
     */
    // Примечание: проще вычисление сделать не при помощи
    // рекуррентного метода, а при помощи стандартного итератора
    private void setLatLonMatExpectation(ArrayDeque<GPS> keeper, double lat, double lon) {
        if (keeper.size() == 0) {
            this.latMatExpectation = lat / this.keeper.size();
            this.lonMatExpectation = lon / this.keeper.size();
        } else {
            GPS temp = keeper.remove();
            lat += temp.lat;
            lon += temp.lon;
            setLatLonMatExpectation(keeper, lat, lon);
        }
    }

    public static void main(String[] args) {
        PointKeeper pk = new PointKeeper();
        for (int i = 0; i < pk.size; i++) {
            pk.add(new GPS(1, 1));
        }
        FilteredGPS good = new FilteredGPS(0, 0);
        pk.add(good);
        assert pk.keeper.peekLast().lon == 0.0 && pk.keeper.peekLast().lat == 0.0;
        FilteredGPS bad = new FilteredGPS(-100, -100);
        pk.add(bad);
        assert pk.keeper.peekLast().lon == 0.0 && pk.keeper.peekLast().lat == 0.0;
    }
}
