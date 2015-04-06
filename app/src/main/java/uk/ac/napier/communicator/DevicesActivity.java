package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import uk.ac.napier.communicator.communication.connections.wifi.local.WifiManager;

public class DevicesActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_devices);

        // Set up Wifi
        WifiManager.discoverDevices();
    }

}
