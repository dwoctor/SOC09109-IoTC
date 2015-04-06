package uk.ac.napier.communicator.communication.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WifiDevices implements Serializable {

    private static WifiDevices instance = null;

    @Expose
    @SerializedName("devices")
    /**
     * Stores the knoown wifi devices.
     * key: device name
     * value: device
     */
    private HashMap<String, WifiDevice> knownDevicesTable;

    private List<WifiDevicesObserver> observers = new ArrayList<WifiDevicesObserver>();

    private WifiDevices() {
        this.knownDevicesTable = new HashMap<String, WifiDevice>();
    }

    public static synchronized WifiDevices getInstance() {
        if (instance == null) {
            instance = new WifiDevices();
        }
        return instance;
    }

    /**
     * Add {@link WifiDevicesObserver observer(s)} to observe {@link WifiDevices this} object for new {@link WifiDevice devices}.
     *
     * @param observers One or Many {@link WifiDevicesObserver observers} to be added.
     */
    public synchronized void addObservers(WifiDevicesObserver... observers) {
        this.observers.addAll(Arrays.asList(observers));
    }

    /**
     * Updates the {@link WifiDevicesObserver observers} of the new {@link WifiDevice device}.
     *
     * @param wifiDevice The {@link WifiDevice device} to be sent to the {@link WifiDevicesObserver observers}.
     */
    private synchronized void updateObservers(WifiDevice wifiDevice) {
        for (WifiDevicesObserver observer : this.observers) {
            observer.update(wifiDevice);
        }
    }

    public synchronized Boolean exists(Device device) {
        return this.knownDevicesTable.containsKey(device.getName());
    }

    public synchronized WifiDevice get(Device device) {
        if (this.knownDevicesTable.containsKey(device.getName())) {
            return this.knownDevicesTable.get(device.getName());
        } else {
            return null;
        }
    }

    public synchronized void merge(WifiDevice... wifiDevicesToMerge) {
        for (WifiDevice wifiDeviceToMerge : wifiDevicesToMerge) {
            if (!this.knownDevicesTable.containsKey(wifiDeviceToMerge.getName())) {
                this.knownDevicesTable.put(wifiDeviceToMerge.getName(), wifiDeviceToMerge);
                this.updateObservers(wifiDeviceToMerge);
            }
        }
    }

}
