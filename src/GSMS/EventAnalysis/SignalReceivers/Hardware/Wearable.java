package GSMS.EventAnalysis.SignalReceivers.Hardware;

/**
 * class to stand as component for wearable signal recipient,
 * INCLUDING: medical info from wearable && doorway rfid sensors
 */

public class Wearable {

    private String wearableData;
    //TODO: shift so that audio can respond back to clasroom of signals read.
    public Wearable() {

    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        wearableData = signal;
    } // end method

} // end class