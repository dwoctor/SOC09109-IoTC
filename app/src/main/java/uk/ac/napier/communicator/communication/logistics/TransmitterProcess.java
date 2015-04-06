package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;

import uk.ac.napier.communicator.communication.messages.bidirectional.BidirectionalMessage;
import uk.ac.napier.communicator.communication.messages.unidirectional.UnidirectionalMessage;

public class TransmitterProcess implements CSProcess {

    private final AltingChannelInput<BidirectionalMessage> inBidirectional;
    private final AltingChannelInput<UnidirectionalMessage> inUnidirectional;
    private final ChannelOutput outBidirectional;
    private final AltingChannelInput[] alternativeChannelInputs;
    private final Alternative alternative;

    private final int BIDIRECTIONAL = 0;
    private final int UNIDIRECTIONAL = 1;

    public TransmitterProcess(AltingChannelInput<BidirectionalMessage> inBidirectional, AltingChannelInput<UnidirectionalMessage> inUnidirectional, ChannelOutput<String> outBidirectional) {
        this.inBidirectional = inBidirectional;
        this.inUnidirectional = inUnidirectional;
        this.outBidirectional = outBidirectional;
        this.alternativeChannelInputs = new AltingChannelInput[]{this.inBidirectional, this.inUnidirectional};
        this.alternative = new Alternative(this.alternativeChannelInputs);
    }

    public void run() {
        while (true) {
            switch (alternative.select()) {
                case BIDIRECTIONAL:
                    BidirectionalMessage bidirectionalMessage = (BidirectionalMessage) this.inBidirectional.read();
                    try {
                        outBidirectional.write(bidirectionalMessage.send());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UNIDIRECTIONAL:
                    UnidirectionalMessage unidirectionalMessage = this.inUnidirectional.read();
                    try {
                        unidirectionalMessage.send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}
