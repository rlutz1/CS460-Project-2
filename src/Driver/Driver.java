package Driver;

import GSMS.Common.AgentId;
import GSMS.Common.Metadata;
import GSMS.Root.GymSpaceManagementController;
import Gym.Gym;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import ;

/**
 * idea is to start demo from this puppy, a "driver" of the demo
 * this will start the MAIN demo space.
 */

public class Driver extends Application {

    public final static String MAIN_FXML = "/fxml/gym.fxml";
    public final static String ROOT_WINDOW_NAME = "GSMS Simulator";

    // hard-code init some people into the system.
    public final static List<Initializer> AGENTS_ONSITE = new ArrayList<>(List.of(
            new Initializer(
                    "Krex Beeble",
                    new AgentId("KBEEBLE1"),
                    "member",
                    new Metadata("Has a bad knee.")),
            new Initializer(
                    "Giorgio Tsoukalos",
                    new AgentId("GTSOUKALOS1"),
                    "member",
                    new Metadata("When his shoulder acts up, a storm's a'comin'.")),
            new Initializer(
                    "Ryan Gosling",
                    new AgentId("RGOSLING1"),
                    "member",
                    new Metadata("No preconditions--literally a perfect human.")),
            new Initializer(
                    "Jack Daniels",
                    new AgentId("JDANIELS1"),
                    "member",
                    new Metadata("Easily exhausted and suffers from dizzy spells.")),
            new Initializer(
                    "Roxanne Krause",
                    new AgentId("RKRAUSE1"),
                    "member",
                    new Metadata("Highly agitated by everything, hoping a fitness journey will help.")),
            new Initializer(
                    "Old Man Jenkins",
                    new AgentId("OMJENKINS1"),
                    "member",
                    new Metadata("Easily irritated, low energy, and basically falling apart. All around mess.")),
            new Initializer(
                    "Jane Fonda",
                    new AgentId("JFONDA1"),
                    "instructor",
                    new Metadata("All around babe instructor."))
    ));

    /**
     * main method to start up the demo.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Beginning driver...");
        launch(args);
    } // end main method

    /**
     * start the gui display with javafx
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        GymSpaceManagementController gsmc = new GymSpaceManagementController();

        URL main = getClass().getResource(MAIN_FXML); // grab main xml

        if (main != null) { // null catch
            FXMLLoader loader = new FXMLLoader(main); // loader
            Parent root = loader.load();
            // how to grab the object associated with the fxml load (MUST BE AFTER .load()):
            Gym gym = loader.getController();

            // initialize the physical simulation and the controller together.
            initDemoSpace(gym, gsmc);

            Scene scene = new Scene(root); // make primary stage root
            primaryStage.setTitle(ROOT_WINDOW_NAME);
            primaryStage.setScene(scene);

            primaryStage.show();
        } else {
            System.out.println("Something went wrong: " + MAIN_FXML + " returned null on start.");
        } // end if

    } // end method

    // TODO:
    // init members and instructors on the gsmc side of things
    // gym needs to init the applications for all those people and HOLD
    // gym needs to pass on to the gsmc the references to all the applications
    //      along with id information to tie to the corresponding backend component
    //      so that the component can "push" a notification.
    private void initDemoSpace(Gym gym, GymSpaceManagementController gsmc) {
        try {
            gym.initAgents(AGENTS_ONSITE, gsmc.getMemberApi(), gsmc.getInstructorApi()); // initialize the visual agents and apps
            gsmc.registerAgentApplications(gym.memberApplications, gym.instructorApplications, AGENTS_ONSITE); // TODO: may need to give AGENTS_ONSITE as well
        } catch (IOException ex) {
            ex.printStackTrace();
        } // end try/catch


    } // end method

} // end class




