package uk.ac.napier.communicator.communication.messages.unidirectional;

import uk.ac.napier.communicator.communication.protocols.unidirectional.UnidirectionalTcpMessage;

public class BinaryMessage extends UnidirectionalMessage {

    public BinaryMessage(String address, Integer port, Integer timeout, byte[] data) throws Exception {
        UnidirectionalTcpMessage protocol = new UnidirectionalTcpMessage();
        protocol.setAddress(address).setPort(port).setTimeout(timeout).setData(data);
        this.setProtocol(protocol);
    }

}
