package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

import uk.ac.napier.communicator.communication.messages.bidirectional.BidirectionalMessage;
import uk.ac.napier.communicator.communication.messages.unidirectional.UnidirectionalMessage;

public class Postie implements Runnable {

    private static Postie instance = null;
    private Thread thread = new Thread(this);

    private One2OneChannel stuff2transmitBidirectional = Channel.one2one();
    private One2OneChannel stuff2transmitUnidirectional = Channel.one2one();
    private Any2OneChannel stuff2print = Channel.any2one();

    private TransmitterProcess transmitterProcess = new TransmitterProcess(stuff2transmitBidirectional.in(), stuff2transmitUnidirectional.in(), stuff2print.out());
    private TcpReceiverProcess tcpReceiverProcess = new TcpReceiverProcess(stuff2print.out());
    private PrintProcess printProcess = new PrintProcess(stuff2print.in());

    private Parallel jobs = new Parallel();

    private Postie() {
        this.jobs.addProcess(this.transmitterProcess);
        this.jobs.addProcess(this.tcpReceiverProcess);
        this.jobs.addProcess(this.printProcess);
    }

    public static synchronized Postie getInstance() {
        if (instance == null) {
            instance = new Postie();
        }
        return instance;
    }

    public void start() {
        if (this.thread.isAlive() == false) {
            thread.start();
        }
    }

    public TransmitterProcess getTransmitterProcess() {
        return this.transmitterProcess;
    }

    public PrintProcess getPrintProcess() {
        return this.printProcess;
    }

    public void post(BidirectionalMessage message) {
        this.stuff2transmitBidirectional.out().write(message);
    }

    public void post(UnidirectionalMessage message) {
        this.stuff2transmitUnidirectional.out().write(message);
    }

    @Override
    public void run() {
        jobs.run();
    }

}
