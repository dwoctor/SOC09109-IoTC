package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.napier.communicator.communication.devices.Devices;
import uk.ac.napier.communicator.ui.ArrayAdapterComponent;

public class DeviceListFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        DeviceAdapter devicesAdapter = new DeviceAdapter(this.getActivity(), Devices.getInstance().getDevices());
        Devices.getInstance().addObservers(new ArrayAdapterComponent(devicesAdapter));
        this.setListAdapter(devicesAdapter);
        return view;
    }
}