package uk.ac.napier.communicator.communication.connections.wifi.handshake;

import android.content.Context;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

import java.io.Serializable;

import uk.ac.napier.communicator.R;

public class HandshakeProcess implements Runnable {

    private static HandshakeProcess instance = null;
    private Thread thread = new Thread(this);

    private One2OneChannel inboundClient = Channel.one2one();
    private One2OneChannel outboundServer = Channel.one2one();

    private Any2OneChannel inboundDebugLogger = Channel.any2one();
    private Any2OneChannel inboundErrorLogger = Channel.any2one();

    private ClientProcess clientProcess;
    private ServerProcess serverProcess;
    private LogProcess loggerProcess;

    private Parallel jobs = new Parallel();

    private HandshakeProcess(Context context, String groupHost, Integer port, Integer timeout) {
        this.clientProcess = new ClientProcess(groupHost, port, timeout, inboundClient.in(), inboundDebugLogger.out(), inboundErrorLogger.out());
        this.serverProcess = new ServerProcess(outboundServer.out(), inboundDebugLogger.out(), inboundErrorLogger.out());
        this.loggerProcess = new LogProcess(context.getString(R.string.log_wifi), inboundDebugLogger.in(), inboundErrorLogger.in(), null, null, null);
        jobs.addProcess(this.clientProcess);
        jobs.addProcess(this.serverProcess);
        jobs.addProcess(this.loggerProcess);
    }

    public static synchronized HandshakeProcess getInstance() {
        return instance;
    }

    public static synchronized HandshakeProcess getInstance(Context context, String groupHost, Integer port, Integer timeout) {
        if (instance == null) {
            instance = new HandshakeProcess(context, groupHost, port, timeout);
        }
        return instance;
    }

    public void start() {
        if (this.thread.isAlive() == false) {
            thread.start();
        }
    }

    public void send(Serializable data) {
        this.inboundClient.out().write(data);
    }

    @Override
    public void run() {
        jobs.run();
    }
}