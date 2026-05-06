package MemberApplication;

import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import javafx.scene.control.TextArea;

/**
 * stand in class for a member application user interface controller
 */

public class UserInterface {

    public TextArea inputArea;
    public TextArea memberLog;
    public TextArea newNotificationLog;

    public UserInterface() {

    } // end constructor

    /**
     * Updates the respective member and instructor
     * application GUI to display request-specific
     * related response data
     * @param information information passed to update the scene with.
     */
    public void updateGUI(Notification information) {
        memberLog.appendText(information.getMessage() + "\n");
    } // end method

    /**
     * displays the appropriate message and visual
     * indicators appropriate for the warning level
     * of a notification for the member/instructor applications
     * @param alertLevel
     * @param msg
     */
    public void displayNotification(AlertLevel alertLevel, String msg) {
        newNotificationLog.appendText(msg);
    } // end method

    /**
     * Marks the current notification on display as resolved
     * to transmit back to the Gym Space Management Controller
     */
    public void markNotificationResolved() {
        if (newNotificationLog.getText() == null || newNotificationLog.getText().trim().isEmpty()) {
            memberLog.appendText("No notification to mark\n");
        } else {
            newNotificationLog.clear();
        } // end if
    } // end method


} // end class
