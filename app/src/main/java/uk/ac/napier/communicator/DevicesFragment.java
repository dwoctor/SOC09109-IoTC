package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.napier.communicator.communication.devices.Devices;
import uk.ac.napier.communicator.ui.component.ArrayAdapterUiComponent;

public class DevicesFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        DevicesAdapter devicesAdapter = new DevicesAdapter(this.getActivity(), Devices.getInstance().getDevices());
        Devices.getInstance().addObservers(new ArrayAdapterUiComponent(devicesAdapter));
        this.setListAdapter(devicesAdapter);
        return view;
    }

}
