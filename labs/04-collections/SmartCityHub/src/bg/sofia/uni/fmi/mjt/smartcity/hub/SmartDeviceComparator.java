package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

public class SmartDeviceComparator implements Comparator<SmartDevice> {
    @Override
    public int compare(SmartDevice o1, SmartDevice o2) {
        double firstDeviceTotalPowerConsumption = calculateTotalPowerConsumption(o1);
        double secondDeviceTotalPowerConsumption = calculateTotalPowerConsumption(o2);

        return Double.compare(firstDeviceTotalPowerConsumption, secondDeviceTotalPowerConsumption);
    }

    private double calculateTotalPowerConsumption(SmartDevice device) {
        LocalDateTime now = LocalDateTime.now();
        long durationInHours = Duration.between(device.getInstallationDateTime(), now).toHours();
        return durationInHours * device.getPowerConsumption();
    }
}
