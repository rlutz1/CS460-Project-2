package Gym.DemoManagement;


import javafx.scene.control.TextArea;

/**
 * the only point of this is to allow static access to the
 * FRONT end demo logger as is appropriate.
 */
public class DemoLogger {

    public static TextArea frontendLogger;

    /**
     * update the logger. no need for newline, added automatically at end.
     * @param str string for logger
     */
    public static void update(String str) {
        if (frontendLogger != null) {
            frontendLogger.appendText(str);
            frontendLogger.appendText("\n");
        } // end if
    } // end method

} // end class
