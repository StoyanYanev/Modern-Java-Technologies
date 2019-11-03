package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public abstract class AbstractSmartDevice implements SmartDevice {
    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;
    private DeviceType deviceType;

    public AbstractSmartDevice(String name, double powerConsumption, LocalDateTime installationDateTime, DeviceType deviceType) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
        this.deviceType = deviceType;
    }

    @Override
    public abstract String getId();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    @Override
    public DeviceType getType() {
        return deviceType;
    }
}
