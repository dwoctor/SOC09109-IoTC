package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;

import java.util.ArrayList;
import java.util.List;

import uk.ac.napier.communicator.ui.PrintProcessObserver;

/**
 * Created by David on 21/01/2015.
 */
public class PrintProcess implements CSProcess {

    private final ChannelInput<String> in;
    private List<PrintProcessObserver> observers = new ArrayList<PrintProcessObserver>();

    public PrintProcess(ChannelInput<String> in) {
        this.in = in;
    }

    public void add(PrintProcessObserver observer) {
        this.observers.add(observer);
    }

    public void run() {
        while (true) {
            String statement = in.read();
            try {
                for (PrintProcessObserver observer : this.observers) {
                    observer.updateText(statement);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
