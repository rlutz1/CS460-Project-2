package MemberApplication;

import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import javafx.stage.Stage;

public class MemberApplication {

    private Stage myStage; // this is for holding onto the initialized application to show later
    private AgentId id; // for ease of use as needed.
    private MemberApplicationAPI api; // this is set during initialization; api to communicate through

    // Placeholder code
    private int age;
    private int normalHeartRateAvg;
    private int targetHeartRate; // Maybe keep separate?
    private int minSafeRate, maxSafeRate;
    private String conditions; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
    private boolean isAthlete;

    public MemberApplication() {
        this.age = 35;
        this.normalHeartRateAvg = 70;
        this.targetHeartRate = 220 - age; // Maybe keep separate?
        this.minSafeRate = (int)(this.targetHeartRate * 0.5);
        this.maxSafeRate = (int)(this.targetHeartRate * 0.85);
        this.conditions = "PRIV_ Type 2 Diabetes"; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
        this.isAthlete = false;
    } // end constructor

//    public MemberApplication(String id, int age, int normalHeartRateAvg, String conditions, boolean isAthlete) {
//        this.age = age;
//        this.normalHeartRateAvg = normalHeartRateAvg;
//        this.targetHeartRate = 220 - age; // Maybe keep separate?
//        this.conditions = conditions; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
//        this.isAthlete = isAthlete;
//    }

    /**
     * Gets the list of member-specific class
     * schedules for the day
     * @param memberId
     */
    public void getClassSchedule(String memberId) {

    } // end method

    /**
     * Requests for a member-specific workout
     * based on workout type
     * @param memberId
     * @param exerciseType
     */
    public void getGeneratedWorkout(AgentId memberId, String exerciseType) {
        // TODO: this needs to be called instead. and then:
        // api.transmitRecommendationRequest(...)
        api.transmitRecommendationRequest(memberId, exerciseType);
    } // end method

    /**
     * for frontend simulation only -- wrapper to the real SAD method
     * @param mouseEvent
     */
    @FXML
    public void getGeneratedWorkout(MouseEvent mouseEvent) {
        getGeneratedWorkout(id, "Glute day, every day.");
    } // end method

    /**
     * Gets the entire general and health-fitness related
     * profile information of a member
     * @param memberId
     */
    public void getMemberProfile(String memberId) {

    } // end method

    @FXML
    private TextArea request;

    @FXML
    public TextArea memberLog;

    @FXML
    private TextArea newNotificationLog;

//    public void updateNotifications(String notifications){
//        newNotificationLog.appendText(notifications);
//    }

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
                    case "view schedule":
                        api.transmitScheduleViewingRequest(id, "");
                        break;
                    case "generate workout":
                        getGeneratedWorkout(id, "Glute day, every day.");
                        break;
                    default:
                        api.transmitRecommendationRequest(id, "XXX");
                        break;
                }
            }
        }
        request.clear();

//        if (request.getText() == null || request.getText().trim().isEmpty()) {
//            memberLog.appendText("No request made\n");
//        } else {
//            String foos[] = request.getText().toLowerCase().split("\n");
//            for (String foo:foos) {
//                if (foo.equals("clear")) {
//                    memberLog.clear();
//                } else {
//                    String output = api.receiveRequest(id, foo, age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
//                    if (output == "invalid request") {
//                        memberLog.appendText("Sorry, system is offline\n");
//                        System.out.println("failure!");
//                    } else {
//                        memberLog.appendText(output);
//                        System.out.println("success!");
//                    }
//                }
//            }
//            request.clear();
//        }
    }

    @FXML
    public void sendSchedule(MouseEvent mouseEvent) {
        api.transmitScheduleViewingRequest(id, "");
//        String schedule;
//        memberLog.appendText("Retrieving gym schedule\n");
//        schedule = api.receiveRequest(id, "view schedule", age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
//        if (schedule == "invalid request") {
//            memberLog.appendText("Sorry, system is offline\n");
//            System.out.println("failure!");
//        } else {
//            memberLog.appendText(schedule);
//            System.out.println("success!");
//        }
    }

    @FXML
    public void requestItinerary(MouseEvent mouseEvent) {
//        String itinerary;
//        memberLog.appendText("Sending request to system...\n");
//        itinerary = api.receiveRequest(id, "make itinerary request", age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
//        if (itinerary == "invalid request") {
//            memberLog.appendText("Sorry, system is offline\n");
//            System.out.println("failure!");
//        } else {
//            memberLog.appendText(itinerary);
//            System.out.println("success!");
//        }
    }

    @FXML
    public void confirm(MouseEvent mouseEvent) {
        if (newNotificationLog.getText() == null || newNotificationLog.getText().trim().isEmpty()) {
            memberLog.appendText("No notification to mark\n");
        } else {
            memberLog.appendText(newNotificationLog.getText() + "\n");
            request.clear();
        }
    }

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
    public void setApi(MemberApplicationAPI api) {
        this.api = api;
    } // end method

} // end class
