package uk.ac.napier.communicator.communication.logistics;

import android.widget.EditText;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import uk.ac.napier.communicator.ui.PrintProcessObserver;

/**
 * Created by David on 21/01/2015.
 */
public class PrintProcess implements CSProcess {

    private final ChannelInput<String> in;
    private EditText editText = null;
    private List<PrintProcessObserver> observers = new ArrayList<PrintProcessObserver>();

    public void add(PrintProcessObserver observer) {
        this.observers.add(observer);
    }

    public PrintProcess(ChannelInput<String> in) {
        this.in = in;
    }

    public PrintProcess(ChannelInput<String> in, EditText editText) {
        this.in = in;
        this.editText = editText;
    }

    public void run() {
        while (true) {
            String statement = in.read();
            try {
                for (PrintProcessObserver observer : this.observers) {
                    observer.update(statement);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
