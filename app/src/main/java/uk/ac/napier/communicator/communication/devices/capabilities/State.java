package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class State implements CSProcess {

    private static Logger logger = LogManager.getLogger();

    private String address;
    private Integer port;
    private Integer timeout;
    private StateCallback callback;

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
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), Charsets.UTF_8);
            try {
                logger.debug("Started reading data.");
                this.callback.run(this.dejsonize(CharStreams.toString(reader)));
                logger.debug("Finished reading data.");
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

    public State address(String address) {
        this.address = address;
        return this;
    }

    public State port(Integer port) {
        this.port = port;
        return this;
    }

    public State timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public State callback(StateCallback callback) {
        this.callback = callback;
        return this;
    }

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public State dejsonize(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, State.class);
    }

    public void send() {
        ProcessManager manager = new ProcessManager(this);
        manager.start();
    }

}
