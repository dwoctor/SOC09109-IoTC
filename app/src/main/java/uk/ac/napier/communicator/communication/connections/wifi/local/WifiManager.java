package uk.ac.napier.communicator.communication.connections.wifi.local;

public class WifiManager {

    public static void discoverDevices() {
        for (Integer i = 2; i <= 11; i++) {
            new HandshakeRequest().address(String.format("192.168.43.%d", i)).port(1111).timeout(500).send();
        }
    }

}
