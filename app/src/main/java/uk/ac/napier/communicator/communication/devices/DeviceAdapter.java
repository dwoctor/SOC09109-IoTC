package uk.ac.napier.communicator.communication.devices;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<Device> {

    public DeviceAdapter(Context context, List<Device> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceView deviceView = (DeviceView) convertView;
        if (null == deviceView) {
            deviceView = DeviceView.inflate(parent);
        }
        deviceView.setItem(super.getItem(position));
        return deviceView;
    }

}
