package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

import uk.ac.napier.communicator.communication.messages.Message;

/**
 * Created by David on 21/01/2015.
 */
public class Postie implements Runnable {

    private static Postie instance = null;

    public static synchronized Postie getInstance() {
        if(instance == null) {
            instance = new Postie();
        }
        return instance;
    }

    private One2OneChannel inMessageSend = Channel.one2one();
    private One2OneChannel outMessageResponse = Channel.one2one();

    private MessageProcess messageProcess = new MessageProcess(inMessageSend.in(), outMessageResponse.out());
    private PrintProcess printProcess = new PrintProcess(outMessageResponse.in());

    private Parallel jobs = new Parallel();

    Thread thread = new Thread(this);

    private Postie() {
        jobs.addProcess(messageProcess);
        jobs.addProcess(printProcess);
    }

    public void start() {
        if (this.thread.isAlive() == false) {
            thread.start();
        }
    }

    public MessageProcess getMessageProcess() {
        return this.messageProcess;
    }

    public PrintProcess getPrintProcess() {
        return this.printProcess;
    }

    public void post(Message message) {
        this.inMessageSend.out().write(message);
    }

    @Override
    public void run() {
        jobs.run();
    }
}
