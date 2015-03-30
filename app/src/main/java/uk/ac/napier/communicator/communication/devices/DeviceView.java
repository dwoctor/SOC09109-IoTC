package uk.ac.napier.communicator.communication.devices;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import uk.ac.napier.communicator.R;

public class DeviceView extends RelativeLayout {
    private TextView deviceName;
    private ToggleButton deviceState;

    public DeviceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.device_view_children, this, true);
        setupChildren();
    }

    public static DeviceView inflate(ViewGroup parent) {
        return (DeviceView) LayoutInflater.from(parent.getContext()).inflate(R.layout.device_view, parent, false);
    }

    private void setupChildren() {
        deviceName = (TextView) findViewById(R.id.name);
        deviceState = (ToggleButton) findViewById(R.id.state);
    }

    public void setItem(Device device) {
        deviceName.setText(device.getName());
    }
}
