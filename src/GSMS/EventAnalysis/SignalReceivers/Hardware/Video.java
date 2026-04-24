package GSMS.EventAnalysis.SignalReceivers.Hardware;

/**
 * class to stand as component for camera feed recipient
 */

public class Video {

    private String videoData;
    //TODO: shift so that audio can respond back to clasroom of signals read.
    public Video() {

    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        videoData = signal;
    } // end method

} // end class