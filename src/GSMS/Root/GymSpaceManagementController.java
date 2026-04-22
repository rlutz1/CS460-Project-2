package GSMS.Root;

/**
 * the "root" of the back end.
 * our main process in getting all other backend components
 * started.
 */

public class GymSpaceManagementController {

    public GymSpaceManagementController() {

    } // end constructor

    /**
     * an entry point upon receipt of a request from the
     * Member or Instructor APIs to initiate the process delegation to an internal
     * component based on the request
     * @param info
     */
    public void scheduleJob(String info) {

    } // end method

    /**
     * an entry point for other
     * components to pass along a needed communication between themselves and
     * another internal component.
     * @param info
     * @param sender
     */
    public void requestJob(String info, String sender) {

    } // end method

} // end class
