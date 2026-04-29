package MemberApplication;

public class MemberApplication {

    private String privateId;

    // when creating new member, have unique id associated to specific member
    public MemberApplication(String id) {
        privateId = id;
    } // end constructor

    /**
     * Gets the list of member-specific class
     * schedules for the day
     * @param id
     */
    public void getClassSchedule(String id) {
        if (id.matches(privateId)) {
            // do something
        } else {
            System.err.println("Unauthorized request to another account! Request Denied");
        }
    } // end method

    /**
     * Requests for a member-specific workout
     * based on workout type
     * @param id
     * @param exerciseType
     */
    public void getGeneratedWorkout(String id, String exerciseType) {
        if (id.matches(privateId)) {
            // do something
        } else {
            System.err.println("Unauthorized request to another account! Request Denied");
        }
    } // end method

    /**
     * Gets the entire general and health-fitness related
     * profile information of a member
     * @param id
     */
    public void getMemberProfile(String id) {
        if (id.matches(privateId)) {
            // do something
        } else {
            System.err.println("Unauthorized access to another account! Access Denied");
        }
    } // end method

} // end class
