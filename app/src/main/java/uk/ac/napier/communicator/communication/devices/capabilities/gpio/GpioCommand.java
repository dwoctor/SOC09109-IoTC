package uk.ac.napier.communicator.communication.devices.capabilities.gpio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.ac.napier.communicator.communication.devices.capabilities.functionality.command.Command;

public class GpioCommand extends Command {

    @Expose
    @SerializedName("pin")
    private Integer pin;

    @Expose
    @SerializedName("state")
    private Boolean state;

    public GpioCommand(Integer pin, Boolean state) {
        this.pin = pin;
        this.state = state;
    }

}
