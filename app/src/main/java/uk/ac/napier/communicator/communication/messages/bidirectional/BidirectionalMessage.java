package uk.ac.napier.communicator.communication.messages.bidirectional;

import uk.ac.napier.communicator.communication.protocols.bidirectional.BidirectionalMessageProtocol;

public abstract class BidirectionalMessage {

    private BidirectionalMessageProtocol protocol;

    public BidirectionalMessageProtocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(BidirectionalMessageProtocol protocol) {
        this.protocol = protocol;
    }

    public Object send() throws Exception {
        return this.protocol.send();
    }

}
