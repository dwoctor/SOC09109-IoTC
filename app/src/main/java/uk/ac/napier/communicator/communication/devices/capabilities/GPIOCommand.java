package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import uk.ac.napier.communicator.communication.devices.WifiDevice;
import uk.ac.napier.communicator.communication.devices.WifiDevices;

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
