package GSMS.Notification;

/**
 * Severity levels for all GSMS notifications.
 * The level drives the routing logic
 * inside the NotificationDispatcher.
 *
 *   WARNING - Non-critical concern for one member (Use Case 1: Exhaustion). Dispatcher routes to
 *   the affected member + their instructor.
 *
 *   CRITICAL - Active hazard in an entire room (Use Case 2: Conflict). Dispatcher routes to
 *   ALL members in the room + the instructor.
 *
 *   EMERGENCY - Life-threatening event for one member (Use Case 4: Collapse).
 *   Dispatcher resolves the member's room, then routes to ALL
 *   members in that room + the instructor.
 */
public enum AlertLevel {
    INFORMATIONAL_MESSAGE, // lowest
    WARNING,
    ALERT // highest
//    CRITICAL,
//    EMERGENCY,

} // end enum