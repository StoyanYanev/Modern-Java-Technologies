package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;
import java.util.Objects;

public class SmartTrafficLight extends AbstractSmartDevice {
    private static final DeviceType DEVICE_TYPE = DeviceType.TRAFFIC_LIGHT;
    private static int counter = 0;

    private final String id;

    public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime, DEVICE_TYPE);
        this.id = String.format("%s-%s-%d", DEVICE_TYPE.getShortName(), name, counter);

        counter++;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartTrafficLight)) return false;
        SmartTrafficLight that = (SmartTrafficLight) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
