package uk.ac.napier.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Tests extends ActionBarActivity {

    private Button helloTestButton;
    private Button wifiLocalTestButton;
    private Button wifiLocalNewUiTestButton;
    private Button wifiDirectTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        this.helloTestButton = this.getHelloTestButton();
        this.wifiLocalTestButton = this.getWifiLocalTestButton();
        this.wifiLocalNewUiTestButton = this.getWifiLocalNewUiTestButton();
        this.wifiDirectTestButton = this.getWifiDirectTestButton();
    }

    private Button getHelloTestButton() {
        if (this.helloTestButton == null) {
            Button helloTestButton = (Button) findViewById(R.id.buttonHelloTest);
            helloTestButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent helloTestIntent = new Intent(Tests.this, HelloWorldTest.class);
                    Tests.this.startActivity(helloTestIntent);
                }
            });
            this.helloTestButton = helloTestButton;
        }
        return this.helloTestButton;
    }

    private Button getWifiLocalTestButton() {
        if (this.wifiLocalTestButton == null) {
            Button wifiLocalTestButton = (Button) findViewById(R.id.buttonWifiLocalTest);
            wifiLocalTestButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent wifiTestIntent = new Intent(Tests.this, WifiLocalTest.class);
                    Tests.this.startActivity(wifiTestIntent);
                }
            });
            this.wifiLocalTestButton = wifiLocalTestButton;
        }
        return this.wifiLocalTestButton;
    }

    private Button getWifiLocalNewUiTestButton() {
        if (this.wifiLocalNewUiTestButton == null) {
            Button wifiLocalTestButton = (Button) findViewById(R.id.buttonWifiLocalNewUiTest);
            wifiLocalTestButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent wifiTestIntent = new Intent(Tests.this, WifiLocalNewUiTest.class);
                    Tests.this.startActivity(wifiTestIntent);
                }
            });
            this.wifiLocalNewUiTestButton = wifiLocalTestButton;
        }
        return this.wifiLocalNewUiTestButton;
    }

    private Button getWifiDirectTestButton() {
        if (this.wifiDirectTestButton == null) {
            Button wifiDirectTestButton = (Button) findViewById(R.id.buttonWifiDirectTest);
            wifiDirectTestButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent wifiTestIntent = new Intent(Tests.this, WifiDirectTest.class);
                    Tests.this.startActivity(wifiTestIntent);
                }
            });
            this.wifiDirectTestButton = wifiDirectTestButton;
        }
        return this.wifiDirectTestButton;
    }

}
