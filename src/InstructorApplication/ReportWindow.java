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


public class ReportWindow {

    public InstructorApplication parent;
    // TODO: this would make more sense contextually as check boxes.
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
            // TODO: would be nice if the window autoclosed here, but not necessary
        } // end if
    } // end method

}
