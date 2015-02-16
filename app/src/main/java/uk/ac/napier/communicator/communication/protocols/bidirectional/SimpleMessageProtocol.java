package uk.ac.napier.communicator.communication.protocols.bidirectional;

public class SimpleMessageProtocol implements BidirectionalMessageProtocol {

    private String message = null;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String send() throws Exception {
        return this.message;
    }

}
