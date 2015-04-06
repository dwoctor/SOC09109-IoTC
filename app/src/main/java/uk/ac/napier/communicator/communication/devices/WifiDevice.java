package uk.ac.napier.communicator.communication.devices;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class WifiDevice extends Device implements Serializable {

    private static WifiDevice localDevice = null;

    private static HashMap<String, String> names;

    static {
        names = new HashMap<String, String>();
        names.put("192.168.43.2", "Merlot");
        names.put("192.168.43.3", "Cabernet Sauvignon");
        names.put("192.168.43.4", "Pinot Noir");
        names.put("192.168.43.5", "Shiraz");
        names.put("192.168.43.6", "Sangiovese");
        names.put("192.168.43.7", "Malbec");
        names.put("192.168.43.8", "Tempranillo");
        names.put("192.168.43.9", "Gamay");
        names.put("192.168.43.10", "Zinfandel");
        names.put("192.168.43.11", "Chardonnay");
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
