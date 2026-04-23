package Driver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * idea is to start demo from this puppy, a "driver" of the demo
 * this will start the MAIN demo space.
 */

public class Driver extends Application {

    /**
     * main method to start up the demo.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("What's up, sluts.");
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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gym.fxml"));

        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setTitle("GSMS Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    } // end method

} // end class


