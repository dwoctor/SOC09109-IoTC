package uk.ac.napier.communicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import uk.ac.napier.communicator.communication.devices.Device;
import uk.ac.napier.communicator.communication.devices.WifiDevice;
import uk.ac.napier.communicator.communication.devices.capabilities.Capability;
import uk.ac.napier.communicator.communication.devices.capabilities.functionality.command.CommandCallback;
import uk.ac.napier.communicator.communication.devices.capabilities.functionality.state.State;
import uk.ac.napier.communicator.communication.devices.capabilities.functionality.state.StateCallback;
import uk.ac.napier.communicator.communication.devices.capabilities.gpio.Gpio;
import uk.ac.napier.communicator.communication.devices.capabilities.gpio.GpioState;
import uk.ac.napier.communicator.ui.component.SwitchUiComponent;
import uk.ac.napier.communicator.ui.update.UpdateBooleanUiComponent;

public class DeviceView extends RelativeLayout {

    private Switch deviceState;
    private UpdateBooleanUiComponent deviceComponent;

    public DeviceView(Context context) {
        this(context, null);
    }

    public DeviceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeviceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.device_view_children, this, true);
        setupChildren();
    }

    public static DeviceView inflate(ViewGroup parent) {
        return (DeviceView) LayoutInflater.from(parent.getContext()).inflate(R.layout.device_view, parent, false);
    }

    private void setupChildren() {
        this.deviceState = (Switch) findViewById(R.id.state);
        this.deviceComponent = new SwitchUiComponent(this.deviceState);
    }

    public void setDevice(final Device device) {
        deviceState.setText(device.getName());
        if (device.isWifiDevice()) {
            final WifiDevice wifiDevice = device.getWifiInfo();
            final Capability capability = wifiDevice.getCapability();
            if (capability instanceof Gpio) {
                final Gpio gpio = (Gpio) capability;
                deviceState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        gpio.createCommand(17, isChecked).address(wifiDevice.getIp()).port(3333).timeout(500).callback(new CommandCallback() {
                            @Override
                            public void run() {

                            }
                        }).send();
                    }
                });
                gpio.createState(17).address(wifiDevice.getIp()).port(2222).timeout(500).callback(new StateCallback() {
                    @Override
                    public void run(State data) {
                        deviceComponent.update(((GpioState) data).getState());
                    }
                }).continuously(true).delay(1000).send();
            }
        }
    }

}
