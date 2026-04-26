package GSMS.EventAnalysis.SignalReceivers;

import GSMS.EventAnalysis.SignalReceivers.Hardware.Audio;
import Gym.Hardware.Camera;

import static GSMS.EventAnalysis.SignalReceivers.SignalType.AUDIO;

/**
 * class to stand as component for classroom abstraction
 */

public class Classroom {

    private int classroomId;

    private String videoFeedData;
    private Integer audioDecibelData;
    private String wearableInfoData;

    public Classroom(int classroomId) {
        this.classroomId = classroomId;
    } // end constructor

    /**
     * entry point to receive signal
     * data tied to this specific classroom’s sensors and feeds.
     * @param signal
     * @param signalType
     */
    public void receiveSignal(Signal<?> signal, SignalType signalType) {
        switch (signalType) {
            case AUDIO:
                audioDecibelData = ((Signal<Integer>) signal).getSignalData();
                break;
            case VIDEO:
                videoFeedData = ((Signal<String>) signal).getSignalData();
                break;
            case WEARABLE:
                wearableInfoData = ((Signal<String>) signal).getSignalData();
                break;
        }
    } // end method

} // end class