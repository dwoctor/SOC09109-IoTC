package uk.ac.napier.communicator.communication.connections.wifi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class Devices implements Serializable {

    private static Devices instance = null;

    public HashMap<String, Device> knownDevicesTable;

    private Devices() {
        this.knownDevicesTable = new HashMap<String, Device>();
    }

    public static synchronized Devices getInstance() {
        if (instance == null) {
            instance = new Devices();
        }
        return instance;
    }

    public void merge(Devices devicesToMerge) {
        HashMap<String, Device> devicesToMergeTable = devicesToMerge.knownDevicesTable;
        for (Entry<String, Device> deviceToMerge : devicesToMergeTable.entrySet()) {
            if (!this.knownDevicesTable.containsKey(deviceToMerge.getKey())) {
                this.knownDevicesTable.put(deviceToMerge.getKey(), deviceToMerge.getValue());
            }
        }
    }
}
