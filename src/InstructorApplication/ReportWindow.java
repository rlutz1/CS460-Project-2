package InstructorApplication;

import javafx.fxml.FXML;

import java.awt.event.MouseEvent;

public class ReportWindow {

    public InstructorApplication parent;

    public ReportWindow() {

    } // end constructor

    @FXML
    public void initialize() {

    } // end FXML init

    /**
     * submit the request
     * @param mouseEvent
     */
    public void submit(MouseEvent mouseEvent) {
        if (parent != null) {
            parent.generateReport();
            // TODO: would be nice if the window autoclosed here, but not necessary
        } // end if
    } // end method

}
