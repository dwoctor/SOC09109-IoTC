package uk.ac.napier.communicator.communication.devices;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevice;
import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevices;
import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevicesObserver;

public class Devices implements WifiDevicesObserver {

    private static Devices instance = null;
    private ArrayList<Device> devices;
    private ArrayAdapter devicesAdapter;
    private Handler handler;

    private Devices() {
        this.devices = new ArrayList<Device>();
        this.handler = this.getHandler();
        WifiDevices.getInstance().addObserver(this);
    }

    public static synchronized Devices getInstance() {
        if (instance == null) {
            instance = new Devices();
        }
        return instance;
    }

    @Override
    public void update(WifiDevice device) {
        devices.add(device);
        Message completeMessage = this.handler.obtainMessage();
        completeMessage.sendToTarget();
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevicesAdapter(ArrayAdapter devicesAdapter) {
        this.devicesAdapter = devicesAdapter;
    }

    public Handler getHandler() {
        if (this.handler == null) {
            this.handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    devicesAdapter.notifyDataSetChanged();
                }
            };
        }
        return this.handler;
    }
}
