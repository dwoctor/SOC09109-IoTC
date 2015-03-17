package uk.ac.napier.communicator.communication.devices.capabilities;

public class GPIO extends Capability {

    public GPIO() {
        super(Ability.Gpio);
    }

    public GPIOCommand createCommand(Integer pin, Boolean state) {
        return new GPIOCommand(pin, state);
    }

    public GPIOState createState(Integer pin) {
        return new GPIOState(pin);
    }

}
