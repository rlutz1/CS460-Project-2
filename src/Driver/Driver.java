package Driver;

import GSMS.Common.AgentId;
import GSMS.Common.AgentType;
import GSMS.Common.Metadata;
import GSMS.Common.RoomId;
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


/**
 * idea is to start demo from this puppy, a "driver" of the demo
 * this will start the MAIN demo space.
 */

public class Driver extends Application {

    public final static String MAIN_FXML = "/fxml/gym.fxml";
    public final static String ROOT_WINDOW_NAME = "GSMS Simulator";
    public final static String TARGET_CLASSROOM_ID = "TARGET_CLASSROOM";

    // hard-code init some people into the system.
    public final static List<AgentInitializer> AGENTS_ONSITE = new ArrayList<>(List.of(
            new AgentInitializer(
                    new AgentId("KBEEBLE1", "Krex Beeble", AgentType.MEMBER),
                    new Metadata("Has a bad knee.")),
            new AgentInitializer(
                    new AgentId("GTSOUKALOS1", "Giorgio Tsoukalos", AgentType.MEMBER),
                    new Metadata("When his shoulder acts up, a storm's a'comin'.")),
            new AgentInitializer(
                    new AgentId("RGOSLING1", "Ryan Gosling", AgentType.MEMBER),
                    new Metadata("No preconditions--literally a perfect human.")),
            new AgentInitializer(
                    new AgentId("JDANIELS1", "Jack Daniels", AgentType.MEMBER),
                    new Metadata("Easily exhausted and suffers from dizzy spells.")),
            new AgentInitializer(
                    new AgentId("RKRAUSE1", "Roxanne Krause", AgentType.MEMBER),
                    new Metadata("Highly agitated by everything, hoping a fitness journey will help.")),
            new AgentInitializer(
                    new AgentId("OMJENKINS1", "Old Man Jenkins", AgentType.MEMBER),
                    new Metadata("Easily irritated, low energy, and basically falling apart. All around mess.")),
            new AgentInitializer(
                    new AgentId("JFONDA1", "Jane Fonda", AgentType.INSTRUCTOR),
                    new Metadata("All around babe instructor."))
    ));

    public final static List<ClassroomInitializer> CLASSROOMS = new ArrayList<>(List.of(
        new ClassroomInitializer(
                  new RoomId(TARGET_CLASSROOM_ID),
          1, // number of audio sensors
                  2, // number of cameras
                  1, // number of doorway sensors (very low priority functionality wise.)
                  // members in this classroom -- right now, all of them
                  // the first, which we'll treat as the target member to use,
                  // we will just give with this list since we're not gonna focus on the doorway thing for now.
                  AGENTS_ONSITE.subList(0, 6),
                  AGENTS_ONSITE.subList(6, 7) // just the one instructor
                  )
    ));

    // for initializing a fake gym
    public final static GymInitializer GYM_INIT_PACKAGE = new GymInitializer(
      AGENTS_ONSITE,
      CLASSROOMS,
      AGENTS_ONSITE.get(0), // first agent is the target that will be sent into the room.
      CLASSROOMS.get(0).instructorsInClass().get(0), // target instructor in the class room
      CLASSROOMS.get(0) // target classroom where all the action is
    );

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
            gym.initAgentApplications(AGENTS_ONSITE, gsmc.getMemberApi(), gsmc.getInstructorApi()); // initialize the visual agents and apps
            gsmc.registerAgentApplications(gym.memberApplications, gym.instructorApplications, AGENTS_ONSITE); // init the apps for responses

            // TODO: need to init the front end components in same way as backend such that i can tie the
            //       components made back there to the visual front-enders so i can "sendSignal" to the back end.
            //       i can get a single classroom id with numbers of sensors and members with ids to the back end
            //       to init, no problem.
            //       then, i'll need an access point through gsmc to grab all the component refs of a class room
            //       in order to be able to set up the front end so it can communicate.

            // initialize the frontend components for the given metadata
            gym.initOnsiteComponents(GYM_INIT_PACKAGE);
            gsmc.getEventAnalyzer().initDemEventAnalyzer(GYM_INIT_PACKAGE);

        } catch (IOException ex) {
            ex.printStackTrace();
        } // end try/catch


    } // end method

} // end class




