package uk.ac.napier.communicator.communication.protocols;

public class SimpleMessageProtocol implements MessageProtocol {

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
