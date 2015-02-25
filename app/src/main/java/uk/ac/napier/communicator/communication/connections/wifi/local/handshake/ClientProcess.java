package uk.ac.napier.communicator.communication.connections.wifi.local.handshake;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevice;
import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevices;

public class ClientProcess implements CSProcess {

    private final ChannelInput<WifiDevice> inChannel;
    private final ChannelOutput<String> debugOutChannel, errorOutChannel, infoOutChannel;

    private String groupHost;
    private Integer port;
    private Integer timeout;
    private Socket socket = new Socket();

    public ClientProcess(ChannelInput<WifiDevice> inChannel, ChannelOutput<String> debugOutChannel, ChannelOutput<String> errorOutChannel, ChannelOutput<String> infoOutChannel) {
        this.inChannel = inChannel;
        this.debugOutChannel = debugOutChannel;
        this.errorOutChannel = errorOutChannel;
        this.infoOutChannel = infoOutChannel;
    }

    public void run() {
        while (true) {
            WifiDevice dataToSend = inChannel.read();
            try {
                this.socket.connect((new InetSocketAddress(this.groupHost, this.port)), this.timeout);
                ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
                outputStream.writeUTF(dataToSend.jsonize());
                outputStream.close();
                ObjectInputStream reader = new ObjectInputStream(this.socket.getInputStream());
                try {
                    WifiDevices.getInstance().merge(WifiDevice.dejsonize(reader.readUTF()));
                    this.infoOutChannel.write("Merged Devices List");
                } catch (Exception e) {
                    this.errorOutChannel.write("WiFiServer error" + e.getMessage());
                } finally {
                    reader.close();
                }
                this.infoOutChannel.write("client done");
            } catch (IOException e) {
                this.errorOutChannel.write("WiFiClient error" + e.getMessage());
            } finally {
                if (this.socket != null) {
                    if (this.socket.isConnected()) {
                        try {
                            this.socket.close();
                        } catch (IOException e) {
                            this.errorOutChannel.write("WiFiClient error" + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public ClientProcess groupHost(String groupHost) {
        this.groupHost = groupHost;
        return this;
    }

    public ClientProcess port(Integer port) {
        this.port = port;
        return this;
    }

    public ClientProcess timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }
}