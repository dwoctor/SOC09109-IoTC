package uk.ac.napier.communicator.communication.connections.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WiFiServer extends AsyncTask {

    private Context context;
    private TextView statusText;

    public WiFiServer(Context context, View statusText) {
        this.context = context;
        this.statusText = (TextView) statusText;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {

            /**
             * Create a server socket and wait for client connections. This
             * call blocks until a connection is accepted from a client
             */
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            /**
             * If this code is reached, a client has connected and transferred data
             * Save the input stream from the client as a JPEG file
             */
            final File file = new File(Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis() + ".jpg");

            File dirs = new File(file.getParent());
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            file.createNewFile();
            InputStream inputstream = client.getInputStream();
            ByteStreams.copy(inputstream, new FileOutputStream(file));
            serverSocket.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            System.out.println(e.getMessage());

            // Log.e(WiFiDirectActivity.TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Start activity that can handle the JPEG image
     */
    protected void onPostExecute(String result) {
        if (result != null) {
            statusText.setText("File copied - " + result);
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + result), "image/*");
            context.startActivity(intent);
        }
    }
}