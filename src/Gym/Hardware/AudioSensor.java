package Gym.Hardware;

import GSMS.EventAnalysis.SignalReceivers.Hardware.Audio;

public class AudioSensor {

    public Audio component; // to receive signals from front end

    public AudioSensor() {

    } // end constructor

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    public void sendSignal() {
        System.out.println("Audio sensor ping.");
        // TODO: when GSMC init, give this a reference to the associated component.
        // then: component.receiveSignal(...)
    } // end method

} // end class
