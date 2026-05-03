package GSMS.Notification;

/**
 * class to stand as component for notification dispatcher
 *
 * main functions:
 * + sending out notifications to members/instructors
 * + knowing who should receive the notifications it is sent
 */

import GSMS.Agents.AgentContainer;
import GSMS.Common.AgentId;
import GSMS.Common.AgentType;

/** The alert level determines the Routing logic
 *   WARNING (Use Case 1 - Exhaustion):
 *   CRITICAL (Use Case 2 - Conflict)
 *   EMERGENCY (Use Case 4 - Health Emergency):
 */

public class NotificationDispatcher {

    public NotificationDispatcher() {

    } // end constructor

    /**
     * entry point initiates the process to receive notification, addresses the
     * relevant parties given the alert level, and optional recipient info.
     * @param notification
     * @param alertLevel
     * @param recipientId
     */
    public void receiveNotification(Notification notification, AlertLevel alertLevel,
                                    AgentId recipientId) {
        // right now, testing -- send to just the agent received
         sendNotification(notification, recipientId); // end by sending the notification
    } // end method

    /**
     * send out a notification to this agent
     * @param notification
     * @param agentId
     */
    public void sendNotification(Notification notification, AgentId agentId) {
        switch (agentId.getType()) {
            case MEMBER -> AgentContainer.MemberApps.get(agentId).sendInformation(notification);
            case INSTRUCTOR -> AgentContainer.InstructorApps.get(agentId).sendInformation(notification);
            default -> System.out.println("[NOTIF DISPATCH] Somehow a sender id was sent with not defined type: " + agentId);
        }
    } // end method

} // end class