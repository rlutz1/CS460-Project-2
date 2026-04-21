package InstructorApplication;

/**
 * encapsulation of a driver for an instructor application
 */
public class InstructorApplication {

    private String privateId;
    private boolean isCoveredEntity;
    public InstructorApplication(String id, boolean isCoveredEntity) {
        privateId = id;
        this.isCoveredEntity = isCoveredEntity;
    } // end method

    /**
     * Organizes a list of workouts and general class
     * information for the instructor’s class to transmit
     * and send to other enrolled members
     * @param info
     */
    public void createItinerary(String info) {

    } // end method

    /**
     * Organizes a request for gathering instructor profile
     * information for general and classroom-related data.
     * @param instructorId
     */
    public void getInstructorProfile(String instructorId) {
        if (instructorId.matches(privateId)) {
            // do something
        } else {
            // unauthorized access
        }
    } // end method

} // end class
