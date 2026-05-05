package InstructorApplication;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * for use in generating a report in a way
 * that doesn't affect the main application window
 * and is more realistic to what information you would
 * give given our capabilities.
 */
public class ReportWindow {

    public InstructorApplication parent;

    @FXML
    public Pane classIds;
    @FXML
    public Pane instructorIds;
    @FXML
    public Pane reportType;
    @FXML
    public DatePicker timeStart;
    @FXML
    public DatePicker timeEnd;


    public ReportWindow() {

    } // end constructor

    @FXML
    public void initialize() {
        timeStart.setValue(LocalDate.of(2026, Month.APRIL, 1));
        timeEnd.setValue(LocalDate.now());
    } // end FXML init

    /**
     * submit the request
     * @param mouseEvent
     */
    @FXML
    public void submit(MouseEvent mouseEvent) {
        if (parent != null) {
            List<String> targetIds = new ArrayList<>();
            List<String> selectedReportTypes = new ArrayList<>();

            for (Node checkBox : classIds.getChildren()) {
                if (((CheckBox)checkBox).isSelected()) {
                    targetIds.add(((CheckBox)checkBox).getText());
                } // end if
            } // end loop

            for (Node checkBox : instructorIds.getChildren()) {
                if (((CheckBox)checkBox).isSelected()) {
                    targetIds.add(((CheckBox)checkBox).getText());
                } // end if
            } // end loop

            parent.generateReport(
                    targetIds,
                    selectedReportTypes,
                    timeStart.getValue().toString(),
                    timeEnd.getValue().toString()
            );
        } // end if
    } // end method

} // end class
