package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPIOCommand extends Command {

    @Expose
    @SerializedName("pin")
    private Integer pin;

    @Expose
    @SerializedName("state")
    private Boolean state;

    public GPIOCommand(Integer pin, Boolean state) {
        this.pin = pin;
        this.state = state;
    }
}
