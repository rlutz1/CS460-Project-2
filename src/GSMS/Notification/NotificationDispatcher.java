package GSMS.Notification;

/**
 * class to stand as component for notification dispatcher
 *
 * main functions:
 * + sending out notifications to members/instructors
 * + knowing who should receive the notifications it is sent
 */

import GSMS.Common.AgentId;

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
    public void receiveNotification(String notification, AlertLevel alertLevel,
                                    AgentId recipientId) {

        // sendNotification(null, null); // end by sending the notification
    } // end method

    /**
     * send out a notification to this agent
     * @param notification
     * @param agentId
     */
    public void sendNotification(String notification, AgentId agentId) {

    } // end method

} // end class