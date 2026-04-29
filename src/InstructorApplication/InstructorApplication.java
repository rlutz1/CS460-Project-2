package InstructorApplication;

import GSMS.Recommendation.RecommendationDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * encapsulation of a driver for an instructor application
 */
public class InstructorApplication {

    private String id;
    private boolean isCoveredEntity;
    private RecommendationDispatcher dispatch = new RecommendationDispatcher();
    public InstructorApplication(){
//    public InstructorApplication(String id, boolean isCoveredEntity) {
        this.id = "I1";
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
    public void getInstructorProfile(String instructorId) {
        if (instructorId.matches(id)) {
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
        if (inputArea.getText() == null || inputArea.getText().trim().isEmpty()) {
            instructorLog.appendText("No request made\n");
        } else {
            String foos[] = inputArea.getText().toLowerCase().split("\n");
            String inputs[] = inputArea.getText().split(" ", 2);
            String memberId = inputs[0]; // assumes first item is member id
            for (String foo:foos) {
                if (foo.equals("clear")) {
                    instructorLog.clear();
                } else {
                    if (memberId.isEmpty() && foo.equals("generate itinerary")) {
                        instructorLog.appendText("Missing member id!\n");
                        System.out.println("failure!");
                    } else {
                        String output = dispatch.receiveRequest(id, foo, "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
                        if (output == "invalid request") {
                            instructorLog.appendText("Sorry, system is offline\n");
                            System.out.println("failure!");
                        } else {
                            instructorLog.appendText(output);
                            System.out.println("success!");
                        }
                    }
                }
            }
//            String output = dispatch.receiveRequest(id, request.getText(), age + ", " + normalHeartRateAvg + ", is member an athlete: " + isAthlete);
//            memberLog.appendText(output);
            inputArea.clear();
        }
    }

    @FXML
    public void sendSchedule(MouseEvent mouseEvent) {
        String schedule;
        String inputs[] = inputArea.getText().split(" ", 2);
        String memberId = inputs[0];  // assumes first item is member id
        instructorLog.appendText("Retrieving gym schedule\n");
        schedule = dispatch.receiveRequest(id, "view schedule", "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
        if (schedule == "invalid request") {
            instructorLog.appendText("Sorry, system is offline\n");
            System.out.println("failure!");
        } else {
            instructorLog.appendText(schedule);
            System.out.println("success!");
        }
        inputArea.clear();
    }

    @FXML
    public void requestItinerary(MouseEvent mouseEvent) {
        String itinerary;
        String inputs[] = inputArea.getText().split(" ", 2);
        String memberId = inputs[0];  // assumes first item is member id
        instructorLog.appendText("Sending request to system...\n");
        if (memberId.isEmpty()) {
            instructorLog.appendText("Missing member id!\n");
            System.out.println("failure!");
        } else {
            itinerary = dispatch.receiveRequest(id, "generate itinerary", "target member: " + memberId + ", is instructor a covered entity: " + isCoveredEntity);
            if (itinerary == "invalid request") {
                instructorLog.appendText("Sorry, system is offline\n");
                System.out.println("failure!");
            } else {
                instructorLog.appendText(itinerary);
                System.out.println("success!");
            }
        }
        inputArea.clear();
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

} // end class
