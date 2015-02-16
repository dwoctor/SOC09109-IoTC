package uk.ac.napier.communicator.communication.connections.wifi.devices;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Random;

import uk.ac.napier.communicator.communication.devices.Device;
import uk.ac.napier.communicator.communication.devices.DeviceType;

public class WifiDevice extends Device implements Serializable {

    private static WifiDevice localDevice = null;

    @Expose
    @SerializedName("ip")
    private String ip;

    @Expose
    @SerializedName("mac")
    private String mac;

    public WifiDevice(String name, String ip, String mac) {
        super(name, DeviceType.Wifi);
        this.ip = ip;
        this.mac = mac;
    }

    public static WifiDevice dejsonize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, WifiDevice.class);
    }

    public static WifiDevice getLocalDevice(Context context) {
        if (WifiDevice.localDevice == null) {
            WifiManager myWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
            int ip = myWifiInfo.getIpAddress();
            String ipAddress = Formatter.formatIpAddress(ip);
            String name = String.format("IoT-Android-%d %s", new Random().nextInt(), ipAddress);
            WifiDevice.localDevice = new WifiDevice(name, ipAddress, myWifiInfo.getMacAddress());
        }
        return WifiDevice.localDevice;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

}
