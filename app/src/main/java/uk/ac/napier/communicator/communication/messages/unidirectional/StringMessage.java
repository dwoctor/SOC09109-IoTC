package uk.ac.napier.communicator.communication.messages.unidirectional;

public class StringMessage extends BinaryMessage {

    public StringMessage(String address, Integer port, Integer timeout, String data) throws Exception {
        super(address, port, timeout, data.getBytes());
    }

}
