package Gym.Hardware;

import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.Audio;

public class AudioSensor implements Hardware {

    public Audio component; // to receive signals from front end
    public RoomId room;     // for readability
    private String scenarioSignal = "10";   // default value

    public AudioSensor(RoomId room) {
        this.room = room;
    }

    /** Set the signal content that will be sent to the backend.
     *  Use before calling sendSignal() in a demo scenario.
     */
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
        System.out.println("Audio sensor ping: " + scenarioSignal);
        component.receiveSignal(scenarioSignal);
    }
}