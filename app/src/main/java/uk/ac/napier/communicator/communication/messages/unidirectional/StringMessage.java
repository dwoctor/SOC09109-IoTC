package uk.ac.napier.communicator.communication.messages.unidirectional;

import uk.ac.napier.communicator.communication.protocols.unidirectional.UnidirectionalTcpMessage;

public class StringMessage extends UnidirectionalMessage {

    public StringMessage(String address, Integer port, Integer timeout, String data) throws Exception {
        UnidirectionalTcpMessage protocol = new UnidirectionalTcpMessage();
        protocol.setAddress(address).setPort(port).setTimeout(timeout).setData(data.getBytes());
        this.setProtocol(protocol);
    }

}
