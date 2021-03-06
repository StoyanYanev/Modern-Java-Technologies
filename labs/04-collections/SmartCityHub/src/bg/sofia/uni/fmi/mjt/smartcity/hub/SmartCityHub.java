package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmartCityHub {
    private Map<String, SmartDevice> devices;

    public SmartCityHub() {
        devices = new LinkedHashMap<>();
    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException         in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        validateDevice(device);

        if (devices.containsKey(device.getId())) {
            throw new DeviceAlreadyRegisteredException("The device already exists!");
        }
        devices.put(device.getId(), device);
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException  in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        validateDevice(device);

        if (devices.remove(device.getId()) == null) {
            throw new DeviceNotFoundException("The device is not found!");
        }
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException  in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        SmartDevice device = devices.get(id);
        if (device == null) {
            throw new DeviceNotFoundException("The device is not found!");
        }

        return device;
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        int counter = 0;
        for (Map.Entry<String, SmartDevice> entry : devices.entrySet()) {
            if (entry.getValue().getType() == type) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     * <p>
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        if (n > devices.size()) {
            n = devices.size();
        }

        List<SmartDevice> devicesId = new ArrayList<>(devices.values());
        Collections.sort(devicesId, new SmartDeviceComparator());
        List<String> topNDevicesByConsumption = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            topNDevicesByConsumption.add(devicesId.get(i).getId());
        }

        return topNDevicesByConsumption;
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        if (devices.size() <= n) {
            return devices.values();
        }

        List<SmartDevice> devicesId = new ArrayList<>(devices.values());
        List<SmartDevice> firstNDevices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            firstNDevices.add(devicesId.get(i));
        }

        return firstNDevices;
    }

    private void validateDevice(SmartDevice device) {
        if (device == null) {
            throw new IllegalArgumentException();
        }
    }
}
