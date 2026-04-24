package GSMS.Notification;

import GSMS.Common.AgentId;
import GSMS.Common.RoomId;

import java.time.LocalDateTime;

/**
 * Represents a notification produced by the
 * Event Analyzer and dispatched by the NotificationDispatcher.
 *
 * The targetId field is what the dispatcher routes on, and its
 * meaning depends on the alert level:
 *
 *   WARNING / EMERGENCY → targetId is an AgentId (the affected member).
 *   CRITICAL → targetId is a RoomId (the room of the event).
 *
 * Both are stored here as their raw String values, so this class stays
 * neutral. The NotificationDispatcher interprets the targetId using
 * the alert level. toString() is used when handing the notification
 * off to Member/Instructor.sendInformation(String).
 */
public class Notification {

    private final String       message;
    private final AlertLevel   alertLevel;
    // AgentId string OR RoomId string
    private final String       targetId;
    private final LocalDateTime timestamp;

    // Constructors

    /**
     * Use for WARNING and EMERGENCY: the target is a specific member.
     * @param message    Human-readable description of the detected event.
     * @param alertLevel WARNING or EMERGENCY.
     * @param agentId    AgentId of the affected member.
     */
    public Notification(String message, AlertLevel alertLevel, AgentId agentId) {
        this.message    = message;
        this.alertLevel = alertLevel;
        this.targetId   = agentId.getId();
        this.timestamp  = LocalDateTime.now();
    } // end constructor

    /**
     * Use for CRITICAL: the target is the room where the event occurred.
     * @param message    Human-readable description of the detected event.
     * @param alertLevel CRITICAL.
     * @param roomId     RoomId of the affected classroom.
     */
    public Notification(String message, AlertLevel alertLevel, RoomId roomId) {
        this.message    = message;
        this.alertLevel = alertLevel;
        this.targetId   = roomId.getId();
        this.timestamp  = LocalDateTime.now();
    } // end constructor


    // Accessors

    /** @return Human-readable event description. */
    public String getMessage() { return message; }

    /** @return Severity level — drives dispatcher routing. */
    public AlertLevel getAlertLevel() { return alertLevel; }

    /**
     * @return Raw target identifier string.
     *         Interpret as AgentId for WARNING/EMERGENCY,
     *         or as RoomId for CRITICAL.
     */
    public String getTargetId() { return targetId; }

    /** @return Time this notification was created. */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * String form passed to Member/Instructor.sendInformation(String).
     * Format: [LEVEL] timestamp | target=id | message
     */
    @Override
    public String toString() {
        return "Alert Level [" + alertLevel + "] "
                + timestamp
                + " | Target =" + targetId
                + " | " + message;
    } // end method

} // end class
