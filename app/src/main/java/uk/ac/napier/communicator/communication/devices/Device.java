package uk.ac.napier.communicator.communication.devices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public abstract class Device implements Serializable {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("type")
    private DeviceType type;

    protected Device(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.type.toString());
    }

    public String getName() {
        return name;
    }

    public DeviceType getType() {
        return type;
    }

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
