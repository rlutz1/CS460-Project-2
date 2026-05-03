package InstructorApplication;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;


public class ReportWindow {

    public InstructorApplication parent;

    @FXML
    public ComboBox classIds;
    @FXML
    public ComboBox instructorIds;
    @FXML
    public ComboBox reportType;
    @FXML
    public ComboBox timeFrame;

    public ReportWindow() {

    } // end constructor

    @FXML
    public void initialize() {
        // right now we're going to HARD code this puppy to test functionality.
        classIds.getItems().add("JFONDA1|AEROBICS");
        classIds.getItems().add("JTAYLOR1|CARDIO");
        classIds.setValue(classIds.getItems().getFirst());

        instructorIds.getItems().add("JFONDA1");
        instructorIds.getItems().add("JTAYLOR1");
        instructorIds.setValue(instructorIds.getItems().getFirst());

        reportType.getItems().add("OVERVIEW");
        reportType.getItems().add("SAFETY INCIDENTS");
        reportType.getItems().add("CONFLICTS");
        reportType.getItems().add("HEALTH EMERGENCIES");
        reportType.setValue(reportType.getItems().getFirst());

        // obviously not very specific, lol
        timeFrame.getItems().add("DAY");
        timeFrame.getItems().add("MONTH");
        timeFrame.getItems().add("YEAR");
        timeFrame.setValue(timeFrame.getItems().getFirst());

    } // end FXML init

    /**
     * submit the request
     * @param mouseEvent
     */
    @FXML
    public void submit(MouseEvent mouseEvent) {
        if (parent != null) {
            parent.generateReport();
            // TODO: would be nice if the window autoclosed here, but not necessary
        } // end if
    } // end method

}
