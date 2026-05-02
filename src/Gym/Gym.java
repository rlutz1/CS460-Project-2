package Gym;

import Driver.AgentInitializer;
import Driver.GymInitializer;
import GSMS.Agents.InstructorApplicationAPI;
import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Common.AgentType;
import Gym.AgentGraphics.InstructorGraphic;
import Gym.AgentGraphics.MemberGraphic;
import Gym.DemoManagement.DemoManager;
import Gym.Hardware.*;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this is the PHYSICAL gym container for simulating
 * our actual gym space with javafx
 */
public class Gym {

    /* METADATA ABOUT THE GYM AND CODE NEEDS */
    public final static String MAIN_INSTRUCTOR_FXML = "/fxml/instructor-app.fxml";
    public final static String INSTRUCTOR_WINDOW_NAME = "Instructor Application";
    public final static String MAIN_MEMBER_FXML = "/fxml/member-app.fxml";
    public final static String MEMBER_WINDOW_NAME = "Member Application";

    // following maps are used for initialization between front/backend and mapping an agent id to a specific application
    public HashMap<AgentId, MemberApplication> memberApplications = new HashMap<AgentId, MemberApplication>();
    public HashMap<AgentId, InstructorApplication> instructorApplications = new HashMap<AgentId, InstructorApplication>();

    private final DemoManager manager = new DemoManager(); // for managing demo steps

    /* FRONTEND PIECES FROM FXML -- DYNAMIC OBJECTS */
    @FXML
    public StackPane mainStage; // overarching gym intricacies

    @FXML
    public AnchorPane targetMemberHouse; // the anchor pane to signify the member house for scene 1
    @FXML
    public Shape rug; // hehe rug
    @FXML
    public Shape sofa; // hehe sofa as start point
    @FXML
    public Shape houseDoorway; // hehe sofa as start point
    @FXML
    public Text houseChatBubble; // hehe for some text

    @FXML
    public AnchorPane entireGym; // the main gym space container
    @FXML
    public HBox otherMembers; // container to place other members into (not target)
    @FXML
    public Rectangle entryWay; // for placing dynamically into fxml scene
    @FXML
    public Rectangle targetInstructorStartPoint; // for placing dynamically into fxml scene

    /* FRONTEND PIECES FROM FXML -- SENSORS */
    @FXML
    public Rectangle audio1;
    @FXML
    public Circle camera1;
    @FXML
    public Circle camera2;

    // corresponding back end pieces
    // TODO: better to be one and same, but will take a moment to inject into
    //       scene builder OR put in programmatically. this is the quick choice.
    //       this is hardcoded for now.
    //       it is corresponding to the static components of the scene above.
    public AudioSensor audioSensor1;
    public Camera cameraFeed1;
    public Camera cameraFeed2;
    public List<WearableSensors> wearableSensors = new ArrayList<>(); // to init later
    public List<Hardware> allSensors; // this is muy mal, but helpful for handing off to demo manager

    /* OBJECTS CREATED DYNAMICALLY AND INSERTED TO FXML LAYOUT */
    public MemberGraphic targetMemberInGym;
    public MemberGraphic targetMemberInHouse;
    public InstructorGraphic targetInstructor;

    /* FRONTEND PIECES FROM FXML -- INTERACTIVE OBJECTS */
    @FXML
    public TextArea frontendLogger; // for printing to the front end, could be handy
    @FXML
    public Button startButton; // start demo
    @FXML
    public Button nextButton; // next demo state
    @FXML
    public Button restartButton; // restart demo from beginning
    @FXML
    public ComboBox memberSelection; // for app initialization
    @FXML
    public ComboBox instructorSelection; // for app initialization

    /* TESTING OBJECTS ONLY */
    public AudioSensor audioTester;
    public Camera cameraTester;
    public WearableSensors wearableTester;
    public DoorwaySensor doorwayTester;

