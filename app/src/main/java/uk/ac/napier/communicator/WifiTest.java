package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import uk.ac.napier.communicator.communication.connections.wifi.WifiManager;
import uk.ac.napier.communicator.communication.devices.Device;
import uk.ac.napier.communicator.communication.devices.Devices;

public class WifiTest extends ActionBarActivity {

    //WifiP2pManager wifiManager;
    //Channel wifiChannel;
    //BroadcastReceiver wifiReceiver;
    //IntentFilter wifiIntentFilter;

    WifiManager wifi;

    ListView devicesListView;
    ArrayAdapter devicesAdapter;

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

        this.wifi = new WifiManager(this);
        this.devicesListView = getDevicesListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(this.wifiReceiver, this.wifiIntentFilter);
        this.wifi.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(this.wifiReceiver);
        this.wifi.unregister();
    }

    private ListView getDevicesListView() {
        if (this.devicesListView == null) {
            ListView devicesListView = (ListView) findViewById(R.id.devices);
            devicesListView.setAdapter(this.getDevicesListAdapter());
            this.devicesListView = devicesListView;
        }
        return this.devicesListView;
    }

    private ArrayAdapter getDevicesListAdapter() {
        if (this.devicesAdapter == null) {
            this.devicesAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, Devices.getInstance().getDevices());
            Devices.getInstance().setDevicesAdapter(this.devicesAdapter);
        }
        return this.devicesAdapter;
    }
}
