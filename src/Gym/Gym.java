package Gym;

import Driver.Initializer;
import GSMS.Agents.InstructorApplicationAPI;
import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Common.AgentType;
import Gym.DemoManagement.DemoManager;
import Gym.Hardware.AudioSensor;
import Gym.Hardware.Camera;
import Gym.Hardware.DoorwaySensor;
import Gym.Hardware.WearableSensors;
import InstructorApplication.InstructorApplication;
import MemberApplication.MemberApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

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

    private final DemoManager manager = new DemoManager(); // for managing demo steps
    // following maps are used for initialization between front/backend and mapping an agent id to a specific application
    public HashMap<AgentId, MemberApplication> memberApplications = new HashMap<AgentId, MemberApplication>();
    public HashMap<AgentId, InstructorApplication> instructorApplications = new HashMap<AgentId, InstructorApplication>();

    // things to setup the middle 3 use cases.
    @FXML
    public StackPane mainStage; // testing, but idea is the main stage built in scene builder
    @FXML
    public AnchorPane targetMember;
    @FXML
    public Circle targetInstructor;
    @FXML
    public HBox otherMembers;


    @FXML
    public TextArea frontendLogger; // for printing to the front end, could be handy

    @FXML
    public Button startButton; // start demo
    @FXML
    public Button nextButton; // next demo state
    @FXML
    public Button restartButton; // restart demo from beginning

    // these are for app initialization
    @FXML
    public ComboBox memberSelection;
    @FXML
    public ComboBox instructorSelection;





    // following are for testing only for now, not fxml components
    public AudioSensor audioTester;
    public Camera cameraTester;
    public WearableSensors wearableTester;
    public DoorwaySensor doorwayTester;



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
        // yield all these to manager
        manager.mainStage = this.mainStage;
        manager.targetMember = this.targetMember;
        manager.targetInstructor = this.targetInstructor;
        manager.otherMembers = this.otherMembers;

        manager.audioSensor = this.audioTester;
        manager.cameraFeed = this.cameraTester;
        manager.wearable = this.wearableTester;
    } // end method

    /*
     * =========================================================================
     * SCENARIO DRIVERS
     * =========================================================================
     */

    /**
     * start the demo sequence
     * @param mouseEvent click
     */
    @FXML
    private void start(MouseEvent mouseEvent) {
        System.out.println("Starting demo sequence.");
        appendLoggingWindow("Starting demo.");
        startButton.setDisable(true); // disable start button
        nextButton.setDisable(false);// enable next button
        restartButton.setDisable(false); // enable restart

        manager.next(); // run the manager first state
    } // end method

    /**
     * go to the next demo step in the sequence
     * @param mouseEvent click
     */
    @FXML
    private void next(MouseEvent mouseEvent) {
        System.out.println("Next demo frame.");

        manager.next(); // run the manager next state
    } // end method

    /**
     * restart the demo sequence, clearing out all before
     * @param mouseEvent click
     */
    @FXML
    private void restart(MouseEvent mouseEvent) {
        System.out.println("Restarting demo sequence from beginning.");
        appendLoggingWindow("Resetting demo.");
        startButton.setDisable(false); // enable start button
        nextButton.setDisable(true);// disable next button
        restartButton.setDisable(true); // disable restart
        // TODO: reset the main stage to first needed state
        manager.reset(); // reset the demo manager
    } // end method

    /*
     * =========================================================================
     * GENERAL FUNCTIONALITY
     * =========================================================================
     */

    /**
     * append the front-end logging window. for
     * general purpose.
     * @param str str to add
     */
    public void appendLoggingWindow(String str) {
        frontendLogger.appendText("\n" + str);
    } // end method

    /**
     * start an "instructor application".
     * @param mouseEvent click
     * @throws IOException
     */
    @FXML
    private void startInstructorApp(MouseEvent mouseEvent) {
        System.out.println("Starting Instructor Application.");
        // TODO: on close, app should really add itself back to the drop down. small detail if time.
        AgentId agentId = new AgentId((String)instructorSelection.getSelectionModel().getSelectedItem());
        instructorSelection.getItems().remove(agentId.getId());
        instructorApplications.get(agentId).start();
    } // end method

    /**
     * initialize an instructor application.
     * not starting it--but mapping it to an agent dynamically.
     * @param id
     * @return
     * @throws IOException
     */
    private InstructorApplication initInstructorApp(AgentId id, InstructorApplicationAPI api) throws IOException {
        URL main = getClass().getResource(MAIN_INSTRUCTOR_FXML); // grab main xml

        if (main != null) { // null catch

            FXMLLoader loader = new FXMLLoader(main);
            Parent root = loader.load(); // load it
            Stage stage = new Stage();
            stage.setTitle(INSTRUCTOR_WINDOW_NAME + ": Hello, " + id.getName());
            stage.setScene(new Scene(root));

            InstructorApplication instructorApp = loader.getController();
            instructorApp.setMyStage(stage);
            instructorApp.setApi(api);

            instructorSelection.getItems().add(id.getId());
            instructorSelection.setValue(id.getId());
            // TODO would like to add agent id and name to the app here

            return instructorApp;
        } else {
            System.out.println("Something went wrong: " + MAIN_INSTRUCTOR_FXML + " returned null on start.");
        } // end if
        return null;
    } // end method

    /**
     * start a "member application".
     * @param mouseEvent click
     * @throws IOException
     */
    @FXML
    private void startMemberApp(MouseEvent mouseEvent) {
        System.out.println("Starting Member Application.");
        // TODO: on close, app should really add itself back to the drop down. small detail if time.
        AgentId agentId = new AgentId((String)memberSelection.getSelectionModel().getSelectedItem());
        memberSelection.getItems().remove(agentId.getId());
        memberApplications.get(agentId).start();
    } // end method

    /**
     * initialize a member application.
     * not starting it--but mapping it to an agent dynamically.
     * @param id
     * @return
     * @throws IOException
     */
    private MemberApplication initMemberApp(AgentId id, MemberApplicationAPI api) throws IOException {
        URL main = getClass().getResource(MAIN_MEMBER_FXML); // grab main xml
        if (main != null) { // null catch
            FXMLLoader loader = new FXMLLoader(main);
            Parent root = loader.load(); // load it
            Stage stage = new Stage();
            stage.setTitle(MEMBER_WINDOW_NAME + ": Hello, " + id.getName());
            stage.setScene(new Scene(root));

            MemberApplication memberApp = loader.getController();
            memberApp.setMyStage(stage);
            memberApp.setApi(api);

            memberSelection.getItems().add(id.getId());
            memberSelection.setValue(id.getId());
            // TODO would like to add agent id and name to the app here

            return memberApp;
        } else {
            System.out.println("Something went wrong: " + MAIN_MEMBER_FXML + " returned null on start.");
        } // end if
        return null;
    } // end method

    /**
     *  gym needs to init the applications for all those people and HOLD
     *  these are kept in a map and differentiated by agent id.
     * @param initPackage
     */
    public void initAgents(
            List<Initializer> initPackage,
            MemberApplicationAPI memberApi,
            InstructorApplicationAPI instructorApi
    ) throws IOException {
        for (Initializer init : initPackage) {
            switch (init.id().getType()) {
                case AgentType.MEMBER:
                    MemberApplication memberApp = initMemberApp(init.id(), memberApi);
                    if (memberApp != null) {
                        memberApplications.put(init.id(), memberApp);
                    } else {
                        System.out.println("[GYM INIT] initMemberApp returned null!");
                    } // end if
                    break;
                case AgentType.INSTRUCTOR:
                    InstructorApplication instructorApp = initInstructorApp(init.id(), instructorApi);
                    if (instructorApp != null) {
                        instructorApplications.put(init.id(), instructorApp);
                    } else {
                        System.out.println("[GYM INIT] initInstructorApp returned null!");
                    } // end if
                    break;
                default:
                    System.out.println("[GYM INIT] Type of agent needs to be 'member' or 'instructor'.");
            } // end switch case

        } // end loop

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
