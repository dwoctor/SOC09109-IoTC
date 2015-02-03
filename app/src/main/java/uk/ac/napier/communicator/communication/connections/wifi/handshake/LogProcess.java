package uk.ac.napier.communicator.communication.connections.wifi.handshake;

import android.util.Log;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;

public class LogProcess implements CSProcess {

    private final AltingChannelInput<String> debugInChannel, errorInChannel, infoInChannel, verboseInChannel, warnInChannel;
    private final Guard[] channelGuard;
    private final Alternative channelAlternatives;
    private final String logTag;
    private final int DEBUG = 0;
    private final int ERROR = 1;
    private final int INFO = 2;
    private final int VERBOSE = 3;
    private final int WARN = 4;

    public LogProcess(String logTag, AltingChannelInput<String> debugInChannel, AltingChannelInput<String> errorInChannel, AltingChannelInput<String> infoInChannel, AltingChannelInput<String> verboseInChannel, AltingChannelInput<String> warnInChannel) {
        this.logTag = logTag;
        this.debugInChannel = debugInChannel;
        this.errorInChannel = errorInChannel;
        this.infoInChannel = infoInChannel;
        this.verboseInChannel = verboseInChannel;
        this.warnInChannel = warnInChannel;
        this.channelGuard = new Guard[]{this.debugInChannel, this.errorInChannel, this.infoInChannel, this.verboseInChannel, this.warnInChannel};
        this.channelAlternatives = new Alternative(this.channelGuard);
    }

    public void run() {
        while (true) {
            switch (channelAlternatives.select()) {
                case DEBUG:
                    Log.d(logTag, this.debugInChannel.read());
                    break;
                case ERROR:
                    Log.e(logTag, this.debugInChannel.read());
                    break;
                case INFO:
                    Log.i(logTag, this.debugInChannel.read());
                    break;
                case VERBOSE:
                    Log.v(logTag, this.debugInChannel.read());
                    break;
                case WARN:
                    Log.w(logTag, this.debugInChannel.read());
                    break;
            }
        }
    }
}