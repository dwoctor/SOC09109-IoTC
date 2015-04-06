package uk.ac.napier.communicator.ui;

import android.widget.ArrayAdapter;

public class ArrayAdapterComponent extends UiComponent implements UiComponentUpdate {

    private ArrayAdapter component;

    public ArrayAdapterComponent(ArrayAdapter component) {
        this.component = component;
    }

    @Override
    public void update() {
        this.handleState(UPDATED);
    }

    @Override
    public void updateUI() {
        component.notifyDataSetChanged();
    }
}
