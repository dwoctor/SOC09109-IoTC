package uk.ac.napier.communicator.communication.devices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.napier.communicator.ui.update.UpdateVoidUiComponent;

public class Devices implements WifiDevicesObserver {

    private static Devices instance = null;
    private ArrayList<Device> devices = new ArrayList<Device>();
    private List<UpdateVoidUiComponent> observers = new ArrayList<UpdateVoidUiComponent>();

    private Devices() {
        WifiDevices.getInstance().addObservers(this);
    }

    public static synchronized Devices getInstance() {
        if (instance == null) {
            instance = new Devices();
        }
        return instance;
    }

    /**
     * Add an {@link uk.ac.napier.communicator.ui.update.UpdateVoidUiComponent observer} to observe {@link uk.ac.napier.communicator.communication.devices.Devices this} object.
     *
     * @param observers One or Many {@link uk.ac.napier.communicator.ui.update.UpdateVoidUiComponent observers} to be added.
     */
    public synchronized void addObservers(UpdateVoidUiComponent... observers) {
        this.observers.addAll(Arrays.asList(observers));
    }

    /**
     * Updates the {@link uk.ac.napier.communicator.ui.update.UpdateVoidUiComponent observers} of {@link uk.ac.napier.communicator.communication.devices.Devices this} object.
     */
    private synchronized void updateObservers() {
        for (UpdateVoidUiComponent observer : this.observers) {
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
