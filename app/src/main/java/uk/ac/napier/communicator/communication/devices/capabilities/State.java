package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public abstract class State implements CSProcess {

    private String address;
    private Integer port;
    private Integer timeout;
    private StateCallback callback;

    public void run() {
        Socket socket = new Socket();
        try {
            socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            socket.getOutputStream().write(this.jsonize().getBytes(Charset.forName("UTF-8")));
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), Charsets.UTF_8);
            try {
                this.callback.run(this.dejsonize(CharStreams.toString(reader)));
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
