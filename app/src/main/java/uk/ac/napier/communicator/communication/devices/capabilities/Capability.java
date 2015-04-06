package uk.ac.napier.communicator.communication.devices.capabilities;

public abstract class Capability {
    private Ability capableOf;

    protected Capability(Ability capableOf) {
        this.capableOf = capableOf;
    }
}
