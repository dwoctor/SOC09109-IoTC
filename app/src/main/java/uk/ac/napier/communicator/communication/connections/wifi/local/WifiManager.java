package uk.ac.napier.communicator.communication.connections.wifi.local;

public class WifiManager {

    public WifiManager() {
        super();
        this.discoverPeers();
    }

    private void discoverPeers() {
        //for (Integer i = 0; i < 255; i++) {
        for (Integer i = 0; i < 10; i++) {
            new HandshakeRequest().address(String.format("192.168.0.%d", i)).port(1111).timeout(500).send();
        }
    }

}
