package InstructorApplication;

import GSMS.Agents.InstructorApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Common.Metadata;
import GSMS.Common.ReportType;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * instructor application to interact through to send requests
 * "over the network" to the GSMC
 */
public class InstructorApplication {

    public final static String REPORT_WINDOW_FXML = "/fxml/report-window.fxml";
    public final static String REPORT_WINDOW_TITLE = "Report Request Form";

    private Stage myStage; // this is for holding onto the initialized application to show later
    private AgentId id; // for ease of use as needed.
    private InstructorApplicationAPI api; // api to communicate through
    private Stage reportWindow;

    @FXML
    private TextArea inputArea;
    @FXML
    public TextArea instructorLog;
    @FXML
    private TextArea newNotificationLog;

    public InstructorApplication(){

    } // end method

    /**
     * directive to generate a report about specific targets
     * and any special categories as needed.
     * @param targetIds target ids, whether agent or class
     * @param reportTypes report type, or categories to focus on if not general
     * @param timeStart start date
     * @param timeEnd end date
     */
    public void generateReport(
            List<String> targetIds,
            List<String> reportTypes,
            String timeStart,
            String timeEnd
    ) {
        // send to api
        api.requestReport(
                id,
                targetIds,
                new ReportType(reportTypes),
                new Metadata(timeStart + "&" + timeEnd)
        );
        // close the report window
        reportWindow.close();
        reportWindow = null;
    } // end method

    /**
     * Organizes a list of workouts and general class
     * information for the instructor’s class to transmit
     * and send to other enrolled members
     * @param info packet to send out itinerary as
     */
    public void createItinerary(String info) {

    } // end method

    /**
     * Organizes a request for gathering instructor profile
     * information for general and classroom-related data.
     * @param instructorId instructor's id
     */
    public void getInstructorProfile(AgentId instructorId) {

    } // end method

    /**
     * Access point to receive information from network.
     * @param notificationOrInformation Notification type if time
     */
    public void receiveInformation(Notification notificationOrInformation) {
        // assume right now it's just a recc
        if (notificationOrInformation.getAlertLevel() == AlertLevel.INFORMATIONAL_MESSAGE) {
            instructorLog.appendText(notificationOrInformation.getMessage() + "\n");
        } else {
            newNotificationLog.appendText(notificationOrInformation.getMessage() + "\n");
        } // end if
    } // end method


    // TODO: we need to work in the next to methods with displayNotification(...)
    //       instead of doing directly here.
    @FXML
    public void confirm(MouseEvent mouseEvent) {
        if (newNotificationLog.getText() == null || newNotificationLog.getText().trim().isEmpty()) {
            instructorLog.appendText("No notification to mark\n");
        } else {
            newNotificationLog.clear();
        } // end if
    } // end method

    /**
     * open the report window, used for demo purposes
     * to put together a report request to GSMC.
     * @param mouseEvent click
     * @throws IOException
     */
    @FXML
    public void openGenReportWindow(MouseEvent mouseEvent) throws IOException {
        URL main = getClass().getResource(REPORT_WINDOW_FXML); // grab main xml

        if (main != null) { // null catch
            FXMLLoader loader = new FXMLLoader(main);
            Parent root = loader.load(); // load it
            reportWindow = new Stage();
            reportWindow.setTitle(REPORT_WINDOW_TITLE);
            reportWindow.setScene(new Scene(root));

            ReportWindow controller = loader.getController();
            controller.parent = this; // set this as the parent controller
            reportWindow.show();
        } else {
            System.out.println("Something went wrong: " + REPORT_WINDOW_FXML + " returned null on start.");
        } // end if
    } // end method


    /*
     ========================================================================
     METHODS FOR TESTING APPLICATION GUI
     ========================================================================
     */
    @FXML
    public void sendAction(MouseEvent mouseEvent) {
//        if (inputArea.getText() == null || inputArea.getText().trim().isEmpty()) {
//            instructorLog.appendText("No request made\n");
//        } else {
//            String foos[] = inputArea.getText().toLowerCase().split("\n");
//            String inputs[] = inputArea.getText().split(" ", 2);
//            String memberId = inputs[0]; // assumes first item is member id
//            for (String foo:foos) {
//                if (foo.equals("clear")) {
//                    instructorLog.clear();
//                } else {
//                    if (memberId.isEmpty() && foo.equals("generate itinerary")) {
//                        instructorLog.appendText("Missing member id!\n");
//                        System.out.println("failure!");
//                    } else {
//                        String output = api.receiveRequest(id, foo, "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
//                        if (output == "invalid request") {
//                            instructorLog.appendText("Sorry, system is offline\n");
//                            System.out.println("failure!");
//                        } else {
//                            instructorLog.appendText(output);
//                            System.out.println("success!");
//                        }
//                    }
//                }
//            }
////            String output = dispatch.receiveRequest(id, request.getText(), age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
////            memberLog.appendText(output);
//            inputArea.clear();
//        }
    }

    @FXML
    public void sendSchedule(MouseEvent mouseEvent) {
//        String schedule;
//        String inputs[] = inputArea.getText().split(" ", 2);
//        String memberId = inputs[0];  // assumes first item is member id
//        instructorLog.appendText("Retrieving gym schedule\n");
//        schedule = dispatch.receiveRequest(id, "view schedule", "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
//        if (schedule == "invalid request") {
//            instructorLog.appendText("Sorry, system is offline\n");
//            System.out.println("failure!");
//        } else {
//            instructorLog.appendText(schedule);
//            System.out.println("success!");
//        }
//        inputArea.clear();
    }

    @FXML
    public void requestItinerary(MouseEvent mouseEvent) {
//        String itinerary;
//        String inputs[] = inputArea.getText().split(" ", 2);
//        String memberId = inputs[0];  // assumes first item is member id
//        instructorLog.appendText("Sending request to system...\n");
//        if (memberId.isEmpty()) {
//            instructorLog.appendText("Missing member id!\n");
//            System.out.println("failure!");
//        } else {
//            itinerary = dispatch.receiveRequest(id, "generate itinerary", "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
//            if (itinerary == "invalid request") {
//                instructorLog.appendText("Sorry, system is offline\n");
//                System.out.println("failure!");
//            } else {
//                instructorLog.appendText(itinerary);
//                System.out.println("success!");
//            }
//        }
//        inputArea.clear();
    }




    // ==============================================================================
    // BELOW IS FOR INITIALIZTION OF APPS
    // ==============================================================================

    /**
     * when initializing applications, hold the stage for a later starting.
     * @param stage
     */
    public void setMyStage(Stage stage) {
        this.myStage = stage;
    } // end method

    /**
     * add an id to associate with this application.
     * @param id
     */
    public void setId(AgentId id) {
        this.id = id;
    } // end method

    /**
     * START the application.
     */
    public void start() {
        if (this.myStage != null) {
            this.myStage.show();
        } // end if
    } // end method

    /**
     * set the needed API to comm through
     * @param api
     */
    public void setApi(InstructorApplicationAPI api) {
        this.api = api;
    } // end method

} // end class
