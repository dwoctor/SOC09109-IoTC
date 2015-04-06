package uk.ac.napier.communicator.ui.component;

import android.widget.ArrayAdapter;

import uk.ac.napier.communicator.ui.update.UpdateUiComponent;
import uk.ac.napier.communicator.ui.update.UpdateVoidUiComponent;

public class ArrayAdapterUiComponent extends UiComponent implements UpdateUiComponent, UpdateVoidUiComponent {

    private ArrayAdapter component;

    public ArrayAdapterUiComponent(ArrayAdapter component) {
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
