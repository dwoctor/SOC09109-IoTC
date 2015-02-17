package uk.ac.napier.communicator.communication.logistics;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;

import java.util.ArrayList;
import java.util.List;

import uk.ac.napier.communicator.ui.UiComponentUpdateText;

public class PrintProcess implements CSProcess {

    private final ChannelInput<String> in;
    private List<UiComponentUpdateText> observers = new ArrayList<UiComponentUpdateText>();

    public PrintProcess(ChannelInput<String> in) {
        this.in = in;
    }

    public void add(UiComponentUpdateText observer) {
        this.observers.add(observer);
    }

    public void run() {
        while (true) {
            String statement = in.read();
            try {
                for (UiComponentUpdateText observer : this.observers) {
                    observer.update(statement);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
