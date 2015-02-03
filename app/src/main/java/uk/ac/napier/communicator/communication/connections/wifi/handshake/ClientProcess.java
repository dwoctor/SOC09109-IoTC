package uk.ac.napier.communicator.communication.connections.wifi.handshake;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientProcess implements CSProcess {

    private final ChannelInput<Serializable> inChannel;
    private final ChannelOutput<String> debugOutChannel, errorOutChannel;
    private String groupHost;
    private Integer port;
    private Integer timeout;
    private Socket socket = new Socket();

    public ClientProcess(String groupHost, Integer port, Integer timeout, ChannelInput<Serializable> inChannel, ChannelOutput<String> debugOutChannel, ChannelOutput<String> errorOutChannel) {
        this.groupHost = groupHost;
        this.port = port;
        this.timeout = timeout;
        this.inChannel = inChannel;
        this.debugOutChannel = debugOutChannel;
        this.errorOutChannel = errorOutChannel;
    }

    public void run() {
        while (true) {
            Serializable dataToSend = inChannel.read();
            try {
                // Create a client socket with the host,
                // port, and timeout information.
                socket.bind(null);
                socket.connect((new InetSocketAddress(this.groupHost, port)), timeout);

                // Create a byte stream from a JPEG file and pipe it to the output stream
                // of the socket. This data will be retrieved by the server device.
                //OutputStream outputStream = socket.getOutputStream();

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(dataToSend);

                //ByteStreams.copy(inputStream, outputStream);

                outputStream.close();

                //inputStream.close();
            } catch (IOException e) {
                this.errorOutChannel.write("WiFiClient error" + e.getMessage());
            } finally {
                // Clean up any open sockets when done
                // transferring or if an exception occurred.
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //catch logic
                            this.errorOutChannel.write("WiFiClient error" + e.getMessage());
                        }
                    }
                }
            }
        }
    }
}