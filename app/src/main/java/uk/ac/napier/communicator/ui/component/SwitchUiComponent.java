package uk.ac.napier.communicator.ui.component;

import android.widget.Switch;

import uk.ac.napier.communicator.ui.update.UpdateBooleanUiComponent;
import uk.ac.napier.communicator.ui.update.UpdateGenericUiComponent;

public class SwitchUiComponent extends UiComponent implements UpdateGenericUiComponent<Boolean>, UpdateBooleanUiComponent {

    private Switch component;
    private Boolean checked;

    public SwitchUiComponent(Switch component) {
        this.component = component;
    }

    @Override
    public void update(Boolean value) {
        this.checked = value;
        this.handleState(UPDATED);
    }

    @Override
    public void updateUI() {
        component.setChecked(this.checked);
    }
}
