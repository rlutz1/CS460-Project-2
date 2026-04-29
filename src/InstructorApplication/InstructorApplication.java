package InstructorApplication;

import GSMS.Agents.InstructorApplicationAPI;
import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import GSMS.Recommendation.RecommendationDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * encapsulation of a driver for an instructor application
 */
public class InstructorApplication {

    private Stage myStage; // this is for holding onto the initialized application to show later
    private AgentId id; // for ease of use as needed.

    private InstructorApplicationAPI api;

//    private String id; // TODO: refer to AgentId above instead, would be better
    private boolean isCoveredEntity;
//    private RecommendationDispatcher dispatch = new RecommendationDispatcher();
    public InstructorApplication(){
//    public InstructorApplication(String id, boolean isCoveredEntity) {
//        this.id = "I1";
        this.isCoveredEntity = true;
    } // end method

    /**
     * Organizes a list of workouts and general class
     * information for the instructor’s class to transmit
     * and send to other enrolled members
     * @param info
     */
    public void createItinerary(String info) {

    } // end method

    /**
     * Organizes a request for gathering instructor profile
     * information for general and classroom-related data.
     * @param instructorId
     */
    public void getInstructorProfile(AgentId instructorId) {
        if (instructorId.getId().matches(id.getId())) {
            // do something
        } else {
            System.err.println("Unauthorized access to another account! Access Denied");
        }
    } // end method

    @FXML
    private TextArea inputArea;

    @FXML
    public TextArea instructorLog;

    @FXML
    private TextArea newNotificationLog;

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

    @FXML
    public void confirm(MouseEvent mouseEvent) {
        if (newNotificationLog.getText() == null || newNotificationLog.getText().trim().isEmpty()) {
            instructorLog.appendText("No notification to mark\n");
        } else {
            instructorLog.appendText(newNotificationLog.getText() + "\n");
            inputArea.clear();
        }
    }

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
        }
    } // end method


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
