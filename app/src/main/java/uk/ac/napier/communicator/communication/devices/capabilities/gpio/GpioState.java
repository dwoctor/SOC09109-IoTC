package uk.ac.napier.communicator.communication.devices.capabilities.gpio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.ac.napier.communicator.communication.devices.capabilities.functionality.state.State;

public class GpioState extends State {

    @Expose
    @SerializedName("pin")
    private Integer pin;

    @Expose
    @SerializedName("state")
    private Boolean state;

    public GpioState(Integer pin) {
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
        return gson.fromJson(json, GpioState.class);
    }

}
