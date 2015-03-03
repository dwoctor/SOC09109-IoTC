package uk.ac.napier.communicator.communication.connections.wifi.local.handshake;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import java.io.IOException;
import java.io.InputStreamReader;
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

    public ClientProcess(ChannelInput<WifiDevice> inChannel, ChannelOutput<String> debugOutChannel, ChannelOutput<String> errorOutChannel, ChannelOutput<String> infoOutChannel) {
        this.inChannel = inChannel;
        this.debugOutChannel = debugOutChannel;
        this.errorOutChannel = errorOutChannel;
        this.infoOutChannel = infoOutChannel;
    }

    public void run() {
        while (true) {
            WifiDevice dataToSend = inChannel.read();
            this.infoOutChannel.write("ClientProcess Started");
            Socket socket = new Socket();
            try {
                socket.connect((new InetSocketAddress(this.groupHost, this.port)), this.timeout);
                this.infoOutChannel.write("ClientProcess Writing");
                ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                try {
                    writer.writeUTF(dataToSend.jsonize());
                } catch (Exception e) {
                    this.errorOutChannel.write("WiFiLocal ClientProcess (writer) error" + e.getMessage());
                }
                this.infoOutChannel.write("ClientProcess Reading");
                InputStreamReader reader = new InputStreamReader(socket.getInputStream(), Charsets.UTF_8);
                try {
                    String data = CharStreams.toString(reader);
                    WifiDevices.getInstance().merge(WifiDevice.dejsonize(data));
                    this.debugOutChannel.write(data);
                } catch (Exception e) {
                    this.errorOutChannel.write("WiFiLocal ClientProcess (reader) error" + e.getMessage());
                }
                this.infoOutChannel.write("ClientProcess Done");
            } catch (Exception e) {
                this.errorOutChannel.write("WiFiLocal ClientProcess (unknown) error" + e.getMessage());
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            this.errorOutChannel.write("WiFiLocal ClientProcess (close) error" + e.getMessage());
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