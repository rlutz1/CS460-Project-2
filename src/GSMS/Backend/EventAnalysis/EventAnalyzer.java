package GSMS.Backend.EventAnalysis;

/**
 * class to stand as component for event analyzer
 */

public class EventAnalyzer {

    public EventAnalyzer() {

    } // end constructor

    /**
     * call to scan all rooms in the gym
     * and retrieve data from various devices
     */
    public void scanRooms() {

    } // end method

    /**
     * entry point to receive decibel levels collected
     * from audio sensors in a given room
     * @param roomId
     */
    public void getAudioData(String roomId) {

    } // end method

    /**
     * entry point to receive video feed collected
     * from video cameras in a given room
     * @param roomId
     */
    public void getVideoData(String roomId) {

    } // end method

    /**
     * entry point to receive a list of member vitals
     * collected from wearable devices from all members in a given room.
     * Additionally an entry point to receive signals from doorway sensors
     * as read from the member wearables
     * @param roomId
     * @param memberId
     */
    public void getWearableData(String roomId, String memberId) {

    } // end method



} // end class