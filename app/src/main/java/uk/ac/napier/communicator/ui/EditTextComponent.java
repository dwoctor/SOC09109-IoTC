package uk.ac.napier.communicator.ui;

import android.widget.EditText;

/**
 * Created by David on 23/01/2015.
 */
public class EditTextComponent extends UIComponent implements PrintProcessObserver {

    private EditText editText;
    private String text;

    public EditTextComponent(EditText component) {
        this.editText = component;
    }

    public void updateText(String text) {
        this.text = text;
        this.handleDecodeState(UIComponent.DECODE_STATE_COMPLETED);
    }

    public void updateUI() {
        this.editText.setText(text);
    }

}
