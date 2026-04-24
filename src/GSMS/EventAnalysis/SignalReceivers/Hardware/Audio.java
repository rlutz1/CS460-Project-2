package GSMS.EventAnalysis.SignalReceivers.Hardware;

import GSMS.EventAnalysis.SignalReceivers.Classroom;

/**
 * class to stand as component for audio sensor recipient
 */

public class Audio {

    private int decibelLevel;

    //TODO: shift so that audio can respond back to clasroom of signals read.
    public Audio() {
    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        decibelLevel = Integer.parseInt(signal);

    } // end method

} // end class