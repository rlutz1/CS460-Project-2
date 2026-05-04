package Gym.Hardware;

import GSMS.Common.AgentId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.WearableListener;

public class WearableSensors implements Hardware {

    public WearableListener component; // to receive signals from front end
    public AgentId member;     // for readability
    private String scenarioSignal = "normal";   // default

    public WearableSensors(AgentId member) {
        this.member = member;
    }

    /** Set the signal data (without the member prefix) that will be sent. */
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
        // Prepend the member ID so the backend can resolve the target agent.
        String fullSignal = member.getId() + ":" + scenarioSignal;
        System.out.println("WearableListener ping: " + fullSignal);
        component.receiveSignal(fullSignal);
    }
}
