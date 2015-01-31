package uk.ac.napier.communicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Tests extends ActionBarActivity {

    private Button helloTestButton;
    private Button wifiTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        this.helloTestButton = this.getHelloTestButton();
        this.wifiTestButton = this.getWifiTestButton();
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

    private Button getWifiTestButton() {
        if (this.wifiTestButton == null) {
            Button wifiTestButton = (Button) findViewById(R.id.buttonWiFiTest);
            wifiTestButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent wifiTestIntent = new Intent(Tests.this, WifiTest.class);
                    Tests.this.startActivity(wifiTestIntent);
                }
            });
            this.wifiTestButton = wifiTestButton;
        }
        return this.wifiTestButton;
    }
}
