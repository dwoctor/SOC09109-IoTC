package uk.ac.napier.communicator.communication.connections.wifi.local;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.apache.log4j.Logger;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import uk.ac.napier.communicator.communication.devices.WifiDevice;
import uk.ac.napier.communicator.communication.devices.WifiDevices;
import uk.ac.napier.communicator.communication.devices.capabilities.Command;

public class HandshakeRequest implements CSProcess {

    private static Logger logger = Logger.getLogger(Command.class.getName());

    private String address;
    private Integer port;
    private Integer timeout;

    public void run() {
        logger.debug("Started");
        Socket socket = new Socket();
        try {
            socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            logger.debug("Socket connection open.");
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), Charsets.UTF_8);
            try {
                logger.debug("Started reading data.");
                String data = CharStreams.toString(reader);
                logger.debug("Finished reading data.");
                logger.debug(data);
                logger.debug("Started merging data.");
                WifiDevices.getInstance().merge(WifiDevice.dejsonize(data));
                logger.debug("Finished merging data.");
            } catch (Exception e) {
                logger.error("Reader error.", e);
            }
        } catch (Exception e) {
            logger.error("Unknown error.", e);
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                        logger.debug("Socket connection closed.");
                    } catch (IOException e) {
                        logger.error("Socket connection close error.", e);
                    }
                }
            }
        }
        logger.debug("Finished.");
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