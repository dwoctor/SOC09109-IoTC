package uk.ac.napier.communicator.communication.devices;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class WifiDevice extends Device implements Serializable {

    private static WifiDevice localDevice = null;

    private static HashMap<String, String> names;

    static {
        names = new HashMap<String, String>();
        names.put("192.168.43.1", "Merlot");
        names.put("192.168.43.2", "Cabernet Sauvignon");
        names.put("192.168.43.3", "Pinot Noir");
        names.put("192.168.43.4", "Shiraz");
        names.put("192.168.43.5", "Sangiovese");
        names.put("192.168.43.6", "Malbec");
        names.put("192.168.43.7", "Tempranillo");
        names.put("192.168.43.8", "Gamay");
        names.put("192.168.43.9", "Zinfandel");
        names.put("192.168.43.10", "Chardonnay");
    }

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

    @Override
    public String getName() {
        return names.get(this.getIp());
    }

    public String getIp() {
        return this.ip;
    }

    public String getMac() {
        return this.mac;
    }

}
