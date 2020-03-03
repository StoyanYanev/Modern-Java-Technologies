package bg.sofia.uni.fmi.mjt.weather.dto;

import java.util.Objects;

public class WeatherForecast {
    private double latitude;
    private double longitude;
    private String timezone;
    private DataPoint currently;
    private DataBlock hourly;
    private DataBlock daily;

    public WeatherForecast() {

    }

    public WeatherForecast(double latitude, double longitude, String timezone, DataPoint currently,
                           DataBlock hourly, DataBlock daily) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.currently = currently;
        this.hourly = hourly;
        this.daily = daily;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public DataPoint getCurrently() {
        return currently;
    }

    public DataBlock getHourly() {
        return hourly;
    }

    public DataBlock getDaily() {
        return daily;
    }

    @Override
    public String toString() {
        return "Latitude: " + latitude + System.lineSeparator()
                + "Longitude: " + longitude + System.lineSeparator()
                + "Timezone: " + timezone + System.lineSeparator()
                + "Currently: " + currently.toString() + System.lineSeparator()
                + "Hourly: " + hourly.toString() + System.lineSeparator()
                + "Daily: " + daily.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherForecast)) return false;
        WeatherForecast that = (WeatherForecast) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                timezone.equals(that.timezone) &&
                currently.equals(that.currently) &&
                hourly.equals(that.hourly) &&
                daily.equals(that.daily);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, timezone, currently, hourly, daily);
    }
}