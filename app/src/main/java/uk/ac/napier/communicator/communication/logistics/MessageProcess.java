package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import uk.ac.napier.communicator.communication.messages.Message;

/**
 * Created by David on 21/01/2015.
 */
public class MessageProcess implements CSProcess {

    private Boolean connected = true;
    private final ChannelInput<Message> inSend;
    private final ChannelOutput<String> outResponse;

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
