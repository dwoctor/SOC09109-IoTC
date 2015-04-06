package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import uk.ac.napier.communicator.communication.connections.wifi.local.WifiManager;

public class WifiLocalNewUiTestActivity extends FragmentActivity {

    WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_wifi_local_new_ui_test);

        // Set up Wifi
        this.wifi = new WifiManager();
    }

}
