package uk.ac.napier.communicator.communication.connections.wifi.handshake;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import uk.ac.napier.communicator.communication.connections.wifi.Devices;

public class ServerProcess implements CSProcess {

    private final ChannelOutput<String> outChannel, debugOutChannel, errorOutChannel;

    public ServerProcess(ChannelOutput<String> outChannel, ChannelOutput<String> debugOutChannel, ChannelOutput<String> errorOutChannel) {
        this.outChannel = outChannel;
        this.debugOutChannel = debugOutChannel;
        this.errorOutChannel = errorOutChannel;
    }

    public void run() {
        while (true) {
            try {
                // Create a server socket and wait for client connections.
                // This call blocks until a connection is accepted from a client.
                ServerSocket serverSocket = new ServerSocket(8888);
                Socket client = serverSocket.accept();
                // If this code is reached, a client has connected and transferred data.
                // Save the input stream from the client.
                ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
                try {
                    // Merge the device list from the client.
                    Devices.getInstance().merge((Devices) reader.readObject());

                    // Send the updated device list to all the clients.
                    ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
                    writer.writeObject(Devices.getInstance());

                    //text = CharStreams.toString(reader);
                } catch (ClassNotFoundException e) {
                    this.errorOutChannel.write(e.getMessage());
                } finally {
                    reader.close();
                }
                serverSocket.close();
            } catch (IOException e) {
                this.errorOutChannel.write(e.getMessage());
            }
        }
    }
}