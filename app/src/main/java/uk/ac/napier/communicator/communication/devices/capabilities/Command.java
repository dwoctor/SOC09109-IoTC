package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class Command implements CSProcess {

    private String address;
    private Integer port;
    private Integer timeout;
    private CommandCallback callback;

    public void run() {
        Socket socket = new Socket();
        try {
            socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            try {
                writer.writeUTF(this.jsonize());
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
        this.callback.run();
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

    public Command callback(CommandCallback callback) {
        this.callback = callback;
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
