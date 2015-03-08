package uk.ac.napier.communicator.communication.connections.wifi.direct.handshake;

import android.content.Context;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

import uk.ac.napier.communicator.R;
import uk.ac.napier.communicator.communication.devices.WifiDevice;

public class HandshakeProcess implements Runnable {

    private static HandshakeProcess instance = null;

    private Thread thread = new Thread(this);

    private One2OneChannel client = Channel.one2one();
    private One2OneChannel server = Channel.one2one();

    private Any2OneChannel debugLogger = Channel.any2one();
    private Any2OneChannel errorLogger = Channel.any2one();
    private Any2OneChannel infoLogger = Channel.any2one();

    private ClientProcess clientProcess;
    private ServerProcess serverProcess;
    private LogProcess loggerProcess;

    private Parallel jobs = new Parallel();

    private HandshakeProcess(Context context) {
        clientProcess = new ClientProcess(client.in(), debugLogger.out(), errorLogger.out(), infoLogger.out());
        serverProcess = new ServerProcess(server.out(), debugLogger.out(), errorLogger.out(), infoLogger.out());
        loggerProcess = new LogProcess(context.getString(R.string.log_wifi), debugLogger.in(), errorLogger.in(), infoLogger.in(), null, null);
        jobs.addProcess(loggerProcess);
        jobs.addProcess(clientProcess);
        jobs.addProcess(serverProcess);
    }

    public static synchronized HandshakeProcess getInstance() {
        return instance;
    }

    public static synchronized HandshakeProcess getInstance(Context context) {
        if (instance == null) {
            instance = new HandshakeProcess(context);
        }
        return instance;
    }

    public void start() {
        if (this.thread.isAlive() == false) {
            thread.start();
        }
    }

    public void send(WifiDevice data) {
        this.client.out().write(data);
    }

    @Override
    public void run() {
        jobs.run();
    }

    public ClientProcess getClientProcess() {
        return clientProcess;
    }

}