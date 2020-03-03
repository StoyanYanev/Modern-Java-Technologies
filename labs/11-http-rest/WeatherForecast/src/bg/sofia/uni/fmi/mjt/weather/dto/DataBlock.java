package bg.sofia.uni.fmi.mjt.weather.dto;

import java.util.List;
import java.util.Objects;

public class DataBlock {
    private String summary;
    private List<DataPoint> data;

    public DataBlock() {
    }

    public DataBlock(String summary, List<DataPoint> data) {
        this.summary = summary;
        this.data = data;
    }

    public String getSummary() {
        return summary;
    }

    public List<DataPoint> getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Summary: " + summary + System.lineSeparator());
        str.append("Data: ");

        for (DataPoint dataPoint : data) {
            str.append(dataPoint);
        }

        return data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBlock)) return false;
        DataBlock dataBlock = (DataBlock) o;
        return summary.equals(dataBlock.summary) &&
                data.equals(dataBlock.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, data);
    }
}