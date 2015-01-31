package uk.ac.napier.communicator.communication.connections.wifi;

import android.content.Context;
import android.os.AsyncTask;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WiFiClient extends AsyncTask<String, Void, Boolean> {

    Context context;
    String host;
    int port;
    int len;
    Socket socket = new Socket();

    public WiFiClient(Context context, String host, int port, int len) {
        this.context = context;
        this.host = host;
        this.port = port;
        this.len = len;
    }

    protected Boolean doInBackground(String... params) {
        for (String param : params) {
            try {
                /**
                 * Create a client socket with the host,
                 * port, and timeout information.
                 */
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), 500);

                /**
                 * Create a byte stream from a JPEG file and pipe it to the output stream
                 * of the socket. This data will be retrieved by the server device.
                 */
                OutputStream outputStream = socket.getOutputStream();

                InputStream inputStream = new ByteArrayInputStream(param.getBytes());

                ByteStreams.copy(inputStream, outputStream);

                outputStream.close();

                inputStream.close();
            } catch (FileNotFoundException e) {
                //catch logic
            } catch (IOException e) {
                //catch logic
            }
            /**
             * Clean up any open sockets when done
             * transferring or if an exception occurred.
             */ finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //catch logic
                        }
                    }
                }
            }
        }
        return true;
    }

    protected void onPostExecute(Boolean result) {

    }
}
