package GSMS.EventAnalysis.SignalReceivers.Hardware;

import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Signal;
import GSMS.EventAnalysis.SignalReceivers.SignalType;

/**
 * class to stand as component for audio sensor recipient
 */

public class AudioListener {

    private Classroom classroom;
    private int decibelLevel;

    /**
     * Constructs AudioListener with a tied Classroom to send signals to.
     * @param classroom
     */
    public AudioListener(Classroom classroom) {
        this.classroom = classroom;
    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        decibelLevel = Integer.parseInt(signal);
        classroom.receiveSignal(new Signal<Integer>(decibelLevel),
                                    SignalType.AUDIO);
    } // end method

} // end class