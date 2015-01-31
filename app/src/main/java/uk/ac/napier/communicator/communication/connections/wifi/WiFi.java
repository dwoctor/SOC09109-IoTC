package uk.ac.napier.communicator.communication.connections.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import uk.ac.napier.communicator.R;

public class WiFi extends BroadcastReceiver {

    public HashMap<String, WifiP2pDevice> connectedDevices = new HashMap<>();
    private WifiP2pManager manager;
    private Channel channel;
    private WifiP2pManager.PeerListListener peerListListener;
    private Context context;

    public WiFi(WifiP2pManager manager, Channel channel, Context context) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                //obtain a peer from the WifiP2pDeviceList
                for (WifiP2pDevice device : peers.getDeviceList()) {
                    if (!connectedDevices.containsKey(device.deviceName)) {
                        connectTo(device);
                    }
                }
            }
        };
        this.context = context;
        this.discoverPeers();
    }

    private void connectTo(final WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        this.manager.connect(this.channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //success logic
                Log.d(context.getString(R.string.log_wifi), String.format("WiFi connectTo Successful to %s", device.deviceName));
                connectedDevices.put(device.deviceName, device);
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
                Log.d(context.getString(R.string.log_wifi), "WiFi connectTo Failed");
            }
        });
    }

    private void discoverPeers() {
        this.manager.discoverPeers(this.channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.
                Log.d(context.getString(R.string.log_wifi), "WiFi Discovery Successful");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Log.d(context.getString(R.string.log_wifi), "WiFi Discovery Failed");
            }
        });
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION: // Check to see if Wi-Fi is enabled and notify appropriate activity
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) { // Wifi P2P is enabled
                    Log.d(context.getString(R.string.log_wifi), "WiFi Enabled");
                } else { // Wi-Fi P2P is not enabled
                    Log.d(context.getString(R.string.log_wifi), "WiFi Disabled");
                }
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION: // Call WifiP2pManager.requestPeers() to get a list of current peers
                // request available peers from the wifi p2p manager. This is an
                // asynchronous call and the calling activity is notified with a
                // callback on PeerListListener.onPeersAvailable()
                if (this.manager != null) {
                    this.manager.requestPeers(this.channel, this.peerListListener);
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
