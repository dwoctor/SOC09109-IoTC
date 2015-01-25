package uk.ac.napier.communicator.communication.protocols;

/**
 * Created by David on 23/01/2015.
 */
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
