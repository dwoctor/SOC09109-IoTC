package uk.ac.napier.communicator.communication.devices;

public enum DeviceType {
    Wifi {
        @Override
        public String toString() {
            return "Wifi";
        }
    },
    Bluetooth {
        @Override
        public String toString() {
            return "Bluetooth";
        }
    }
}
