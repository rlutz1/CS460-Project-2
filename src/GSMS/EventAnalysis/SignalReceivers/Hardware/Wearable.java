package GSMS.EventAnalysis.SignalReceivers.Hardware;

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

    /**
     * Constructs Wearable with a tied Classroom to send signals to.
     * @param classroom
     */
    public Wearable(Classroom classroom) {
        this.classroom = classroom;
    } // end constructor

    /**
     * entry point to receive signal data tied
     * to this specific classroom’s sensors and feeds.
     * @param signal
     */
    public void receiveSignal(String signal) {
        wearableData = signal;
        classroom.receiveSignal(new Signal<String>(wearableData),
                                    SignalType.WEARABLE);
    } // end method

} // end class