package uk.ac.napier.communicator.communication.messages.unidirectional;

import uk.ac.napier.communicator.communication.protocols.unidirectional.UnidirectionalMessageProtocol;

public abstract class UnidirectionalMessage {

    private UnidirectionalMessageProtocol protocol;

    public UnidirectionalMessageProtocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(UnidirectionalMessageProtocol protocol) {
        this.protocol = protocol;
    }

    public void send() throws Exception {
        this.protocol.send();
    }

}
