package Gym;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.When;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * this is the PHYSICAL gym container for simulating
 * our actual gym space with javafx
 */
public class Gym implements Initializable {

    public Gym() {


    } // end constructor
//    @FXML // tag to signify fx:id to refer to in the
//    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void startInstructorApp(MouseEvent mouseEvent) throws IOException {
        System.out.println("Starting Instructor Application.");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/instructor-app.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Instructor Application");
        stage.setScene(new Scene(root));
        stage.show();
    } // end method

    @FXML
    private void startMemberApp(MouseEvent mouseEvent) throws IOException {
        System.out.println("Starting Member Application.");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/member-app.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Member Application");
        stage.setScene(new Scene(root));
        stage.show();
    } // end method

} // end class

// old initialize code, keeping for notes
//        rotate = new RotateTransition(Duration.millis(2500), stackPane);
//        rotate.setToAngle(360);
//        rotate.setFromAngle(0);
//        rotate.setInterpolator(Interpolator.LINEAR);
//        rotate.statusProperty().addListener(
//                (observableValue, oldValue, newValue) -> {
//                    text2.setText("Was " + oldValue + ", Now " + newValue);
//                });
//        text2.strokeProperty().bind(new When(rotate.statusProperty()
//                .isEqualTo(Animation.Status.RUNNING))
//                .then(Color.GREEN).otherwise(Color.RED));

// keeping for notes
//    @FXML
//    private void handleMouseClick(MouseEvent mouseEvent) {
//        if (rotate.getStatus().equals(Animation.Status.RUNNING)) {
//            rotate.pause();
//        } else {
//            rotate.play();
//        }
//    }



/*
package org.modernclient;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.When;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
public class FXMLController implements Initializable {
    @FXML
    private StackPane stackPane;
    @FXML
    private Text text2;
    private RotateTransition rotate;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rotate = new RotateTransition(Duration.millis(2500), stackPane);
        rotate.setToAngle(360);
        rotate.setFromAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.statusProperty().addListener(
                           (observableValue, oldValue, newValue) -> {
            text2.setText("Was " + oldValue + ", Now " + newValue);
        });
        text2.strokeProperty().bind(new When(rotate.statusProperty()
                 .isEqualTo(Animation.Status.RUNNING))
                 .then(Color.GREEN).otherwise(Color.RED));
    }
    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (rotate.getStatus().equals(Animation.Status.RUNNING)) {
            rotate.pause();
        } else {
            rotate.play();
        }
    }
}
 */