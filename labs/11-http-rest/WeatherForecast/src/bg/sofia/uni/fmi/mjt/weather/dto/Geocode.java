package bg.sofia.uni.fmi.mjt.weather.dto;

public class Geocode {
    private double lat;
    private double lon;

    public Geocode() {

    }

    public Geocode(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Latitude: " + lat + System.lineSeparator()
                + "Longitude: " + lon;
    }
}