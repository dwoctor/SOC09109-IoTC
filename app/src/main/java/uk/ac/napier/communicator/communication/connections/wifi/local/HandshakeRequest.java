package uk.ac.napier.communicator.communication.connections.wifi.local;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import uk.ac.napier.communicator.communication.devices.WifiDevice;
import uk.ac.napier.communicator.communication.devices.WifiDevices;

public class HandshakeRequest implements CSProcess {

    private String address;
    private Integer port;
    private Integer timeout;

    public void run() {
        Socket socket = new Socket();
        try {
            socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), Charsets.UTF_8);
            try {
                WifiDevices.getInstance().merge(WifiDevice.dejsonize(CharStreams.toString(reader)));
            } catch (Exception e) {
            }
        } catch (Exception e) {
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    public HandshakeRequest address(String address) {
        this.address = address;
        return this;
    }

    public HandshakeRequest port(Integer port) {
        this.port = port;
        return this;
    }

    public HandshakeRequest timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public void send() {
        ProcessManager manager = new ProcessManager(this);
        manager.start();
    }

}
