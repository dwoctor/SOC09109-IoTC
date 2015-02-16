package uk.ac.napier.communicator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.napier.communicator.communication.logistics.Postie;
import uk.ac.napier.communicator.communication.messages.bidirectional.SimpleMessage;
import uk.ac.napier.communicator.ui.EditTextComponent;

public class HelloWorldTest extends ActionBarActivity {

    private Button startButton;
    private EditText startText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_test);
        this.startButton = this.getStartButton();
        this.startText = this.getStartText();
        Postie.getInstance().getPrintProcess().add(new EditTextComponent(this.getStartText()));
        Postie.getInstance().start();
    }

    private Button getStartButton() {
        if (this.startButton == null) {
            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Postie.getInstance().post(new SimpleMessage());
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

