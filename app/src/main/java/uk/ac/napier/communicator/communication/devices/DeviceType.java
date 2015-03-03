package uk.ac.napier.communicator.communication.devices;

import com.google.gson.annotations.SerializedName;

public enum DeviceType {
    @SerializedName("wifi")
    Wifi {
        @Override
        public String toString() {
            return "Wifi";
        }
    },
    @SerializedName("bluetooth")
    Bluetooth {
        @Override
        public String toString() {
            return "Bluetooth";
        }
    }
}
