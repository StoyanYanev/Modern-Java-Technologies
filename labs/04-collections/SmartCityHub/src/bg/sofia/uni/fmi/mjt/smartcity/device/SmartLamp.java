package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;
import java.util.Objects;

public class SmartLamp extends AbstractSmartDevice {
    private static final DeviceType DEVICE_TYPE = DeviceType.LAMP;
    private static int counter = 0;

    private final String id;

    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
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
        if (!(o instanceof SmartLamp)) return false;
        SmartLamp smartLamp = (SmartLamp) o;
        return id.equals(smartLamp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
