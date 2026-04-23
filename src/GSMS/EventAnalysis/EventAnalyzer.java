package GSMS.EventAnalysis;

import java.util.List;

/**
 * class to stand as component for event analyzer
 */

public class EventAnalyzer {

    public EventAnalyzer() {

    } // end constructor

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
     * given a doorway sensor’s signal of access to a room,
     * initiate a deterministic protocol to verify that this
     * member should be in the room, tracking attendance if they are, and sending
     * notifications to attendants if they are not.
     * @param roomId
     * @param memberId
     */
    public void verifyRoomAccess(String roomId, String memberId) {

    } // end method

    /**
     * internal functionality to
     * send a single member, or optionally a list of members,
     * to the Logger to log their attendance
     * @param classId
     * @param memberId
     */
    public void pushAttendance(String classId, String memberId) {

    } // end method

    /**
     * overload:
     * internal functionality to
     * send a single member, or optionally a list of members,
     * to the Logger to log their attendance
     * @param classId
     * @param memberId
     */
    public void pushAttendance(String classId, List<String> memberId) {

    } // end method


    /**
     * sends an alert/notification to the
     * Notification Dispatcher, as well as its alert level
     * and an optional target identification
     * (instructor, member, entire room).
     * TODO: can overload this to give AgentId, RoomId types
     * @param alert
     * @param alertLevel
     * @param targetId
     */
    public void pushAlert(String alert, String alertLevel, String targetId) {

    } // end method



} // end class