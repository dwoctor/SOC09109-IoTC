package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import uk.ac.napier.communicator.communication.messages.Message;

public class MessageProcess implements CSProcess {

    private final ChannelInput<Message> inSend;
    private final ChannelOutput<String> outResponse;
    private Boolean connected = true;

    public MessageProcess(ChannelInput<Message> inSend, ChannelOutput<String> outResponse) {
        this.inSend = inSend;
        this.outResponse = outResponse;
    }

    public void run() {
        while (connected) {
            Message messageToSend = inSend.read();
            try {
                outResponse.write(messageToSend.send());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
