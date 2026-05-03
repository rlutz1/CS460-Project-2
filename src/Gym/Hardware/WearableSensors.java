package Gym.Hardware;

import GSMS.Common.AgentId;
import GSMS.EventAnalysis.SignalReceivers.Hardware.Wearable;

public class WearableSensors implements Hardware {

    public Wearable component; // to receive signals from front end
    public AgentId member; // for readability

    public WearableSensors(AgentId member) {
        this.member = member;
    } // end constructor

    /**
     * action taken by the device driver to send
     * either a live transmission continuously on
     * intervals over the network or upon trigger
     */
    public void sendSignal() {
        System.out.println("Wearable ping.");
        component.receiveSignal("exhaustion");
    } // end method

} // end class
