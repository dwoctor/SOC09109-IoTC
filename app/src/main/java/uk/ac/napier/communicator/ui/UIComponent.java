package uk.ac.napier.communicator.ui;

import uk.ac.napier.communicator.communication.logistics.Postie;

/**
 * Created by David on 25/01/15.
 */
public abstract class UIComponent {

    protected static final int DECODE_STATE_UNKNOWN = 0;
    protected static final int DECODE_STATE_COMPLETED = 1;

    private Postie pat = Postie.getInstance();

    public void handleDecodeState(int state) {
        int outState;
        // Converts the decode state to the overall state.
        switch(state) {
            case DECODE_STATE_COMPLETED:
                outState = Postie.TASK_COMPLETE;
                break;
            case DECODE_STATE_UNKNOWN:
            default:
                outState = Postie.TASK_UNKNOWN;
                break;
        }
        // Calls the generalized state method
        this.handleState(outState);
    }

    // Passes the state to Postie
    void handleState(int state) {
        // Passes a handle to this UIComponent and the current state to the class that created the thread pools (or JCSP Processes).
        this.pat.handleState(this, state);
    }

    public abstract void updateUI();
}
