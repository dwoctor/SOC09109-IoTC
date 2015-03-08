package uk.ac.napier.communicator.communication.connections.wifi.direct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

import java.util.HashMap;

import uk.ac.napier.communicator.R;
import uk.ac.napier.communicator.communication.connections.wifi.direct.handshake.HandshakeProcess;
import uk.ac.napier.communicator.communication.devices.WifiDevice;

public class WifiManager extends BroadcastReceiver {

    private HashMap<String, WifiP2pDevice> connectedDevices = new HashMap<>();
    private HashMap<String, String> knownDevices = new HashMap<>();
    private WifiP2pManager manager;
    private Channel channel;
    private PeerListListener peerListListener;
    private Context context;
    private IntentFilter intentFilter;

    public WifiManager(Context context) {
        super();
        this.context = context;
        this.manager = (WifiP2pManager) this.context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(this.context, this.context.getMainLooper(), null);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        this.peerListListener = new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                for (WifiP2pDevice device : peers.getDeviceList()) {
//                    if (!connectedDevices.containsKey(device.deviceName)) {
                    connectTo(device);
//                    }
                }
            }
        };
        this.discoverPeers();
        HandshakeProcess.getInstance(context).start();
    }

    public void register() {
        this.context.registerReceiver(this, this.intentFilter);
    }

    public void unregister() {
        this.context.unregisterReceiver(this);
    }

    private void connectTo(final WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        this.manager.connect(this.channel, config, new ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(context.getString(R.string.log_wifi), String.format("connectTo Successful to %s", device.deviceName));
                connectedDevices.put(device.deviceName, device);
                if (device.isGroupOwner()) {
                    manager.requestConnectionInfo(channel, new ConnectionInfoListener() {
                        public void onConnectionInfoAvailable(final WifiP2pInfo info) {
                            Log.d(context.getString(R.string.log_wifi), String.format("requestConnectionInfo response for %s", device.deviceName));
                            HandshakeProcess.getInstance()
                                    .getClientProcess()
                                    .groupHost(info.groupOwnerAddress.getHostAddress())
                                    .port(8888)
                                    .timeout(500);
                            HandshakeProcess.getInstance()
                                    .send(WifiDevice.getLocalDevice(context));
                        }
                    });
                }
            }

            @Override
            public void onFailure(int reason) {
                Log.d(context.getString(R.string.log_wifi), "connectTo Failed");
            }
        });
    }

    private void discoverPeers() {
        this.manager.discoverPeers(this.channel, new ActionListener() {
            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.
                Log.d(context.getString(R.string.log_wifi), "Discovery Successful");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                Log.d(context.getString(R.string.log_wifi), "Discovery Failed");
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
                    Log.d(context.getString(R.string.log_wifi), "Enabled");
                } else { // Wi-Fi P2P is not enabled
                    Log.d(context.getString(R.string.log_wifi), "Disabled");
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
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION: // Respond to this device's wifi state changing
                break;
        }
    }
}
