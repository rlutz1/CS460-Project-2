package GSMS.Backend.DataManagement;

/**
 * class to stand as component for data manager
 */

public class DataManager {

    public DataManager() {

    } // end constructor

    /**
     * return a member or instructor’s profile information
     * requested by the requesting component
     * @param agentId
     * @return
     */
    public String getProfile(String agentId) {
        return null;
    } // end method

    /**
     * return a class’s profile information requested
     * by the requesting component
     * @param agentId
     * @return
     */
    public String getClass(String agentId) {
        return null;
    } // end method

    /**
     * modify a member or instructor’s profile information as
     * requested by the requesting component
     * @param agentId
     * @param modifiction
     */
    public void modifyProfile(String agentId, String modifiction) {

    } // end method

    /**
     * modify a class’s profile information as requested
     * by the requesting component
     * @param classId
     * @param modifiction
     */
    public void modifyClass(String classId, String modifiction) {

    } // end method

} // end class