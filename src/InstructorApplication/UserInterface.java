package InstructorApplication;

/**
 * stand in class for an instructor application.
 */

public class UserInterface {

    public UserInterface() {
        // Dummy instructor with full access
        InstructorApplication instructor = new InstructorApplication("I001", true);
    } // end constructor

    /**
     * Updates the respective member and instructor
     * application GUI to display request-specific
     * related response data
     */
    public void updateGUI() {

    } // end method

    /**
     * displays the appropriate message and visual
     * indicators appropriate for the warning level
     * of a notification for the member/instructor applications
     * @param alertLevel
     * @param msg
     */
    public void displayNotification(String alertLevel, String msg) {

    } // end method

    /**
     * Marks the current notification on display as resolved
     * to transmit back to the Gym Space Management Controller
     */
    public void markNotificationResolved() {

    } // end method

} // end class
