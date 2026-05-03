package Gym.Hardware;

import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.Video;

public class Camera implements Hardware {

    public Video component; // to receive signals from front end
    public RoomId room; // for readability

    public Camera(RoomId room) {
        this.room = room;
    } // end constructor

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    public void sendSignal() {
        System.out.println("Camera feed ping.");
        component.receiveSignal("violence"); // TODO
    } // end method

} // end class
