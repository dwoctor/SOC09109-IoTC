package uk.ac.napier.communicator.communication.protocols.unidirectional;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class UnidirectionalTcpMessage implements UnidirectionalMessageProtocol {

    private String address;
    private Integer port;
    private Integer timeout;
    private Socket socket = new Socket();
    private byte[] data;

    public UnidirectionalTcpMessage setAddress(String address) {
        this.address = address;
        return this;
    }

    public UnidirectionalTcpMessage setPort(Integer port) {
        this.port = port;
        return this;
    }

    public UnidirectionalTcpMessage setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public UnidirectionalTcpMessage setData(byte[] data) {
        this.data = data;
        return this;
    }

    public void send() throws Exception {
        try {
            this.socket.connect((new InetSocketAddress(this.address, this.port)), this.timeout);
            OutputStream outputStream = this.socket.getOutputStream();
            outputStream.write(this.data);
            outputStream.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (this.socket != null) {
                if (this.socket.isConnected()) {
                    try {
                        this.socket.close();
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        }
    }

}
