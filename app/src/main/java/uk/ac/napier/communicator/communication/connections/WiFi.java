package uk.ac.napier.communicator.communication.connections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;


/**
 * Created by David on 27/01/2015.
 */
public class WiFi extends BroadcastReceiver {

    public HashMap<String, WifiP2pDevice> connectedDevices = new HashMap<String, WifiP2pDevice>();
    private WifiP2pManager manager;
    private Channel channel;
    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pDeviceList devices;
    private Pattern deviceNamePattern = Pattern.compile("IOTD-\\w+-\\d+");

    public WiFi(WifiP2pManager manager, Channel channel) {
        super();
        this.manager = manager;
        this.channel = channel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION: // Check to see if Wi-Fi is enabled and notify appropriate activity
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) { // Wifi P2P is enabled
                    System.out.println("WiFi Enabled");
                } else { // Wi-Fi P2P is not enabled
                    System.out.println("WiFi Disabled");
                }
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION: // Call WifiP2pManager.requestPeers() to get a list of current peers
                this.manager.requestPeers(this.channel, this.peerListListener); // request available peers from the wifi p2p manager. This is an asynchronous call and the calling activity is notified with a callback on PeerListListener.onPeersAvailable()
                this.peerListListener.onPeersAvailable(this.devices);
                for (final WifiP2pDevice device : this.devices.getDeviceList()) {
                    if (deviceNamePattern.matcher(device.deviceName).matches()) {
                        final WifiP2pConfig config = new WifiP2pConfig();
                        config.deviceAddress = device.deviceAddress;
                        this.manager.connect(this.channel, config, new WifiP2pManager.ActionListener() {
                            @Override
                            public void onSuccess() { //success logic
                                if (!connectedDevices.containsKey(device.deviceName)) {
                                    connectedDevices.put(device.deviceName, device);
                                }
                            }

                            @Override
                            public void onFailure(int reason) { //failure logic
                                if (connectedDevices.containsKey(device.deviceName)) {
                                    connectedDevices.remove(device.deviceName);
                                }
                            }
                        });
                    }
                }
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION: // Respond to new connection or disconnections
                Iterator deviceIterator = this.connectedDevices.entrySet().iterator();
                while (deviceIterator.hasNext()) {
                    Entry<String, WifiP2pDevice> device = (Entry<String, WifiP2pDevice>) deviceIterator.next();
                    switch (device.getValue().status) {
                        case WifiP2pDevice.INVITED:
                        case WifiP2pDevice.FAILED:
                        case WifiP2pDevice.AVAILABLE:
                        case WifiP2pDevice.UNAVAILABLE:
                            this.connectedDevices.remove(device.getKey());
                            deviceIterator.remove();
                    }
                }
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION: // Respond to this device's wifi state changing
                break;
        }

    }
}
