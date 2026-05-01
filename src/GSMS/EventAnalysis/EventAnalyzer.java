package GSMS.EventAnalysis;

import GSMS.Common.AgentId;
import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.SignalType;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.NotificationDispatcher;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * (Parent) class to stand as component for event analyzer
 */

public class EventAnalyzer {
    protected static final double THRESHOLD = 0.8;
    protected List<Classroom> classrooms;

    // Might need to be singletons for
    // easy use within specific methods.
    protected LiveEventAI liveEventAI;
    protected NotificationDispatcher notificationDispatcher;

    public EventAnalyzer() {
    } // end constructor

    /************************* NON-SAD * helper START *************************/
    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }
    public Classroom findClassroom(RoomId classroomId) {
        for (Classroom classroom : classrooms) {
            if (classroom.getClassroomId().equals(classroomId)) {
                return classroom;
            }
        }
        return null;
    }
    protected void decideIfNeedToNotify(Event event){
        if (event.probabilityOfCorrectness() > THRESHOLD) {
            pushAlert(event.eventInfo(),
                      event.alertLevel(),
                      event.agentId());
        }
    }
    /************************* NON-SAD * helper END   *************************/

    public void getData(RoomId roomId){
        // for subclasses to implement
    }


    // COMMENTING OUT GETTERS AS THEY WON"T BE NEEDED IF WE COMMIT TO THIS
    // 2-analyzer design:
//    /**
//     * entry point to receive video feed collected
//     * from video cameras in a given room
//     * @param roomId classroom id.
//     */
//    public void getVideoData(RoomId roomId) {
//        Classroom room = findClassroom(roomId);
//        if (room != null) {
//            Event videoEvent =
//                    liveEventAI.detectEvent(room.getVideoFeedData(),
//                            SignalType.VIDEO);
//            if (videoEvent != null) {
//                decideIfNeedToNotify(videoEvent);
//            }
//            room.getVideoFeedData();
//        }
//    } // end method
//
//    /**
//     * entry point to receive a list of member vitals
//     * collected from wearable devices from all members in a given room.
//     * Additionally an entry point to receive signals from doorway sensors
//     * as read from the member wearables
//     * @param roomId classroom id.
//     * @param memberId
//     */
//    public void getWearableData(RoomId roomId, AgentId memberId) {
//        Classroom room = findClassroom(roomId);
//        if (room != null) {
//            Event wearableEvent =
//                    liveEventAI.detectEvent(room.getWearableInfoData(memberId),
//                            SignalType.WEARABLE);
//            if (wearableEvent != null) {
//                decideIfNeedToNotify(wearableEvent);
//            }
//        }
//    } // end method

    /**
     * given a doorway sensor’s signal of access to a room,
     * initiate a deterministic protocol to verify that this
     * member should be in the room, tracking attendance if they are, and sending
     * notifications to attendants if they are not.
     * @param roomId
     * @param memberId
     */
    public void verifyRoomAccess(RoomId roomId, AgentId memberId) {
        Classroom room = findClassroom(roomId);
        if (room != null) {
            room.getWearableInfoData(memberId);
            //TODO: get the doorway specific sensory details.
        }
    } // end method

    /**
     * internal functionality to
     * send a single member, or optionally a list of members,
     * to the Logger to log their attendance
     * @param classId
     * @param memberId
     */
    public void pushAttendance(RoomId classId, String memberId) {
        // TODO: implement this with Logger/Data Manager
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
        // TODO: implement this with Logger/Data Manager
    } // end method


    /**
     * sends an alert/notification to the
     * Notification Dispatcher, as well as its alert level
     * and an optional target identification
     * (instructor, member, entire room).
     * TODO: can overload this to give AgentId, RoomId types
     * @param alert
     * @param alertLevel
     * @param targetId (member/instructor).
     */
    public void pushAlert(String alert, AlertLevel alertLevel, AgentId targetId) {
        String fullNotification =
                "ALERT LEVEL: "+alertLevel.toString()+"\n. ALERT: "+alert;
        notificationDispatcher.receiveNotification(fullNotification,
                                                   alertLevel,
                                                   targetId);
    } // end method



} // end class