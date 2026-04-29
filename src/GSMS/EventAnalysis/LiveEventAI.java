package GSMS.EventAnalysis;

/**
 * class to stand as component for live event AI
 */

public class LiveEventAI {

    private final double DETECTION_THRESHOLD = 0.8;

    private EventAnalyzer eventAnalyzer;

    public LiveEventAI(EventAnalyzer eventAnalyzer) {
        this.eventAnalyzer = eventAnalyzer;
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
     * @return
     */
    public String detectEvent(String data, String signalType) {

        // dummy data for now (prior to actual Ai).
        String alertLevel = "WARNING";
        String notifInfo = "Member has fallen."
        boolean safetyOrConflictDetected = false;
        double confidenceProbability = 0.5;

        if (safetyOrConflictDetected && confidenceProbability > DETECTION_THRESHOLD) {
            eventAnalyzer.pushAlert(alertLevel, notifInfo, "eliud123");
        }

        // TODO: flesh out with randomized imitated data prior to full on Ai.
        return null;
    } // end method

} // end class