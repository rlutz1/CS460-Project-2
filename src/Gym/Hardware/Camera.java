package Gym.Hardware;

import GSMS.EventAnalysis.SignalReceivers.Hardware.Video;

public class Camera {

    public Video component; // to receive signals from front end

    public Camera() {

    } // end constructor

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    public void sendSignal() {
        System.out.println("Camera feed ping.");
        // TODO: when GSMC init, give this a reference to the associated component.
        // then: component.receiveSignal(...)
    } // end method

} // end class
