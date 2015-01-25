package uk.ac.napier.communicator.ui;

import android.widget.EditText;

import uk.ac.napier.communicator.R;

/**
 * Created by David on 23/01/2015.
 */
public class EditTextComponent implements PrintProcessObserver {

    private final EditText editText;

    public EditTextComponent(EditText component) {
        this.editText = component;
    }

    public void update(String text) {
        this.editText.setText(text);
    }

}
