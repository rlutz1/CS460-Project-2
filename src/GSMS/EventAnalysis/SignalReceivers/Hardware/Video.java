package GSMS.EventAnalysis.SignalReceivers.Hardware;

import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Signal;
import GSMS.EventAnalysis.SignalReceivers.SignalType;

/**
 * class to stand as component for camera feed recipient
 */

public class Video {

    private Classroom classroom;
    private String videoData;

    /**
     * Constructs Video with a tied Classroom to send signals to.
     * @param classroom
     */
    public Video(Classroom classroom) {
        this.classroom = classroom;
    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        videoData = signal;
        classroom.receiveSignal(new Signal<String>(videoData),
                                    SignalType.VIDEO);
    } // end method



} // end class