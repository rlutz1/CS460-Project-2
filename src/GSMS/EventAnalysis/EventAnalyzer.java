package GSMS.EventAnalysis;

/**
 * class to stand as component for event analyzer
 */

public class EventAnalyzer {

    public EventAnalyzer() {

    } // end constructor

    /*
    getSystemState(roomID):
    returns current occupancy, active alerts, and activities of a given room.

    scanRooms():
    call to scan all rooms in the gym
    and retrieve data from various devices
    */

    public void verifyRoomAccess(String roomId, String memberId) {}

    public void pushAttendance(String classId, String agentIds) {}

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

    /**
     * sends an alert/notification to the
     * Notification Dispatcher, as well as its alert level
     * and an optional target identification
     * (instructor, member).
     * @param alert
     * @param alertLevel
     * @param targetId
     */
    public void pushAlert(String alert, String alertLevel, String targetId) {

    } // end method



} // end class