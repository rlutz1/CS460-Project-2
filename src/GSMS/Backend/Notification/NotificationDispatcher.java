package GSMS.Backend.Notification;

import java.util.List;

/**
 * class to stand as component for notification dispatcher
 *
 * main functions:
 * + sending out notifications to members/instructors
 * + knowing who should receive the notifications it is sent
 */

public class NotificationDispatcher {

    public NotificationDispatcher() {

    } // end constructor

    /**
     * entry point to receive and send a notification.
     * should be able to take the notification and alert level
     * and determine the appropriate receipients
     */
    public void receiveNotification(String notification, String alertLevel, String recipientInfo) {

    } // end method


    /**
     * returns a list of all notifications that are pending to be sent.
     * this could be
     */
    public List<String> retrieveNotifications() {
        return null;
    } // end method



} // end class