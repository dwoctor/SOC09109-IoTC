package uk.ac.napier.communicator.communication.connections.wifi.devices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.napier.communicator.communication.devices.Device;

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

    public static WifiDevices dejsonize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, WifiDevices.class);
    }

    public void addObserver(WifiDevicesObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(WifiDevicesObserver observer) {
        this.observers.remove(observer);
    }

    private void updateObservers(WifiDevice wifiDevice) {
        for (WifiDevicesObserver observer : this.observers) {
            observer.update(wifiDevice);
        }
    }

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public Boolean exists(Device device) {
        return this.knownDevicesTable.containsKey(device.getName());
    }

    public WifiDevice get(Device device) {
        if (this.knownDevicesTable.containsKey(device.getName())) {
            return this.knownDevicesTable.get(device.getName());
        } else {
            return null;
        }
    }

    public void merge(WifiDevice wifiDeviceToMerge) {
        if (!this.knownDevicesTable.containsKey(wifiDeviceToMerge.getName())) {
            this.knownDevicesTable.put(wifiDeviceToMerge.getName(), wifiDeviceToMerge);
            this.updateObservers(wifiDeviceToMerge);
        }
    }

    public void merge(WifiDevices wifiDevicesToMerge) {
        this.knownDevicesTable = wifiDevicesToMerge.knownDevicesTable;
    }
}
