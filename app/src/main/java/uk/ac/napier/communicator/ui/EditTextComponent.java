package uk.ac.napier.communicator.ui;

import android.widget.EditText;

public class EditTextComponent extends UiComponent implements UiComponentUpdateText {

    private EditText editText;
    private String text;

    public EditTextComponent(EditText component) {
        this.editText = component;
    }

    public void update(String text) {
        this.text = text;
        this.handleState(UPDATED);
    }

    public void updateUI() {
        this.editText.setText(text);
    }

}
