package ru.progwards.java2.lessons.patterns.proxy;

/**
 * Upgraded class of GPS with checking method of verification
 */
public class FilteredGPS extends GPS {
    public FilteredGPS(double lat, double lon) {
        super(lat, lon);
    }

    /**
     * Overrided method with verification
     * @param lat is kepper's lat
     * @param lon is keeper's lon
     * @return resut of verification work
     */
    @Override
    public boolean verification(double lat, double lon) {
        // более простой и менее наглядный вариант
        // checkAll(lat, lon);

        if (checkAll(lat, lon)) {
            return super.verification(lat, lon);
        } else {
            return false;
        }
    }

    /**
     * Method for divide lat and lon
     */
    private boolean checkAll(double lat, double lon) {
        return checkPart(lat, this.lat) && checkPart(lon, this.lon);
    }

    /**
     * Method for check "three-sigmas" rules
     * @param arrayValue is lat or lon of keeper
     * @param thisValue is lat or lon of this GPS
     * @return result of verification work
     */
    private boolean checkPart(double arrayValue, double thisValue) {
        double dispertion = Math.abs((arrayValue + thisValue) / 2);
        double sigma = Math.sqrt(dispertion);
        return thisValue < arrayValue + sigma * 3 && thisValue > arrayValue - sigma * 3;
    }
}
