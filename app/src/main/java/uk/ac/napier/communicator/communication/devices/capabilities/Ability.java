package uk.ac.napier.communicator.communication.devices.capabilities;

import com.google.gson.annotations.SerializedName;

public enum Ability {
    @SerializedName("gpio")
    Gpio {
        @Override
        public String toString() {
            return "GPIO";
        }
    }
}
