package bg.sofia.uni.fmi.mjt.weather.dto;

import java.util.Objects;

public class DataPoint {
    private int time;
    private String summary;
    private double precipProbability;
    private double temperature;

    public DataPoint() {
    }

    public DataPoint(int time, String summary, double precipProbability, double temperature) {
        this.time = time;
        this.summary = summary;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
    }

    public int getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "Time: " + time + System.lineSeparator()
                + "Summary: " + summary + System.lineSeparator()
                + "Precipitation probability: " + precipProbability + System.lineSeparator()
                + "Temperature: " + temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPoint)) return false;
        DataPoint dataPoint = (DataPoint) o;
        return time == dataPoint.time &&
                Double.compare(dataPoint.precipProbability, precipProbability) == 0 &&
                Double.compare(dataPoint.temperature, temperature) == 0 &&
                summary.equals(dataPoint.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, summary, precipProbability, temperature);
    }
}