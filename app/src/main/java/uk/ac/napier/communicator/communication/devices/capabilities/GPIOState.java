package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPIOState extends State {

    @Expose
    @SerializedName("pin")
    private Integer pin;

    @Expose
    @SerializedName("state")
    private Boolean state;

    public GPIOState(Integer pin) {
        this.pin = pin;
    }

    public Boolean getState() {
        return this.state;
    }

    public Integer getPin() {
        return this.pin;
    }

    public State dejsonize(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, GPIOState.class);
    }
}
