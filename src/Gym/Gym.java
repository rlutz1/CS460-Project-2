package Gym;

import Gym.Hardware.AudioSensor;
import Gym.Hardware.Camera;
import Gym.Hardware.DoorwaySensor;
import Gym.Hardware.WearableSensors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * this is the PHYSICAL gym container for simulating
 * our actual gym space with javafx
 */
//public class Gym {
public class Gym {

    public final static String MAIN_INSTRUCTOR_FXML = "/fxml/instructor-app.fxml";
    public final static String INSTRUCTOR_WINDOW_NAME = "Instructor Application";
    public final static String MAIN_MEMBER_FXML = "/fxml/member-app.fxml";
    public final static String MEMBER_WINDOW_NAME = "Member Application";

    //    @FXML // tag to signify fx:id to refer to in the
    //    private StackPane stackPane;

    // following are for testing only for now, not fxml components
    private final AudioSensor audioTester;
    private final Camera cameraTester;
    private final WearableSensors wearableTester;
    private final DoorwaySensor doorwayTester;


    public Gym() {
        audioTester = new AudioSensor();
        cameraTester = new Camera();
        wearableTester = new WearableSensors();
        doorwayTester = new DoorwaySensor();
    } // end constructor

    /**
     * invoked at runtime. this is better compared to the
     * traditional "implements Initializable", do to JavaFX
     * injecting those fields automatically. according to
     * javadocs, injection is suggested when possible.
     *
     * a difference between this and constructor:
     *
     * In a few words: The constructor is called first,
     * then any @FXML annotated fields are populated, then initialize() is called.
     * This means the constructor does not have access to @FXML fields referring to
     * components defined in the .fxml file, while initialize() does have access to them.
     *
     * so, any code for setting up anything for ui components should be
     * done here if they are fx:id components.
     * constructor will not have access to these.
     * ref:
     * https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method
     */
    @FXML
    public void initialize() {
//        System.out.println("init");
    } // end method

    @FXML
    private void startInstructorApp(MouseEvent mouseEvent) throws IOException {
        System.out.println("Starting Instructor Application.");

        URL main = getClass().getResource(MAIN_INSTRUCTOR_FXML); // grab main xml

        if (main != null) { // null catch
            Parent root = FXMLLoader.load(main); // load it
            Stage stage = new Stage();
            stage.setTitle(INSTRUCTOR_WINDOW_NAME);
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            System.out.println("Something went wrong: " + MAIN_INSTRUCTOR_FXML + " returned null on start.");
        } // end if

    } // end method

    @FXML
    private void startMemberApp(MouseEvent mouseEvent) throws IOException {
        System.out.println("Starting Member Application.");

        URL main = getClass().getResource(MAIN_MEMBER_FXML); // grab main xml

        if (main != null) { // null catch
            Parent root = FXMLLoader.load(main); // load it
            Stage stage = new Stage();
            stage.setTitle(MEMBER_WINDOW_NAME);
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            System.out.println("Something went wrong: " + MAIN_MEMBER_FXML + " returned null on start.");
        } // end if
    } // end method

    /*
     * =========================================================================
     * TESTING FUNCTIONS
     * =========================================================================
     */

    @FXML
    private void testCameraFeed(MouseEvent mouseEvent) {
        System.out.println("Sending a camera feed signal.");
        cameraTester.sendSignal();
    } // end method

    @FXML
    private void testWearableSignal(MouseEvent mouseEvent) {
        System.out.println("Sending a wearable medical stat signal.");
        wearableTester.sendSignal();
    } // end method

    @FXML
    private void testAudioSensor(MouseEvent mouseEvent) {
        System.out.println("Sending an audio sensor signal.");
        audioTester.sendSignal();
    } // end method

    @FXML
    private void testDoorwaySensor(MouseEvent mouseEvent) {
        System.out.println("Sending a doorway sensor signal.");
        doorwayTester.sendSignal();
    } // end method

    @FXML
    private void sendAll3Signals(MouseEvent mouseEvent) {
        System.out.println("Sending camera, audio, and wearable signal.");
        cameraTester.sendSignal();
        audioTester.sendSignal();
        wearableTester.sendSignal();
    } // end method

    // testing only
    public void test() {
        System.out.println("testing hehe");
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