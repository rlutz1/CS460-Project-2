package Gym.Hardware;

import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.VideoListener;

public class Camera implements Hardware {

    public VideoListener component; // to receive signals from front end
    public RoomId room;     // for readability
    private String scenarioSignal = "normal";   // default

    public Camera(RoomId room) {
        this.room = room;
    }

    /** Set the signal content that will be sent to the backend. */
    public void setScenarioSignal(String scenarioSignal) {
        this.scenarioSignal = scenarioSignal;
    }

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    @Override
    public void sendSignal() {
        System.out.println("Camera feed ping: " + scenarioSignal);
        component.receiveSignal(scenarioSignal);
    } // end method
} // end class
