package uk.ac.napier.communicator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.napier.communicator.communication.connections.WiFi;
import uk.ac.napier.communicator.communication.logistics.Postie;
import uk.ac.napier.communicator.communication.messages.SimpleMessage;
import uk.ac.napier.communicator.ui.EditTextComponent;


public class Devices extends ActionBarActivity {

    WifiP2pManager wifiManager;
    WifiP2pManager.Channel wifiChannel;
    BroadcastReceiver wifiReceiver;
    IntentFilter wifiIntentFilter;
    Postie pat;
    private Button startButton;
    private EditText startText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        // Set up WiFi
        this.wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        this.wifiChannel = this.wifiManager.initialize(this, getMainLooper(), null);
        this.wifiReceiver = new WiFi(this.wifiManager, this.wifiChannel);
        this.wifiIntentFilter = new IntentFilter();
        this.wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        this.wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        this.wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        this.wifiIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        this.startButton = this.getStartButton();
        this.startText = this.getStartText();
        this.pat = Postie.getInstance();
        this.pat.getPrintProcess().add(new EditTextComponent(this.getStartText()));
        this.pat.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(this.wifiReceiver, this.wifiIntentFilter); // register the broadcast receiver with the intent values to be matched
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.wifiReceiver); // unregister the broadcast receiver
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_devices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private Button getStartButton() {
        if (this.startButton == null) {
            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    try {
                        pat.post(new SimpleMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            this.startButton = startButton;
        }
        return this.startButton;
    }

    private EditText getStartText() {
        if (this.startText == null) {
            EditText startText = (EditText) findViewById(R.id.startText);
            this.startText = startText;
        }
        return this.startText;
    }
}

