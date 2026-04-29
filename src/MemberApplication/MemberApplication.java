package MemberApplication;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import GSMS.Recommendation.RecommendationDispatcher;
import javafx.stage.Stage;

public class MemberApplication {
    private RecommendationDispatcher dispatch = new RecommendationDispatcher();

    private Stage myStage; // this is for holding onto the initialized application to show later

    // Placeholder code
    public String id;
    private int age;
    private int normalHeartRateAvg;
    private int targetHeartRate; // Maybe keep separate?
    private String conditions; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
    private boolean isAthlete;

    public MemberApplication() {
        this.id = "M1";
        this.age = 35;
        this.normalHeartRateAvg = 70;
        this.targetHeartRate = 220 - age; // Maybe keep separate?
        this.conditions = "PRIV_ Type 2 Diabetes"; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
        this.isAthlete = false;
    } // end constructor
    public MemberApplication(String id, int age, int normalHeartRateAvg, String conditions, boolean isAthlete) {
        this.id = id;
        this.age = age;
        this.normalHeartRateAvg = normalHeartRateAvg;
        this.targetHeartRate = 220 - age; // Maybe keep separate?
        this.conditions = conditions; // the "PRIV_" is meant for database to keep such info safe from anyone other than member and permitted individuals
        this.isAthlete = isAthlete;
    }

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
    public void getGeneratedWorkout(String memberId, String exerciseType) {

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
            String foos[] = request.getText().toLowerCase().split("\n");
            for (String foo:foos) {
                if (foo.equals("clear")) {
                    memberLog.clear();
                } else {
                    String output = dispatch.receiveRequest(id, foo, age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
                    if (output == "invalid request") {
                        memberLog.appendText("Sorry, system is offline\n");
                        System.out.println("failure!");
                    } else {
                        memberLog.appendText(output);
                        System.out.println("success!");
                    }
                }
            }
            request.clear();
        }
    }

    @FXML
    public void sendSchedule(MouseEvent mouseEvent) {
        String schedule;
        memberLog.appendText("Retrieving gym schedule\n");
        schedule = dispatch.receiveRequest(id, "view schedule", age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
        if (schedule == "invalid request") {
            memberLog.appendText("Sorry, system is offline\n");
            System.out.println("failure!");
        } else {
            memberLog.appendText(schedule);
            System.out.println("success!");
        }
    }

    @FXML
    public void requestItinerary(MouseEvent mouseEvent) {
        String itinerary;
        memberLog.appendText("Sending request to system...\n");
        itinerary = dispatch.receiveRequest(id, "make itinerary request", age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
        if (itinerary == "invalid request") {
            memberLog.appendText("Sorry, system is offline\n");
            System.out.println("failure!");
        } else {
            memberLog.appendText(itinerary);
            System.out.println("success!");
        }
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
     * START the application.
     */
    public void start() {
        if (this.myStage != null) {
            this.myStage.show();
        } // end if
    } // end method

} // end class
