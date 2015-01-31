package uk.ac.napier.communicator.communication.messages;

import uk.ac.napier.communicator.communication.protocols.MessageProtocol;

public abstract class Message {

    private MessageProtocol messageProtocol;

    public MessageProtocol getMessageProtocol() {
        return this.messageProtocol;
    }

    public void setMessageProtocol(MessageProtocol messageProtocol) {
        this.messageProtocol = messageProtocol;
    }

    public String send() throws Exception {
        return this.messageProtocol.send();
    }

}
