package GSMS.EventAnalysis;

import Driver.ClassroomInitializer;
import Driver.GymInitializer;
import GSMS.Common.AgentId;
import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.Hardware.AudioListener;
import GSMS.EventAnalysis.SignalReceivers.Hardware.VideoListener;
import GSMS.EventAnalysis.SignalReceivers.Hardware.WearableListener;
import GSMS.EventAnalysis.SignalReceivers.SignalType;
import GSMS.Notification.AgentRegistry;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import GSMS.Notification.NotificationDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * class to stand as component for event analyzer
 */


public class EventAnalyzer {
    private static final double THRESHOLD = 0.7;
    private List<Classroom> classrooms;
    private LiveEventAI liveEventAI;
    private NotificationDispatcher notificationDispatcher;
    private long lastAlertTimestamp = 0;   // for duplicate suppression
    private final AgentRegistry agentRegistry;   // new field

    public EventAnalyzer(AgentRegistry agentRegistry) {
        this.agentRegistry = agentRegistry;
        this.liveEventAI = new LiveEventAI();
        this.notificationDispatcher = new NotificationDispatcher(agentRegistry);
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
    private void decideIfNeedToNotify(Event event){
        long now = System.currentTimeMillis();
        if (now - lastAlertTimestamp < 1000) {
            System.out.println("[EventAnalyzer] Suppressed duplicate alert within 1 second.");
            return;
        }
        lastAlertTimestamp = now;

        if (event.probabilityOfCorrectness() > THRESHOLD) {
            System.out.println("[EventAnalyzer] DECIDED TO PUSH ALERT: " + event.eventInfo()
                    + " | Level=" + event.alertLevel() + " | Target=" + event.agentId());
            pushAlert(event.eventInfo(), event.alertLevel(), event.agentId());
        }
    }

    /** DEMO INITIALIZER SPECIFIC (START)**/

    // TARGET CLASSROOM METHOD:
    public void initDemEventAnalyzer(GymInitializer gymInitializer) {
        classrooms = new ArrayList<>(); // adding due to null list error

        ClassroomInitializer classInitializer =
                gymInitializer.targetClassroom();

        // Classroom itself.
        Classroom initClassroom = new Classroom(this,
                classInitializer.roomId());
        // init AudioListener components.
        for (int i = 0; i < classInitializer.numAudioSensors(); i++) {
            initClassroom.addNewAudioComponent();
        }
        // init VideoListener Components.
        for (int i = 0; i < classInitializer.numCameras(); i++) {
            initClassroom.addNewVideoComponent();
        }
        // inti WearableListener Componenets.
        for (int i = 0; i < classInitializer.membersInClass().size(); i++) {
            initClassroom.addNewWearableComponent(classInitializer.membersInClass()
                                                                  .get(i)
                                                                  .id());
        }

        // add class to this EventAnalyzer.
        classrooms.add(initClassroom);
    }

    public List<AudioListener> getAudioComponentsToInit(RoomId classroomId) {
        Classroom classroom = findClassroom(classroomId);
        return classroom.getAudioComponent();
    }
    public List<VideoListener> getVideoComponentsToInit(RoomId classroomId) {
        Classroom classroom = findClassroom(classroomId);
        return classroom.getVideosComponents();
    }
    public List<WearableListener> getWearablesToInit(RoomId classroomId) {
        Classroom classroom = findClassroom(classroomId);
        return classroom.getWearableComponents();
    }
    /** DEMO INITIALIZER SPECIFIC (END)**/

    /************************* NON-SAD * helper END   *************************/

    /**
     * entry point to receive decibel levels collected
     * from audio sensors in a given room
     * @param roomId classroom id.
     */
    public void getAudioData(RoomId roomId) {
        System.out.println("[EventAnalyzer] getAudioData CALLED room=" + roomId);
        Classroom room = findClassroom(roomId);
        if (room != null) {
            Event audioEvent =
                    liveEventAI.detectEvent(room.getAudioDecibelData().toString(),
                    SignalType.AUDIO);
            if (audioEvent != null) {
                decideIfNeedToNotify(audioEvent);
            }
        }
    } // end method

    /**
     * entry point to receive video feed collected
     * from video cameras in a given room
     * @param roomId classroom id.
     */
    public void getVideoData(RoomId roomId) {
        System.out.println("[EventAnalyzer] getVideoData CALLED room=" + roomId);
        Classroom room = findClassroom(roomId);
        if (room != null) {
            Event videoEvent =
                    liveEventAI.detectEvent(room.getVideoFeedData(),
                            SignalType.VIDEO);
            if (videoEvent != null) {
                decideIfNeedToNotify(videoEvent);
            }
            room.getVideoFeedData();
        }
    } // end method

    /**
     * entry point to receive a list of member vitals
     * collected from wearable devices from all members in a given room.
     * Additionally an entry point to receive signals from doorway sensors
     * as read from the member wearables
     * @param roomId classroom id.
     * @param memberId
     */
    public void getWearableData(RoomId roomId, AgentId memberId) {
        System.out.println("[EventAnalyzer] getWearableData CALLED room=" + roomId
                + " memberId=" + memberId);
        Classroom room = findClassroom(roomId);
        if (room != null) {
            Event wearableEvent =
                    liveEventAI.detectEvent(room.getWearableInfoData(memberId),
                            SignalType.WEARABLE);
            if (wearableEvent != null) {
                decideIfNeedToNotify(wearableEvent);
            }
        }
    } // end method

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
        Notification fullNotification = new Notification(
                "ALERT LEVEL: " + alertLevel.toString() + "\n. ALERT: " + alert,
                alertLevel,
                targetId
        );
//        String fullNotification =
//                "ALERT LEVEL: "+alertLevel.toString()+"\n. ALERT: "+alert;
        notificationDispatcher.receiveNotification(fullNotification,
                                                   alertLevel,
                                                   targetId);
    } // end method



} // end class