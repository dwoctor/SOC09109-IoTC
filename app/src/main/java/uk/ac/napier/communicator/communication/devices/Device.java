package uk.ac.napier.communicator.communication.devices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

import uk.ac.napier.communicator.communication.devices.capabilities.Ability;
import uk.ac.napier.communicator.communication.devices.capabilities.Capability;
import uk.ac.napier.communicator.communication.devices.capabilities.GPIO;

public abstract class Device implements Serializable {

    private static HashMap<String, String> names;

    static {
        names = new HashMap<String, String>();
        names.put("192.168.0.1", "Merlot");
        names.put("192.168.0.2", "Cabernet Sauvignon");
        names.put("192.168.0.3", "Pinot Noir");
        names.put("192.168.0.4", "Shiraz");
        names.put("192.168.0.5", "Sangiovese");
        names.put("192.168.0.6", "Malbec");
        names.put("192.168.0.7", "Tempranillo");
        names.put("192.168.0.8", "Gamay");
        names.put("192.168.0.9", "Zinfandel");
        names.put("192.168.0.10", "Chardonnay");
    }

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("type")
    private DeviceType type;

    @Expose
    @SerializedName("capability")
    private Ability capability;

    protected Device(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.type.toString());
    }

    public String getName() {
        return names.get(this.name);
    }

    public DeviceType getType() {
        return type;
    }

    public Capability getCapability() {
        switch (this.capability) {
            case Gpio:
                return new GPIO();
            default:
                return null;
        }
    }

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public Boolean isWifiDevice() {
        return WifiDevices.getInstance().exists(this);
    }

    public WifiDevice getWifiInfo() {
        return WifiDevices.getInstance().get(this);
    }
}
