package uk.ac.napier.communicator.communication.logistics;

import com.google.common.io.ByteStreams;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpReceiverProcess implements CSProcess {
    private final ChannelOutput out;

    public TcpReceiverProcess(ChannelOutput out) {
        this.out = out;
    }

    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(9999);
                Socket client = serverSocket.accept();
                InputStream reader = client.getInputStream();
                try {
                    this.out.write(new String(ByteStreams.toByteArray(reader)));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reader.close();
                }
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
