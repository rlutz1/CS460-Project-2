package MemberApplication;

import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import javafx.stage.Stage;

/**
 * member application to interact through to send requests
 * "over the network" to the GSMC
 */
public class MemberApplication {

    private Stage myStage; // this is for holding onto the initialized application to show later
    private AgentId id; // for ease of use as needed.
    private MemberApplicationAPI api; // this is set during initialization; api to communicate through

    @FXML
    private TextArea request;
    @FXML
    public TextArea memberLog;
    @FXML
    private TextArea newNotificationLog;

    public MemberApplication() {

    } // end constructor

    /**
     * Requests for a member-specific workout
     * based on workout type
     * @param memberId this members id
     * @param exerciseType optional type of exercise
     */
    public void getGeneratedWorkout(AgentId memberId, String exerciseType) {
        api.transmitRecommendationRequest(memberId, exerciseType);
    } // end method

    /**
     * for frontend simulation only -- wrapper to the real SAD method
     * @param mouseEvent click
     */
    @FXML
    public void getGeneratedWorkout(MouseEvent mouseEvent) {
        getGeneratedWorkout(id, "Anything is fine.");
    } // end method

    /**
     * Gets the list of member-specific class
     * schedules for the day
     * @param memberId this members id
     */
    public void getClassSchedule(String memberId) {

    } // end method


    /**
     * Gets the entire general and health-fitness related
     * profile information of a member
     * @param memberId this members id
     */
    public void getMemberProfile(String memberId) {

    } // end method

    /**
     * Access point to receive information from network.
     * @param notificationOrInformation Notification type if time
     */
    public void receiveInformation(Notification notificationOrInformation) {
        // assume right now it's just a recc
        if (notificationOrInformation.getAlertLevel() == AlertLevel.INFORMATIONAL_MESSAGE) {
            memberLog.appendText(notificationOrInformation.getMessage() + "\n");
        } else {
            newNotificationLog.appendText(notificationOrInformation.getMessage() + "\n");
        } // end if
    } // end method

    // TODO: we need to work in the next to methods with displayNotification(...)
    //       instead of doing directly here.
    @FXML
    public void confirm(MouseEvent mouseEvent) {
        if (newNotificationLog.getText() == null || newNotificationLog.getText().trim().isEmpty()) {
            memberLog.appendText("No notification to mark\n");
        } else {
            newNotificationLog.clear();
        } // end if
    } // end method

    // ========================================================================
    // METHODS FOR TESTING APPLICATION GUI
    // ========================================================================

    @FXML
    public void sendAction(MouseEvent mouseEvent) {
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            memberLog.appendText("No request made\n");
        } else {
            String actions[] = request.getText().toLowerCase().split("\n");
            for (String action:actions) {
                switch (action) {
                    case "clear":
                        memberLog.clear();
                        break;
                      //  commenting out since this is not quite adhering to architecture.
//                    case "view schedule":
//                        api.transmitScheduleViewingRequest(id, "");
//                        break;
                    case "generate workout":
                        getGeneratedWorkout(id, "Anything is fine");
                        break;
                    default:
                        api.transmitRecommendationRequest(id, "XXX");
                        break;
                }
            }
        }
        request.clear();
    } // end method

    @FXML
    public void sendSchedule(MouseEvent mouseEvent) {
        //  commenting out since this is not quite adhering to architecture.
//        api.transmitScheduleViewingRequest(id, "");
    } // end method


    // ==============================================================================
    // BELOW IS FOR INITIALIZTION OF APPS
    // ==============================================================================

    /**
     * when initializing applications, hold the stage for a later starting.
     * @param stage stage of this application
     */
    public void setMyStage(Stage stage) {
        this.myStage = stage;
    } // end method

    /**
     * add an id to associate with this application.
     * @param id if of member
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
     * @param api api to send requests to gsmc through
     */
    public void setApi(MemberApplicationAPI api) {
        this.api = api;
    } // end method

} // end class
