package uk.ac.napier.communicator.ui;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class UiComponent {

    protected static final int UPDATED = 0;
    private Handler handler = null;

    /**
     * Passes the state of the {@link uk.ac.napier.communicator.ui.UiComponent UiComponent} to the Handler running on the  main (ui) thread.
     *
     * @param state The state of this {@link uk.ac.napier.communicator.ui.UiComponent UiComponent}.
     */
    protected void handleState(int state) {
        // Passes a message to the handler running on the main (ui) thread.
        this.getHandler().obtainMessage(state, this).sendToTarget();
    }

    /**
     * Sets up {@link android.os.Handler Handler} for {@link uk.ac.napier.communicator.ui.UiComponent UiComponent} to update UI elements in the main (ui) thread.
     *
     * @return Returns a {@link android.os.Handler Handler} running on the main (ui) thread.
     */
    public Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler(Looper.getMainLooper()) {
                /**
                 * {@link android.os.Handler.Callback#handleMessage handleMessage} defines the operations to perform when the {@link android.os.Handler Handler} receives a new {@link android.os.Message Message} to process.
                 */
                @Override
                public void handleMessage(Message message) {
                    // Gets the UIComponent from the incoming Message object.
                    UiComponent uiComponent = (UiComponent) message.obj;
                    switch (message.what) {
                        case UPDATED:
                            uiComponent.updateUI();
                            break;
                    }
                }
            };
        }
        return this.handler;
    }

    public abstract void updateUI();
}
