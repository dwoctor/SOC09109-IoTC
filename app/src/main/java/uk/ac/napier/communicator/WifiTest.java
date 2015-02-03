package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import uk.ac.napier.communicator.communication.connections.wifi.WiFi;

public class WifiTest extends ActionBarActivity {

    //WifiP2pManager wifiManager;
    //Channel wifiChannel;
    //BroadcastReceiver wifiReceiver;
    //IntentFilter wifiIntentFilter;

    WiFi wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_test);

        //this.wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        //this.wifiChannel = this.wifiManager.initialize(this, getMainLooper(), null);
        //this.wifiReceiver = new WiFi(this.wifiManager, this.wifiChannel, this);

        //wifiIntentFilter = new IntentFilter();
        //wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        //wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        //wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        //wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        this.wifi = new WiFi(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(this.wifiReceiver, this.wifiIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(this.wifiReceiver);
    }
}
