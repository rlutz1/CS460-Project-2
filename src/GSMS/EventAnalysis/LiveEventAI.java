package GSMS.EventAnalysis;

import GSMS.Common.AgentId;
import GSMS.Common.AgentType;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.SignalType;
import GSMS.Notification.AlertLevel;

/**
 * class to stand as component for live event AI
 */

public class LiveEventAI {

    public LiveEventAI() {
    } // end constructor

    /**
     * analyzes the data of a given signal type
     * (audio, visual, vitals).
     * If a safety/conflict event is detected,
     * it returns an event category as well as an
     * associated probability based on the given processed
     * data.
     * @param data
     * @param signalType
     * @return non-null event safety/conflict details;
     *         null, when no safety/conflict event detected.
     */
    public Event detectEvent(String data, SignalType signalType) {

        // dummy event detection for now (prior to actual Ai).
        AlertLevel alertLevel = AlertLevel.WARNING;
        String eventInfo = "GENERIC  EVENT  DETECTED";
        boolean safetyOrConflictDetected = true;
        double confidenceProbability = 0.8;

        if ( safetyOrConflictDetected ) {
            return new Event(alertLevel, eventInfo, confidenceProbability,
                    new AgentId("JFONDA1", "Jane Fonda", AgentType.INSTRUCTOR)); // USE FOR TESTING AS NEEDED!
//                    new AgentId("");
        }
        return null;

    } // end method

} // end class