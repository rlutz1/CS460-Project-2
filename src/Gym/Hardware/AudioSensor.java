package Gym.Hardware;

import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.Audio;

public class AudioSensor implements Hardware {

    public Audio component; // to receive signals from front end
    public RoomId room; // for readability

    public AudioSensor(RoomId room) {
        this.room = room;
    } // end constructor

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    public void sendSignal() {
        System.out.println("Audio sensor ping.");
        component.receiveSignal("10");
    } // end method

} // end class
