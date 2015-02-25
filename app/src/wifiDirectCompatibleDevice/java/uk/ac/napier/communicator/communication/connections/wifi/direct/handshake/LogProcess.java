package uk.ac.napier.communicator.communication.connections.wifi.direct.handshake;

import android.util.Log;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;

import static java.lang.System.arraycopy;

public class LogProcess implements CSProcess {

    private final Alternative channelAlternatives;
    private final String logTag;
    private final int DEBUG = 0;
    private final int ERROR = 1;
    private final int INFO = 2;
    private final int VERBOSE = 3;
    private final int WARN = 4;
    private AltingChannelInput<String>[] inChannels = null;

    public LogProcess(String logTag, AltingChannelInput<String> debugInChannel, AltingChannelInput<String> errorInChannel, AltingChannelInput<String> infoInChannel, AltingChannelInput<String> verboseInChannel, AltingChannelInput<String> warnInChannel) {
        this.logTag = logTag;
        inChannels = addChannelAlternative(debugInChannel, inChannels, DEBUG);
        inChannels = addChannelAlternative(errorInChannel, inChannels, ERROR);
        inChannels = addChannelAlternative(infoInChannel, inChannels, INFO);
        inChannels = addChannelAlternative(verboseInChannel, inChannels, VERBOSE);
        inChannels = addChannelAlternative(warnInChannel, inChannels, WARN);
        channelAlternatives = new Alternative(this.inChannels);
    }

    private AltingChannelInput<String>[] addChannelAlternative(AltingChannelInput<String> channelAlternative, AltingChannelInput<String>[] oldChannelAlternatives, int index) {
        if (channelAlternative == null) {
            return oldChannelAlternatives;
        }
        AltingChannelInput<String>[] newChannelAlternatives;
        if (oldChannelAlternatives == null) newChannelAlternatives = new AltingChannelInput[1];
        else {
            newChannelAlternatives = new AltingChannelInput[oldChannelAlternatives.length + 1];
            arraycopy(oldChannelAlternatives, 0, newChannelAlternatives, 0, oldChannelAlternatives.length);
        }
        newChannelAlternatives[newChannelAlternatives.length - 1] = channelAlternative;
        return newChannelAlternatives;
    }

    public void run() {
        while (true) {
            switch (channelAlternatives.select()) {
                case DEBUG:
                    Log.d(logTag, inChannels[DEBUG].read());
                    break;
                case ERROR:
                    Log.e(logTag, inChannels[ERROR].read());
                    break;
                case INFO:
                    Log.i(logTag, inChannels[INFO].read());
                    break;
                case VERBOSE:
                    Log.v(logTag, inChannels[VERBOSE].read());
                    break;
                case WARN:
                    Log.w(logTag, inChannels[WARN].read());
                    break;
            }
        }
    }

}