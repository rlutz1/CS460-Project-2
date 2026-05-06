package InstructorApplication;

import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * stand in class for an instructor application user interface controller
 */

public class UserInterface {

    public final static String REPORT_WINDOW_FXML = "/fxml/report-window.fxml";
    public final static String REPORT_WINDOW_TITLE = "Report Request Form";

    private Stage reportWindow;
    public TextArea inputArea;
    public TextArea instructorLog;
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
        instructorLog.appendText(information.getMessage() + "\n");
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
            instructorLog.appendText("No notification to mark\n");
        } else {
            newNotificationLog.clear();
        } // end if
    } // end method

    /**
     * open the report window
     * @param parent the parent application
     */
    public void openReportWindow(InstructorApplication parent) throws IOException {
        URL main = getClass().getResource(REPORT_WINDOW_FXML); // grab main xml

        if (main != null) { // null catch
            FXMLLoader loader = new FXMLLoader(main);
            Parent root = loader.load(); // load it
            reportWindow = new Stage();
            reportWindow.setTitle(REPORT_WINDOW_TITLE);
            reportWindow.setScene(new Scene(root));

            ReportWindow controller = loader.getController();
            controller.parent = parent; // set this as the parent controller
            reportWindow.show();
        } else {
            System.out.println("Something went wrong: " + REPORT_WINDOW_FXML + " returned null on start.");
        } // end if
    } // end method

    /**
     * close the report window on submission of report generation request.
     */
    public void closeReportWindow() {
        if (reportWindow != null) {
            reportWindow.close();
            reportWindow = null;
        } // end if
    } // end method

} // end class
