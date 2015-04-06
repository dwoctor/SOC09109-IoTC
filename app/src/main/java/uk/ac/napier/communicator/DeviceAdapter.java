package uk.ac.napier.communicator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import uk.ac.napier.communicator.communication.devices.Device;

public class DeviceAdapter extends ArrayAdapter<Device> {

    public DeviceAdapter(Context context, List<Device> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceView deviceView = (DeviceView) convertView;
        if (deviceView == null) {
            deviceView = DeviceView.inflate(parent);
        }
        deviceView.setDevice(super.getItem(position));
        return deviceView;
    }

}
