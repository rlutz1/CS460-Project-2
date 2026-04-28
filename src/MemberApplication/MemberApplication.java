package MemberApplication;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import GSMS.Recommendation.RecommendationDispatcher;

public class MemberApplication {
    private RecommendationDispatcher dispatch = new RecommendationDispatcher();

    // Placeholder code
    String id = "M001";
    int age = 35;
    int normalHeartRateAvg = 70;
    int targetHeartRate = 220 - age;
    boolean isAthlete = false;

    public MemberApplication() {
//        String id = "M001";
//        int age = 35;
//        int normalHeartRateAvg = 70;
//        int targetHeartRate = 220 - age;
//        boolean isAthlete = false;
    } // end constructor

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

    @FXML
    public void sendAction(MouseEvent mouseEvent) {
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            memberLog.appendText("No request made\n");
        } else {
            memberLog.appendText(request.getText() + "\n");
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
        itinerary = dispatch.receiveRequest(id, "make itinerary", age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
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
} // end class
