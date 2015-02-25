package uk.ac.napier.communicator.communication.connections.wifi.local;

import android.content.Context;

import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevice;
import uk.ac.napier.communicator.communication.connections.wifi.local.handshake.HandshakeProcess;

public class WifiManager {

    private Context context;

    public WifiManager(Context context) {
        super();
        this.context = context;
    }

    private void discoverPeers() {
        for (Integer i = 0; i < 7; i++)
        {
            String ip = String.format("192.168.0.$d", i);
            HandshakeProcess.getInstance().getClientProcess().groupHost(ip).port(1000).timeout(500);
            HandshakeProcess.getInstance().send(WifiDevice.getLocalDevice(this.context));
        }
    }
}
