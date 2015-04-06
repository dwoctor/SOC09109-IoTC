package uk.ac.napier.communicator.communication.devices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.napier.communicator.ui.UiComponentUpdate;

public class Devices implements WifiDevicesObserver {

    private static Devices instance = null;
    private ArrayList<Device> devices;
    private List<UiComponentUpdate> observers = new ArrayList<UiComponentUpdate>();

    private Devices() {
        this.devices = new ArrayList<Device>();
        WifiDevices.getInstance().addObservers(this);
    }

    public static synchronized Devices getInstance() {
        if (instance == null) {
            instance = new Devices();
        }
        return instance;
    }

    /**
     * Add an {@link uk.ac.napier.communicator.ui.UiComponentUpdate observer} to observe {@link uk.ac.napier.communicator.communication.devices.Devices this} object.
     *
     * @param observers One or Many {@link uk.ac.napier.communicator.ui.UiComponentUpdate observers} to be added.
     */
    public synchronized void addObservers(UiComponentUpdate... observers) {
        this.observers.addAll(Arrays.asList(observers));
    }

    /**
     * Updates the {@link uk.ac.napier.communicator.ui.UiComponentUpdate observers} of {@link uk.ac.napier.communicator.communication.devices.Devices this} object.
     */
    private synchronized void updateObservers() {
        for (UiComponentUpdate observer : this.observers) {
            observer.update();
        }
    }

    @Override
    public synchronized void update(WifiDevice device) {
        this.devices.add(device);
        this.updateObservers();
    }

    public synchronized ArrayList<Device> getDevices() {
        return this.devices;
    }

}
