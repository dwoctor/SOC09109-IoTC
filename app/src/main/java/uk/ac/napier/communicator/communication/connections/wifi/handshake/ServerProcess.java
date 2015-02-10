package uk.ac.napier.communicator.communication.connections.wifi.handshake;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevice;
import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevices;

public class ServerProcess implements CSProcess {

    private final ChannelOutput<String> outChannel, debugOutChannel, errorOutChannel, infoOutChannel;

    public ServerProcess(ChannelOutput<String> outChannel, ChannelOutput<String> debugOutChannel, ChannelOutput<String> errorOutChannel, ChannelOutput<String> infoOutChannel) {
        this.outChannel = outChannel;
        this.debugOutChannel = debugOutChannel;
        this.errorOutChannel = errorOutChannel;
        this.infoOutChannel = infoOutChannel;
    }

    public void run() {
        while (true) {
            try {
                // Create a server socket and wait for client connections.
                ServerSocket serverSocket = new ServerSocket(8888);

                // This call blocks until a connection is accepted from a client.
                Socket client = serverSocket.accept();

                // If this code is reached, a client has connected and transferred data.
                ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                try {
                    WifiDevices.getInstance().merge(WifiDevice.dejsonize(reader.readUTF()));
                    this.infoOutChannel.write("Merged Devices List");
                } catch (Exception e) {
                    this.errorOutChannel.write("WiFiServer error" + e.getMessage());
                } finally {
                    reader.close();
                }
                serverSocket.close();
            } catch (Exception e) {
                this.errorOutChannel.write("WiFiServer error" + e.getMessage());
            }
        }
    }
}