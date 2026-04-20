package GSMS.Backend.DataManagement;

import java.util.List;

/**
 * class to stand as component for data manager
 */

public class DataManager {

    public DataManager() {

    } // end constructor

    /**
     * return a member or instructor’s profile information
     * requested by the requesting component
     * Optionally include what piece of information to focus on.
     * @param agentId
     * @param dataSpec
     * @return
     */
    public String getProfile(String agentId, String dataSpec) {
        return null;
    } // end method

    /**
     * return a class’s profile information requested
     * by the requesting component
     * Optionally include what piece of information to focus on.
     * @param classId
     * @param dataSpec
     * @return
     */
    public String getClass(String classId, String dataSpec) {
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

    /**
     * retrieve all alerts about a specific target
     * (member, instructor, class)
     * with an optional alertLevel and time frame
     * @param targetId
     * @param alertLevel
     * @param timeFrame
     * @return
     */
    public List<String> retrieveLogs(String targetId, String alertLevel, String timeFrame) {
        return null;
    } // end method

    /**
     *  initiate the generation of a general or
     *  specific report from the Report AI.
     *  Pull all necessary profile and notification
     *  information from the database and
     *  hand to Report AI for generation.
     *  Optionally, it can be filtered by giving a
     *  specific target ID (instructor, member, class, room)
     *  or a list of them.
     * @param targetId
     * @param reportType
     * @param timeFrame
     */
    public void generateReport(String targetId, String reportType, String timeFrame) {

    } // end method

} // end class