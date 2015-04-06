package uk.ac.napier.communicator.communication.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import uk.ac.napier.communicator.communication.devices.capabilities.Ability;
import uk.ac.napier.communicator.communication.devices.capabilities.Capability;
import uk.ac.napier.communicator.communication.devices.capabilities.gpio.Gpio;

public abstract class Device implements Serializable {

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
        return String.format("%s (%s)", this.getName(), this.getType().toString());
    }

    public String getName() {
        return this.name;
    }

    public DeviceType getType() {
        return type;
    }

    public Capability getCapability() {
        switch (this.capability) {
            case Gpio:
                return new Gpio();
            default:
                return null;
        }
    }

    public Boolean isWifiDevice() {
        return WifiDevices.getInstance().exists(this);
    }

    public WifiDevice getWifiInfo() {
        return WifiDevices.getInstance().get(this);
    }

}
