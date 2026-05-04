package GSMS.EventAnalysis.SignalReceivers.Hardware;

import GSMS.Common.AgentId;
import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Signal;
import GSMS.EventAnalysis.SignalReceivers.SignalType;

/**
 * class to stand as component for wearable signal recipient,
 * INCLUDING: medical info from wearable && doorway rfid sensors
 */

public class Wearable {

    private Classroom classroom;
    private String wearableData;
    private AgentId memberId;

    /**
     * Constructs Wearable with a tied Classroom to send signals to.
     * @param classroom
     */
    public Wearable(Classroom classroom, AgentId memberId) {
        this.classroom = classroom;
        this.memberId = memberId;
    } // end constructor

    public AgentId getMemberIdFromWearable() {
        return memberId;
    }

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        wearableData = signal;

        // The front-end sends "memberId:scenarioSignal", extract the memberId.
        String[] parts = signal.split(":", 2);
        if (parts.length == 2) {
            memberId = new AgentId(parts[0].trim());
        }

        Signal<String> sig = new Signal<>(wearableData);
        sig.setAgentId(memberId);            // attach the ID so Classroom can use it
        classroom.receiveSignal(sig, SignalType.WEARABLE);
    } // end method

} // end class