    /* CONSTRUCTOR */
    public Gym() {
        audioTester = new AudioSensor(null);
        cameraTester = new Camera(null);
        wearableTester = new WearableSensors(null);
        doorwayTester = new DoorwaySensor(null);
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
        manager.entryWay = this.entryWay;
        manager.targetMemberHouse = this.targetMemberHouse;
        manager.houseChatBubble = this.houseChatBubble;
        manager.houseDoorway = this.houseDoorway;
        manager.entireGym = this.entireGym;
        // the rest wait for dynamic creation
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
            stage.setOnCloseRequest(event -> {
                instructorSelection.getItems().add(id.getId());
                instructorSelection.setValue(id.getId());
            });

            InstructorApplication instructorApp = loader.getController();
            instructorApp.setMyStage(stage);
            instructorApp.setApi(api);
            instructorApp.setId(id);

            instructorSelection.getItems().add(id.getId());
            instructorSelection.setValue(id.getId());

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
            stage.setOnCloseRequest(event -> {
                memberSelection.getItems().add(id.getId());
                memberSelection.setValue(id.getId());
            });

            MemberApplication memberApp = loader.getController();
            memberApp.setMyStage(stage);
            memberApp.setApi(api);
            memberApp.setId(id);

            memberSelection.getItems().add(id.getId());
            memberSelection.setValue(id.getId());

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
    public void initAgentApplications(
            List<AgentInitializer> initPackage,
            MemberApplicationAPI memberApi,
            InstructorApplicationAPI instructorApi
    ) throws IOException {
        for (AgentInitializer init : initPackage) {
            switch (init.id().getType()) {
                case MEMBER:
                    MemberApplication memberApp = initMemberApp(init.id(), memberApi);
                    if (memberApp != null) {
                        memberApplications.put(init.id(), memberApp);
                    } else {
                        System.out.println("[GYM INIT] initMemberApp returned null!");
                    } // end if
                    break;
                case INSTRUCTOR:
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

    /**
     * this is to be the actionable driver of initializing visual components dynamically.
     * it is to receive the SAME package as the backend to build out some custom
     * components for mimicking hardware.
     * TODO: better auto positioning of the dynamic movement of agents
     * TODO: more dynamic creation of hardware if time
     * @param initPackage
     */
    public void initOnsiteComponents(GymInitializer initPackage) {
        // insert the target member on the gym stage
        targetMemberInGym = new MemberGraphic(initPackage.targetMember().id(), Color.BLUE);
        targetMemberInGym.setLayoutX(entryWay.getLayoutX() + entryWay.getWidth());
        targetMemberInGym.setLayoutY(entryWay.getLayoutY()  + (entryWay.getHeight() / 2) - targetMemberInGym.height);
        entireGym.getChildren().add(targetMemberInGym);
        // insert target member in their house as a separate component for now instead
        // of refactoring a ton about the existing graphic.
        targetMemberInHouse = new MemberGraphic(initPackage.targetMember().id(), Color.BLUE);
        targetMemberInHouse.setLayoutX(sofa.getLayoutX() + targetMemberInHouse.width);
        targetMemberInHouse.setLayoutY(sofa.getLayoutY());
        targetMemberHouse.getChildren().add(targetMemberInHouse);
        // insert the target instructor on the gym stage
        targetInstructor= new InstructorGraphic(initPackage.targetInstructor().id(), Color.PURPLE);
        targetInstructor.setLayoutX(targetInstructorStartPoint.getLayoutX() - targetInstructor.width);
        targetInstructor.setLayoutY(targetInstructorStartPoint.getLayoutY() - targetInstructor.height);
        entireGym.getChildren().add(targetInstructor);
        // insert the general members on the gym stage
        // only the target right now. room for change, but not right now, lol
        for (AgentInitializer member : initPackage.targetClassroom().membersInClass()) {
            otherMembers.getChildren().add(new MemberGraphic(member.id(), Color.GREEN));
        } // end loop

        // initalize the needed front end "signal senders"
        audioSensor1 = new AudioSensor(initPackage.targetClassroom().roomId());
        cameraFeed1 = new Camera(initPackage.targetClassroom().roomId());
        cameraFeed2 = new Camera(initPackage.targetClassroom().roomId());

        allSensors = new ArrayList<>(List.of(
                audioSensor1,
                cameraFeed1,
                cameraFeed2
        ));

        for (AgentInitializer agent : initPackage.allAgentsOnsite()) {
            if (agent.id().getType() == AgentType.MEMBER) {
                WearableSensors wearable = new WearableSensors(agent.id());
                wearableSensors.add(wearable);
                allSensors.add(wearable);
            } // end if
        } // end loop

        // add to demo manager
        manager.targetMemberInGym = this.targetMemberInGym;
        manager.targetMemberInHouse = this.targetMemberInHouse;
        manager.targetInstructor = this.targetInstructor;
        manager.otherMembers = this.otherMembers;
        manager.targetHardware = this.allSensors;
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
