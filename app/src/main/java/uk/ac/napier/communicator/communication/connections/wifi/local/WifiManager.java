package uk.ac.napier.communicator.communication.connections.wifi.local;

import android.content.Context;

import uk.ac.napier.communicator.communication.connections.wifi.devices.WifiDevice;
import uk.ac.napier.communicator.communication.connections.wifi.local.handshake.HandshakeProcess;

public class WifiManager {

    private Context context;

    public WifiManager(Context context) {
        super();
        this.context = context;
        this.discoverPeers();
    }

    private void discoverPeers() {
        for (Integer i = 0; i < 10; i++) {
            this.discoverPeer(String.format("192.168.0.%d", i));
        }
    }

    private void discoverPeer(String ip) {
        HandshakeProcess.getInstance(this.context).start();
        HandshakeProcess.getInstance().getClientProcess().groupHost(ip).port(9999).timeout(500);
        HandshakeProcess.getInstance().send(WifiDevice.getLocalDevice(this.context));
    }
}
