package GSMS.Backend.EventAnalysis;

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
     * @param signalType
     * @param data
     * @return
     */
    public String detectEvent(String signalType, String data) {
        return null;
    } // end method

} // end class