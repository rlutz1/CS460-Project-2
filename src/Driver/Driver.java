package Driver;

import Gym.Gym;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
//import ;

/**
 * idea is to start demo from this puppy, a "driver" of the demo
 * this will start the MAIN demo space.
 */

public class Driver extends Application {

    public final static String MAIN_FXML = "/fxml/gym.fxml";
    public final static String ROOT_WINDOW_NAME = "GSMS Simulator";

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
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL main = getClass().getResource(MAIN_FXML); // grab main xml

        if (main != null) { // null catch
            FXMLLoader loader = new FXMLLoader(main); // loader
            Parent root = loader.load();

            // how to grab the object associated with the fxml load (MUST BE AFTER .load()):
            //  Gym gym = loader.getController();
            //  gym.test();

            Scene scene = new Scene(root); // make primary stage root
            primaryStage.setTitle(ROOT_WINDOW_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Something went wrong: " + MAIN_FXML + " returned null on start.");
        } // end if

    } // end method

} // end class


