package uk.ac.napier.communicator.communication.devices.capabilities.functionality.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ProcessManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public abstract class Command implements CSProcess {

    private String address = "127.0.0.1";
    private Integer port = 80;
    private Integer timeout = 500;
    private CommandCallback callback = new CommandCallback() {
        @Override
        public void run() {

        }
    };
    private Boolean continuously = false;
    private Integer delay = 0;

    public void run() {
        do {
            Socket socket = new Socket();
            try {
                socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
                socket.getOutputStream().write(this.jsonize().getBytes(Charset.forName("UTF-8")));
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
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
            }
        } while (this.continuously);
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

    public Command continuously(Boolean continuously) {
        this.continuously = continuously;
        return this;
    }

    public Command delay(Integer delay) {
        this.delay = delay;
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
