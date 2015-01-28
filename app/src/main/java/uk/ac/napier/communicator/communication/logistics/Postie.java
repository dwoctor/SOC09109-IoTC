package uk.ac.napier.communicator.communication.logistics;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

import uk.ac.napier.communicator.communication.messages.SimpleMessage;
import uk.ac.napier.communicator.ui.UIComponent;

/**
 * Created by David on 21/01/2015.
 */
public class Postie implements Runnable {

    public static final byte TASK_UNKNOWN = 0;
    public static final byte TASK_COMPLETE = 1;

    private static Postie instance = null;
    Thread thread = new Thread(this);
    private Handler handler = this.getHandler();

    private One2OneChannel inMessageSend = Channel.one2one();
    private One2OneChannel outMessageResponse = Channel.one2one();

    private MessageProcess messageProcess = new MessageProcess(inMessageSend.in(), outMessageResponse.out());
    private PrintProcess printProcess = new PrintProcess(outMessageResponse.in());

    private Parallel jobs = new Parallel();

    private Postie() {
        jobs.addProcess(messageProcess);
        jobs.addProcess(printProcess);
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

    public MessageProcess getMessageProcess() {
        return this.messageProcess;
    }

    public PrintProcess getPrintProcess() {
        return this.printProcess;
    }

    public void post(SimpleMessage message) {
        this.inMessageSend.out().write(message);
    }

    @Override
    public void run() {
        jobs.run();
    }


    // Sets up Handler for UIComponents to update UI elements in UIThread.
    public Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler(Looper.getMainLooper()) {
                // handleMessage() defines the operations to perform when the Handler receives a new Message to process.
                @Override
                public void handleMessage(Message inputMessage) {
                    // Gets the UIComponent from the incoming Message object.
                    UIComponent uiComponent = (UIComponent) inputMessage.obj;
                    switch (inputMessage.what) {
                        case TASK_COMPLETE:
                            uiComponent.updateUI();
                            break;
                        default:
                            // Pass along other messages from the UI.
                            super.handleMessage(inputMessage);
                    }
                }
            };
        }
        return this.handler;
    }

    // Handle status messages from UIComponents.
    public void handleState(UIComponent editText, int state) {
        switch (state) {
            // The UIComponent finished.
            case TASK_COMPLETE:
                // Creates a message for the Handler with the state and the UIComponent object.
                Message completeMessage = this.handler.obtainMessage(state, editText);
                completeMessage.sendToTarget();
                break;
        }
    }
}
