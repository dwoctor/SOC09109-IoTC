package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class Command implements CSProcess {

    private static Logger logger = Logger.getLogger(Command.class.getName());

    private String address;
    private Integer port;
    private Integer timeout;

    public void run() {
        logger.debug("Started.");
        Socket socket = new Socket();
        try {
            socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            logger.debug("Socket connection open.");
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            try {
                logger.debug("Started writing data.");
                writer.writeUTF(this.jsonize());
                logger.debug("Finished writing data.");
            } catch (Exception e) {
                logger.error("Writer error.", e);
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
        logger.info("Finished.");
    }

    public Command address(String address) {
        this.address = address;
        return this;
    }

    public Command port(Integer port) {
        this.port = port;
        return this;
    }

    public Command timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public void send() {
        ProcessManager manager = new ProcessManager(this);
        manager.start();
    }

}
