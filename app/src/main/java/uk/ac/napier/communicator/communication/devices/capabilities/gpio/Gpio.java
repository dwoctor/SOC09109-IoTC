package uk.ac.napier.communicator.communication.devices.capabilities.gpio;

import uk.ac.napier.communicator.communication.devices.capabilities.Ability;
import uk.ac.napier.communicator.communication.devices.capabilities.Capability;

public class Gpio extends Capability {

    public Gpio() {
        super(Ability.Gpio);
    }

    public GpioCommand createCommand(Integer pin, Boolean state) {
        return new GpioCommand(pin, state);
    }

    public GpioState createState(Integer pin) {
        return new GpioState(pin);
    }

}
