package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import uk.ac.napier.communicator.communication.connections.wifi.local.WifiManager;
import uk.ac.napier.communicator.communication.devices.Device;
import uk.ac.napier.communicator.communication.devices.Devices;
import uk.ac.napier.communicator.communication.devices.WifiDevice;
import uk.ac.napier.communicator.communication.devices.capabilities.Capability;
import uk.ac.napier.communicator.communication.devices.capabilities.GPIO;
import uk.ac.napier.communicator.communication.logistics.Postie;
import uk.ac.napier.communicator.ui.ArrayAdapterComponent;
import uk.ac.napier.communicator.ui.EditTextComponent;

public class WifiLocalTest extends ActionBarActivity {

    WifiManager wifi;
    ListView devicesListView;
    ArrayAdapter devicesAdapter;
    EditText incomingMessagesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_local_test);

        // Set up GUI
        this.devicesListView = getDevicesListView();

        // Set up Wifi
        this.wifi = new WifiManager(this);
        Postie.getInstance().getPrintProcess().add(new EditTextComponent(this.getIncomingMessagesEditText()));
        Postie.getInstance().start();
    }

    private ListView getDevicesListView() {
        if (this.devicesListView == null) {
            ListView devicesListView = (ListView) findViewById(R.id.devices);
            devicesListView.setAdapter(this.getDevicesListAdapter());
            devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Device item = (Device) parent.getAdapter().getItem(position);
                    if (item.isWifiDevice()) {
                        WifiDevice wifiDevice = item.getWifiInfo();
                        try {
                            //Postie.getInstance().post(new BinaryMessage(wifiDevice.getIp(), 9999, 500, item.toString().getBytes()));
                            Capability capability = wifiDevice.getCapability();
                            if (capability instanceof GPIO) {
                                ((GPIO) capability).createCommand(3, true).address(wifiDevice.getIp()).port(2222).timeout(500).send();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            this.devicesListView = devicesListView;
        }
        return this.devicesListView;
    }

    private ArrayAdapter getDevicesListAdapter() {
        if (this.devicesAdapter == null) {
            ArrayAdapter devicesAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, Devices.getInstance().getDevices());
            Devices.getInstance().addObservers(new ArrayAdapterComponent(devicesAdapter));
            this.devicesAdapter = devicesAdapter;
        }
        return this.devicesAdapter;
    }

    private EditText getIncomingMessagesEditText() {
        if (this.incomingMessagesEditText == null) {
            EditText incomingMessagesEditText = (EditText) findViewById(R.id.incomingMessages);
            this.incomingMessagesEditText = incomingMessagesEditText;
        }
        return this.incomingMessagesEditText;
    }
}
